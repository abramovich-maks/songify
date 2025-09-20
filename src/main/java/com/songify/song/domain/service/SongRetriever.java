package com.songify.song.domain.service;

import com.songify.song.domain.model.SongEntity;
import com.songify.song.domain.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class SongRetriever {

    private final SongRepository songRepository;

    public SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongEntity> findAll() {
        return songRepository.findAll();
    }

    public List<SongEntity> findAllByLimited(Integer limit) {
        return songRepository.findAll()
                .stream()
                .limit(limit)
                .toList();
    }
}
