package com.courier.tracking.exception;

import org.springframework.http.HttpStatus;

public class GeneralAppException extends AbstractBaseException {
    public GeneralAppException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}
