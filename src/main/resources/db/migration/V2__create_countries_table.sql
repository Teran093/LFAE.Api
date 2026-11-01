-- =============================================================================
--      MASTER COUNTRIES CATALOG & TRANSLATIONS (ISO 3166-1 alpha-2)
-- =============================================================================
CREATE TABLE countries
(
    code VARCHAR(2) PRIMARY KEY, -- ISO 3166-1 alpha-2 code
    CONSTRAINT chk_country_code_format
            CHECK (code ~ '^[A-Z]{2}$')
);

-- Seed initial ISO countries & translations
INSERT INTO countries (code)
VALUES ('ES'),
       ('GB'),
       ('US'),
       ('FR'),
       ('PT'),
       ('DE'),
       ('IT');

CREATE TABLE country_translations
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    country_code  VARCHAR(2)   NOT NULL,
    language_code VARCHAR(5)   NOT NULL,
    name          VARCHAR(100) NOT NULL,
    CONSTRAINT fk_country_trans_country
        FOREIGN KEY (country_code) REFERENCES countries (code) ON DELETE CASCADE,
    CONSTRAINT fk_country_trans_language
        FOREIGN KEY (language_code) REFERENCES languages (code) ON DELETE RESTRICT,
    CONSTRAINT uq_country_translation UNIQUE (country_code, language_code),
    CONSTRAINT chk_language_code_format
        CHECK (language_code ~ '^[a-z]{2}(-[A-Z]{2})?$')
);

INSERT INTO country_translations (country_code, language_code, name)
VALUES ('GB', 'en', 'United Kingdom'),
       ('GB', 'es', 'Reino Unido'),
       ('GB', 'pt', 'Reino Unido'),
       ('GB', 'de', 'Vereinigtes Königreich'),
       ('GB', 'it', 'Regno Unito'),
       ('GB', 'fr', 'Royaume-Uni'),

       ('ES', 'en', 'Spain'),
       ('ES', 'es', 'España'),
       ('ES', 'pt', 'Espanha'),
       ('ES', 'de', 'Spanien'),
       ('ES', 'it', 'Spagna'),
       ('ES', 'fr', 'Espagne'),

       ('PT', 'en', 'Portugal'),
       ('PT', 'es', 'Portugal'),
       ('PT', 'pt', 'Portugal'),
       ('PT', 'de', 'Portugal'),
       ('PT', 'it', 'Portogallo'),
       ('PT', 'fr', 'Portugal'),

       ('DE', 'en', 'Germany'),
       ('DE', 'es', 'Alemania'),
       ('DE', 'pt', 'Alemanha'),
       ('DE', 'de', 'Deutschland'),
       ('DE', 'it', 'Germania'),
       ('DE', 'fr', 'Allemagne'),

       ('IT', 'en', 'Italy'),
       ('IT', 'es', 'Italia'),
       ('IT', 'pt', 'Itália'),
       ('IT', 'de', 'Italien'),
       ('IT', 'it', 'Italia'),
       ('IT', 'fr', 'Italie'),

       ('FR', 'en', 'France'),
       ('FR', 'es', 'Francia'),
       ('FR', 'pt', 'França'),
       ('FR', 'de', 'Frankreich'),
       ('FR', 'it', 'Francia'),
       ('FR', 'fr', 'France');