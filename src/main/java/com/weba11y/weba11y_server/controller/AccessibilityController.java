package com.weba11y.weba11y_server.controller;


import com.weba11y.weba11y_server.dto.AccessibilityViolationDto;
import com.weba11y.weba11y_server.service.AccessibilityCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AccessibilityController {
    private final AccessibilityCheckerService checkerService;
    @GetMapping(value = "/accessibility-check", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccessibilityViolationDto> checkAccessibility(@RequestParam("url") String url) {
        return checkerService.runChecks(url);
    }
}
