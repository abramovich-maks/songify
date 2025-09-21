package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@Transactional
public class SongUpdater {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;

    public SongUpdater(SongRepository songRepository, SongRetriever songRetriever) {
        this.songRepository = songRepository;
        this.songRetriever = songRetriever;
    }

    public void updateById(Long id, SongEntity newSong) {
        songRetriever.existById(id);
        songRepository.updateById(id, newSong);
    }

    public SongEntity updatePartiallyById(Long id, SongEntity songFromRequest) {
        SongEntity songFromDatabase = songRetriever.findById(id);
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if (songFromRequest.getName() != null) {
            builder.name(songFromRequest.getName());
            log.info("partially updated song (old song: \"{}\") with id: {}", songFromDatabase.getName(), id);
        } else {
            builder.name(songFromDatabase.getName());
        }
        if (songFromRequest.getArtist() != null) {
            builder.artist(songFromRequest.getArtist());
            log.info("partially updated artist (old artist: \"{}\") with id: {}", songFromDatabase.getArtist(), id);
        } else {
            builder.artist(songFromDatabase.getArtist());
        }
        SongEntity toSave = builder.build();
        updateById(id, toSave);
        return toSave;
    }

}
