package com.songify.song.domain.model;

import lombok.Builder;

@Builder
public record SongEntity(String song, String artist) {
}
