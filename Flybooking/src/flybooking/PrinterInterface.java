
package flybooking;

import java.text.SimpleDateFormat;

/**
 * An interface for creating a printer that prints reservation receipts.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface PrinterInterface {

    /**
     * Print the full receipt. 
     */
    String print();

    
     /**
     * Create the details about the reservationsID.
     * @return a string with the reservationID
     */
    String createReservationIDDetails();
    
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
    
    /**
     * Create the details about the price of the reservation.
     * @return a String with the price of the reservation.
     */
    String createPriceDetails();
   
}
