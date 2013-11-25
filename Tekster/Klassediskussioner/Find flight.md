GUI:
	- Lav liste over datoer, fyld i dato-dropbdown
	- Lav talliste og fyld i people-dropdown
	- Hent airports fra controller, fyld i destinationsdropwdown
	- Lav søgeknap, der er bundet til søgemetode i controller
	- Ved søgemetodekald, bliver en fyld-liste-metode kaldt, der hente ralle flight-objekter returnerert fra controllerens liste over passende flight. 

Controller:
	- Hent datoliste
	- Hent alle Airports fra database
	- Søgemetode tager alle udfyldte felter, og klader søgemetode med disse felter i database-klassen. 
	
	
Databaseklasse:
	- Søgemetode tager imod søgeparametre, søger i database, og spytter data til controller, 
	der opretter alle objekter involveret.
	
Reservation:
	- Opret constructor med parametre, så søgemetode i databasen kan returnere reservationer. 