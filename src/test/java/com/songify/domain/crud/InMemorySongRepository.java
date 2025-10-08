package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

class InMemorySongRepository implements SongRepository {
    Map<Long, SongEntity> db = new HashMap<>();
    AtomicInteger index = new AtomicInteger(0);

    @Override
    public List<SongEntity> findAll(final Pageable pageable) {
        return List.of();
    }

    @Override
    public Optional<SongEntity> findById(final Long id) {
        return Optional.ofNullable(db.get(id));

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
        long index = this.index.getAndIncrement();
        db.put(index, song);
        song.setId(index);
        return song;
    }

    @Override
    public int deleteByIdIn(final Collection<Long> ids) {
        ids.forEach(
                id -> db.remove(id)
        );
        return 0;
    }
}
