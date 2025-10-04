package com.songify.infrastructure.crud.album;

import org.springframework.http.HttpStatus;

public record DeleteAlbumResponseDto(String message, HttpStatus status) {
}
