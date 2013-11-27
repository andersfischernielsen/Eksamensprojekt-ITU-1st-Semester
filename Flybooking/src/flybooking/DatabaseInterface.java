package flybooking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Anders Wind Steffensen, Chistoffer Forup & Anders Fischer-Nielsen
 */
public interface DatabaseInterface
{
    
    // returns the plane with ID PlaneID (needs to implement something with seats)
    Plane getPlane(String PlaneID);

    // returns an array of seats which are not available.
    ArrayList<Seat> getTakenSeats(Flight flight, Person[] persons);

    // returns an arraylist flight which respects the parameters. used in searching.
    ArrayList<FlightInterface> getFlight(Date DepartureDate, int amtOfPeople, Airport startDestination, Airport endDestination, boolean nextTo);

    // Returns an arrayList of reservations which respects the parameters.
    ArrayList<ReservationInterface> getReservation(String ReservationID, String CPR);

    // Enten den her
    void newReservation(Flight flight, Person[] persons, String CPR, double Price);

    // eller den 
    void newReservation(Reservation reservationToMake);

    // remove the reservation with ID reservationID
    void removeReservation(String reservationID);
    
    /**
     * Adds a person to a reservation.
     * @param reservationID The reservation in the database to add to.
     * @param personToAdd The person to add to the reservation.
     * @param reservationSpot The spot in the reservation to add the person (first, second etc.)
     * @throws java.sql.SQLException
     */
    void addPerson(String reservationID, Person personToAdd, String reservationSpot) throws SQLException;
    
    /**
     * Get all the available flights as an ArrayList.
     * @return An ArrayList of all the available flights.
     * @throws java.sql.SQLException An SQLException if something went wrong.
     */
    ArrayList<String> getAirportCitiesAsStrings() throws SQLException;
    
    /**
     * Check whether a given ID already exists. 
     * @return Whether the ID exists or not. 
     */
    boolean checkForID(String ID);
}
