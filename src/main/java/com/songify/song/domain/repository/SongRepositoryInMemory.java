package com.songify.song.domain.repository;

import com.songify.song.domain.model.SongEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepositoryInMemory implements SongRepository {

    Map<Integer, SongEntity> dataBase = new HashMap<>(Map.of(
            1, new SongEntity("ShawnMendes songs1", "Shawn Mendes"),
            2, new SongEntity("Ariana song1", "Ariana Grande"),
            3, new SongEntity("Neon Lights", "Crimsonsun"),
            4, new SongEntity("Go to hell", "Letdown")));

    @Override
    public SongEntity save(SongEntity song) {
        dataBase.put(dataBase.size() + 1, song);
        return song;
    }

    @Override
    public List<SongEntity> findAll() {
        return dataBase.values().stream().toList();
    }
}
