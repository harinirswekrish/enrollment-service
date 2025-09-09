package com.mini.school.erp.enrollement_service.controller;

import com.mini.school.erp.enrollement_service.dto.EnrollmentCreateRequest;
import com.mini.school.erp.enrollement_service.dto.EnrollmentResponse;
import com.mini.school.erp.enrollement_service.service.EnrollmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public EnrollmentResponse enrollStudent(@Valid @RequestBody EnrollmentCreateRequest request) {
        return enrollmentService.enrollStudent(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/student/{studentId}")
    public List<EnrollmentResponse> getEnrollmentsByStudent(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/revert/{studentId}/{courseId}")
    public EnrollmentResponse revertEnrollment(@PathVariable Long studentId, @PathVariable Long courseId) {
        return enrollmentService.revertEnrollment(studentId, courseId);
    }
}