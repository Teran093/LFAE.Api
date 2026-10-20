-- =============================================================================
-- 5. STREAMING SOURCES FOR EVENTS
-- =============================================================================
CREATE TABLE event_streams (
   id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
   event_id      UUID NOT NULL,
   provider      VARCHAR(30) NOT NULL, -- 'YOUTUBE', 'TWITCH', 'VIMEO', 'CUSTOM_IFRAME'
   title         VARCHAR(100),
   stream_url    VARCHAR(1000) NOT NULL,
   language_code VARCHAR(5),
   is_primary    BOOLEAN NOT NULL DEFAULT FALSE,
   CONSTRAINT fk_event_streams_event
       FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
   CONSTRAINT fk_event_streams_language
       FOREIGN KEY (language_code) REFERENCES languages(code) ON DELETE RESTRICT
);

CREATE INDEX idx_event_streams_event_id ON event_streams (event_id);