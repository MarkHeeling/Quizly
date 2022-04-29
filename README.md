# Quizly

# Installatie handleiding

Om quizly lokaal te draaien is het volgende nodig

## Benodigheden

- Een code editor (Intellj, Visual Studio Code etc.)
- Node.js
- Java SDK

## **Backend**

Om de backend klaar te maken voor de gebruik moeten de volgende stappen worden gevolgd:

1. Maak een mysql database aan

2. Ga vervolgens naar `Quizly/quizly-server/src/main/resources/application.properties`

3. Vul de dik gedrukte velden aan met de database gegevens van stap 1 
spring.datasource.url= jdbc:mysql://localhost:3306/**NAAMDATABASE**
spring.datasource.username= **USERNAME**
spring.datasource.password= **PASSWORD**

4. Genereer vervolgens een geldige SHA512 secret ([Dit kan hier](https://passwordsgenerator.net/sha512-hash-generator/))

5. Vul de SHA512 secret in bij JOUW SECRET in het `application.properties` bestand
   `## App Properties `
   `app.jwtSecret= <`JOUW SECRET`>`

6. Vervolgens kun je de backend starten met de volgende commands 
   `cd quizly-server` 
   `mvn springboot:run`

## Frontend

Om de frontend klaar te maken voor de gebruik moeten de volgende stappen worden gevolgd:

1. Zorg er eerst voor dat je in de goede directory zit met de command `cd quizly-server`
2. Gebruik vervolgens de `npm install` command om de packages te installeren
3. Als alles is geinstalleerd kun je de frontend starten met de volgende command `npm start`

## Testdata

Om de applicatie te vullen met testdata kan er gebruikt gemaakt worden van deze [Sql dump](https://github.com/MarkHeeling/Quizly/blob/main/demo-data/test-data.sql) deze kun je importeren in de door jou aangemaakt database. Hiermee wordt het volgende toegevoegd:

- 1x Gebruiker met admin rechten **Inlogegevens:** admin Wachtwoord12
- 1x Gebruiker zonder admin rechten **Inlogegevens:** gebruiker Wachtwoord12
- 5x Quiz vragen

## REST API DOCUMENTATIE



## Authenticatie



### POST Gebruiker registreren

http://localhost:8080/api/auth/register

```
{
  "name": "Karel Appel",
  "username": "appelman",
  "email": "karel@gmail.com",
  "password": "Wachtwoord"
}
```



### POST Gebruiker inloggen

http://localhost:8080/api/auth/login

```
{
  "usernameOrEmail": "appelman",
  "password": "Wachtwoord"
}
```



## Gebruiker



### GET Ingelogde gebruiker ophalen

http://localhost:8080/api/user/me

**Authorization** Bearer Token

**Role** Admin, Gebruiker

### POST Gegevens gebruiker wijzigen

http://localhost:8080/api/user/update

**Authorization** Bearer Token

**Role** Admin, Gebruiker

```
{
  "name": "Karel Peer",
  "username": "karelpeer",
  "email": "peer@gmail.com"
}
```



### GET Profiel foto naam ophalen ingelogde gebruiker

http://localhost:8080/api/user/me/profile-picture

**Authorization** Bearer Token

**Role** Admin, Gebruiker



### GET Alle gebruikers ophalen

http://localhost:8080/api/user/users

**Authorization** Bearer Token

**Role** Admin



### DEL Gebruiker verwijderen met id

http://localhost:8080/api/user/deleteUser/2

**Authorization** Bearer Token

**Role** Admin



## Bestanden



### POST Foto uploaden

http://localhost:8080/api/file/upload

**Authorization** Bearer Token

**Role** Admin, Gebruiker



### GET Alle bestanden ophalen

http://localhost:8080/api/file/files

**Authorization** Bearer Token

**Role** Admin



## Vraag



### GET Vragen ophalen

http://localhost:8080/api/question/getQuestions

**Authorization** Bearer Token

**Role** Admin, Gebruiker



### GET Vraag ophalen met id

http://localhost:8080/api/question/1

**Authorization** Bearer Token

**Role** Admin, Gebruiker



### POST Nieuwe vraag aanmaken

http://localhost:8080/api/question/newQuestion

**Authorization** Bearer Token

**Role** Admin, Gebruiker

```
{
  "question": "Hoeveel dagen zitten er in een jaar?",
  "category": "Entertainment",
  "correct_answer": "365",
  "incorrect_answer": "356,354,364"
}
```



### DEL Vraag verwijderen met id

http://localhost:8080/api/question/deleteQuestion/2

**Authorization** Bearer Token

**Role** Admin, Gebruiker
