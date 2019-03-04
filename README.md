# MyOwnDictionary
Spring Boot App using Thymeleaf - custom dictionary to store user's vocabulary list and effectively repeat it

#PL

#Idea aplikacji
Aplikacja jest pomocą przy nauce języka angielskiego (lub dowolnego języka obcego). Pozwala użytkownikowi zapisywać wszystkie nowe słówka,  które poznaje przy nauce w bazie danych i następnie powtarzać je poprzez zgadywanie losowo wybranych słówek z listy. Dzięki odkładanym statystykom poprawnych i błędnych odpowiedzi per poszczególne słówko, w sekcji ze statystykami użytkownik może łatwo wylistować słówka, które najtrudniej mu zapamiętać (słówka z najniższym stosunkiem odpowiedzi poprawnych do odpowiedzi ogółem)

#Technologie:

*Backend
- Java 8
- Spring Boot 2.1.3 (web, jpa, security, thymeleaf)
- MySQL 8.0.13
- Maven 3.6.0

*Frontend
- HTML 5
- CSS 3
- Bootstrap 4
- JavaScript

#Funkcjonalność:

Funkcje użytkownika:
- dodawanie, update, usuwanie słówek
- wyszukiwanie słówka z listy
- zgadywanie wylosowanego angielskiego słówka
- zgadywanie wylosowanego polskiego słówka
- dostęp do statystyk zgadywania, tj. liczba udanych "zgadnięć", liczba prób i skuteczność(zgadnięcia/wylosowania) per słówko

Funkcje Administratora
- wszystkie funkcje użytkownika poza odkładaniem statystyk zgadywania
- dostęp do wszystkich słówek wprowadzonych przez użytkowników
- dostęp do statystyk zgadywania dla wszystkich słówek
- dostęp do listy użytkowników i danych o liczbie dodanych przez nich słówek oraz dacie ostatniej aktywności

