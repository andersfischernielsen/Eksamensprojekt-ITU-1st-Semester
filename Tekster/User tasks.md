User tasks
==========================================================

Checklist-tasks:
----------------------------------------------------------
1. Ny booking
	- Find/Tilføj rejse ud fra tid, pris, destination, flynummer
		- Tilføj info - book sæder - person info.
		- Udskriv booking
		- Gem booking
2. Ændre booking
	- Find booking ud fra - navn, cpr, bookingnr.
		- Tilføj/slet rejse
		- Vælg rejse (ud fra flynummer, destination, tidspunkt)
			- Slet/tilføj personer,
			- Ændre data - sæder
		- Udskriv booking ønsker sine reoplysningerger
		- Gem ændringer - slet hele bookingen.




Uddybede user tasks (evt. til tests)
----------------------------------------------------------
##Reservation:
- Reservere sæder til en afgang mellem to destinationer
	- Vælg to destinationer, og find alle afgange mellem disse to.
	
- Reservere sæder på et specifikt fly
	- Vælg flynummer, og find alle afgange med det fly.
	
- Reservere sæder sammen med en specifik person
	- Vælg personnavn, og find alle afgange med den person ombord.
	
- Reservere sæder til en afgang på et specifikt tidspunkt
	- Vælg to destinationer, og find alle afgange mellem disse to.
	
- Alle ovenstående, men ved siden af medrejsende
	- Man skal f.eks. kunne finde to destinationer, men kunne sidde ved siden af specifikke personer.

##Søgning:
- Søge efter rejse ud fra destination(er)
- Søge efter rejse ud fra flynummer
- Søge efter rejse ud fra person (navn)
- Søge efter rejse ud fra tidspunkt
- Søge efter ledige rejser
- Samme som ovenstående, men med søgning
- Alle ovenstående, men med ledige sæder

##Redigering:
- Følgende anvender alle ovenstående søge/oprettelsestasks, og kan derefter:
- Tilføje/fjerne personer til en eksisterende rejse
	- Find rejse ud fra bookingnummer, og redigér personer ombord.
- Ændre sæde til et andet sæde på samme fly
	- Find rejse ud fra bookingnummer og ændr sæde.
- Slette en hel reservation
	- Find reservation ud fra bookingnummer og fjern den. 



Ekstra uddybede tasks til tests:
-----------------------------------------------------------
Bruger(Person) ringer til sekretæren og ønsker at blive på sit ophold en uge længere (ændre sin rejse).
Brugeren opgiver sit reservationsnummer. Sekretæren indtaster data.
Brugerens bestillingsinfo kommer frem. Brugeren ønsker sine rejseoplysninger.
Brugeren ønsker at hans rejse skal vare en uge længere. Sekretæren finder en ny rejse og sletter den gamle.
Sekretæren kigger manualt om de kan sidde 2 ved siden af hinanden.
Oplys om bestillingen er gennemført.

