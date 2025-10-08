package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreDtoWithSongsAndArtist;
import com.songify.domain.crud.dto.SongWithArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class GenreRetriever {

    private final GenreRepository genreRepository;


    Set<GenreDto> findAll(final Pageable pageable) {
        Set<Genre> all = genreRepository.findAll(pageable);
        return all.stream()
                .map(genre -> new GenreDto(genre.getId(), genre.getName()))
                .collect(Collectors.toSet());
    }

    GenreDtoWithSongsAndArtist findGenreByIdWithAllSongsAndArtist(final Long id) {
        Genre genre = genreRepository.findByIdWithSongsAndArtists(id)
                .orElseThrow(() -> new GenreNotFoundExceptions("Genre with id: " + id + " not found"));

        GenreDto genreDto = new GenreDto(genre.getId(), genre.getName());

        List<SongWithArtistDto> songDtos = genre.getSongs().stream()
                .map(song -> new SongWithArtistDto(
                        song.getId(),
                        song.getName(),
                        song.getArtists().stream()
                                .map(artist -> new ArtistDto(artist.getId(), artist.getName()))
                                .toList()
                ))
                .toList();

        return new GenreDtoWithSongsAndArtist(genreDto, songDtos);
    }

    Genre findById(final Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundExceptions("Genre with id: " + genreId + " not found"));
    }

    GenreDto findGenreById(final Long genreId) {
        Genre genre = findById(genreId);
        return new GenreDto(genre.getId(), genre.getName());
    }
}
