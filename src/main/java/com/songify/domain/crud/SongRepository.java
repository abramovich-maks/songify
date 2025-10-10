package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface SongRepository extends Repository<SongEntity, Long> {

    @Query("SELECT s FROM SongEntity s LEFT JOIN FETCH s.genre")
    List<SongEntity> findAll(Pageable pageable);

    @Query("SELECT s FROM SongEntity s WHERE s.id = :id")
    Optional<SongEntity> findById(Long id);

    @Modifying
    @Query("DELETE FROM SongEntity s WHERE s.id = :id")
    void deleteById(Long id);

    @Modifying
    @Query("UPDATE SongEntity s SET s.name = :#{#newSong.name} WHERE s.id = :id")
    void updateById(Long id, SongEntity newSong);

    boolean existsById(Long id);

    SongEntity save(SongEntity song);

    @Modifying
    @Query("delete from SongEntity s where s.id in :ids")
    int deleteByIdIn(Collection<Long> ids);
}
