package com.songify.infrastructure.crud.song.controller.dto.request;


import com.songify.domain.crud.dto.SongLanguageDto;

import java.time.Instant;
import java.util.Set;

public record PartiallyUpdateSongRequestDto(
        String name,
        Instant releaseDate,
        Long duration,
        SongLanguageDto language,
        Long genreId,
        Set<Long> artistIds,
        Long albumId
) {
}
