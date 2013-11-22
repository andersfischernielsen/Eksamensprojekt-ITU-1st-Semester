
package flybooking;

import java.util.ArrayList;
import java.util.Date;

/**
 * An interface for creating reservations for plane journeys.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface ReservationInterface {
    
    /**
     * 
     * @return the persons on this reservation
     */
    ArrayList<Person> getPersons();
    
    /**
     * Returns all the journeys of this travel.
     * @return the journeys of this travel.
     */
    Flight getFlight();
    
    /**
     * Return the ID of the reservation.
     * @return The ID of the reservation.
     */
    int getID();
    
    /**
     * Return the creation date of the reservation.
     * @return A Date object for the creation of the reservation.
     */
    Date getReservationDate();

    /**
     * Return the date the journey begins.
     * @return A Date object for the beginning of the journey.
     */
    Date getStartDate();

    /**
     * Return the date the journey ends.
     * @return A Date object for the end of the journey.
     */
    Date getEndDate();
        
    
    /**
     * Return the CPR number of the person that's made the reservation.
     * @return The CPR number of the person that's made the reservation.
     */
    int getCPR(); 
    
    /**
     * Return whether the reservation is paid (and therefore valid).
     * @return Whether the reservation is paid for.
     */
    boolean isPaid();
}
