package com.lfae.api.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Read model: the name is resolved from language_translations for the requested display language. */
public record LanguageDto(
        String code,
        String name,
        boolean active,
        @JsonProperty("isDefault")
        boolean isDefault
) {
}