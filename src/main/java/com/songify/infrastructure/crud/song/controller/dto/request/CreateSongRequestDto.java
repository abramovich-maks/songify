package com.songify.infrastructure.crud.song.controller.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateSongRequestDto(
        @NotNull(message = "song must not be null")
        @NotEmpty(message = "song must not be empty")
        String song
) {
}
