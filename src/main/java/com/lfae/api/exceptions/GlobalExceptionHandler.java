package com.lfae.api.exceptions;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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

    /** DB constraint violations that slipped past the business checks (races, future FKs...). */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrity(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation: {}", ex.getMostSpecificCause().getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, "The operation violates a data integrity constraint."
        );
        problemDetail.setTitle("DATA_INTEGRITY_VIOLATION");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("code", "DATA_INTEGRITY_VIOLATION");
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

    /** Bean Validation errors (@Valid on @RequestBody). Overrides the Spring default. */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        log.warn("Validation exception in request payload: {}", ex.getBindingResult().getObjectName());

        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                fieldErrors.putIfAbsent(err.getField(), err.getDefaultMessage())
        );

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Validation failed for request"
        );
        problemDetail.setTitle("VALIDATION_ERROR");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("code", "VALIDATION_ERROR");
        problemDetail.setProperty("fieldErrors", fieldErrors);
        return ResponseEntity.status(status).headers(headers).body(problemDetail);
    }

    /** Adds timestamp/code to the rest of Spring MVC errors (400 malformed JSON, 405, 415...). */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex, Object body, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode statusCode, @NonNull WebRequest request) {
        ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);
        if (response != null && response.getBody() instanceof ProblemDetail pd) {
            HttpStatus resolved = HttpStatus.resolve(statusCode.value());
            pd.setProperty("timestamp", Instant.now());
            pd.setProperty("code", resolved != null ? resolved.name() : "ERROR");
        }
        return response;
    }

    @ExceptionHandler(AccessDeniedException.class)   // org.springframework.security.access
    public ProblemDetail handleAccessDenied(AccessDeniedException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN, "You do not have permission to perform this action.");
        pd.setTitle("ACCESS_DENIED");
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("code", "ACCESS_DENIED");
        return pd;
    }

    @ExceptionHandler(AuthenticationException.class) // org.springframework.security.core
    public ProblemDetail handleAuthentication(AuthenticationException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, "Authentication is required.");
        pd.setTitle("UNAUTHORIZED");
        pd.setProperty("timestamp", Instant.now());
        pd.setProperty("code", "UNAUTHORIZED");
        return pd;
    }
}
