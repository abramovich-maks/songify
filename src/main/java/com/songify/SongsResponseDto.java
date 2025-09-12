package com.songify;

import java.util.Map;

public record SongsResponseDto(Map<Integer, String> songs) {
}
