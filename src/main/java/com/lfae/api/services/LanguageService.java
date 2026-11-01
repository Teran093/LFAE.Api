package com.lfae.api.services;

import com.lfae.api.models.dto.LanguageDto;
import com.lfae.api.models.entities.Language;
import com.lfae.api.repositories.LanguageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LanguageService {
    public final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> findAllLanguage(boolean onlyActives) {
        if (onlyActives) {
            return languageRepository.findByActiveTrue();
        }
        return languageRepository.findAll();
    }

    public Language findLanguage(String languageCode) {
        return languageRepository.findById(languageCode).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Language not found: " + languageCode));
    }

    @Transactional
    public void removeLanguage(String id) {

    }

    @Transactional
    public LanguageDto saveLanguage(LanguageDto dto) {
        Language language = languageRepository.save(toEntity(dto));
        return toDto(language);
    }

    public LanguageDto toDto(Language language) {
        return new LanguageDto(
                language.getCode(),
                language.isActive(),
                language.isDefault()
        );
    }

    public Language toEntity(LanguageDto dto) {
        Language language = new Language();
        language.setCode(dto.code());
        language.setActive(dto.active());
        language.setDefault(dto.isDefault());
        return language;
    }
}
