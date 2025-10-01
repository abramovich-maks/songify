package com.songify.infrastructure.crud.song.controller.dto.response;

import java.time.Instant;

public record UpdateSongResponseDto(
        String name,
        Instant releaseDate,
        Long duration
) {
}
