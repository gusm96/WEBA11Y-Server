package com.weba11y.weba11y_server.service.impl;

import com.weba11y.weba11y_server.checker.AccessibilityChecker;
import com.weba11y.weba11y_server.domain.AccessibilityViolation;
import com.weba11y.weba11y_server.dto.AccessibilityViolationDto;
import com.weba11y.weba11y_server.repository.AccessibilityViolationRepository;
import com.weba11y.weba11y_server.service.AccessibilityCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.weba11y.weba11y_server.util.ListUtil.partitionList;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessibilityCheckerServiceImpl implements AccessibilityCheckerService {

    private final List<AccessibilityChecker> accessibilityCheckers;
    private final AccessibilityViolationRepository accessibilityViolationRepository;
    private final WebClient webClient;
    private static final int BATCH_SIZE = 100;


    @Transactional
    @Override
    public Flux<AccessibilityViolationDto> runChecks(String inspectionUrl) {
        return fetchHtml(inspectionUrl)
                .map(Jsoup::parse)
                .flatMapMany(doc -> Flux.fromIterable(accessibilityCheckers)
                        .flatMap(checker -> checker.check(doc))
                )
                .collectList()
                .flatMapMany(resultDtoList -> {
                    List<AccessibilityViolation> entities = resultDtoList.stream()
                            .map(dto -> dto.toEntity(inspectionUrl))
                            .toList();

                    return Flux.fromIterable(partitionList(entities, BATCH_SIZE)) // 배치 단위로 나눔
                            .concatMap(batch -> accessibilityViolationRepository.saveAll(batch).then()) // 순차 저장
                            .thenMany(Flux.fromIterable(resultDtoList)); // 저장 후 결과 리턴
                });
    }

    private Mono<String> fetchHtml(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("웹 페이지 가져오기 실패: {}", e.getMessage()));
    }
}
