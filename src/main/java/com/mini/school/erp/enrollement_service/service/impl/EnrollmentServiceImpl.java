package com.mini.school.erp.enrollement_service.service.impl;

import com.mini.school.erp.enrollement_service.dto.EnrollmentCreateRequest;
import com.mini.school.erp.enrollement_service.dto.EnrollmentResponse;
import com.mini.school.erp.enrollement_service.entity.Enrollment;
import com.mini.school.erp.enrollement_service.exception.BusinessValidationException;
import com.mini.school.erp.enrollement_service.exception.errormessages.ErrorMessages;
import com.mini.school.erp.enrollement_service.repository.EnrollmentRepository;
import com.mini.school.erp.enrollement_service.service.EnrollmentService;
import com.mini.school.erp.enrollement_service.utils.AppConstant;
import com.mini.school.erp.enrollement_service.validation.business.BusinessValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final BusinessValidation businessValidation;

    @Override
    public EnrollmentResponse enrollStudent(EnrollmentCreateRequest request) {
        return enrollmentRepository.findByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())
                .map(existing -> {
                    if (AppConstant.ACTIVE.equals(existing.getStatus())) {
                        throw new BusinessValidationException(ErrorMessages.STUDENT_ALREADY_ENROLLED);
                    }
                    // Reactivate if reverted
                    existing.setStatus(AppConstant.ACTIVE);
                    existing.setEnrolledAt(java.time.LocalDateTime.now());
                    enrollmentRepository.save(existing);
                    return mapToResponse(existing);
                })
                .orElseGet(() -> {
                    Enrollment newEnrollment = Enrollment.builder()
                            .studentId(request.getStudentId())
                            .courseId(request.getCourseId())
                            .status(AppConstant.ACTIVE)
                            .build();
                    enrollmentRepository.save(newEnrollment);
                    return mapToResponse(newEnrollment);
                });
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentIdAndStatus(studentId, AppConstant.ACTIVE).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponse revertEnrollment(Long studentId, Long courseId) {
        Enrollment enrollment = businessValidation.validateEnrollmentExists(studentId, courseId);

        if (AppConstant.REVERTED.equals(enrollment.getStatus())) {
            throw new BusinessValidationException(ErrorMessages.ENROLLMENT_ALREADY_REVERTED);
        }

        enrollment.setStatus(AppConstant.REVERTED);
        enrollmentRepository.save(enrollment);

        return mapToResponse(enrollment);
    }

    private EnrollmentResponse mapToResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudentId())
                .courseId(enrollment.getCourseId())
                .enrolledAt(enrollment.getEnrolledAt())
                .status(enrollment.getStatus())
                .build();
    }
}

