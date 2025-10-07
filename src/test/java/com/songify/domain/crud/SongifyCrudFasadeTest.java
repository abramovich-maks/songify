package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SongifyCrudFasadeTest {


    SongifyCrudFasade songifyCrudFasade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );

    @Test
    public void first() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("new Test artist")
                .build();
        // when
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("new Test artist");
    }

    @Test
    public void first_wariant_B() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        // when
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);
        // then
        assertThat(response.id()).isEqualTo(70L);
        assertThat(response.name()).isEqualTo("amigo");
    }
}