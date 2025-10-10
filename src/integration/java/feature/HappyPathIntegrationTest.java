package feature;

import com.songify.SongifyApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SongifyApplication.class)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class HappyPathIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    public MockMvc mockMvc;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

    @Test
    public void happy_path() throws Exception {
        // 1. **When** I `GET /songs` **Then** widzę brak piosenek.
        mockMvc.perform(get("/songs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songs", empty()));

        // 2. **When** I `POST /songs` z piosenką "Till I Collapse", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "ENGLISH" (name, releaseDate, duration, language) **Then** zwrócona piosenka ma `id = 1`.
        mockMvc.perform(post("/songs")
                        .content("""
                                {
                                  "name": "Till I Collapse",
                                  "releaseDate": "2025-10-10T08:57:09.358Z",
                                  "duration": 123,
                                  "language": "ENGLISH"
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(1)))
                .andExpect(jsonPath("$.song.name", is("Till I Collapse")))
                .andExpect(jsonPath("$.song.genre", is("default")));

        // 3. **When** I `POST /songs` z piosenką "Lose Yourself", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "ENGLISH" **Then** zwrócona piosenka ma `id = 2`.
        mockMvc.perform(post("/songs")
                        .content("""
                                {
                                  "name": "Lose Yourself",
                                  "releaseDate": "2025-10-10T08:57:09.358Z",
                                  "duration": 123,
                                  "language": "ENGLISH"
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(2)))
                .andExpect(jsonPath("$.song.name", is("Lose Yourself")))
                .andExpect(jsonPath("$.song.genre", is("default")));

        // 4. **When** I `GET /genres` **Then** widzę brak gatunków.
        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genre", empty()));


        // 5. **When** I `POST /genres` z gatunkiem "Rap" **Then** zwrócony gatunek ma `id = 1`.
        mockMvc.perform(post("/genres")
                        .content("""
                                {
                                  "name": "Rap"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Rap")));

        // 6. **When** I `GET /songs/1` **Then** widzę id: `1`, name: `Till I Collapse`, artysta: `Nieznany artysta`, genre: `"default"`, album: `Brak albumu`, releaseDate: `2025-10-10T08:57:09.358Z`, language: `ENGLISH`.
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Till I Collapse")))
                .andExpect(jsonPath("$.artists[0]", is("Nieznany artysta")))
                .andExpect(jsonPath("$.genre", is("default")))
                .andExpect(jsonPath("$.album", is("Brak albumu")))
                .andExpect(jsonPath("$.releaseDate", is("2025-10-10T08:57:09.358Z")))
                .andExpect(jsonPath("$.language", is("ENGLISH")));

        // 7. **When** I `PUT /songs/1/genre/1` **Then** gatunek `id = 1` ("Rap") jest przypisany do piosenki `id = 1`.
        mockMvc.perform(put("/songs/1/genre/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("For the song with id: 1 the genre was changed to id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 8. **When** I `GET /songs/1` **Then** widzę gatunek "Rap" dla piosenki `id = 1`.
        mockMvc.perform(get("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Till I Collapse")))
                .andExpect(jsonPath("$.artists[0]", is("Nieznany artysta")))
                .andExpect(jsonPath("$.genre", is("Rap")))
                .andExpect(jsonPath("$.album", is("Brak albumu")))
                .andExpect(jsonPath("$.releaseDate", is("2025-10-10T08:57:09.358Z")))
                .andExpect(jsonPath("$.language", is("ENGLISH")));

        // 9. **When** I `PUT /songs/2/genre/1` **Then** gatunek "Rap" jest przypisany do piosenki `id = 2`.
        mockMvc.perform(put("/songs/2/genre/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("For the song with id: 2 the genre was changed to id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 10. **When** I `GET /albums` **Then** widzę brak albumów.
        mockMvc.perform(get("/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.allAlbums", empty()));

        // 11. **When** I `POST /albums` z albumem "EminemAlbum1", listą piosenek zawierającą `id = 1`, releaseDate: "2025-10-10T09:12:13.212Z" **Then** zwrócony album ma `id = 1`.
        mockMvc.perform(post("/albums")
                        .content("""
                                {
                                  "songId": 1,
                                  "title": "EminemAlbum1",
                                  "releaseDate": "2025-10-10T09:12:13.212Z"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("EminemAlbum1")));

        // 12. **When** I `GET /albums/1` **Then** widzę, że album zawiera piosenkę `id = 1`, ale brak przypisanych artystów.
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(1)))
                .andExpect(jsonPath("$.album.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.artist", empty()))
                .andExpect(jsonPath("$.song[0].id", is(1)))
                .andExpect(jsonPath("$.song[0].name", is("Till I Collapse")))
                .andExpect(jsonPath("$.song[0].genreName", is("Rap")));

        // 13. **When** I `PUT /albums/1/song/2` **Then** piosenka `id = 2` jest przypisana do albumu `id = 1`.
        mockMvc.perform(put("/albums/1/song/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned song with id: 2 to album with id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 14. **When** I `GET /albums/1` **Then** widzę piosenki `id = 1` i `id = 2`, ale brak przypisanych artystów.
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(1)))
                .andExpect(jsonPath("$.album.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.artist", empty()))
                .andExpect(jsonPath("$.song[0].id", is(1)))
                .andExpect(jsonPath("$.song[0].name", is("Till I Collapse")))
                .andExpect(jsonPath("$.song[0].genreName", is("Rap")))
                .andExpect(jsonPath("$.song[1].id", is(2)))
                .andExpect(jsonPath("$.song[1].name", is("Lose Yourself")))
                .andExpect(jsonPath("$.song[1].genreName", is("Rap")));

        // 15. **When** I `POST /artists` z artystą "Eminem" **Then** zwrócony artysta ma `id = 1`.
        mockMvc.perform(post("/artists")
                        .content("""
                                {
                                  "name": "Eminem"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Eminem")));

        // 16. **When** I `PUT /albums/1/artists/1` **Then** artysta `id = 1` ("Eminem") jest przypisany do albumu `id = 1`.
        mockMvc.perform(put("/albums/1/artist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned artist with id: 1 to album with id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 17. **When** I `POST /artists` z artystą "Guest" **Then** zwrócony artysta ma `id = 2`.
        mockMvc.perform(post("/artists")
                        .content("""
                                {
                                  "name": "Guest"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Guest")));

        // 18. **When** I `PUT /albums/1/artists/2` **Then** artysta `id = 2` jest przypisany do albumu `id = 1`.
        mockMvc.perform(put("/albums/1/artist/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned artist with id: 2 to album with id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 19. **When** I `GET /albums/1` **Then** widzę, że album zawiera piosenki `id = 1`, `id = 2` oraz artystów: `id = 1`, `id = 2`.
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(1)))
                .andExpect(jsonPath("$.album.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.artist[0].id", is(2)))
                .andExpect(jsonPath("$.artist[0].name", is("Guest")))
                .andExpect(jsonPath("$.artist[1].id", is(1)))
                .andExpect(jsonPath("$.artist[1].name", is("Eminem")))
                .andExpect(jsonPath("$.song[0].id", is(1)))
                .andExpect(jsonPath("$.song[0].name", is("Till I Collapse")))
                .andExpect(jsonPath("$.song[0].genreName", is("Rap")))
                .andExpect(jsonPath("$.song[1].id", is(2)))
                .andExpect(jsonPath("$.song[1].name", is("Lose Yourself")))
                .andExpect(jsonPath("$.song[1].genreName", is("Rap")))
                .andExpect(jsonPath("$.artist", hasSize(2)))
                .andExpect(jsonPath("$.song", hasSize(2)));

        // 20. **When** I `DELETE /artists/2` **Then** artysta `id = 2` jest usunięty, w albumie `id = 1` pozostaje tylko artysta `id = 1`, piosenki pozostają.
        mockMvc.perform(delete("/artists/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Artist with id: 2 have been deleted.")))
                .andExpect(jsonPath("$.status", is("OK")));

        // ---
        // 21. **When** I `DELETE /genres/1` **Then** operacja jest odrzucona (4xx), bo istnieją piosenki z tym gatunkiem.
        // ---

        // 22. **When** I `DELETE /songs/1` **Then** piosenka `id = 1` jest usunięta, album `id = 1` i artysta `id = 1` pozostają.
        mockMvc.perform(delete("/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Song with id: 1 have been deleted.")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 23. **When** I `GET /albums/1` **Then** widzę, że została tylko piosenka z `id = 2`.
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(1)))
                .andExpect(jsonPath("$.album.name", is("EminemAlbum1")))
                .andExpect(jsonPath("$.artist[0].id", is(1)))
                .andExpect(jsonPath("$.artist[0].name", is("Eminem")))
                .andExpect(jsonPath("$.song[0].id", is(2)))
                .andExpect(jsonPath("$.song[0].name", is("Lose Yourself")))
                .andExpect(jsonPath("$.song[0].genreName", is("Rap")))
                .andExpect(jsonPath("$.artist", hasSize(1)))
                .andExpect(jsonPath("$.song", hasSize(1)));

        // 24. **When** I `DELETE /songs/2` **Then** piosenka `id = 2` jest usunięta, album `id = 1` i artysta `id = 1` pozostają.
        mockMvc.perform(delete("/songs/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Song with id: 2 have been deleted.")))
                .andExpect(jsonPath("$.status", is("OK")));

//        25. **When** I `GET /albums/1` **Then** widzę, że w album nie posiada piosenek`.
        mockMvc.perform(get("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song", empty()));

//        26. **When** I `DELETE /albums/1` **Then** album `id = 1` jest usunięty.
        mockMvc.perform(delete("/albums/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Album with id: 1 deleted successfully")))
                .andExpect(jsonPath("$.status", is("OK")));

//        27. **When** I `PUT /artists/1` z nazwą "EminemUpdated" **Then** nazwa artysty zostaje zaktualizowana.
        mockMvc.perform(put("/artists/1")
                        .content("""
                                {
                                  "newArtistName": "EminemUpdated"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("EminemUpdated")));

        //28. **When** I `PUT /genres/1` z nazwą "RapUpdated" **Then** nazwa gatunku zostaje zmieniona.
        mockMvc.perform(put("/genres/1")
                        .content("""
                                {
                                  "newGenreName": "RapUpdated"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("RapUpdated")));

        //29. **When** I `POST /songs` z piosenką "NewSong3", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "POLISH" **Then** zwrócona piosenka ma `id = 3`.
        mockMvc.perform(post("/songs")
                        .content("""
                                {
                                  "name": "NewSong3",
                                  "releaseDate": "2025-10-10T08:57:09.358Z",
                                  "duration": 123,
                                  "language": "ENGLISH"
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(3)))
                .andExpect(jsonPath("$.song.name", is("NewSong3")));

        //30. **When** I `POST /albums` z nowym albumem "NewAlbum" i piosenką `id = 3` **Then** mogę:
        //- dodawać/usuwać piosenki (`PUT/DELETE /albums/{id}/songs/{id}`),
        //- dodawać/usuwać artystów (`PUT/DELETE /albums/{id}/artists/{id}`),
        //- zmieniać nazwę albumu/releaseDate (`PATCH /albums/{id}`).
        mockMvc.perform(post("/albums")
                        .content("""
                                {
                                  "songId": 3,
                                  "title": "NewAlbum",
                                  "releaseDate": "2025-10-10T09:12:13.212Z"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("NewAlbum")));
        // sprawdzamy
        mockMvc.perform(get("/albums/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(2)))
                .andExpect(jsonPath("$.album.name", is("NewAlbum")))
                .andExpect(jsonPath("$.artist", empty()))
                .andExpect(jsonPath("$.song[0].id", is(3)))
                .andExpect(jsonPath("$.song[0].name", is("NewSong3")));
        // dodanie artysty
        mockMvc.perform(put("/albums/2/artist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned artist with id: 1 to album with id: 2")))
                .andExpect(jsonPath("$.status", is("OK")));
        // usuwanie piosenki
        mockMvc.perform(delete("/albums/2/song/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Song with id: 3 has been deleted from album with id: 2")))
                .andExpect(jsonPath("$.status", is("OK")));
        // sprawdzamy
        mockMvc.perform(get("/albums/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(2)))
                .andExpect(jsonPath("$.album.name", is("NewAlbum")))
                .andExpect(jsonPath("$.artist[0].id", is(1)))
                .andExpect(jsonPath("$.artist[0].name", is("EminemUpdated")))
                .andExpect(jsonPath("$.song", empty()))
                .andExpect(jsonPath("$.artist", hasSize(1)))
                .andExpect(jsonPath("$.song", hasSize(0)));

        // usuwanie artysta
        mockMvc.perform(delete("/albums/2/artist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Artist with id: 1 has been deleted from album with id: 2")))
                .andExpect(jsonPath("$.status", is("OK")));
        // dodanie piosenki
        mockMvc.perform(put("/albums/2/song/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned song with id: 3 to album with id: 2")))
                .andExpect(jsonPath("$.status", is("OK")));
        // sprawdzamy
        mockMvc.perform(get("/albums/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(2)))
                .andExpect(jsonPath("$.album.name", is("NewAlbum")))
                .andExpect(jsonPath("$.song[0].id", is(3)))
                .andExpect(jsonPath("$.song[0].name", is("NewSong3")))
                .andExpect(jsonPath("$.artist", empty()))
                .andExpect(jsonPath("$.artist", hasSize(0)))
                .andExpect(jsonPath("$.song", hasSize(1)));
//        zmieniamy nazwę albumu (`PATCH /albums/{id}`).
        mockMvc.perform(patch("/albums/2")
                        .content("""
                                {
                                  "title": "Patch update title and releaseDate",
                                  "releaseDate": "9999-10-10T08:57:09.358Z"
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Patch update title and releaseDate")))
                .andExpect(jsonPath("$.releaseDate", is("9999-10-10T08:57:09.358Z")));
        // sprawdzamy
        mockMvc.perform(get("/albums/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.album.id", is(2)))
                .andExpect(jsonPath("$.album.name", is("Patch update title and releaseDate")));

        //31. **When** I `PUT /songs/3/artist/1` **Then** piosenka `id = 3` jest przypisana do artysty `id = 1`.
        mockMvc.perform(put("/songs/3/artist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned song with id: 3 to artist with id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        // 32. **When** I `POST /songs` z piosenką "Song_4", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "ENGLISH" **Then** zwrócona piosenka ma `id = 4`.
        mockMvc.perform(post("/songs")
                        .content("""
                                {
                                  "name": "Song_4",
                                  "releaseDate": "2025-10-10T08:57:09.358Z",
                                  "duration": 123,
                                  "language": "ENGLISH"
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.song.id", is(4)))
                .andExpect(jsonPath("$.song.name", is("Song_4")))
                .andExpect(jsonPath("$.song.genre", is("default")));

        //33. **When** I `PUT /songs/4/album/2` **Then** piosenka `id = 4` jest przypisana do albumu `id = 2`.
        mockMvc.perform(put("/songs/4/album/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Assigned song with id: 4 to album with id: 2")))
                .andExpect(jsonPath("$.status", is("OK")));

        //34. **When** I `PUT /songs/3/genre/1` **Then** piosenka `id = 3` jest przypisana do gatunku `id = 1`.
        mockMvc.perform(put("/songs/3/genre/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("For the song with id: 3 the genre was changed to id: 1")))
                .andExpect(jsonPath("$.status", is("OK")));

        //35. **When** I `PATCH /songs/3` **Then** edytuje name, releaseDate, duration.
        mockMvc.perform(patch("/songs/3")
                        .content("""
                                {
                                  "name": "Alalala new songName",
                                  "releaseDate": "1111-10-10T08:57:09.358Z",
                                  "duration": 9999
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alalala new songName")))
                .andExpect(jsonPath("$.releaseDate", is("1111-10-10T08:57:09.358Z")))
                .andExpect(jsonPath("$.duration", is(9999)));
        // sprawdzamy
        mockMvc.perform(get("/songs/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Alalala new songName")))
                .andExpect(jsonPath("$.artists[0]", is("EminemUpdated")))
                .andExpect(jsonPath("$.genre", is("RapUpdated")))
                .andExpect(jsonPath("$.album", is("Patch update title and releaseDate")))
                .andExpect(jsonPath("$.releaseDate", is("1111-10-10T08:57:09.358Z")))
                .andExpect(jsonPath("$.language", is("ENGLISH")))
                .andExpect(jsonPath("$.duration", is(9999)));

        // 36. **When** I `POST /genres` z gatunkiem "Pop" **Then** zwrócony gatunek ma `id = 2`.
        mockMvc.perform(post("/genres")
                        .content("""
                                {
                                  "name": "Pop"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Pop")));

        // 37. **When** I `POST /artists` z artystą "Balak" **Then** zwrócony artysta ma `id = 3`.
        mockMvc.perform(post("/artists")
                        .content("""
                                {
                                  "name": "Balak"
                                }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Balak")));

        //38 **When** I `PATCH /songs/3` **Then** edytuje language, genreId, listę artistIds.
        mockMvc.perform(patch("/songs/3")
                        .content("""
                                {
                                  "language": "POLISH",
                                  "genreId": "2",
                                  "artistIds": [1,3]
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.language", is("POLISH")))
                .andExpect(jsonPath("$.genreId", is(2)))
                .andExpect(jsonPath("$.artistIds[*]", containsInAnyOrder(1, 3)));
        // sprawdzamy
        mockMvc.perform(get("/songs/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Alalala new songName")))
                .andExpect(jsonPath("$.artists[*]", containsInAnyOrder("EminemUpdated", "Balak")))
                .andExpect(jsonPath("$.genre", is("Pop")))
                .andExpect(jsonPath("$.album", is("Patch update title and releaseDate")))
                .andExpect(jsonPath("$.releaseDate", is("1111-10-10T08:57:09.358Z")))
                .andExpect(jsonPath("$.language", is("POLISH")))
                .andExpect(jsonPath("$.duration", is(9999)));

        //39. **When** I `POST /artist/album/song` z nazwą artysty "DefaultArtist" **Then** utworzy artystę z `id = 4` oraz utworzy przypisany album z przypisaną do artysty piosenką.
        mockMvc.perform(post("/artists/album/song")
                        .content("""
                                {
                                  "name": "DefaultArtist"
                                  }
                                """.trim())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artistId", is(4)))
                .andExpect(jsonPath("$.artistName", is("DefaultArtist")))
                .andExpect(jsonPath("$.albumName", startsWith("default-album")))
                .andExpect(jsonPath("$.songName", startsWith("default-song")));
    }
}


//40. **When** I `PATCH /albums/2` **Then** mogę zmienić title albo releaseDate.
//41. **When** I `GET /songs` **Then** widzę wszystkie piosenki.
//42. **When** I `GET /genres` **Then** widzę wszystkie gatunki.
//43. **When** I `GET /artists` **Then** widzę wszystkich artystów.
//44. **When** I `GET /albums` **Then** widzę wszystkie albumy.
//45. **When** I `GET /songs/{id}` **Then** widzę piosenkę wraz z listą artystów, gatunkiem, albumem, releaseDate oraz językiem.
//46. **When** I `GET /albums/{id}` **Then** widzę album wraz z artystami i piosenkami.
//47. **When** I `GET /genres/{id}` **Then** widzę gatunek z piosenkami.
//48. **When** I `GET /artists/{id}` **Then** widzę artystę z jego albumami.
