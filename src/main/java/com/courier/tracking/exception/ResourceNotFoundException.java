package com.courier.tracking.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractBaseException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
