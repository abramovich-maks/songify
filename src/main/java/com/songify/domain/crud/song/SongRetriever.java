package com.songify.domain.crud.song;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
class SongRetriever {

    private final SongRepository songRepository;

    SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    List<SongEntity> findAll(Pageable pageable) {
        return songRepository.findAll(pageable);
    }

    SongEntity findSongDtoById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    void existById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
    }
}
