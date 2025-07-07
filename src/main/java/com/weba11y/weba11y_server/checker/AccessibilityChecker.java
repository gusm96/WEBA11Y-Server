package com.weba11y.weba11y_server.checker;


import com.weba11y.weba11y_server.dto.AccessibilityViolationDto;
import org.jsoup.nodes.Document;
import reactor.core.publisher.Flux;

public interface AccessibilityChecker {
    Flux<AccessibilityViolationDto> check(Document doc);
}