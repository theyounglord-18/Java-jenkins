package com.mohan.online_course.dto;

import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponse {
    private String courseTitle;
    private String courseDescription;
    private Double price;
    private LocalDateTime enrolledAt;
}