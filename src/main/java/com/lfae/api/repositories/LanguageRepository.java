package com.lfae.api.repositories;

import com.lfae.api.models.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, String> {
    List<Language> findByActiveTrue();
}
