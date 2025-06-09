package com.mohan.online_course.service;

import com.mohan.online_course.dto.*;
import com.mohan.online_course.entity.*;
import com.mohan.online_course.exception.AlreadyEnrolledException;
import com.mohan.online_course.exception.ResourceNotFoundException;
import com.mohan.online_course.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public String enrollInCourse(EnrollmentRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (enrollmentRepository.findByStudentIdAndCourseId(student.getId(), course.getId()).isPresent()) {
            throw new AlreadyEnrolledException("Already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .build();

        enrollmentRepository.save(enrollment);
        return "Enrollment successful";
    }

    public List<EnrollmentResponse> getMyEnrollments() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Enrollment> enrollments = enrollmentRepository.findAllByStudent(student);

        return enrollments.stream()
                .map(e -> new EnrollmentResponse(
                        e.getCourse().getTitle(),
                        e.getCourse().getDescription(),
                        e.getCourse().getPrice(),
                        e.getEnrolledAt()
                ))
                .collect(Collectors.toList());
    }
}