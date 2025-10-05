package com.songify.infrastructure.crud.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record GenreUpdateRequestDto(
        @NotNull(message = "newGenreName must not be null")
        @NotEmpty(message = "newGenreName must not be empty")
        @NotBlank(message = "newGenreName must not be blank")
        String newGenreName) {
}
