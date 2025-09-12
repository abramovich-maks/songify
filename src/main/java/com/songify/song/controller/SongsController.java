package com.songify.song.controller;

import com.songify.song.dto.DeleteSongResponseDto;
import com.songify.song.dto.SingleSongResponseDto;
import com.songify.song.dto.SongRequestDto;
import com.songify.song.dto.SongsResponseDto;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class SongsController {

    Map<Integer, String> dataBase = new HashMap<>(Map.of(
            1, "ShawnMendes songs1",
            2, "ArianaGrande songs2",
            3, "Crimsonsun song3",
            4, "Letdown"));

    @GetMapping("/songs")
    public ResponseEntity<SongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, String> limitedMap = dataBase.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            SongsResponseDto response = new SongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        SongsResponseDto response = new SongsResponseDto(dataBase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongsById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        String song = dataBase.get(id);
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        String songName = request.songName();
        dataBase.put(dataBase.size() + 1, songName);
        log.info("added new song: {}", songName);
        return ResponseEntity.ok(new SingleSongResponseDto(songName));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Integer id) {
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        dataBase.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK));
    }
}
