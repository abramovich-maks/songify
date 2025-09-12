package com.songify.song.dto.response;

import com.songify.song.controller.SongEntity;

import java.util.Map;

public record SongsResponseDto(Map<Integer, SongEntity> songs) {
}
