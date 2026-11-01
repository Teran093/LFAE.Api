package com.lfae.api.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "language_translations")
public class LanguageTranslation {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "language_code", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "display_language_code", nullable = false)
    private Language displayLanguage;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    protected LanguageTranslation() {
    }

    public UUID getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getDisplayLanguage() {
        return displayLanguage;
    }

    public void setDisplayLanguage(Language displayLanguage) {
        this.displayLanguage = displayLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
