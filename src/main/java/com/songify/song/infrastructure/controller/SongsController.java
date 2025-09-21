package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.SongAdder;
import com.songify.song.domain.service.SongBuilder;
import com.songify.song.domain.service.SongRetriever;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.domain.model.SongNotFoundException;
import com.songify.song.domain.model.SongEntity;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongsController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongBuilder songBuilder;

    public SongsController(SongAdder songAdder, SongRetriever songRetriever, SongBuilder songBuilder) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songBuilder = songBuilder;
    }

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false) Integer limit) {
        List<SongEntity> allSongs = songRetriever.findAll();
        if (limit != null) {
            List<SongEntity> limitedMap = songRetriever.findAllByLimited(limit);
            GetAllSongsResponseDto response = SongMapper.mapFromSongToGetAllSongsResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        GetAllSongsResponseDto response = SongMapper.mapFromSongToGetAllSongsResponseDto(allSongs);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongsById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongEntity songEntity = songRetriever.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song with id: " + id + " not found"));
        GetSongResponseDto response = SongMapper.mapFromSongToGetSongResponseDto(songEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request) {
        SongEntity song = SongMapper.mapFromCreateSongRequestDtoToSong(request);
        songAdder.addSong(song);
        CreateSongResponseDto createSongResponseDto = SongMapper.mapFromSongToCreateSongResponseDto(song);
        return ResponseEntity.ok(createSongResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Integer id) {
        List<SongEntity> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        allSongs.remove(id);
        return ResponseEntity.ok(SongMapper.mapFromSongToDeleteSongResponseDto(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UpdateSongRequestDto request) {
        List<SongEntity> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        String newSongName = request.song();
        String newArtist = request.artist();
        SongEntity newSong = SongMapper.mapFromUpdateSongRequestDtoToSong(newSongName, newArtist);
        SongEntity oldSong = songAdder.addSong(newSong);
        log.info("Updated song with id: \"{}\" with oldSongName: \"{}\" to newSongName: \"{}\", oldArtist \"{}\" to newArtist \"{}\"", id, oldSong.
                getName(), newSong.getId(), oldSong.getArtist(), newSong.getArtist());
        UpdateSongResponseDto body = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id, @RequestBody PartiallyUpdateSongRequestDto request) {
        List<SongEntity> allSongs = songRetriever.findAll();
        if (!allSongs.contains(id)) {
            throw new SongNotFoundException("Song with id: " + id + " not found");
        }
        SongEntity songFromDatabase = allSongs.get(id);
        SongEntity updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        SongEntity build = songBuilder.buildUpdatedSong(id, updatedSong, songFromDatabase);
        songAdder.addSong(build);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(build);
        return ResponseEntity.ok(body);
    }
}
