package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SongBuilder {

    public SongEntity buildUpdatedSong(Integer id, SongEntity updatedSong, SongEntity songFromDatabase) {
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if (updatedSong.song() != null) {
            builder.song(updatedSong.song());
            log.info("partially updated song (old song: \"{}\") with id: {}", songFromDatabase.song(), id);
        } else {
            builder.song(songFromDatabase.song());
        }
        if (updatedSong.artist() != null) {
            builder.artist(updatedSong.artist());
            log.info("partially updated artist (old artist: \"{}\") with id: {}", songFromDatabase.artist(), id);
        } else {
            builder.artist(songFromDatabase.artist());
        }
        return builder.build();
    }
}
