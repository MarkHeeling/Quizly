Voor het lokaal installeren van Quizly is het moet het volgende gesinstalleerd zijn
Benodigheden
Een code editor (Intellj, Visual Studio Code etc.)
Node.js
Java SDK

Backend
Om de backend klaar te maken voor de gebruik moeten de volgende stappen worden gevolgd:
Maak een mysql database aan
Ga vervolgens naar Quizly/quizly-server/src/main/resources/application.properties
Vul de dik gedrukte velden aan met de database gegevens van stap 1
## Server Properties spring.datasource.url= jdbc:mysql://localhost:3306/<NAAM DATABASE>? spring.datasource.username= USERNAMEspring.datasource.password= PASSWORD**
Genereer vervolgens een geldige SHA512 secret (Dit kan hier)
Vul de SHA512 secret in bij JOUW SECRET in het application.properties bestand## App Properties app.jwtSecret= <JOUW SECRET>
Vervolgens kun je de backend starten met de volgende commands
cd quizly-server
mvn springboot:run

Frontend
Om de frontend klaar te maken voor de gebruik moeten de volgende stappen worden gevolgd:
Zorg er eerst voor dat je in de goede directory zit met de command
cd quizly-server
Gebruik vervolgens de npm install command om de packages te installeren
Als alles is geinstalleerd kun je de frontend starten met de volgende command
npm start

Testdata
Om de applicatie te vullen met testdata kan er gebruikt gemaakt worden van deze Sql dump deze kun je importeren in de door jou aangemaakt database. Hiermee wordt het volgende toegevoegd:
1x Gebruiker met admin rechten
Inlogegevens:
admin
Wachtwoord12
1x Gebruiker zonder admin rechten
Inlogegevens:
gebruiker
Wachtwoord12
5x Quiz vragen

REST API


