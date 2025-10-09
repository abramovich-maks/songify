package com.songify.domain.songplayer;

import com.songify.domain.crud.dto.GetSongDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SongifyCrudFacadeTest {

    com.songify.domain.crud.SongifyCrudFacade songifyCrudFacade = mock(com.songify.domain.crud.SongifyCrudFacade.class);
    YoutubeHttpClient youtubeHttpClient = mock(YoutubeHttpClient.class);

    SongifyCrudFacade songPlayerFacade = new SongifyCrudFacade(
            songifyCrudFacade,
            youtubeHttpClient
    );

    @Test
    @DisplayName("Should return success when played song with id")
    public void should_return_success_when_played_song_with_id() {
        // given
        when(songifyCrudFacade.findSongDtoById(any())).thenReturn(
                GetSongDto.builder()
                        .id(1L)
                        .name("songName")
                        .build()
        );
        when(youtubeHttpClient.playSongByName(any())).thenReturn("success");
        // when
        String result = songPlayerFacade.playSongWithId(1L);
        // then
        assertThat(result).isEqualTo("success");
    }

    @Test
    @DisplayName("should throw exception")
    public void should_throw_exception() {
        // given
        when(songifyCrudFacade.findSongDtoById(any())).thenReturn(
                GetSongDto.builder()
                        .id(1L)
                        .name("songName")
                        .build()
        );
        when(youtubeHttpClient.playSongByName(any())).thenReturn("some failure");
        // when
        Throwable throwable = catchThrowable(() -> songPlayerFacade.playSongWithId(1L));
        // then
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable.getMessage()).isEqualTo("some error - result failed");
    }

}
