package com.weba11y.weba11y_server.repository;

import com.weba11y.weba11y_server.domain.AccessibilityViolation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AccessibilityViolationRepository extends ReactiveCrudRepository<AccessibilityViolation, Long> {
}
