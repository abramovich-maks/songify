package com.songify.song.dto.request;


public record PartiallyUpdateSongRequestDto(
        String song,
        String artist) {
}
