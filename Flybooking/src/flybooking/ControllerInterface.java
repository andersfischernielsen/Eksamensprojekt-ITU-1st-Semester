
package flybooking;

import java.sql.SQLException;

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
    //HER SKAL VI NOK HAVE FLERE METODER. JEG VED BARE IKKE LIGE HELT PRÆCIST
    //HVORDAN VI TACKLER HELE DATABASEN OSV. 

    /**
     * Save the reservation when finished.
     *
     * @param database The database to save the reservation to.
     */
    void saveReservation(DatabaseInterface database);

    /**
     * Get a specific reservation.
     *
     * @param database The database to get the reservation from.
     */
    void getReservation(DatabaseInterface database, String reservationID, String CPR);

    /**
     * Delete a reservation.
     *
     * @param database The database to delete the reservation from.
     */
    void deleteReservation(DatabaseInterface database, String reservationID) throws SQLException;

    /**
     * Print the receipt of the reservation.
     *
     * @param reservation The reservation to print.
     * @param printer The printer to use to print.
     */
    void printReceipt(ReservationInterface reservation, ReceiptPrinter printer);

    /**
     * Get the total price of the reservation.
     *
     * @param calculator The calculator to use
     * @param reservation The reservation to calculate the price for.
     * @return The price for the reservation
     */
    int getPrice(Calculator calculator, Reservation reservation);

    /**
     * Find something.
     *
     * @param database The database to search in.
     */
    void search(DatabaseInterface database); //SEARCH BLIVER MERE AVANCERET AT LAVE, SÅ VI SKAL SIKKERT HAVE EN DEL ANDRE SØGEMETODER.

    /**
     * Checks to see if anyone is next to the specified person.
     *
     * @param person The person to check for neighbors.
     * @return Whether there are any neighbors next to the specified person.
     */
    boolean hasPersonNextTo(Person person);

    /**
     * Get the person next to the specified person.
     *
     * @param person The person to check for neighbors.
     * @return The person next to the specified person.
     */
    Person personNextTo(Person person);

    //JEG VED IKKE HELT OM VI SKAL OPFATTE CONTROLLER-KLASSEN SOM EN DER STYRER ALTING, 
    //ELLER VI GODT KAN KALDE F.EKS. addPerson() PÅ RESERVATIONEN ISTEDET. 
    //DET MEST ELEGANTE VILLE VÆRE AT CONTROLLEREN STYRER ALTING, MEN SÅ FÅR VI BARE EN DEL METODER,
    //DER BARE KALDER EN TILSVARENDE METODE PÅ ET ANDET OBJEKT (F.EKS. addPerson()).
    
    /**
     * Get the number of destinations from the database.
     *
     * @return The number of destinations in the database.
     * @throws java.sql.SQLException If something went wrong.
     */
    int getNumberOfDestinations() throws SQLException;
    
    /**
     * Get all destination cities from the database as strings.
     * @return A string array of cities in the database.
     * @throws java.sql.SQLException
     */
    String[] getDestinationsAsStrings() throws SQLException;
    
    /**
     * Check whether an ID already exists in the database. 
     * @return Whether the ID exists in the database.
     * @throws SQLException 
     */
    boolean checkForID(String ID) throws SQLException;
}
