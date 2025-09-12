package com.songify.song;

import java.util.Map;

public record SongsResponseDto(Map<Integer, String> songs) {
}
