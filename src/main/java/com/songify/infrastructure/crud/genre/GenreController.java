package com.songify.infrastructure.crud.genre;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreDtoWithSongsAndArtist;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.infrastructure.crud.artist.ArtistUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static com.songify.infrastructure.crud.genre.GenreControllerMapper.mapFromGenreDtoToGetAllGenresDto;

@RestController
@AllArgsConstructor
@RequestMapping("/genres")
class GenreController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping("/{name}")
    public ResponseEntity<GenreDto> postGenre(@RequestBody GenreRequestDto genreRequestDto) {
        GenreDto genreDto = songifyCrudFasade.addGenre(genreRequestDto);
        return ResponseEntity.ok(genreDto);
    }

    @GetMapping()
    public ResponseEntity<GetAllGenresDto> getAllGenres(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        Set<GenreDto> allGenres = songifyCrudFasade.findAllGenres(pageable);
        GetAllGenresDto body = mapFromGenreDtoToGetAllGenresDto(allGenres);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDtoWithSongsAndArtist> findGenreByIdWithAllSongsAndArtist(@PathVariable Long id) {
        GenreDtoWithSongsAndArtist genreByIdWithAllSongsAndArtist = songifyCrudFasade.findGenreByIdWithAllSongsAndArtist(id);
        return ResponseEntity.ok(genreByIdWithAllSongsAndArtist);
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<GenreDto> updateArtistNameById(@PathVariable Long genreId, @RequestBody @Valid GenreUpdateRequestDto dto) {
        GenreDto updatedGenre = songifyCrudFasade.updateGenreById(genreId, dto.newGenreName());
        return ResponseEntity.ok(updatedGenre);
    }
}
