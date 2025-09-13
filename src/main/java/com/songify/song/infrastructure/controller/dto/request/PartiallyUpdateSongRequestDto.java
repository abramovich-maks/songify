package com.songify.song.infrastructure.controller.dto.request;


public record PartiallyUpdateSongRequestDto(
        String song,
        String artist) {
}
