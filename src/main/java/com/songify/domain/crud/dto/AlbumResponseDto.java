package com.songify.domain.crud.dto;

import java.time.Instant;

public record AlbumResponseDto(

        String title,
        Instant releaseDate
) {

}
