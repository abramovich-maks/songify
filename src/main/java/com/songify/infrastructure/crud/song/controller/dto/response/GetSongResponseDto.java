package com.songify.infrastructure.crud.song.controller.dto.response;

import java.util.List;

public record
GetSongResponseDto(Long id, String name, List<String> artist, String genre) {
}
