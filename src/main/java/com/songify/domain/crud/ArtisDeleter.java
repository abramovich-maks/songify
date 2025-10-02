package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
class ArtisDeleter {

    private final ArtistRepository artistRepository;
    private final ArtistRetriever artistRetriever;
    private final AlbumDeleter albumDeleter;
    private final AlbumRetriever albumRetriever;
    private final SongDeleter songDeleter;


    void deleteArtistByIdWithAlbumsAndSongs(final Long artistId) {
        Artist artist = artistRetriever.findById(artistId);
        Set<Album> artistAlbums = albumRetriever.findAlbumsByArtistId(artist.getId());
        if (artistAlbums.isEmpty()) {
            log.info("Artist with id: {} have 0 albums", artistId);
            artistRepository.deleteById(artistId);
            return;
        }

        Set<Album> albumsWithOnlyOneArtist = artistAlbums.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());

        Set<Long> allSongsIdsFromAlLAlbumsWhereWasOnlyThisArtist = albumsWithOnlyOneArtist.stream()
                .flatMap(album -> album.getSongs().stream())
                .map(SongEntity::getId)
                .collect(Collectors.toSet());

        songDeleter.deleteAllSongsById(allSongsIdsFromAlLAlbumsWhereWasOnlyThisArtist);

        Set<Long> albumsIdsToDelete = albumsWithOnlyOneArtist.stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        albumDeleter.deleteAllAlbumsByIds(albumsIdsToDelete);

        artistAlbums.stream()
                .filter(album -> album.getArtists().size() >= 2)
                .forEach(album -> album.removeArtist(artist));

        artistRepository.deleteById(artistId);
    }
}
