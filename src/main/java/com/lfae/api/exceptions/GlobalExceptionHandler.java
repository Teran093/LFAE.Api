package com.lfae.api.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Captures operational exceptions from the application (ResourceNotFoundException, etc.)
     */
    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex) {
        log.warn("Operational error: [{}] - {}", ex.getErrorCode(), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        problemDetail.setTitle(ex.getErrorCode());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("code", ex.getErrorCode());
        return problemDetail;
    }

    /**
     * Captures validation errors from Spring (@Valid / DTOs)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handlerValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation exception in request payload: {}", ex.getBindingResult().getObjectName());

        Map<String, String> fieldErrors = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.put(err.getField(), err.getDefaultMessage())
        );

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Validation failed for request"
        );
        problemDetail.setTitle("VALIDATION_ERROR");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("code", "VALIDATION_ERROR");
        problemDetail.setProperty("fieldErrors", fieldErrors);
        return problemDetail;
    }

    /**
     * Captures any other non-controlled exception (Bugs, NullPointes, etc)
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handlerUnexpectedException(Exception ex) {
        log.error("Unexpected internal error: ", ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected server error occurred."
        );
        problemDetail.setTitle("INTERNAL_SERVER_ERROR");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("code", "INTERNAL_SERVER_ERROR");
        return problemDetail;
    }
}
