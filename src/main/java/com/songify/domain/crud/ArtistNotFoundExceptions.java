package com.songify.domain.crud;

class ArtistNotFoundExceptions extends RuntimeException {
    ArtistNotFoundExceptions(final String message) {
        super(message);
    }
}
