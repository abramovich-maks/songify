package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.Set;

interface GenreRepository extends Repository<Genre, Long> {

    Genre save(Genre genre);

    Set<Genre> findAll(Pageable pageable);

    @Query("""
    select g from Genre g
    left join fetch g.songs s
    left join fetch s.artists a
    where g.id = :id
""")
    Optional<Genre> findByIdWithSongsAndArtists(Long id);
}
