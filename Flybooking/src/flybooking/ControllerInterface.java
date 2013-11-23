
package flybooking;

/**
 * An interface for creating reservations and controlling the booking system in
 * general.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface ControllerInterface {

    //(FISCHER) JEG HAR LAVET NOGLE GENERELLE METODER. DER MANGLER EN DEL, 
    //OG DE SKAL SIKKERT ÆNDRES/UDDYBES, MEN SÅ HAR VI ET STED AT STARTE. 
    
    /**
     * Begin the process of creating a new reservation.
     */
    void createReservation();

    /**
     * Save the reservation when finished.
     */
    void saveReservation();

    /**
     * Get a specific reservation.
     */
    void getReservation();

    /**
     * Delete a reservation.
     */
    void deleteReservation();

    /**
     * Print the receipt of the reservation.
     */
    void printReceipt();

    /**
     * Get the total price of the reservation.
     */
    void getPrice();

    /**
     * Find a flight.
     */
    void search(); //SEARCH BLIVER MERE AVANCERET AT LAVE, SÅ VI SKAL SIKKERT HAVE EN DEL ANDRE SØGEMETODER.

    /**
     * Checks to see if anyone is next to the specified person.
     * @param person The person to check for neighbors.
     * @return Whether there are any neighbors next to the specified person.
     */
    boolean hasPersonNextTo(Person person);
    
    /**
     * Get the person next to the specified person.
     * @param person The person to check for neighbors.
     */
    void personNextTo(Person person);

    //JEG VED IKKE HELT OM VI SKAL OPFATTE CONTROLLER-KLASSEN SOM EN DER STYRER ALTING, 
    //ELLER VI GODT KAN KALDE F.EKS. addPerson() PÅ RESERVATIONEN ISTEDET. 
    //DET MEST ELEGANTE VILLE VÆRE AT CONTROLLEREN STYRER ALTING, MEN SÅ FÅR VI BARE EN DEL METODER,
    //DER BARE KALDER EN TILSVARENDE METODE PÅ ET ANDET OBJEKT (F.EKS. addPerson()).
}
