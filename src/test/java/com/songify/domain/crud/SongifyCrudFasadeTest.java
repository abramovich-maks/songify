package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;

class SongifyCrudFasadeTest {


    SongifyCrudFasade songifyCrudFasade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );

    @Test
    public void first() {
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("new Test artist")
                .build();
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);

        assert  response.id().equals(0L);
        assert  response.name().equals("new Test artist");
    }

    @Test
    public void first_wariant_B() {
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);

        assert  response.id().equals(0L);
        assert  response.name().equals("amigo");
    }
}