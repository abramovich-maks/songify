package com.songify.domain.crud;

import com.songify.domain.crud.dto.GetSongDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
class SongRetriever {

    private final SongRepository songRepository;

    SongRetriever(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    List<SongDto> findAll(Pageable pageable) {
        return songRepository.findAll(pageable)
                .stream().map(song -> SongDto.builder()
                        .id(song.getId())
                        .name(song.getName())
                        .genreName(song.getGenre() == null ? "default" : song.getGenre().getName())
                        .build())
                .collect(Collectors.toList());
    }

    GetSongDto findSongDtoById(Long id) {
        return songRepository.findById(id)
                .map(song -> {
                    return GetSongDto.builder()
                            .id(song.getId())
                            .name(song.getName())
                            .artists((song.getArtists() == null || song.getArtists().isEmpty()) ? List.of("Nieznany artysta") : song.getArtists().stream().map(Artist::getName).toList())
                            .genre(song.getGenre() == null ? "default" : song.getGenre().getName())
                            .album(song.getAlbum() == null ? "Brak albumu" : song.getAlbum().getTitle())
                            .releaseDate(song.getReleaseDate())
                            .language(song.getLanguage().name())
                            .build();
                })
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    SongEntity findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + " not found"));
    }

    void existById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
    }
}
