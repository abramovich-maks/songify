package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface SongRepository extends Repository<SongEntity, Long> {

    SongEntity saveToDatabase(SongEntity song);

    List<SongEntity> findAll();
}
