package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends Repository<SongEntity, Long> {

    SongEntity save(SongEntity song);

    List<SongEntity> findAll();

    Optional<SongEntity> findById(Long id);

    void deleteById(Long id);

    @Modifying
    @Query("UPDATE SongEntity s SET s.name = :#{#newSong.name}, s.artist = :#{#newSong.artist} WHERE s.id = :id")
    void updateById(Long id, SongEntity newSong);
}
