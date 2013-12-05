package flybooking;

import java.util.ArrayList;
import java.util.Date;

/**
 * An interface for creating reservations for plane journeys.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface ReservationInterface
{

    /**
     * Get the people in the reservation.
     * @return the persons on this reservation
     */
    ArrayList<Person> getPersons();

    /**
     * Returns the flight in the reservation.
     *
     * @return The flight in the reservation.
     */
    Flight getFlight();

    /**
     * Return the ID of the reservation.
     *
     * @return The ID of the reservation.
     */
    String getID();

    /**
     * Return the date the reservation was made.
     *
     * @return A Date object for the creation of the reservation.
     */
    Date getReservationDate();

    /**
     * Return the CPR of the person that's made the reservation.
     *
     * @return The CPR of the person that's made the reservation.
     */
    String getCPR();

    /**
     * Set the people in the reservation.
     *
     * @param person The person to add to the reservation.
     */
    
    /**
     * Generates an ID via the converter and sets it on the reservation.
     */
    void setID();
    
    /**
     * Set the ID of the reservation.
     * @param IDToSet The ID to set.
     */
    void setID(String IDToSet);
    
    public void addPerson(Person person);

    /**
     * Remove a person from the reservation.
     *
     * @param person The person to remove from the reservation.
     */
    public void removePerson(Person person);

    /**
     * Clear the old list of people and create a new one.
     */
    public void clearPersonList();
    
    /**
     * Set the flight for the reservation.
     *
     * @param flight The flight to set.
     */
    public void setFlight(Flight flight);

    /**
     * Set the date the reservation was made to specified date.
     * @param date The date to set.
     */
    public void setReservationDate(Date date);

    /**
     * Set the CPR number for the person paying for the reservation.
     *
     * @param CPR The CPR of the paying person.
     */
    public void setCPR(String CPR);

    /**
     * Set the price for the reservation.
     *
     * @param price The price for the reservation.
     */
    public void setPrice(double price);

    /**
     * Get the price for the reservation.
     *
     * @return The price for the reservation.
     */
    public double getPrice();

    /**
     * Book the seatIDs on this reservation.
     *
     * @param seatIDs The seats to book on this reservation.
     */
    public void bookSeats(ArrayList<String> seatIDs);

    /**
     * Get an array of the booked seats in the reservation.
     *
     * @return An ArrayList of SeatIDs as strings.
     */
    public ArrayList<String> getBookedSeats();

    /**
     * Set a list of people as the people currently in the reservation.
     *
     * @param persons A list of the people to put into reservation.
     */
    public void bookPersons(ArrayList<Person> persons);

    /**
     * Get a list of people booked in this reservation.
     *
     * @return A list of people booked in this reservation.
     */
    public ArrayList<Person> getBookedPersons();
    
}
