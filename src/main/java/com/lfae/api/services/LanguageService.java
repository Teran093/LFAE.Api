package com.lfae.api.services;

import com.lfae.api.exceptions.BusinessValidationException;
import com.lfae.api.exceptions.ResourceNotFoundException;
import com.lfae.api.models.dto.LanguageDto;
import com.lfae.api.models.dto.LanguageUpdateRequest;
import com.lfae.api.models.entities.Language;
import com.lfae.api.repositories.LanguageRepository;
import com.lfae.api.repositories.LanguageTranslationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageTranslationRepository translationRepository;

    public LanguageService(LanguageRepository languageRepository,
                           LanguageTranslationRepository translationRepository) {
        this.languageRepository = languageRepository;
        this.translationRepository = translationRepository;
    }

    public List<LanguageDto> findAll(String lang, boolean onlyActives) {
        return translationRepository.findAllByDisplayLanguage(resolveDisplayLanguage(lang), onlyActives);
    }

    @Transactional
    public void update(String code, LanguageUpdateRequest request) {
        Language language = languageRepository.findById(normalize(code))
                .orElseThrow(() -> new ResourceNotFoundException("Language", code));

        boolean active = request.active();
        boolean makeDefault = request.isDefault();

        if (makeDefault && !active) {
            throw new BusinessValidationException(
                    "The default language must be active", "DEFAULT_LANGUAGE_MUST_BE_ACTIVE");
        }
        if (language.isDefault() && !makeDefault) {
            throw new BusinessValidationException(
                    "There must always be a default language. Mark another one as default instead.",
                    "DEFAULT_LANGUAGE_REQUIRED");
        }

        // Only one default is allowed (unique index): unset the previous one first and flush.
        if (makeDefault && !language.isDefault()) {
            languageRepository.findDefault().ifPresent(previous -> previous.setDefault(false));
            languageRepository.flush();
        }

        language.setActive(active);
        language.setDefault(makeDefault);
    }

    /** Uses the requested language if it exists and is active; otherwise the default one. */
    private String resolveDisplayLanguage(String lang) {
        if (lang != null && !lang.isBlank()) {
            String code = normalize(lang);
            if (languageRepository.findById(code).filter(Language::isActive).isPresent()) {
                return code;
            }
        }
        return languageRepository.findDefault()
                .map(Language::getCode)
                .orElseThrow(() -> new IllegalStateException("No default language configured"));
    }

    /** "ES", " es " or "es-ES" -> "es" */
    private String normalize(String code) {
        String value = code.trim().toLowerCase(Locale.ROOT);
        return value.length() > 2 ? value.substring(0, 2) : value;
    }
}