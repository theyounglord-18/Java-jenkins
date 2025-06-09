package com.mohan.online_course.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Integer id;
    private String title;
    private String description;
    private Double price;
}