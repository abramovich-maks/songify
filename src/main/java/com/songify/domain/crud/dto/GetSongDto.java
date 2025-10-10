package com.songify.domain.crud.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record GetSongDto(
        Long id,
        String name,
        List<String> artists,
        String genre,
        String album,
        Instant releaseDate,
        String language,
        Long duration
) {
}
