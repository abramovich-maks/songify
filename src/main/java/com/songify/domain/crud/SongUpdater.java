package com.songify.domain.crud;

import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
class SongUpdater {
    private final SongRepository songRepository;
    private final SongRetriever songRetriever;
    private final GenreRetriever genreRetriever;
    private final AlbumRetriever albumRetriever;
    private final ArtistRetriever artistRetriever;

    void updateById(Long id, SongEntity newSong) {
        songRetriever.existById(id);
        songRepository.updateById(id, newSong);
    }

    void updatePartiallyById(final Long id, final SongRequestDto songFromRequest) {
        songRetriever.existById(id);
        SongEntity songFromDatabase = songRetriever.findSongById(id);
        if (songFromRequest.name() != null) {
            songFromDatabase.setName(songFromRequest.name());
            log.info("Song with id: {} updated name: \"{}\"", songFromDatabase.getId(), songFromRequest.name());
        }

        if (songFromRequest.releaseDate() != null) {
            songFromDatabase.setReleaseDate(songFromRequest.releaseDate());
            log.info("Song with id: {} updated releaseDate: \"{}\"", songFromDatabase.getId(), songFromRequest.releaseDate());
        }

        if (songFromRequest.duration() != null) {
            songFromDatabase.setDuration(songFromRequest.duration());
            log.info("Song with id: {} updated duration: \"{}\"", songFromDatabase.getId(), songFromRequest.duration());
        }

        if (songFromRequest.language() != null) {
            songFromDatabase.setLanguage(SongLanguage.valueOf(songFromRequest.language().name()));
            log.info("Song with id: {} updated language: \"{}\"", songFromDatabase.getId(), SongLanguage.valueOf(songFromRequest.language().name()));
        }

        if (songFromRequest.genreId() != null) {
            Genre retrievedGenre = genreRetriever.findById(songFromRequest.genreId());
            Genre genre = new Genre();
            genre.setId(songFromRequest.genreId());
            songFromDatabase.setGenre(genre);
            log.info("Song with id: {} updated genre: \"{}\"", songFromDatabase.getId(), retrievedGenre.getName());
        }

        if (songFromRequest.artistIds() != null && !songFromRequest.artistIds().isEmpty()) {
            Set<Artist> artists = songFromRequest.artistIds().stream()
                    .map(dto -> {
                        Artist artist = new Artist();
                        Artist retrievedArtist = artistRetriever.findById(dto);
                        artist.setId(dto);
                        log.info("Song with id: {} updated artist: \"{}\"", songFromDatabase.getId(), retrievedArtist.getName());
                        return artist;
                    })
                    .collect(Collectors.toSet());
            songFromDatabase.setArtists(artists);
        }

        if (songFromRequest.albumId() != null) {
            Album retrievedAlbum = albumRetriever.findById(songFromRequest.albumId());
            Album album = new Album();
            album.setId(songFromRequest.albumId());
            songFromDatabase.setAlbum(album);
            log.info("Song with id: {} updated album: \"{}\"", songFromDatabase.getId(), retrievedAlbum.getTitle());
        }
    }
}
