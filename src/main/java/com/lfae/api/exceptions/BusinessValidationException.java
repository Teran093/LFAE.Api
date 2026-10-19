package com.lfae.api.exceptions;

import org.springframework.http.HttpStatus;

public class BusinessValidationException extends AppException {
    public BusinessValidationException(String message, String errorCode) {
        super(message, errorCode, HttpStatus.BAD_REQUEST);

    }
}
