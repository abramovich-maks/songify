package com.songify.song.dto;

import java.util.Map;

public record SongsResponseDto(Map<Integer, String> songs) {
}
