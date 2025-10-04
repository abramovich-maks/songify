package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.dto.GenreDto;

import java.util.Set;

class GenreControllerMapper {

    public static GetAllGenresDto mapFromGenreDtoToGetAllGenresDto(final Set<GenreDto> allGenres) {
        return new GetAllGenresDto(allGenres);
    }
}
