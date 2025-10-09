package com.songify.domain.crud;

class SongifyCrudFacadeConfiguration {

    public static SongifyCrudFacade createSongifyCrud(final SongRepository songRepository,
                                                      final GenreRepository genreRepository,
                                                      final ArtistRepository artistRepository,
                                                      final AlbumRepository albumRepository) {
        SongRetriever songRetriever = new SongRetriever(songRepository);
        GenreRetriever genreRetriever = new GenreRetriever(genreRepository);
        AlbumRetriever albumRetriever = new AlbumRetriever(albumRepository);
        ArtistRetriever artistRetriever = new ArtistRetriever(artistRepository);
        SongAdder songAdder = new SongAdder(songRepository, genreRetriever, artistRetriever, albumRetriever);
        SongDeleter songDeleter = new SongDeleter(songRepository,songRetriever);
        SongUpdater songUpdater = new SongUpdater(songRepository, songRetriever, genreRetriever, albumRetriever, artistRetriever);
        SongAssigner songAssigner = new SongAssigner(songRetriever, artistRetriever, albumRetriever, genreRetriever);
        GenreAdder genreAdder = new GenreAdder(genreRepository);
        GenreUpdater genreUpdater = new GenreUpdater(genreRetriever);
        AlbumAdder albumAdder = new AlbumAdder(songRetriever, albumRepository);
        AlbumDeleter albumDeleter = new AlbumDeleter(albumRepository,albumRetriever);
        AlbumUpdater albumUpdater = new AlbumUpdater(albumRetriever, artistRetriever, songRetriever);
        ArtistAdder artistAdder = new ArtistAdder(artistRepository, albumAdder, songAdder);
        ArtisDeleter artisDeleter = new ArtisDeleter(artistRepository, artistRetriever, albumDeleter, albumRetriever, songDeleter);
        ArtistAssigner artistAssigner = new ArtistAssigner(artistRetriever, albumRetriever);
        ArtistUpdater artistUpdater = new ArtistUpdater(artistRetriever);
        return new SongifyCrudFacade(
                songAdder,
                songRetriever,
                songDeleter,
                songUpdater,
                songAssigner,
                genreAdder,
                genreRetriever,
                genreUpdater,
                albumAdder,
                albumRetriever,
                albumDeleter,
                albumUpdater,
                artistAdder,
                artistRetriever,
                artisDeleter,
                artistAssigner,
                artistUpdater
        );
    }
}
