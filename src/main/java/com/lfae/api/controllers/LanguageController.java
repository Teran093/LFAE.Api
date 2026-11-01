package com.lfae.api.controllers;

import com.lfae.api.models.dto.LanguageDto;
import com.lfae.api.repositories.LanguageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @GetMapping
    public List<LanguageDto> getLanguages(@RequestParam(defaultValue = "true") boolean onlyActives) {
        return
    }
}
