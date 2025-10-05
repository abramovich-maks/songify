package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongDefaultRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class SongAdder {

    private final SongRepository songRepository;
    private final GenreRetriever genreRetriever;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;

    SongDto addSong(final SongRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        SongEntity songEntity = new SongEntity(songDto.name(), songDto.releaseDate(), songDto.duration(), songLanguage);
        if (songDto.genreId() != null) {
            Genre genre = genreRetriever.findById(songDto.genreId());
            songEntity.setGenre(genre);
        }
        if (songDto.artistIds() != null) {
            for (Long artistId : songDto.artistIds()) {
                Artist artist = artistRetriever.findById(artistId);
                songEntity.addArtist(artist);
            }
        }
        if (songDto.albumId() != null) {
            Album album = albumRetriever.findById(songDto.albumId());
            songEntity.addAlbum(album);
        }
        log.info("added new songDto: {}", songDto);
        SongEntity save = songRepository.save(songEntity);
        String genreName = save.getGenre() != null ? save.getGenre().getName() : null;
        return new SongDto(save.getId(), save.getName(), genreName);
    }

    SongEntity addDefaultSong(final SongDefaultRequestDto songDto) {
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        SongEntity songEntity = new SongEntity(songDto.name(), songDto.releaseDate(), songDto.duration(), songLanguage);
        log.info("added new default song: {}", songDto);
        return songRepository.save(songEntity);
    }
}
