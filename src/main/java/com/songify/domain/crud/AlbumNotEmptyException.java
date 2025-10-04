package com.songify.domain.crud;

public class AlbumNotEmptyException extends RuntimeException {

    AlbumNotEmptyException(final String message) {
        super(message);
    }
}
