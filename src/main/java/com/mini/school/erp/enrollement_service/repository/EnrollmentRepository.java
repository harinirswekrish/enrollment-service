package com.mini.school.erp.enrollement_service.repository;

import com.mini.school.erp.enrollement_service.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<Enrollment> findByStudentIdAndStatus(Long studentId, String status);

}