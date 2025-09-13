package com.songify.song.controller;

import com.songify.song.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.dto.response.*;
import com.songify.song.dto.request.CreateSongRequestDto;
import com.songify.song.dto.request.UpdateSongRequestDto;
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
@RequestMapping("/songs")
public class SongsController {

    Map<Integer, SongEntity> dataBase = new HashMap<>(Map.of(
            1, new SongEntity("ShawnMendes songs1", "Shawn Mendes"),
            2, new SongEntity("Ariana song1", "Ariana Grande"),
            3, new SongEntity("Neon Lights", "Crimsonsun"),
            4, new SongEntity("Go to hell", "Letdown")));

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        if (limit != null) {
            Map<Integer, SongEntity> limitedMap = dataBase.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            GetAllSongsResponseDto response = new GetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(dataBase);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Integer id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        SongEntity song = dataBase.get(id);
        GetSongResponseDto response = new GetSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity song = new SongEntity(request.song(), request.artist());
        dataBase.put(dataBase.size() + 1, song);
        log.info("added new song: {}", song);
        return ResponseEntity.ok(new CreateSongResponseDto(song));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Integer id) {
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        dataBase.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("Song with id: " + id + " have been deleted.", HttpStatus.OK));
    }

    @PutMapping("/{id}")
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

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id, @RequestBody PartiallyUpdateSongRequestDto request) {
        if (!dataBase.containsKey(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        SongEntity songFromDatabase = dataBase.get(id);
        SongEntity.SongEntityBuilder builder = SongEntity.builder();
        if (request.song() != null) {
            builder.song(request.song());
            log.info("partially updated song name");
        } else {
            builder.song(songFromDatabase.song());
        }
        if (request.artist() != null) {
            builder.artist(request.artist());
            log.info("partially updated artist");
        } else {
            builder.artist(songFromDatabase.artist());
        }
        SongEntity updatedSong = builder.build();
        dataBase.put(id, updatedSong);
        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong));
    }

}
