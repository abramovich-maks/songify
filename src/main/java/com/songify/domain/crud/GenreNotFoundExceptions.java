package com.songify.domain.crud;

class GenreNotFoundExceptions extends RuntimeException {

    GenreNotFoundExceptions(final String message) {
        super(message);
    }
}
