package com.practice.spring_mvc.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Dữ liệu nhập vào không hợp lệ!");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}