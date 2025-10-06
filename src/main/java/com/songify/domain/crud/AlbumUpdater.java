package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class AlbumUpdater {

    private final AlbumRetriever albumRetriever;
    private final ArtistRetriever artistRetriever;
    private final SongRetriever songRetriever;

    public void addSongToAlbumById(final Long albumId, final Long songId) {
        Album album = albumRetriever.findById(albumId);
        SongEntity song = songRetriever.findSongById(songId);
        Album isExist = song.getAlbum();
        if (isExist == null) {
            song.addAlbum(album);
            log.info("Added song id={} to album id={}", songId, albumId);
        } else {
            throw new SongAlreadyExistException("Song with id: " + songId + " already belongs to album with id: " + isExist.getId());

        }
    }

    void deleteSongFromAlbumById(final Long albumId, final Long songId) {
        Album album = albumRetriever.findById(albumId);
        SongEntity song = songRetriever.findSongById(songId);
        Album isExist = song.getAlbum();
        if (isExist == null) {
            throw new SongDoesntHaveAlbumException("Song with id: " + songId + " doesn't have an album");
        }
        if (!Objects.equals(isExist.getId(), albumId)) {
            throw new AlbumNotHaveSongException("Album with id: " + albumId + " does not have song with id: " + songId);
        }
        song.deleteAlbum(album);
        log.info("Removed song id={} from album id={}", songId, albumId);
    }

    void addArtistToAlbumById(final Long albumId, final Long artistId) {
        Album album = albumRetriever.findById(albumId);
        Artist artist = artistRetriever.findById(artistId);
        Set<Long> albumIds = artist.getAlbums().stream()
                .map(Album::getId)
                .collect(Collectors.toSet());
        if (albumIds.contains(albumId)) {
            throw new AlbumAlreadyContainsArtistException("Album with id: " + albumId + " already contains artist with id: " + artistId);
        } else {
            artist.addAlbum(album);
            log.info("Added artist id={} to album id={}", artistId, albumId);
        }
    }

    void deleteArtistFromAlbumById(final Long albumId, final Long artistId) {
        Album album = albumRetriever.findById(albumId);
        Artist artist = artistRetriever.findById(artistId);
        Set<Long> albumIds = artist.getAlbums().stream()
                .map(Album::getId)
                .collect(Collectors.toSet());
        if (albumIds.contains(albumId)) {
            artist.removeAlbum(album);
            log.info("Removed artist id={} from album id={}", artistId, albumId);
        } else {
            throw new AlbumNotHaveArtistException("Album with id: " + albumId + " does not have artist with id: " + artistId);
        }
    }

    void updateAlbumNameOrReleaseById(Long albumId, final UpdateAlbumRequestDto request) {
        Album album = albumRetriever.findById(albumId);
        if (request.title() != null && !request.title().isBlank()) {
            album.setTitle(request.title());
            log.info("Album id={} title updated to '{}'", albumId, request.title());
        }

        if (request.releaseDate() != null) {
            album.setReleaseDate(request.releaseDate());
            log.info("Album id={} releaseDate updated to '{}'", albumId, request.releaseDate());
        }
    }
}
