package com.songify.domain.crud.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GetSongDto(Long id, String name, List<String> artist, String genre) {
}
