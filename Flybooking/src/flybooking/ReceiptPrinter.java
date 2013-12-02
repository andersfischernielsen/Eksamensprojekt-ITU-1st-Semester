
package flybooking;

import java.text.SimpleDateFormat;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface ReceiptPrinter {

    /**
     * Print the full receipt. 
     */
    String print();

    /**
     * Create the details about the departure and arrival dates and airports. 
     * @return A string with details about the airports and dates in the reservation. 
     */
    String createAirportDetails();

    /**
     * Create the details about the people in the reservation and the seats they're on. 
     * @return A string with details about the people in the reservation.
     */
    String createPeopleDetails(); 

    /**
     * Create the details about the plane in the reservation.
     * @return A string with details about the plane in the reservation. 
     */
    String createPlaneDetails();
}
