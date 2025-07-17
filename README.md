# Custom GitHub API

Aplikacja Spring Boot dostarczająca REST API dla listy nie fork'owanych repozytoriów danego użytkownika wraz z ich gałęziami oraz kodami sha ostatnich commit'ów. Używa GitHub API v3 (https://api.github.com). Dla nie istniejących użytkowników zwraca odpowiedź 404 z odpowiedzą zwrotną.

## Features
- **Endpoint**: `GET /api/v1/{username}`
- **Odpowiedź**:  Zwraca tablice JSON repozytoriów w formacie:
  - Nazwa repozytorium
  - Nazwa własciciela repozytoriun
  - Lista nazw gałęzi z ich kodami sha ostatnich commit'ów
- **Obsługa błędów**: Zwraca odpowiedż z kodem 404 dla nieistniejącego użytkownika:
  ```json
  {
      "status": 404,
      "message": "User {username} not found"
  }
  ```
- **Test integracyjny**: Test weryfikujący 'happy path' dla poprawnej nazwy użytkownika ("oktocat"), sprawdzający szczegóły repozytorium i gałęzi.
- 
## Wymagane
- Java 21
- Maven 3.8+
- Dostęp do internetu by uzyskać dostęp do GitHub API (`https://api.github.com`)

## Setup
1. Sklonuj repozytorium:
   ```
   git clone https://github.com/PREZESjohn/AtiperaTask.git
   ```
2. Przejdż do folderu aplikacji:
   ```
   cd AtiperaTask
   ```
3. Zbuduj aplikacje:
   ```
   mvn clean install
   ```
4. Uruchom aplikacje:
   ```
   mvn spring-boot:run
   ```

## Użycie
- **Endpoint**: Zrób żądanie GET na:
  ```
  http://localhost:8080/api/v1/{username}
  ```
  Zamień `{username}` na poprawną nazwę użytkownika GitHub (np., `octocat`).
- **Przykładowa odpowiedź** (dla `octocat`):
  ```json
  [
      {
          "repositoryName": "hello-world",
          "ownerLogin": "octocat",
          "branches": [
              {
                  "name": "main",
                  "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80c135d252"
              }
          ]
      }
  ]
  ```
- **Błąd** (dla nieistniejącego użyytkownika):
  ```json
  {
      "status": 404,
      "message": "User nonexistinguser not found"
  }
  ```

## Testowanie
Projekt zawiera test integracyjny (`GitApiControllerIntegrationTest`) który weryfikuje:
- Przypadek użycia dla żądania: `/api/v1/octocat`.
- **Założenia**:
    - Status odpowiedzi tp `200 OK`.
    - Zwraca przynajmniej jedno repozytorium (dla użyktownika octocat ma zwrócić repozytorium `hello-world`).
  - Sprawdza nazwe repozytorium, nazwę właściciela (`octocat`) i przynajmniej jedną gałęź wraz z kodem sha.
- Testy uruchamia sie przy użyciu polecenia:
  ```
  mvn test
  ```
