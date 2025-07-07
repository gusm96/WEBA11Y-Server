package com.weba11y.weba11y_server.dto;

import com.weba11y.weba11y_server.domain.AccessibilityViolation;
import com.weba11y.weba11y_server.enums.AccessibilityViolationStatus;
import com.weba11y.weba11y_server.enums.InspectionItems;
import lombok.*;
import org.jsoup.nodes.Element;

import java.time.LocalDateTime;

import static com.weba11y.weba11y_server.enums.AccessibilityViolationStatus.PENDING;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessibilityViolationDto {

    private Long id;

    private String inspectionUrl;

    private InspectionItems inspectionItem;

    private String description;

    private String codeLine;

    private AccessibilityViolationStatus status;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public AccessibilityViolation toEntity(String inspectionUrl) {
        return AccessibilityViolation.builder()
                .inspectionUrl(inspectionUrl)
                .inspectionItem(this.inspectionItem.getName())
                .importance(this.inspectionItem.getImportance().name())
                .assessmentLevel(this.inspectionItem.getAssessmentLevel().name())
                .description(this.description)
                .codeLine(this.codeLine)
                .status(this.status.getDescription())
                .build();
    }

    public static AccessibilityViolationDto createDto(Element element, InspectionItems inspectionItem) {
        String codeLine = element.outerHtml();
        if (codeLine.length() > 255) {
            codeLine = codeLine.substring(0, 255); // 255자까지 자르기
        }

        return AccessibilityViolationDto.builder()
                .inspectionItem(inspectionItem)
                .description(inspectionItem.getDescription())
                .codeLine(codeLine)
                .status(PENDING)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
