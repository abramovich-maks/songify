# SONGIFY: Aplikacja do Zarządzania Albumami, Artystami i Piosenkami

## Wymagania:

1. ~~Można dodać artystę (nazwa artysty)~~
2. ~~Można dodać gatunek muzyczny (nazwa gatunku)~~
3. ~~Można dodać album (tytuł, data wydania, ale musi być w nim przynajmniej jedna piosenka)~~
4. ~~Można dodać piosenkę (tytuł, czas trwania, data wydania, język piosenki)~~ + ~~od razu dodawać genre, artiste, album (albo zostawic na pozniej)~~
5. ~~Można dodać artystę od razu z albumem i z piosenką (domyślne wartości)~~
6. ~~Można usunąć artystę (usuwamy wtedy jego piosenki oraz albumy, ale jeśli było więcej niż jeden artysta w albumie, to usuwamy tylko artystę z albumu i samego artystę)~~
7. Można usunąć gatunek muzyczny (ale nie może istnieć piosenka z takim gatunkiem)
8. ~~Można usunąć album (ale dopiero wtedy kiedy nie ma już żadnej piosenki przypisanej do albumu)~~
9. ~~Można usunąć piosenkę, ale nie usuwamy albumu i artystów gdy była tylko 1 piosenka w albumie~~
10. ~~Można edytować nazwę artysty~~
11. ~~Można edytować nazwę gatunku muzycznego~~
12. ~~Można edytować album (dodawać/usuwać piosenki, artystów, zmieniać nazwę albumu)~~ 
13. ~~Można edytować piosenkę (nazwe piosenki, date wydania, długość piosenki w sekundach, język piosenki, id gatunku muzycznego, id artysty, id albumu)~~
14. ~~Można przypisać piosenki do albumów (poprzez song)~~
15. ~~Można przypisać piosenki do artysty (poprzez song)~~
16. ~~Można przypisać artystów do albumów (album może mieć więcej artystów, artysta może mieć kilka albumów)~~ 
17. ~~Można przypisać tylko jeden gatunek muzyczny do piosenki (poprzez song)~~ 
18. ~~Gdy nie ma przypisanego gatunku muzycznego do piosenki, to wyświetlamy "default"~~
19. ~~Można wyświetlać wszystkie piosenki~~
20. ~~Można wyświetlać wszystkie gatunki~~
21. ~~Można wyświetlać wszystkich artystów~~
22. ~~Można wyświetlać wszystkie albumy~~
23. ~~Można wyświetlać konkretne albumy z artystami oraz piosenkami w albumie~~
24. ~~Można wyświetlać konkretne gatunki muzyczne wraz z piosenkami i artystami~~
25. ~~Można wyświetlać konkretnych artystów wraz z ich albumami~~
26. ~~Chcemy mieć trwałe dane~~
    ##### SECURITY:
27. Kazdy bez uwierzytelnienia (authentication) moze przegladac piosenki, albumy itp (gosc niezalogowany)~~
28. Są 2 role: ROLE_USER i ROLE_ADMIN
29. Uzywanie bezstanowego tokena JWT (uzyskuje go po zalogowaniu) - wlasna implementacja authorization i potem oauth2 google
30. tylko admin moze przejrzec loginy i role uzytkownikow endpoint /users
31. zeby zostac uzytkownikiem trzeba sie zarejestrowac login/haslo - wlasna implementacja i GOOGLE
32. zapisujemy uzytkownika i admina do bazy danych (w przypadku wlasnej implementacji) - admin tworzony w migracji flyway
33. uzytkownik moze wyswietlac piosenki, ale nie moze zarzadzac (w przyszlosci uzytkownik moze miec swoj profil, a tam "ulubione piosenki") - ROLE_USER
34. tylko admin moze zmieniac stan aplikacji (usuwac, dodawac, edytowac piosenki/albumy itp)
35. chcemy miec szyfrowanie HTTPS, certyfikat wygenerowany recznie openssl
36. chcemy miec obsługe CORS - zapytania z domeny frontendowej
37. chcemy zabezpiecznie CSRF bo bedzie frontend uzywany
38. jako bonus potwierdzenie e-maila po rejestracji


# HAPPY PATH:

**Feature:** Tworzenie albumu, piosenek, artystów i gatunków oraz weryfikacja CRUD.

**Given** że wcześniej nie istnieją żadne piosenki, artyści, albumy ani gatunki.

1. **When** I go to `GET /songs` **Then** widzę brak piosenek.
2. **When** I ` /songs` z piosenką "Till i collapse" (tytuł, czas, data, język) **Then** zwrócona piosenka ma `id = 1`.
3. **When** I ` /songs` z piosenką "Lose Yourself" **Then** zwrócona piosenka ma `id = 2`.
    - (pokrywa wymaganie 4 — dodawanie piosenki)

4. **When** I go to `GET /genres` **Then** widzę brak gatunków.
5. **When** I ` /genres` z gatunkiem "Rap" **Then** zwrócony gatunek ma `id = 1`.
    - (pokrywa wymaganie 2 — dodawanie gatunku)

6. **When** I go to `GET /songs/1` **Then** widzę gatunek `"default"`.
    - (pokrywa wymaganie 18 — domyślny gatunek)

7. **When** I `PUT /songs/1/genre/1` **Then** gatunek `id = 1` ("Rap") jest przypisany do piosenki `id = 1`.
8. **When** I `GET /songs/1` **Then** widzę gatunek "Rap" dla piosenki `id = 1`.
9. **When** I `PUT /songs/2/genre/1` **Then** gatunek "Rap" jest przypisany do piosenki `id = 2`.
    - (pokrywa wymagania 15 i 17)

10. **When** I go to `GET /albums` **Then** widzę brak albumów.
11. **When** I ` /albums` z albumem "EminemAlbum1" i listą piosenek zawierającą `id = 1` **Then** zwrócony album ma `id = 1`.
    - (pokrywa wymaganie 3)

12. **When** I `GET /albums/1` **Then** widzę że album zawiera piosenkę `id = 1`.
13. **When** I `PUT /albums/1/songs/1` **Then** piosenka `id = 1` jest przypisana do albumu `id = 1`.
14. **When** I `PUT /albums/1/songs/2` **Then** piosenka `id = 2` jest przypisana do albumu `id = 1`.
15. **When** I `GET /albums/1/songs` **Then** widzę piosenki `id = 1` i `id = 2`.

16. **When** I ` /artists` z artystą "Eminem" **Then** zwrócony artysta ma `id = 1`.
17. **When** I `PUT /albums/1/artists/1` **Then** artysta `id = 1` ("Eminem") jest przypisany do albumu `id = 1`.

18. **When** I ` /artists` z artystą "Guest" **Then** zwrócony artysta ma `id = 2`.
19. **When** I `PUT /albums/1/artists/2` **Then** artysta `id = 2` jest przypisany do albumu `id = 1`.

20. **When** I `DELETE /artists/2` **Then** artysta `id = 2` jest usunięty, w albumie `id = 1` pozostaje tylko artysta `id = 1`, piosenki pozostają.

21. **When** I `DELETE /genres/1` **Then** operacja jest odrzucona (4xx) bo istnieją piosenki z tym gatunkiem.

22. **When** I `DELETE /songs/1` **Then** piosenka `id = 1` jest usunięta, album `id = 1` i artysta `id = 1` pozostają.
23. **When** I `DELETE /songs/2` **Then** piosenka `id = 2` jest usunięta.
24. **When** I `GET /albums/1/songs` **Then** widzę brak piosenek w albumie `id = 1`.
25. **When** I `DELETE /albums/1` **Then** album `id = 1` jest usunięty.

26. **When** I `PUT /artists/1` z nazwą "EminemUpdated" **Then** nazwa artysty zostaje zaktualizowana.
27. **When** I `PUT /genres/1` z nazwą "RapUpdated" **Then** nazwa gatunku zostaje zmieniona.

28. **When** I ` /albums` z nowym albumem "NewAlbum" i piosenką w payloadzie **Then** mogę:
    - dodawać/usuwać piosenki (`PUT/DELETE /albums/{id}/songs/{id}`),
    - dodawać/usuwać artystów (`PUT/DELETE /albums/{id}/artists/{id}`),
    - zmieniać nazwę albumu (`PUT /albums/{id}`).

29. **When** I `PUT /songs/{id}` z nowym tytułem, czasem trwania lub przypisaniem artysty **Then** piosenka zostaje zaktualizowana.

30. **When** I `GET /songs` **Then** widzę wszystkie piosenki.
31. **When** I `GET /genres` **Then** widzę wszystkie gatunki.
32. **When** I `GET /artists` **Then** widzę wszystkich artystów.
33. **When** I `GET /albums` **Then** widzę wszystkie albumy.
34. **When** I `GET /albums/{id}` **Then** widzę album wraz z artystami i piosenkami.
35. **When** I `GET /genres/{id}` **Then** widzę gatunek z piosenkami.
36. **When** I `GET /artists/{id}` **Then** widzę artystę z jego albumami.


# HAPPY PATH Version_2 (after refactoring)

#### **Feature:** Tworzenie albumu, piosenek, artystów i gatunków oraz weryfikacja CRUD.
#### **Opis:** Użytkownik może tworzyć, edytować, przypisywać i usuwać piosenki, gatunki, albumy oraz artystów. Scenariusz obejmuje pełen cykl życia danych w systemie Songify.
#### **Given:** Wcześniej nie istnieją żadne piosenki, artyści, albumy ani gatunki.

1. **When** I `GET /songs` **Then** widzę brak piosenek.
2. **When** I `POST /songs` z piosenką "Till I Collapse", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "ENGLISH" (name, releaseDate, duration, language) **Then** zwrócona piosenka ma `id = 1`.
3. **When** I `POST /songs` z piosenką "Lose Yourself", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "ENGLISH" **Then** zwrócona piosenka ma `id = 2`.
4. **When** I `GET /genres` **Then** widzę brak gatunków.
5. **When** I `POST /genres` z gatunkiem "Rap" **Then** zwrócony gatunek ma `id = 1`.
6. **When** I `GET /songs/1` **Then** widzę id: `1`, name: `Till I Collapse`, artysta: `Nieznany artysta`, genre: `"default"`, album: `Brak albumu`, releaseDate: `2025-10-10T08:57:09.358Z`, language: `ENGLISH`.
7. **When** I `PUT /songs/1/genre/1` **Then** gatunek `id = 1` ("Rap") jest przypisany do piosenki `id = 1`.
8. **When** I `GET /songs/1` **Then** widzę gatunek "Rap" dla piosenki `id = 1`.
9. **When** I `PUT /songs/2/genre/1` **Then** gatunek "Rap" jest przypisany do piosenki `id = 2`.
10. **When** I `GET /albums` **Then** widzę brak albumów.
11. **When** I `POST /albums` z albumem "EminemAlbum1", listą piosenek zawierającą `id = 1`, releaseDate: "2025-10-10T09:12:13.212Z" **Then** zwrócony album ma `id = 1`.
12. **When** I `GET /albums/1` **Then** widzę, że album zawiera piosenkę `id = 1`, ale brak przypisanych artystów.
13. **When** I `PUT /albums/1/song/2` **Then** piosenka `id = 2` jest przypisana do albumu `id = 1`.
14. **When** I `GET /albums/1` **Then** widzę piosenki `id = 1` i `id = 2`, ale brak przypisanych artystów.
15. **When** I `POST /artists` z artystą "Eminem" **Then** zwrócony artysta ma `id = 1`.
16. **When** I `PUT /albums/1/artists/1` **Then** artysta `id = 1` ("Eminem") jest przypisany do albumu `id = 1`.
17. **When** I `POST /artists` z artystą "Guest" **Then** zwrócony artysta ma `id = 2`.
18. **When** I `PUT /albums/1/artists/2` **Then** artysta `id = 2` jest przypisany do albumu `id = 1`.
19. **When** I `GET /albums/1` **Then** widzę, że album zawiera piosenki `id = 1`, `id = 2` oraz artystów: `id = 1`, `id = 2`.
20. **When** I `DELETE /artists/2` **Then** artysta `id = 2` jest usunięty, w albumie `id = 1` pozostaje tylko artysta `id = 1`, piosenki pozostają.
---
21. **When** I `DELETE /genres/1` **Then** operacja jest odrzucona (4xx), bo istnieją piosenki z tym gatunkiem.
---
22. **When** I `DELETE /songs/1` **Then** piosenka `id = 1` jest usunięta, album `id = 1` i artysta `id = 1` pozostają.
23. **When** I `GET /albums/1` **Then** widzę, że została tylko piosenka z `id = 2`.
24. **When** I `DELETE /songs/2` **Then** piosenka `id = 2` jest usunięta, album `id = 1` i artysta `id = 1` pozostają.
25. **When** I `GET /albums/1` **Then** widzę, że w album nie posiada piosenek`.

26. **When** I `DELETE /albums/1` **Then** album `id = 1` jest usunięty.
27. **When** I `PUT /artists/1` z nazwą "EminemUpdated" **Then** nazwa artysty zostaje zaktualizowana.
28. **When** I `PUT /genres/1` z nazwą "RapUpdated" **Then** nazwa gatunku zostaje zmieniona.
29. **When** I `POST /songs` z piosenką "NewSong3", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "POLISH" **Then** zwrócona piosenka ma `id = 3`.
30. **When** I `POST /albums` z nowym albumem "NewAlbum" i piosenką `id = 3` **Then** mogę:
    - dodawać/usuwać piosenki (`PUT/DELETE /albums/{id}/songs/{id}`),
    - dodawać/usuwać artystów (`PUT/DELETE /albums/{id}/artists/{id}`),
    - zmieniać nazwę albumu/releaseDate (`PATCH /albums/{id}`).
31. **When** I `PUT /songs/3/artist/1` **Then** piosenka `id = 3` jest przypisana do artysty `id = 1`.
32. **When** I `PUT /songs/3/album/2` **Then** piosenka `id = 3` jest przypisana do albumu `id = 2`.
32. **When** I `POST /songs` z piosenką "Song_4", releaseDate: "2025-10-10T08:57:09.358Z", duration: 123, language: "ENGLISH" **Then** zwrócona piosenka ma `id = 4`.
33. **When** I `PUT /songs/4/album/2` **Then** piosenka `id = 4` jest przypisana do albumu `id = 2`.
34. **When** I `PUT /songs/3/genre/1` **Then** piosenka `id = 3` jest przypisana do gatunku `id = 1`.
35. **When** I `PATCH /songs/3` **Then** edytuje name, releaseDate, duration.
36. **When** I `POST /genres` z gatunkiem "Pop" **Then** zwrócony gatunek ma `id = 2`.
37. **When** I `POST /artists` z artystą "Balak" **Then** zwrócony artysta ma `id = 3`.
38. **When** I `PATCH /songs/3` **Then** edytuje language, genreId, listę artistIds.
39. **When** I `POST /artist/album/song` z nazwą artysty "DefaultArtist" **Then** utworzy artystę z `id = 3` oraz utworzy przypisany album z przypisaną do artysty piosenką.
40. **When** I `PATCH /albums/2` **Then** mogę zmienić title albo releaseDate.
41. **When** I `GET /songs` **Then** widzę wszystkie piosenki.
42. **When** I `GET /genres` **Then** widzę wszystkie gatunki.
43. **When** I `GET /artists` **Then** widzę wszystkich artystów.
44. **When** I `GET /albums` **Then** widzę wszystkie albumy.
45. **When** I `GET /songs/3` **Then** widzę piosenkę wraz z listą artystów, gatunkiem, albumem, releaseDate oraz językiem.
46. **When** I `GET /albums/2` **Then** widzę album wraz z artystami i piosenkami.
47. **When** I `GET /genres/2` **Then** widzę gatunek z piosenkami.
48. **When** I `GET /artists/4` **Then** widzę artystę z jego albumami.