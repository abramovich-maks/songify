package com.songify.infrastructure.crud.song.controller.dto.response;

import lombok.Builder;

@Builder
public record SongControllerResponseDto(String name, String artist) {
}
