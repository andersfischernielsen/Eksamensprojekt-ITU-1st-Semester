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
    
    /**
     * returns the plane with ID PlaneID (needs to implement something with seats)
     * @param PlaneID
     * @return 
     * @throws java.sql.SQLException 
     */
    Plane getPlane(String PlaneID)throws SQLException; 

    /**
     * returns an array of seats which are not available.
     * @param flight
     * @param persons
     * @return 
     */
    ArrayList<Seat> getTakenSeats(Flight flight, Person[] persons);

    /** 
     * Returns an ArrayList of flights that match the specified search terms.
     * @param departureDate The date to depart.
     * @param startDestination The start destination for the flight.
     * @param endDestination The end destination for the flight.
     * @return An array of flights that match the specified parameters.
     */
    ArrayList<Flight> getFlight(Date departureDate, 
                String startDestination, String endDestination)
                throws SQLException;

    /**
     * Returns an arrayList of reservations which respects the parameters.
     * @param ReservationID
     * @param CPR
     * @return 
     */
    ArrayList<ReservationInterface> getReservations(String reservationID, String CPR);

    /**
     * Enten den her
     * @param flight
     * @param persons
     * @param Price HUSK AT ÆNDRE DEN TIL LILLE SKRIFT!
     * @param CPR
     */
    void newReservation(Flight flight, Person[] persons, String CPR, double Price);

    /**
     * eller den 
     * @param reservationToMake
     */
    void newReservation(Reservation reservationToMake);

    /**
     * remove the reservation with ID reservationID
     * @param reservationID
     */
    void removeReservation(String reservationID) throws SQLException;
    
    /**
     * Adds a person to a reservation.
     * @param reservationID The reservation in the database to add to.
     * @param personToAdd The person to add to the reservation.
     * @param reservationSpot The spot in the reservation to add the person (first, second etc.)
     * @throws java.sql.SQLException
     */
    void addPersonToReservation(String reservationID, Person personToAdd, String reservationSpot) throws SQLException;
    
    /**
     * Get all the available flights as an ArrayList.
     * @return An ArrayList of all the available flights.
     * @throws java.sql.SQLException An SQLException if something went wrong.
     */
    ArrayList<String> getAirportCitiesAsStrings() throws SQLException;
    
    /**
     * Check whether a given ID already exists. 
     * @param ID
     * @return true if the ID exists.
     * @throws java.sql.SQLException 
     */
    boolean checkForID(String ID) throws SQLException;
}
