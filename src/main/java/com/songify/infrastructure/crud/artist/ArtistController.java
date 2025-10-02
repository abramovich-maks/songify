package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.songify.infrastructure.crud.artist.ArtistControllerMapper.getDeleteArtistResponseDto;

@RestController
@AllArgsConstructor
@RequestMapping("/artists")
class ArtistController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping("/{name}")
    public ResponseEntity<ArtistDto> postArtist(@RequestBody ArtistRequestDto artistRequestDto) {
        ArtistDto artistDto = songifyCrudFasade.addArtist(artistRequestDto);
        return ResponseEntity.ok(artistDto);
    }

    @GetMapping
    public ResponseEntity<AllArtistsDto> getArtist(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(pageable);
        AllArtistsDto allArtistsDto = new AllArtistsDto(allArtists);
        return ResponseEntity.ok(allArtistsDto);
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<DeleteArtistResponseDto> deleteArtistWithAlbumsAndSongs(@PathVariable Long artistId) {
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        DeleteArtistResponseDto body = getDeleteArtistResponseDto(artistId);
        return ResponseEntity.ok(body);
    }
}
