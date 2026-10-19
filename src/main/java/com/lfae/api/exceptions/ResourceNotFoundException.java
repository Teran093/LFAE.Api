package com.lfae.api.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AppException {
    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(
                String.format("%s not found with id: %s", resourceName, identifier),
                "RESOURCE_NOT_FOUND",
                HttpStatus.NOT_FOUND
        );
    }
}
