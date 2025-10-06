package com.songify.domain.crud;

class AlbumNotHaveSongException extends RuntimeException {
    AlbumNotHaveSongException(final String message) {
        super(message);
    }
}
