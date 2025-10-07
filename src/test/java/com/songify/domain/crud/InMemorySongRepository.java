package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

class InMemorySongRepository implements SongRepository {
    @Override
    public List<SongEntity> findAll(final Pageable pageable) {
        return List.of();
    }

    @Override
    public Optional<SongEntity> findById(final Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(final Long id) {

    }

    @Override
    public void updateById(final Long id, final SongEntity newSong) {

    }

    @Override
    public boolean existsById(final Long id) {
        return false;
    }

    @Override
    public SongEntity save(final SongEntity song) {
        return null;
    }

    @Override
    public int deleteByIdIn(final Collection<Long> ids) {
        return 0;
    }
}
