package com.mohan.online_course.exception;

public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException(String message) {
        super(message);
    }
}