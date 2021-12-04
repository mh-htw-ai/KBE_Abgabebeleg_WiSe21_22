# Einrichtung der Datenbank für die Springboot-Anwendung

Diese Komponente benötigt eine PostgreSQL-Datenbank. 
Diese kann bei heroku.com im kleinen kostenlos angemeldet werden.


Um die Komponente zu verwenden benötigt es die Umgebungsvariabeln:
- "kbe_postgreDB_Abgabebeleg_Path"
- "kbe_postgreDB_Abgabebeleg_Benutzer"
- "kbe_postgreDB_Abgabebeleg_PW"

Diese werden für den Verbindungsaufbau verwendet.

## Umgebungsvariabeln in Windows einrichten

Bei Windows muss man das Fenster "Umgebungsvariabeln" öffnen. 
Dies ist möglich in dem man die "Windows-Taste" betätigt und "Umgebungsvariablen für dieses Konto bearbeiten" eingibt.
Es sollte sich das Fenster mit "Umgebungsvariabeln" öffnen. 
Hier muss man nun die oben erwähnten jeweils eine Variable erstellen in den Benutzervariablen für den jeweiligen Benutzer hinzufügen.
Als "Wert" muss dann die jeweilige Benutzerinformation eingetragen werden.

### Path - Erstellung anhand der Heroku-Credentials

Beispiel-Path:

jdbc:postgresql://ec9-99-99-99-999.eu-west-1.compute.amazonaws.com:8888/dbname

Schema von den Informationen aus den Heroku-Credentials:

jdbc:postgesql://[Host]:[Port]/[Database]