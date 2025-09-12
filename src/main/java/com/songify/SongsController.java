package com.songify;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import java.util.Map;

@RestController
public class SongsController {

    Map<Integer, String> dataBase = new HashMap<>(Map.of(1, "ShawnMendes songs1", 2, "ArianaGrande songs2"));

    @GetMapping("/songs")
    public ResponseEntity<SongsResponseDto> getAllSongs() {
        SongsResponseDto response = new SongsResponseDto(dataBase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable Integer id) {
        String song = dataBase.get(id);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

}
