Noter til Projekt
=================

- Hvis man importerer biblioteker skal man huske at skrive kilde på, ellers anses det for at være plagiat.
- Alt hvad der ligger i JDK (arraylist osv.) er ikke nødvendigt at dokumentere.
- ITU har en database vi kan bruge.

## Opbygning af rapport:
- Forord
- Problembeskrivelse - hvad går systemet ud på? Skal skrives så folk på holdet næste år ville kunne modtage rapporten og forstå den.
- Giv eksempel på hvad systemet gør. Hvordan ser brugergrænsefladen ud og hvad gør den?
- Analyse hvor det ovenstående uddybes!
- Afsnit om overordnet design - vi bruger databaser, brugergrænseflader, kommunikation imellem dem osv.
- Efterprøvning/afprøvning - her skal vi argumentere for at vi har afprøvet vores system og at det virker. Hvilke generelle cases har vi afprøvet osv.
Forkert input, evt. usability.
Mht. test - det er ikke sikkert at vi kan nå at teste alt, så vælg centrale klasser ud og test dem for de forskellige cases! Så længe de kan se at vi godt kan finde ud af at teste, gør det ikke noget, at der er et par klasser, vi ikke har nået at teste. JUnit test.
- Konklusion/Perspektivering - Konklusion på projektet. Hvad fungerede, hvad kunne gøres bedre, hvordan kunne vi udvide i fremtiden? Vigtigt at man giver overblik over: det er hvad vi har tænkt, hvad vi har gjort og hvad vi har afprøvet.

## Andre noter:
- Hold koden på engelsk, men skriv rapporten på dansk. 
- Lad være med at skrive kode i selve rapporten! Brug kun tekst og figurer! Koden vedlægges i appendix!
- Lav interfaces så komponenter let kan udskiftes.
- Lav javadoc!