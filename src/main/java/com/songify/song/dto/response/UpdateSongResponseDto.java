package com.songify.song.dto.response;

import org.springframework.http.HttpStatus;

public record UpdateSongResponseDto(String song,String artist) {
}
