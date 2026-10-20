-- =============================================================================
--      MASTER COUNTRIES CATALOG & TRANSLATIONS (ISO 3166-1 alpha-2)
-- =============================================================================
CREATE TABLE countries (
    code VARCHAR(2) PRIMARY KEY -- ISO 3166-1 alpha-2 code
);

-- Seed initial ISO countries & translations
INSERT INTO countries (code) VALUES ('ES'), ('GB'), ('US'), ('FR');

CREATE TABLE country_translations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_code VARCHAR(2) NOT NULL,
    language_code VARCHAR(5) NOT NULL,
    name VARCHAR(100) NOT NULL,
    CONSTRAINT fk_country_trans_country
      FOREIGN KEY (country_code) REFERENCES countries(code) ON DELETE CASCADE,
    CONSTRAINT fk_country_trans_language
      FOREIGN KEY (language_code) REFERENCES languages(code) ON DELETE RESTRICT,
    CONSTRAINT uq_country_translation UNIQUE (country_code, language_code)
);
CREATE INDEX idx_country_trans_lookup ON country_translations (country_code, language_code);

INSERT INTO country_translations (country_code, language_code, name) VALUES
     ('ES', 'es', 'España'), ('ES', 'en', 'Spain'),
     ('GB', 'es', 'Reino Unido'), ('GB', 'en', 'United Kingdom'),
     ('US', 'es', 'Estados Unidos'), ('US', 'en', 'United States'),
     ('FR', 'es', 'Francia'), ('FR', 'en', 'France');