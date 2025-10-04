CREATE TABLE song_artists
(
    artists_id BIGINT NOT NULL,
    songs_id   BIGINT NOT NULL,
    CONSTRAINT pk_song_artists PRIMARY KEY (artists_id, songs_id)
);

ALTER TABLE song_artists
    ADD CONSTRAINT fk_sonart_on_artist FOREIGN KEY (artists_id) REFERENCES artist (id);

ALTER TABLE song_artists
    ADD CONSTRAINT fk_sonart_on_song_entity FOREIGN KEY (songs_id) REFERENCES song (id);