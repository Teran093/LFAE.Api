package com.lfae.api.models.dto;

import jakarta.validation.constraints.NotNull;

 public record LanguageUpdateRequest(
        @NotNull Boolean active,
        @NotNull Boolean isDefault
) {
}

