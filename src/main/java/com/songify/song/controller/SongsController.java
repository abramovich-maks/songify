package com.songify.song.controller;

import com.songify.song.dto.response.DeleteSongResponseDto;
import com.songify.song.dto.response.SingleSongResponseDto;
import com.songify.song.dto.request.SongRequestDto;
import com.songify.song.dto.response.SongsResponseDto;
import com.songify.song.dto.request.UpdateSongRequestDto;
import com.songify.song.dto.response.UpdateSongResponseDto;
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

    Map<Integer, SongEntity> dataBase = new HashMap<>(Map.of(
            1, new SongEntity("ShawnMendes songs1", "Shawn Mendes"),
            2, new SongEntity("Ariana song1", "Ariana Grande"),
            3, new SongEntity("Neon Lights", "Crimsonsun"),
            4, new SongEntity("Go to hell", "Letdown")));

    @GetMapping("/songs")
    public ResponseEntity<SongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, SongEntity> limitedMap = dataBase.entrySet()
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
        SongEntity song = dataBase.get(id);
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {
        SongEntity song = new SongEntity(request.song(), request.artist());
        dataBase.put(dataBase.size() + 1, song);
        log.info("added new song: {}", song);
        return ResponseEntity.ok(new SingleSongResponseDto(song));
    }

    @DeleteMapping("/songs/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Integer id) {
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        dataBase.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK));
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request) {
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        String newSongName = request.song();
        String newArtist = request.artist();
        SongEntity newSong = new SongEntity(newSongName, newArtist);
        SongEntity oldSong = dataBase.put(id, newSong);
        log.info("Updated song with id: \"{}\" with oldSongName: \"{}\" to newSongName: \"{}\", oldArtist \"{}\" to newArtist \"{}\"", id, oldSong.song(), newSong.song(), oldSong.artist(), newSong.artist());
        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.song(), newSong.artist()));
    }

}
