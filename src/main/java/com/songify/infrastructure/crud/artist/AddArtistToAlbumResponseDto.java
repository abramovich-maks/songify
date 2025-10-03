package com.songify.infrastructure.crud.artist;

import org.springframework.http.HttpStatus;

public record AddArtistToAlbumResponseDto(String message, HttpStatus status) {
}
