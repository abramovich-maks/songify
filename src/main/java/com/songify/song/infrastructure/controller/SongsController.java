package com.songify.song.infrastructure.controller;

import com.songify.song.domain.service.*;
import com.songify.song.infrastructure.controller.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.response.*;
import com.songify.song.infrastructure.controller.dto.request.CreateSongRequestDto;
import com.songify.song.infrastructure.controller.dto.request.UpdateSongRequestDto;
import com.songify.song.domain.model.SongEntity;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongsController {

    private final SongAdder songAdder;
    private final SongRetriever songRetriever;
    private final SongDeleter songDeleter;
    private final SongUpdater songUpdater;

    public SongsController(SongAdder songAdder, SongRetriever songRetriever, SongDeleter songDeleter, SongUpdater songUpdater) {
        this.songAdder = songAdder;
        this.songRetriever = songRetriever;
        this.songDeleter = songDeleter;
        this.songUpdater = songUpdater;
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
        SongEntity songEntity = songRetriever.findById(id);
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
    public ResponseEntity<DeleteSongResponseDto> deleteSong(@PathVariable Long id) {
        songDeleter.deleteById(id);
        DeleteSongResponseDto body = SongMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSongRequestDto request) {
        SongEntity newSong = SongMapper.mapFromUpdateSongRequestDtoToSong(request);
        songUpdater.updateById(id, newSong);
        UpdateSongResponseDto body = SongMapper.mapFromSongToUpdateSongResponseDto(newSong);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Long id, @RequestBody PartiallyUpdateSongRequestDto request) {
        SongEntity updatedSong = SongMapper.mapFromPartiallyUpdateSongRequestDtoToSong(request);
        SongEntity saveSong = songUpdater.updatePartiallyById(id, updatedSong);
        PartiallyUpdateSongResponseDto body = SongMapper.mapFromSongToPartiallyUpdateSongResponseDto(saveSong);
        return ResponseEntity.ok(body);
    }
}
