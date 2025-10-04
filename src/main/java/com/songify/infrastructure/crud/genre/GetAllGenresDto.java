package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.dto.GenreDto;

import java.util.Set;

public record GetAllGenresDto(Set<GenreDto> genre) {
}
