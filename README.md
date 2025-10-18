
# 🎵 SONGIFY  
**Aplikacja do Zarządzania Albumami, Artystami i Piosenkami**

[Frontend (React + Vite) → repozytorium](https://github.com/abramovich-maks/songify-frontend)

Songify to aplikacja typu **CRUD**, stworzona w technologii **Spring Boot**, służąca do kompleksowego zarządzania danymi muzycznymi — **albumami**, **piosenkami**, **artystami** i **gatunkami muzycznymi**.  
Projekt posiada pełną obsługę **autoryzacji i uwierzytelniania (JWT + Google OAuth2)**, a dane są przechowywane w bazie **PostgreSQL** uruchamianej w **Dockerze**.

Aplikacja backendowa działa w trybie **HTTPS** z własnym certyfikatem SSL oraz współpracuje z frontendem stworzonym w **Vite + React**.

---

## 🧩 Główne funkcjonalności

### 🎶 CRUD i relacje
- **CRUD dla:**
  - Artystów (dodawanie, edycja, usuwanie, wyświetlanie)
  - Gatunków muzycznych
  - Albumów (przypisywanie wielu artystów i piosenek)
  - Piosenek (z przypisanym artystą, albumem, gatunkiem)
- **Relacje:**
  - Jeden gatunek → wiele piosenek  
  - Jeden artysta ↔ wiele albumów  
  - Jeden album ↔ wiele piosenek  
  - Wiele artystów ↔ jeden album  
- **Domyślne wartości:** gdy brak artysty lub gatunku, wyświetlany jest `"default"`.

### 👤 Uwierzytelnianie i autoryzacja
- **Role użytkowników:**
  - `ROLE_USER` — może przeglądać dane  
  - `ROLE_ADMIN` — może dodawać, edytować i usuwać dane
- **Uwierzytelnianie:**
  - **JWT Token** (rejestracja, logowanie, potwierdzenie e-maila)  
  - **Google OAuth2 Login**
- Rejestracja wymaga potwierdzenia e-maila.
- Admin tworzony automatycznie przez migrację Flyway.

### 🔒 Bezpieczeństwo
- **Spring Security** + **JWT + OAuth2**
- **HTTPS** (certyfikat wygenerowany przez `OpenSSL`)
- **CORS** (dla połączenia z frontendem na porcie `5174`)
- **CSRF Protection**
- Klucze generowane z wykorzystaniem **Algorithm.RSA256**

---

## ⚙️ Stos technologiczny

**Backend:**
- Java 17+
- Spring Boot
- Spring MVC
- Spring Security (JWT, OAuth2)
- Hibernate / JPA
- PostgreSQL (Docker)
- Flyway
- Maven
- JUnit 5, Mockito (testy jednostkowe i integracyjne)

**Frontend:**
- React + Vite *(uruchamiany na osobnym porcie: `https://localhost:5174`)*

---

## 🧱 Architektura systemu

| Komponent     | Technologia         | Adres                          |
|---------------|-------------------|--------------------------------|
| Backend       | Spring Boot       | `https://localhost:8443`       |
| Frontend      | Vite + React      | `https://localhost:5174`       |
| Baza danych   | PostgreSQL (Docker)| `localhost:5432`              |

---

## 🖼️ Diagram bazy danych
![Diagram bazy danych](./images/db-diagram.png)

---

## 🧪 Testy

Projekt zawiera:
- **Testy jednostkowe** (JUnit 5, Mockito)  
- **Testy integracyjne** (Spring Boot Test / Spring Security Test)  

Testowane są m.in. kontrolery REST, warstwa serwisowa, autoryzacja i interakcje z bazą danych.

---

## 🐳 Uruchamianie projektu

### 1️⃣ Uruchom bazę danych PostgreSQL w Dockerze:

    docker-compose up -d

### 2️⃣ Uruchom backend

    mvn spring-boot:run

Backend dostępny pod adresem: 👉 https://localhost:8443

### 3️⃣ Uruchom frontend (repozytorium powyżej)

    npm install

    npm run dev  

Frontend dostępny pod adresem: 👉 https://localhost:5174


## 🧾 Dokumentacja API

### 🔗 Endpointy REST

Poniżej kompletna lista dostępnych endpointów (ścieżki oraz ich opis).


### 🎵 Songs
| Metoda | Endpoint | Opis |
|--------|---------|-----|
| GET    | `/songs` | Wyświetla wszystkie piosenki |
| GET    | `/songs/{songId}` | Wyświetla konkretną piosenkę wraz z artystami, albumem, gatunkiem, datą wydania, językiem |
| POST   | `/songs` | Tworzy nową piosenkę |
| PUT    | `/songs/{songId}` | Edytuje pełne dane piosenki |
| PATCH  | `/songs/{songId}` | Aktualizuje częściowe dane piosenki |
| DELETE | `/songs/{songId}` | Usuwa piosenkę |
| PUT    | `/songs/{songId}/genre/{genreId}` | Przypisuje gatunek do piosenki |
| PUT    | `/songs/{songId}/artist/{artistId}` | Przypisuje artystę do piosenki |
| PUT    | `/songs/{songId}/album/{albumId}` | Przypisuje album do piosenki |

---

### 💿 Albums
| Metoda | Endpoint | Opis |
|--------|---------|-----|
| GET    | `/albums` | Wyświetla wszystkie albumy |
| GET    | `/albums/{albumId}` | Wyświetla konkretny album wraz z artystami i piosenkami |
| POST   | `/albums` | Tworzy nowy album |
| PATCH  | `/albums/{albumId}` | Aktualizuje dane albumu |
| PUT    | `/albums/{albumId}/song/{songId}` | Dodaje piosenkę do albumu |
| DELETE | `/albums/{albumId}/song/{songId}` | Usuwa piosenkę z albumu |
| PUT    | `/albums/{albumId}/artists/{artistId}` | Przypisuje artystę do albumu |
| DELETE | `/albums/{albumId}/artists/{artistId}` | Usuwa artystę z albumu |
| DELETE | `/albums/{albumId}` | Usuwa album |

---

### 🎸 Artists
| Metoda | Endpoint | Opis |
|--------|---------|-----|
| GET    | `/artists` | Wyświetla wszystkich artystów |
| GET    | `/artists/{artistId}` | Wyświetla konkretnego artystę |
| GET    | `/artists/{artistId}/albums` | Wyświetla konkretnego artystę z jego albumami|
| POST   | `/artists` | Tworzy nowego artystę |
| POST   | `/artists/album/song` | Tworzy nowego artystę, od razu z albumem i z piosenką (domyślne wartości)  |
| PUT    | `/artists/{artistId}` | Edytuje nazwę artysty |
| PUT    | `/artists/{artistId}/{albumId}` | Przypisuje artystę do albumu |
| DELETE | `/artists/{artistId}` | Usuwa artystę |

---

### 🎧 Genres
| Metoda | Endpoint | Opis |
|--------|---------|-----|
| GET    | `/genres` | Wyświetla wszystkie gatunki muzyczne |
| GET    | `/genres/{genreId}` | Wyświetla konkretny gatunek |
| POST   | `/genres` | Tworzy nowy gatunek |
| PUT    | `/genres/{genreId}` | Edytuje nazwę gatunku |
| DELETE | `/genres/{genreId}` | Usuwa gatunek (jeśli nie ma powiązanych piosenek) |

---

### 🔐 Auth / Users
| Metoda | Endpoint | Opis |
|--------|---------|-----|
| POST   | `/register` | Rejestracja użytkownika (login/hasło) + wysłanie maila potwierdzającego |
| POST   | `/token` | Logowanie użytkownika → otrzymanie JWT tokena |
| GET   | `/token` | Logowanie przez Google OAuth2 |
| GET    | `/users` | (tylko ROLE_ADMIN) Wyświetlanie listy użytkowników i ich ról |

## ✅ Wymagania funkcjonalne (spełnione)

-   Dodawanie / edycja / usuwanie artystów
    
-   Dodawanie / edycja / usuwanie gatunków muzycznych
    
-   Dodawanie / edycja / usuwanie albumów i piosenek
    
-   Powiązania między artystami, albumami i piosenkami
    
-   Usuwanie artysty powoduje:
    
    -   usunięcie jego albumów i piosenek
        
    -   jeśli album był współdzielony z innym artystą — pozostaje w bazie
        
-   Gatunek muzyczny nie może być usunięty, jeśli istnieją powiązane piosenki
    
-   Użytkownik niezalogowany może przeglądać dane (publiczne endpointy)
    
-   Tylko admin może modyfikować dane
    
-   HTTPS, CORS, CSRF — aktywne zabezpieczenia
    
-   Email potwierdzający rejestrację użytkownika
    

----------

## 🔮 Przyszłe plany (TODO)

-   Profil użytkownika z listą „ulubionych piosenek”
    
-   Upload okładek albumów
    
-   Dashboard administratora
    
-   Statystyki najczęściej odtwarzanych utworów
    
-   Wersja mobilna (React Native)
    

----------

## 👨‍💻 Autor

Projekt stworzony w ramach nauki technologii  **Spring Boot**,  **Security**,  **JWT**,  **OAuth2**,  **Docker**,  **PostgreSQL**.  
Aplikacja ma charakter edukacyjny, ale odwzorowuje realny system zarządzania danymi muzycznymi.
