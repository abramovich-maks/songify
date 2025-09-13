package com.songify.song.infrastructure.controller.dto.response;

import com.songify.song.domain.model.SongEntity;

public record PartiallyUpdateSongResponseDto(SongEntity update) {
}
