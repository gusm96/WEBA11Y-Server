package com.weba11y.weba11y_server.domain;

import com.weba11y.weba11y_server.dto.AccessibilityViolationDto;
import com.weba11y.weba11y_server.enums.AccessibilityViolationStatus;
import com.weba11y.weba11y_server.enums.InspectionItems;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "accessibility_violation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessibilityViolation {

    @Id
    @Column("av_id")
    private Long id;

    @Column("inspection_url")
    private String inspectionUrl;

    @Column("inspection_item")
    private String inspectionItem;

    @Column("importance")
    private String importance;

    @Column("assessment_level")
    private String assessmentLevel;

    @Column("description")
    private String description;

    @Column("code_line")
    private String codeLine;

    @Column("status")
    private String status;

    @Column("create_date")
    private String createDate;

    @Column("update_date")
    private String updateDate;

    @Column("delete_date")
    private String deleteDate;

    @Builder
    public AccessibilityViolation(String inspectionUrl,
                                  String inspectionItem,
                                  String importance,
                                  String assessmentLevel,
                                  String description,
                                  String codeLine,
                                  String status) {
        this.inspectionUrl = inspectionUrl;
        this.inspectionItem = inspectionItem;
        this.importance = importance;
        this.assessmentLevel = assessmentLevel;
        this.description = description;
        this.codeLine = codeLine;
        this.status = status;
    }

    public AccessibilityViolationDto toDto() {
        return AccessibilityViolationDto.builder()
                .inspectionUrl(this.inspectionUrl)
                .inspectionItem(InspectionItems.valueOf(this.inspectionItem))
                .description(this.description)
                .codeLine(this.codeLine)
                .status(AccessibilityViolationStatus.valueOf(this.status))
                .createDate(LocalDateTime.parse(this.createDate))
                .updateDate(LocalDateTime.parse(this.updateDate))
                .build();
    }
}
