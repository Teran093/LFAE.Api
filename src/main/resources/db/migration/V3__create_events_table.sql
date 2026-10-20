-- 3. BASE EVENTS TABLE (Language-neutral metadata)
-- =============================================================================
CREATE TABLE events (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    creation_date TIMESTAMPTZ NOT NULL DEFAULT now(),
    start_date    TIMESTAMPTZ NOT NULL,
    end_date      TIMESTAMPTZ,
    time_zone     VARCHAR(50) NOT NULL DEFAULT 'UTC',
    active        BOOLEAN NOT NULL DEFAULT FALSE,
    country_code  VARCHAR(2),
    city          VARCHAR(100),
    address       VARCHAR(255),
    CONSTRAINT chk_events_dates CHECK (end_date IS NULL OR end_date >= start_date),
    CONSTRAINT fk_events_country FOREIGN KEY (country_code) REFERENCES countries(code) ON DELETE RESTRICT
);
CREATE INDEX idx_events_start_date ON events (start_date);
CREATE INDEX idx_events_country ON events (country_code);

-- =============================================================================
-- 4. MULTI-LANGUAGE EVENT CONTENT
-- =============================================================================
CREATE TABLE event_translations (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id            UUID NOT NULL,
    language_code       VARCHAR(5) NOT NULL,
    name                VARCHAR(255) NOT NULL,
    description         TEXT,
    invitation_pdf_url  VARCHAR(500),
    invitation_content  TEXT,
    CONSTRAINT fk_event_translations_event
        FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
    CONSTRAINT fk_event_translations_language
        FOREIGN KEY (language_code) REFERENCES languages(code) ON DELETE RESTRICT,
    CONSTRAINT uq_event_language UNIQUE (event_id, language_code)
);
CREATE INDEX idx_event_trans_lookup ON event_translations (event_id, language_code);