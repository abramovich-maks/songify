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
}
