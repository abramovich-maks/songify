package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumDtoWithArtistAndSongs;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.ArtistWithAlbumDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SongifyCrudFacadeTest {


    SongifyCrudFacade songifyCrudFacade = SongifyCrudFacadeConfiguration.createSongifyCrud(
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
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        allArtists.isEmpty();
        assertTrue(allArtists.isEmpty());
        // when
        ArtistDto response = songifyCrudFacade.addArtist(addArtist);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("ArtistName");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should add artist 'amigo' with id:0 When 'amigo' was sent ")
    public void should_add_artist_amigo_with_id_zero_when_amigo_was_sent() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Set<ArtistDto> allArtists = songifyCrudFacade.findAllArtists(Pageable.unpaged());
        allArtists.isEmpty();
        assertTrue(allArtists.isEmpty());
        // when
        ArtistDto response = songifyCrudFacade.addArtist(addArtist);
        // then
        assertThat(response.id()).isEqualTo(0L);
        assertThat(response.name()).isEqualTo("amigo");
        int size = songifyCrudFacade.findAllArtists(Pageable.unpaged()).size();
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Should trow exception ArtistNotFound when id: 0")
    public void should_trow_exceptions_artist_not_found_when_id_was_zero() {
        // given
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(0L));
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
        Long artistId = songifyCrudFacade.addArtist(addArtist).id();
        assertThat(songifyCrudFacade.getAlbumsByArtistId(artistId));
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
    }

    @Test
    @DisplayName("Should delete artist with album and songs by id When artist had one album and he was the only artist in album")
    public void should_delete_artist_with_album_and_songs_by_id_when_artist_had_one_album_and_he_was_the_only_artist_in_album() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Long artistId = songifyCrudFacade.addArtist(addArtist).id();

        GenreRequestDto genre = GenreRequestDto.builder()
                .name("Rap")
                .build();
        GenreDto genreDto = songifyCrudFacade.addGenre(genre);

        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .genreId(genreDto.id())
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();

        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(AlbumRequestDto
                .builder()
                .songId(songId)
                .title("album title 1")
                .build());

        Long albumId = albumDto.id();
        songifyCrudFacade.addArtistToAlbumById(albumId, artistId);

        assertThat(songifyCrudFacade.getAlbumsByArtistId(artistId).size()).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();

        Throwable songNotFound = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(songNotFound).isInstanceOf(SongNotFoundException.class);
        assertThat(songNotFound.getMessage()).isEqualTo("Song with id: 0 not found");

        Throwable albumNotFound = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId));
        assertThat(albumNotFound).isInstanceOf(AlbumNotFoundException.class);
        assertThat(albumNotFound.getMessage()).isEqualTo("Album with id: 0 not found");
    }

    @DisplayName("Should delete only artist from album by id When there were more than 1 artist in album")
    @Test
    public void should_delete_only_artist_from_album_by_id_when_there_were_more_than_one_artist_in_album() {
        // given
        ArtistRequestDto addArtist1 = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Long artistId1 = songifyCrudFacade.addArtist(addArtist1).id();

        ArtistRequestDto addArtist2 = ArtistRequestDto.builder()
                .name("diego")
                .build();
        Long artistId2 = songifyCrudFacade.addArtist(addArtist2).id();

        SongRequestDto newSong = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(newSong);
        Long songId = songDto.id();

        AlbumRequestDto album1 = AlbumRequestDto
                .builder()
                .songId(songId)
                .title("album title 1")
                .build();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album1);
        Long albumId = albumDto.id();

        songifyCrudFacade.addArtistToAlbumById(albumId, artistId1);
        songifyCrudFacade.addArtistToAlbumById(albumId, artistId2);

        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).size().isEqualTo(2);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId)).isEqualTo(2);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId2);
        // then
        AlbumDtoWithArtistAndSongs album = songifyCrudFacade.findAlbumByIdWithArtistAndSong(albumId);
        Set<ArtistDto> artist = album.artist();

        assertAll(
                () -> assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).size().isEqualTo(1),
                () -> assertThat(songifyCrudFacade.findArtistByIdWithAlbum(artistId1).artist().name()).isEqualTo(addArtist1.name()),
                () -> assertThat(artist)
                        .extracting("id")
                        .containsOnly(artistId1)
        );


        Throwable artistNotFound = catchThrowable(() -> songifyCrudFacade.findArtistByIdWithAlbum(artistId2));
        assertThat(artistNotFound).isInstanceOf(ArtistNotFoundExceptions.class);
        assertThat(artistNotFound.getMessage()).isEqualTo("Artist with id: " + artistId2 + " not found");

        AlbumDtoWithArtistAndSongs albumDetails = songifyCrudFacade.findAlbumByIdWithArtistAndSong(albumDto.id());
        Set<SongDto> songs = albumDetails.song();
        assertTrue(songs.stream().anyMatch(song -> song.id().equals(songDto.id())));

        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isNotEmpty();
        assertThat(songifyCrudFacade.findAlbumById(albumId).id()).isEqualTo(albumDto.id());

    }

    @Test
    @DisplayName("Should delete artist with album and songs by id When artist was the only artist in albums")
    public void should_delete_artist_with_albums_and_songs_by_id_when_artist_was_the_only_artist_in_albums() {
        // given
        ArtistRequestDto addArtist = ArtistRequestDto.builder()
                .name("amigo")
                .build();
        Long artistId = songifyCrudFacade.addArtist(addArtist).id();

        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();

        AlbumRequestDto album1 = AlbumRequestDto
                .builder()
                .songId(songId)
                .title("album title 1")
                .build();

        AlbumRequestDto album2 = AlbumRequestDto
                .builder()
                .songId(songId)
                .title("album title 2")
                .build();

        AlbumDto albumDto1 = songifyCrudFacade.addAlbumWithSong(album1);
        AlbumDto albumDto2 = songifyCrudFacade.addAlbumWithSong(album2);

        Long albumId1 = albumDto1.id();
        Long albumId2 = albumDto2.id();
        songifyCrudFacade.addArtistToAlbumById(albumId1, artistId);
        songifyCrudFacade.addArtistToAlbumById(albumId2, artistId);

        assertThat(songifyCrudFacade.getAlbumsByArtistId(artistId).size()).isEqualTo(2);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId1)).isEqualTo(1);
        assertThat(songifyCrudFacade.countArtistByAlbumId(albumId2)).isEqualTo(1);
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged()).size()).isEqualTo(1);
        // when
        songifyCrudFacade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        // then
        assertThat(songifyCrudFacade.findAllArtists(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();

        Throwable songNotFound = catchThrowable(() -> songifyCrudFacade.findSongDtoById(songId));
        assertThat(songNotFound).isInstanceOf(SongNotFoundException.class);
        assertThat(songNotFound.getMessage()).isEqualTo("Song with id: 0 not found");

        Throwable albumNotFound = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId1));
        assertThat(albumNotFound).isInstanceOf(AlbumNotFoundException.class);
        assertThat(albumNotFound.getMessage()).isEqualTo("Album with id: 0 not found");

        Throwable albumNotFound2 = catchThrowable(() -> songifyCrudFacade.findAlbumById(albumId2));
        assertThat(albumNotFound2).isInstanceOf(AlbumNotFoundException.class);
        assertThat(albumNotFound2.getMessage()).isEqualTo("Album with id: 1 not found");
    }

    @Test
    @DisplayName("Should add artist to album")
    public void should_add_artist_to_album() {
        // given
        ArtistRequestDto artist = ArtistRequestDto.builder()
                .name("Goga")
                .build();
        ArtistDto artistDto = songifyCrudFacade.addArtist(artist);

        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        songifyCrudFacade.addSong(songRequestDto);

        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songId(0L)
                .title("album title 1")
                .build();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        // when
        songifyCrudFacade.addArtistToAlbum(artistDto.id(), albumDto.id());
        ArtistWithAlbumDto artistWithAlbums = songifyCrudFacade.findArtistByIdWithAlbum(artistDto.id());
        // then
        boolean isAlbumPresent = artistWithAlbums.albums().stream().anyMatch(albumTitle -> albumTitle.name().equals("album title 1"));
        assertAll(
                () -> assertEquals(artist.name(), artistWithAlbums.artist().name(), "ArtistName not equals!"),
                () -> assertTrue(isAlbumPresent, "Artist not have album with title " + album.title())
        );
    }

    @Test
    @DisplayName("Should add song")
    public void should_add_song() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        // when
        SongDto newSongDto = songifyCrudFacade.addSong(song);
        // then
        assertThat(newSongDto.genreName()).isEqualTo("default");
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()))
                .extracting(SongDto::id)
                .containsExactly(0L);
    }

    @Test
    public void should_add_album_with_song() {
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(songRequestDto);

        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songId(0L)
                .title("album title 1")
                .build();

        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        // when
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);
        // then
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isNotEmpty();
        AlbumDtoWithArtistAndSongs albumDetails = songifyCrudFacade.findAlbumByIdWithArtistAndSong(albumDto.id());
        Set<SongDto> songs = albumDetails.song();
        assertTrue(songs.stream().anyMatch(song -> song.id().equals(songDto.id())));
    }

    @Test
    public void should_add_genre() {
        // given
        GenreRequestDto genre = GenreRequestDto.builder()
                .name("Rap")
                .build();
        // when
        GenreDto genreDto = songifyCrudFacade.addGenre(genre);
        // then
        assertThat(songifyCrudFacade.findGenreById(genreDto.id()).id()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return album by id")
    public void should_return_album_by_id() {
        // given
        SongRequestDto songRequestDto = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        songifyCrudFacade.addSong(songRequestDto);

        AlbumRequestDto album = AlbumRequestDto
                .builder()
                .songId(0L)
                .title("album title 1")
                .build();

        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        AlbumDto albumDto = songifyCrudFacade.addAlbumWithSong(album);

        // when
        AlbumDto getAlbumDto = songifyCrudFacade.findAlbumById(albumDto.id());
        // then
        assertAll(
                () -> assertEquals(0, getAlbumDto.id(), "Album not return id"),
                () -> assertEquals(album.title(), getAlbumDto.name(), "Album not return name"));
    }

    @Test
    @DisplayName("Should trow exception when album not found by id")
    public void should_throw_exception_when_album_not_found_by_id() {
        // given
        assertThat(songifyCrudFacade.findAllAlbums(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findAlbumById(2L));
        // then
        assertAll(
                () -> assertThat(throwable).isInstanceOf(AlbumNotFoundException.class),
                () -> assertThat(throwable.getMessage()).isEqualTo("Album with id: 2 not found"));
    }

    @Test
    @DisplayName("Should throw exceptions When song not found by id")
    public void should_throw_exception_when_song_not_found_by_id() {
        //given
        assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged())).isEmpty();
        // when
        Throwable throwable = catchThrowable(() -> songifyCrudFacade.findSongDtoById(1L));
        // then
        assertAll(
                () -> assertThat(throwable).isInstanceOf(SongNotFoundException.class),
                () -> assertThat(throwable.getMessage()).isEqualTo("Song with id: 1 not found"));
    }

    @Test
    @DisplayName("Should song by id with genre")
    public void should_retriever_song_by_id_with_genre() {
        // given
        SongRequestDto song = SongRequestDto.builder()
                .name("song1")
                .language(SongLanguageDto.DEFAULT)
                .build();
        SongDto songDto = songifyCrudFacade.addSong(song);
        Long songId = songDto.id();
        // when
        songifyCrudFacade.findSongDtoById(songId);
        // then
        assertAll(
                () -> assertThat(songifyCrudFacade.findAllSongs(Pageable.unpaged()))
                        .extracting(SongDto::id)
                        .containsExactly(0L),
                () -> assertEquals(songDto.genreName(), "default", "Genre not equals default"),
                () -> assertEquals(songDto.id(), 0L, "Id must be 0"),
                () -> assertEquals(songDto.name(), "song1", "SongName must be `song1`")
        );


    }
}