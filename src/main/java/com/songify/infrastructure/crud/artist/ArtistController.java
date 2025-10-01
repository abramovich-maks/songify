package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/artist")
class ArtistController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping("/{name}")
    public ResponseEntity<ArtistDto> postArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFasade.addArtist(artistRequestDto);
        return ResponseEntity.ok(artistDto);
    }

    @GetMapping
    public ResponseEntity<AllArtistsDto> getArtist() {
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists();
        AllArtistsDto allArtistsDto = new AllArtistsDto(allArtists);
        return ResponseEntity.ok(allArtistsDto);
    }
}
