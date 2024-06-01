package com.courier.tracking.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractBaseException extends RuntimeException{
    private final String message;
    private final int httpStatus;

    public AbstractBaseException(String message, int httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
