package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryGenreRepository implements GenreRepository {

    Map<Long, Genre> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public Genre save(final Genre genre) {
        long index = this.index.getAndIncrement();
        db.put(index, genre);
        genre.setId(index);
        return genre;
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
        return Optional.ofNullable(db.get(id));
    }
}
