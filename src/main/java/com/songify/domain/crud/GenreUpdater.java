package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class GenreUpdater {

    private final GenreRetriever genreRetriever;

    GenreDto updateGenreById(final Long genreId, final String newGenre) {
        Genre genre = genreRetriever.findById(genreId);
        genre.setName(newGenre);
        return new GenreDto(genre.getId(), genre.getName());
    }
}
