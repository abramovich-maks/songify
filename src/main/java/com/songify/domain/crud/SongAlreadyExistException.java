package com.songify.domain.crud;

class SongAlreadyExistException extends RuntimeException {
    SongAlreadyExistException(final String message) {
        super(message);
    }
}
