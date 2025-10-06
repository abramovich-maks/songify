package com.songify.domain.crud;

class AlbumNotHaveArtistException extends RuntimeException {

    AlbumNotHaveArtistException(final String message) {
        super(message);
    }
}
