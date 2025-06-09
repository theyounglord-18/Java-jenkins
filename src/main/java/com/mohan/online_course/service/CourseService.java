package com.mohan.online_course.service;

import com.mohan.online_course.dto.*;
import com.mohan.online_course.entity.Course;
import com.mohan.online_course.exception.ResourceNotFoundException;
import com.mohan.online_course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseResponse createCourse(CourseRequest request) {
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        courseRepository.save(course);

        return mapToResponse(course);
    }

    public CourseResponse updateCourse(Integer id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        courseRepository.save(course);

        return mapToResponse(course);
    }

    public void deleteCourse(Integer id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        courseRepository.delete(course);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CourseResponse mapToResponse(Course course) {
        return new CourseResponse(course.getId(), course.getTitle(), course.getDescription(), course.getPrice());
    }
}