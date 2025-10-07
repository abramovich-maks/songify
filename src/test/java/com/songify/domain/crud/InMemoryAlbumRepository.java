package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class InMemoryAlbumRepository implements AlbumRepository {

    Map<Long, Album> db = new HashMap<>();

    @Override
    public Album save(final Album album) {
        return null;
    }

    @Override
    public Optional<Album> findAlbumByIdWithSongsAndArtists(final Long id) {
        return Optional.empty();
    }

    @Override
    public Set<Album> findAllAlbumsByArtistId(final Long id) {
        return db.values().stream()
                .filter(album -> album.getArtists().stream()
                        .anyMatch(artist -> artist.getId().equals(id)))
                .collect(Collectors.toSet());
    }

    @Override
    public int deleteByIdIn(final Collection<Long> ids) {
        return 0;
    }

    @Override
    public Optional<Album> findById(final Long albumId) {
        return Optional.empty();
    }

    @Override
    public List<Album> findAll(final Pageable pageable) {
        return List.of();
    }

    @Override
    public void deleteById(final Long id) {

    }

    @Override
    public Optional<Album> findAlbumByIdWithSongs(final Long id) {
        return Optional.empty();
    }
}
