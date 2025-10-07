package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
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

    @Test
    @DisplayName("Should trow exception ArtistNotFound when id: 0")
    public void should_trow_exceptions_artist_not_found_when_id_was_zero() {
        // given
        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(0L));
        // then
        assertThat(throwable).isInstanceOf(ArtistNotFoundExceptions.class);
        assertThat(throwable.getMessage()).isEqualTo("Artist with id: 0 not found");
    }

    @Test
    @DisplayName("Should delete artist by id  when he have no albums")
    public void should_delete_artist_by_id_when_he_have_no_albums() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        ArtistDto response = songifyCrudFasade.addArtist(addArtist);
        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isNotEmpty();
        Long artistId = response.id();
        assertThat(songifyCrudFasade.getAlbumsByArtistId(artistId));
        // when
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFasade.findAllArtists(Pageable.unpaged())).isEmpty();
    }
}