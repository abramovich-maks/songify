package com.songify.domain.crud;

class AlbumNotFoundExceptions extends RuntimeException {

    AlbumNotFoundExceptions(final String message) {
        super(message);
    }
}
