package com.lfae.api.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Instant creationDate,

        @NotBlank @Size(max = 150)
        String name,
        @Size(max = 500)
        String description,
        @NotBlank @Size(max = 100)
        String country,
        @Size(max = 100)
        String city,
        @Size(max = 255)
        String address,

        // TODO: Make GPS Position properties

        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean active
) {
}
