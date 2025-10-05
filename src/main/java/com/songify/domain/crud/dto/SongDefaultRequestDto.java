package com.songify.domain.crud.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
public record SongDefaultRequestDto(
        String name,
        Instant releaseDate,
        Long duration,
        SongLanguageDto language
) {
}
