package com.songify.domain.crud;

class AlbumAlreadyContainsArtistException extends RuntimeException {

    AlbumAlreadyContainsArtistException(final String message) {
        super(message);
    }
}
