package com.courier.tracking.exception;

import com.courier.tracking.model.response.CourierBaseResponse;
import com.courier.tracking.model.response.Response;
import com.courier.tracking.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CourierBaseResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        Response errResponse = ResponseUtil.createErrorResponse(ex,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(new CourierBaseResponse<>(errResponse,null,false));
    }

    @ExceptionHandler(GeneralAppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CourierBaseResponse<Void>> handleGeneralException(ResourceNotFoundException ex, HttpServletRequest request) {
        Response errResponse = ResponseUtil.createErrorResponse(ex,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(new CourierBaseResponse<>(errResponse,null,false));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CourierBaseResponse<Void>> handleException(ResourceNotFoundException ex, HttpServletRequest request) {
        Response errResponse = ResponseUtil.createErrorResponse(ex,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.OK).body(new CourierBaseResponse<>(errResponse,null,false));
    }

}
