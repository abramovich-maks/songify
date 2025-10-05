package com.songify.domain.crud;

import com.songify.domain.crud.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "song",
        indexes = @Index(
                name = "idx_song_name",
                columnList = "name"
        ))
class SongEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "song_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
            name = "song_id_seq",
            sequenceName = "song_id_seq",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    Instant releaseDate;

    private Long duration;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre genre;

    @ManyToMany
    private Set<Artist> artists;

    @Enumerated(EnumType.STRING)
    private SongLanguage language;

    public SongEntity(String name) {
        this.name = name;
    }

    SongEntity(final String name, final Instant releaseDate, final Long duration, final SongLanguage language) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.language = language;
    }

    void addArtist(final Artist artist) {
        artists.add(artist);
        artist.addSong(this);
    }

    void addAlbum(final Album album) {
        album.getSongs().add(this);
    }
}
