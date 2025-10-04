package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

interface AlbumRepository extends Repository<Album, Long> {

    Album save(Album album);

    @Query("""
            select a from Album a
            left join fetch a.songs songs
            left join fetch a.artists artists
            where a.id = :id""")
    Optional<Album> findAlbumByIdWithSongsAndArtists(Long id);

    @Query("""
            select a from Album a 
            inner join a.artists artists 
            where artists.id = :id
            """)
    Set<Album> findAllAlbumsByArtistId(@Param("id") Long id);

    @Modifying
    @Query("delete from Album a where a.id in :ids")
    int deleteByIdIn(Collection<Long> ids);

    Optional<Album> findById(Long albumId);

    List<Album> findAll(Pageable pageable);

    @Modifying
    @Query("delete from Album a where a.id = :id")
    void deleteById(Long id);

    @Query("""
            select a from Album a
            left join fetch a.songs songs
            where a.id = :id""")
    Optional<Album> findAlbumByIdWithSongs(Long id);
}