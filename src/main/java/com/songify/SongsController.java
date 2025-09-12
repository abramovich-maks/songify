package com.songify;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SongsController {

    @GetMapping("/songs")
    public ResponseEntity<SongsResponseDto> getAllSongs(){
        SongsResponseDto response = new SongsResponseDto(List.of("ShawnMendes songs1", "ArianaGrande songs2"));
        return ResponseEntity.ok(response);
    }

}
