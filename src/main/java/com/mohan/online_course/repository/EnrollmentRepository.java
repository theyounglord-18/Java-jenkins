package com.mohan.online_course.repository;

import com.mohan.online_course.entity.Enrollment;
import com.mohan.online_course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Optional<Enrollment> findByStudentIdAndCourseId(Integer userId, Integer courseId);
    List<Enrollment> findAllByStudent(User student);
}