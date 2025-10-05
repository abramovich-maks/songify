package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SongAssigner {

    private final SongRetriever songRetriever;
    private final ArtistRetriever artistRetriever;
    private final AlbumRetriever albumRetriever;
    private final GenreRetriever genreRetriever;

    void addSongToArtist(final Long songId, final Long artistId) {
        SongEntity song = songRetriever.findSongById(songId);
        Artist artist = artistRetriever.findById(artistId);
        song.addArtist(artist);
    }

    void addSongToAlbum(final Long songId, final Long albumId) {
        SongEntity song = songRetriever.findSongById(songId);
        Album album = albumRetriever.findById(albumId);
        song.addAlbum(album);
    }

    void addGenreToSong(final Long songId, final Long genreId) {
        SongEntity song = songRetriever.findSongById(songId);
        Genre genre = genreRetriever.findById(genreId);
        song.addGenre(genre);
    }
}
