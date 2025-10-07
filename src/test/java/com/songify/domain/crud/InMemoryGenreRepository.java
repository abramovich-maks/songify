package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

class InMemoryGenreRepository implements GenreRepository {
    @Override
    public Genre save(final Genre genre) {
        return null;
    }

    @Override
    public Set<Genre> findAll(final Pageable pageable) {
        return Set.of();
    }

    @Override
    public Optional<Genre> findByIdWithSongsAndArtists(final Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Genre> findById(final Long id) {
        return Optional.empty();
    }
}
