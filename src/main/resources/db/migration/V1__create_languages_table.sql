-- =============================================================================
--          MASTER LANGUAGES CATALOG & TRANSLATIONS (ISO 639-1)
-- =============================================================================
CREATE TABLE languages (
   code VARCHAR(2) PRIMARY KEY, -- ISO 639-1 code
   active BOOLEAN NOT NULL DEFAULT TRUE,
   is_default BOOLEAN NOT NULL DEFAULT FALSE,
   CONSTRAINT chk_language_code_format
       CHECK (code ~ '^[a-z]{2}$')
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
    language_code VARCHAR(2) NOT NULL,
    display_language_code VARCHAR(2) NOT NULL, -- UI language context
    name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_lang_trans_language
       FOREIGN KEY (language_code) REFERENCES languages(code) ON DELETE CASCADE,
    CONSTRAINT fk_lang_trans_display_language
       FOREIGN KEY (display_language_code) REFERENCES languages(code) ON DELETE RESTRICT,
    CONSTRAINT uq_language_translation UNIQUE (language_code, display_language_code),
    CONSTRAINT chk_lang_trans_language_code
        CHECK (language_code ~ '^[a-z]{2}$'),
    CONSTRAINT chk_lang_trans_display_code
        CHECK (display_language_code ~ '^[a-z]{2}$'),
    CONSTRAINT chk_lang_trans_name_not_empty
        CHECK (LENGTH(TRIM(name)) > 0)
);

-- Seed English language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
    ('en', 'en', 'English'),
    ('es', 'en', 'Spanish'),
    ('pt', 'en', 'Portuguese'),
    ('de', 'en', 'German'),
    ('it', 'en', 'Italian'),
    ('fr', 'en', 'French');

-- Seed Spanish language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
   ('en', 'es', 'Inglés'),
   ('es', 'es', 'Español'),
   ('pt', 'es', 'Portugués'),
   ('de', 'es', 'Alemán'),
   ('it', 'es', 'Italiano'),
   ('fr', 'es', 'Francés');

-- Seed Portuguese language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
   ('en', 'pt', 'Inglês'),
   ('es', 'pt', 'Espanhol'),
   ('pt', 'pt', 'Português'),
   ('de', 'pt', 'Alemão'),
   ('it', 'pt', 'Italiano'),
   ('fr', 'pt', 'Francês');

-- Seed German language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
   ('en', 'de', 'Englisch'),
   ('es', 'de', 'Spanisch'),
   ('pt', 'de', 'Portugiesisch'),
   ('de', 'de', 'Deutsch'),
   ('it', 'de', 'Italienisch'),
   ('fr', 'de', 'Französisch');

-- Seed Italian language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
   ('en', 'it', 'Inglese'),
   ('es', 'it', 'Spagnolo'),
   ('pt', 'it', 'Portoghese'),
   ('de', 'it', 'Tedesco'),
   ('it', 'it', 'Italiano'),
   ('fr', 'it', 'Francese');

-- Seed French language names
INSERT INTO language_translations (language_code, display_language_code, name) VALUES
   ('en', 'fr', 'Anglais'),
   ('es', 'fr', 'Espagnol'),
   ('pt', 'fr', 'Portugais'),
   ('de', 'fr', 'Allemand'),
   ('it', 'fr', 'Italien'),
   ('fr', 'fr', 'Français');
