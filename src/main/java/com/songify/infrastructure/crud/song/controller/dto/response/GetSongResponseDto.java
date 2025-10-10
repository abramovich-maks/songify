package com.songify.infrastructure.crud.song.controller.dto.response;

import java.time.Instant;
import java.util.List;

public record
GetSongResponseDto(
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
