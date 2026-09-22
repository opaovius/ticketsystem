# Ticketsystem

Ein rollenbasiertes Ticketsystem, welches als Backend Spring Boot und als Frontend HTML/CSS/JavaScript nutzt.

Ich nutze das Projekt, um Spring Boot in Kombination mit Frontend zu lernen. Es ist daher nur ein Spaß- und Lernprojekt und nicht für den produktiven Einsatz gedacht. Falls man es ohne lokale Installation austesten möchte geht dies auf meiner [Website](https://ticket.opaovi.de/html/login.html)

Test-User-EMail: 		peter@mail.com 

Test-Support-EMail: 	Sup@mail.de

Test-Support-EMail2:	Sup2@mail.de
					
Test-Password: 			password1 (bei allen drei gleich)

# Installation

1. Repository klonen
2. Datenbank anlegen
3. application.yamp.example anpassen
4. Projekt starten

# API-Übersicht

| Methode | Endpoint | Beschreibung | Zugriff |
|---|---|---|---|
| POST | `/auth/login` | Login, setzt Refresh-Token-Cookie und liefert Access-Token | öffentlich |
| POST | `/auth/refresh` | Erneuert den Access-Token anhand des Refresh-Cookies | öffentlich (Cookie erforderlich) |
| POST | `/auth/logout` | Löscht das Refresh-Token-Cookie | öffentlich |
| GET | `/auth/me` | Liefert die Daten des eingeloggten Nutzers | authentifiziert |
| GET | `/tickets/customer` | Eigene Tickets abrufen | CUSTOMER |
| GET | `/tickets/support` | Zugewiesene Tickets abrufen | SUPPORT |
| POST | `/tickets/customer/create` | Neues Ticket erstellen | CUSTOMER |
| PUT | `/tickets/updateStatus/{id}` | Ticketstatus aktualisieren | CUSTOMER, SUPPORT |
| POST | `/reply/create` | Antwort auf ein Ticket verfassen | CUSTOMER, SUPPORT |

# Zukunft

- Admin Rolle einfügen
- Gelöste Tickets optional ausblenden können
- Sortieren der Tickets zB nach Status
