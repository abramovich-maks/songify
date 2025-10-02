    package com.songify.infrastructure.crud.artist;

import org.springframework.http.HttpStatus;

public record DeleteArtistResponseDto(String message, HttpStatus status) {
}
