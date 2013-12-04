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
     *
     * @return the persons on this reservation
     */
    ArrayList<Person> getPersons();

    /**
     * Returns all the journeys of this travel.
     *
     * @return the journeys of this travel.
     */
    Flight getFlight();

    /**
     * Return the ID of the reservation.
     *
     * @return The ID of the reservation.
     */
    String getID();

    /**
     * Return the creation date of the reservation.
     *
     * @return A Date object for the creation of the reservation.
     */
    Date getReservationDate();

    /**
     * Return the CPR number of the person that's made the reservation.
     *
     * @return The CPR number of the person that's made the reservation.
     */
    String getCPR();

    /**
     * Get the person that has paid for the reservation.
     *
     * @return The person that has paid for the reservation.
     */
    Person getPayer();

    /**
     * Set the person that has paid for the reservation
     *
     * @param payer The person that has paid for the reservation.
     */
    void setPayer(Person payer);

    /**
     * Set the people in the reservation.
     *
     * @param person The person to add to the reservation.
     */
    
    /**
     * Calculates an ID via the calculator and sets it
     */
    void setID();
    
    /**
     * set the ID of the reservation to IDToSet
     * @param IDToSet 
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
     * Set the flight for the reservation
     *
     * @param flight The flight to fly on/with.
     */
    public void setFlight(Flight flight);

    /**
     * Set the date the reservation was made to the current date.
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
     * @param seatIDs the seats to book on this reservation
     */
    public void bookSeats(ArrayList<String> seatIDs);

    /**
     * returns the SeatIDs arrayList in reservation.
     *
     * @return an arrayList of seatID strings
     */
    public ArrayList<String> getBookedSeats();

    /**
     * Puts an ArrayList of persons into the arrayList of persons that
     * reservation holds.
     *
     * @param persons the persons to put into reservation
     */
    public void bookPersons(ArrayList<Person> persons);

    /**
     * Takes the ArrayList of persons in the array and returns it.
     *
     * @return the arrayList of booked Persons on this reservation
     */
    public ArrayList<Person> getBookedPersons();
    
}
