package com.songify.song.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Entity
@Getter
@Setter
@Table(name = "song")
@NoArgsConstructor
@AllArgsConstructor
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String artist;

    Instant releaseDate;

    private Long duration;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public SongEntity(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }
}
