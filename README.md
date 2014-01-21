Eksamensprojekt - GPP på ITU
===============

Flybookingssystem
---------------

![First screen](https://raw.github.com/andersfischern/Eksamensprojekt/master/README/1.png)

##Mål:
Dette er det færdige resultat af vores tre uger lange eksamensprojekt i første semester på IT Universitetet i København. 

Målet med projektet var at udarbejde et program, hvor formålet var at styre flyreservationer for et mindre flyselskab. 

Flyselskabet har forskellige flytyper og flyafgange (destinationer og tidspunkter).
Det var meningen, at det udviklede system skulle anvendes af en ansat hos flyselskabet og kun betjenes af én ansat ad gangen. Kunder kunne ringe til flyselskabet og foretage reservationer, men ikke reservere over internettet.
Systemet skulle gøre det muligt for brugeren at oprette reservationer, at rette i disse samt at give overblik over reservationer i en bestemt periode, på bestemte afgange eller på bestemte ruter /destinationer.

Programmet henter tilgængelige afgange fra en MySQL-database og viser disse i en brugergrænseflade, der gør brugeren i stand til at finde afgange, reservere sæder og booke samt redigere rejser. Den færdige løsning er udviklet i NetBeans, med en database-backend på ITUs server.


##Udførelse:
Et krav for programmet var, at det skulle gemme/hente reservationer fra en database. Dette er gjort med en MySQL-database på ITU’s server. 
Projektet blev skrevet i Java og (meget lidt) SQL. 
Gruppen bag projektet bestod af tre studerende. 


###Screenshots:

![New Reservation](https://raw.github.com/andersfischern/Eksamensprojekt/master/README/2.png)

![Edit Passengers](https://raw.github.com/andersfischern/Eksamensprojekt/master/README/3.png)