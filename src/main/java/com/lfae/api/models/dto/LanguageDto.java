package com.lfae.api.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LanguageDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String code,
        boolean active,
        boolean isDefault
) {
}
