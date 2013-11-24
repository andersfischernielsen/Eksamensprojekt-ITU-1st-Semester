
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
     * Return the CPR number of the person that's made the reservation.
     * @return The CPR number of the person that's made the reservation.
     */
    int getCPR(); 
    
    /**
     * Get the person that has paid for the reservation.
     * @return The person that has paid for the reservation.
     */
    Person getPayer();
    
    /**
     * Set the person that has paid for the reservation
     * @param payer The person that has paid for the reservation.
     */
    void setPayer(Person payer);
    
    /**
     * Return whether the reservation is paid (and therefore valid).
     * @return Whether the reservation is paid for.
     */
    boolean isPaid();
    
    /**
     * Set the people in the reservation. 
     * @param person The person to add to the reservation.
     */
    public void addPerson(Person person);

    /**
     * Remove a person from the reservation.
     * @param person The person to remove from the reservation.
     */
    public void removePerson(Person person);
    
    /**
     * Set the flight for the reservation
     * @param flight The flight to fly on/with.
     */
    public void setFlight(Flight flight);

    /**
     * Set the date the reservation was made to the current date.
     */
    public void setReservationDate();

    /**
     * Set the CPR number for the person paying for the reservation.
     * @param CPR The CPR of the paying person.
     */
    public void setCPR(int CPR);

    /**
     * Set whether the reservation has been paid for or not. 
     * @param paid Whether the reservation has been paid for. 
     */
    public void setPaid(boolean paid);
}
