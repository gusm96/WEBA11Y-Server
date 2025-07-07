package com.weba11y.weba11y_server.service;

import com.weba11y.weba11y_server.dto.AccessibilityViolationDto;
import reactor.core.publisher.Flux;

public interface AccessibilityCheckerService {

    Flux<AccessibilityViolationDto> runChecks(String url);
}
