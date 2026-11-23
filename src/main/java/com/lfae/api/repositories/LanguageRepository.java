package com.lfae.api.repositories;

import com.lfae.api.models.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, String> {

    @Query("select l from Language l where l.isDefault = true")
    Optional<Language> findDefault();
}