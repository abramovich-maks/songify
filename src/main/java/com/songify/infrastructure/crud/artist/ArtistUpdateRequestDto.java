package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ArtistUpdateRequestDto(
        @NotNull(message = "newArtistName must not be null")
        @NotEmpty(message = "newArtistName must not be empty")
        @NotBlank(message = "newArtistName must not be blank")
        String newArtistName) {
}
