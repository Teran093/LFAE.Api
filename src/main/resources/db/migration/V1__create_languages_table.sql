-- =============================================================================
--          MASTER LANGUAGES CATALOG & TRANSLATIONS (ISO 639-1)
-- =============================================================================
CREATE TABLE languages (
   code VARCHAR(5) PRIMARY KEY, -- ISO 639-1 code
   active BOOLEAN NOT NULL DEFAULT TRUE,
   is_default BOOLEAN NOT NULL DEFAULT FALSE
);

-- Enforces that only ONE language can be marked as default at any time
CREATE UNIQUE INDEX uq_languages_single_default
    ON languages (is_default)
    WHERE is_default = TRUE;

-- Seed initial languages
INSERT INTO languages (code, active, is_default) VALUES
    ('en', TRUE, TRUE), -- English
    ('es', FALSE, FALSE), -- Spanish
    ('pt', FALSE, FALSE), -- Portuguese
    ('de', FALSE, FALSE), -- German
    ('it', FALSE, FALSE), -- Italian
    ('fr', FALSE, FALSE); -- French


CREATE TABLE language_translations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    language_code VARCHAR(5) NOT NULL,
    display_language_code VARCHAR(5) NOT NULL, -- UI language context
    name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_lang_trans_language
       FOREIGN KEY (language_code) REFERENCES languages(code) ON DELETE CASCADE,
    CONSTRAINT fk_lang_trans_display_language
       FOREIGN KEY (display_language_code) REFERENCES languages(code) ON DELETE RESTRICT,
    CONSTRAINT uq_language_translation UNIQUE (language_code, display_language_code)
);
CREATE INDEX idx_language_trans_lookup ON language_translations (language_code, display_language_code);

-- Seed English language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
    ('en', 'en', 'English'),
    ('es', 'en', 'Spanish'),
    ('pt', 'en', 'Portuguese'),
    ('de', 'en', 'German'),
    ('it', 'en', 'Italian'),
    ('fr', 'en', 'French'),;
