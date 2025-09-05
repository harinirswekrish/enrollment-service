package com.mini.school.erp.enrollement_service.validation.business;

import com.mini.school.erp.enrollement_service.entity.Enrollment;
import com.mini.school.erp.enrollement_service.exception.BusinessValidationException;
import com.mini.school.erp.enrollement_service.exception.errormessages.ErrorMessages;
import com.mini.school.erp.enrollement_service.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessValidation {

    private final EnrollmentRepository enrollmentRepository;

    public Enrollment validateEnrollmentExists(Long studentId, Long courseId) {
        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new BusinessValidationException(ErrorMessages.ENROLLMENT_NOT_FOUND + studentId + ErrorMessages.COURSE_ID + courseId));
    }
}
