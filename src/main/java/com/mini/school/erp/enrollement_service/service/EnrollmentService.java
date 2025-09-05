package com.mini.school.erp.enrollement_service.service;

import com.mini.school.erp.enrollement_service.dto.EnrollmentCreateRequest;
import com.mini.school.erp.enrollement_service.dto.EnrollmentResponse;
import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse enrollStudent(EnrollmentCreateRequest request);
    List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId);
    EnrollmentResponse revertEnrollment(Long studentId, Long courseId);
}