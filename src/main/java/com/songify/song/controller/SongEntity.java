package com.songify.song.controller;

import lombok.Builder;

@Builder
public record SongEntity(String song, String artist) {
}
