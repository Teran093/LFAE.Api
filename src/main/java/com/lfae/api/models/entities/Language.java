package com.lfae.api.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "languages")
public class Language {
    /**
     * ISO 639-1 language code used as primary key.
     */
    @Id
    @Column(name = "code", length = 2, nullable = false, updatable = false)
    private String code;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    public Language() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
