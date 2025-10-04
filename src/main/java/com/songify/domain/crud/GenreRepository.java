package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Set;

interface GenreRepository extends Repository<Genre, Long> {

    Genre save(Genre genre);

    Set<Genre> findAll(Pageable pageable);
}
