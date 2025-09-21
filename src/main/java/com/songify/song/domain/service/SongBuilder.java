package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongBuilder {

    public SongEntity buildUpdatedSong(Integer id, SongEntity updatedSong, SongEntity songFromDatabase) {
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if (updatedSong.getName() != null) {
            builder.name(updatedSong.getName());
            log.info("partially updated song (old song: \"{}\") with id: {}", songFromDatabase.getName(), id);
        } else {
            builder.name(songFromDatabase.getName());
        }
        if (updatedSong.getArtist() != null) {
            builder.artist(updatedSong.getArtist());
            log.info("partially updated artist (old artist: \"{}\") with id: {}", songFromDatabase.getArtist(), id);
        } else {
            builder.artist(songFromDatabase.getArtist());
        }
        return builder.build();
    }
}
