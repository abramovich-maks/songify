package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class SongAssigner {

    private final SongRetriever songRetriever;
    private final ArtistRetriever artistRetriever;

    void addSongToArtist(final Long songId, final Long artistId) {
        SongEntity song = songRetriever.findSongById(songId);
        Artist artist = artistRetriever.findById(artistId);
        song.addArtist(artist);
    }
}
