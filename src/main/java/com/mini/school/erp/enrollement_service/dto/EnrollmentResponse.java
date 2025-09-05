package com.mini.school.erp.enrollement_service.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnrollmentResponse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private LocalDateTime enrolledAt;
    private String status;
}
