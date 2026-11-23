package com.lfae.api.controllers;

import com.lfae.api.models.dto.LanguageDto;
import com.lfae.api.models.dto.LanguageUpdateRequest;
import com.lfae.api.services.LanguageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public List<LanguageDto> getLanguages(
            @RequestParam(required = false) String lang,
            @RequestParam(defaultValue = "true") boolean onlyActives) {
        return languageService.findAll(lang, onlyActives);
    }

    @PutMapping("/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String code, @Valid @RequestBody LanguageUpdateRequest request) {
        languageService.update(code, request);
    }
}