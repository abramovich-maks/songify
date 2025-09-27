-- CREATE TABLE IF NOT EXISTS SONG
-- (
--     ID     BIGSERIAL PRIMARY KEY,
--     ARTIST VARCHAR(255) NOT NULL,
--     NAME   VARCHAR(255) NOT NULL
-- );

CREATE TABLE song
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    artist       VARCHAR(255) NOT NULL,
    release_date TIMESTAMP WITHOUT TIME ZONE,
    duration     BIGINT,
    language     VARCHAR(255)
);

