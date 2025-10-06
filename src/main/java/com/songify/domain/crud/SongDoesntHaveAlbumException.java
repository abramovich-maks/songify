package com.songify.domain.crud;

class SongDoesntHaveAlbumException extends RuntimeException {

    SongDoesntHaveAlbumException(final String message) {
        super(message);
    }
}
