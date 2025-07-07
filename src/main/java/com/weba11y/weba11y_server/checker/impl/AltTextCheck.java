package com.weba11y.weba11y_server.checker.impl;

import com.weba11y.weba11y_server.checker.AccessibilityChecker;
import com.weba11y.weba11y_server.dto.AccessibilityViolationDto;
import com.weba11y.weba11y_server.enums.InspectionItems;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import static com.weba11y.weba11y_server.enums.InspectionItems.ALT_TEXT;

@Slf4j
@Component
public class AltTextCheck implements AccessibilityChecker {

    @Override
    public Flux<AccessibilityViolationDto> check(Document doc) {
        return Flux.fromIterable(ALT_TEXT.getTargetTags())
                .flatMap(tag -> Flux.fromIterable(doc.select(tag)))
                .filter(element -> !element.hasAttr("alt") || element.attr("alt").isEmpty())
                .map(element -> {
                    log.debug("[AltTextCheck] ALT 누락 요소 발견: <{}>", element.tagName());
                    return AccessibilityViolationDto.createDto(element, ALT_TEXT);
                });
    }
}
