package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SongifyCrudFasadeTest {


    SongifyCrudFasade songifyCrudFasade = SongifyCrudFacadeConfiguration.createSongifyCrud(
            new InMemorySongRepository(),
            new InMemoryGenreRepository(),
            new InMemoryArtistRepository(),
            new InMemoryAlbumRepository()
    );

    @Test
    @DisplayName("Should add artist 'ArtistName' with id:0 When 'ArtistName' was sent ")
    public void should_add_artist_artistname_with_id_zero_when_artistname_was_sent() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("ArtistName")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(Pageable.unpaged());
        allArtists.isEmpty();
        assertTrue(allArtists.isEmpty());
        // when
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("ArtistName");
        int size = songifyCrudFasade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should add artist 'amigo' with id:0 When 'amigo' was sent ")
    public void should_add_artist_amigo_with_id_zero_when_amigo_was_sent() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(Pageable.unpaged());
        allArtists.isEmpty();
        assertTrue(allArtists.isEmpty());
        // when
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("amigo");
        int size = songifyCrudFasade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }
}