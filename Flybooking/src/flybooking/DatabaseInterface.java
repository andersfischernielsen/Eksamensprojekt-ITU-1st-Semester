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
     * returns the plane with ID PlaneID (needs to implement something with
     * seats)
     *
     * @param PlaneID
     *
     * @return
     *
     * @throws java.sql.SQLException
     */
    Plane getPlane(String PlaneID) throws SQLException;
    
    /**
     * Returns the airport with the airportID from the databse
     * @param AirportID the id of the airport we creates.
     * @return an airport
     * @throws SQLException 
     */
    Airport getAirport(String AirportID) throws SQLException;
    
    /**
     * Returns a person object created from the database's information
     * @param PersonID the personID to look for
     * @return a Person object
     * @throws SQLException 
     */
    Person getPerson(int PersonID) throws SQLException;

    /**
     * Returns an ArrayList of flights that match the specified search terms.
     *
     * @param departureDate    The date to depart.
     * @param startDestination The start destination for the flight.
     * @param endDestination   The end destination for the flight.
     *
     * @return An array of flights that match the specified parameters.
     */
    
    /**
     * returns a flight with flightID
     * @param flightID the flightID of the wanted flight
     * @return a flight object
     * @throws SQLException 
     */
    FlightInterface getFlight(int flightID) throws SQLException;
    
    ArrayList<Flight> getFlightList(Date departureDate,
                                String startDestination, String endDestination)
            throws SQLException;

    /**
     * Returns an arrayList of reservations which respects the parameters.
     *
     * @param ReservationID
     * @param CPR
     *
     * @return
     */
    ArrayList<ReservationInterface> getReservationList(String reservationID, String CPR) throws SQLException;

    /**
     * Enten den her
     *
     * @param flight
     * @param persons
     * @param Price   HUSK AT ÆNDRE DEN TIL LILLE SKRIFT!
     * @param CPR
     */
    void newReservation(Flight flight, Person[] persons, String CPR, double Price);

    /**
     * eller den
     *
     * @param reservationToMake
     */
    void newReservation(ReservationInterface reservationToMake)throws SQLException; 

    /**
     * remove the reservation with ID reservationID
     *
     * @param reservationID
     */
    void removeReservation(String reservationID) throws SQLException;

    /**
     * Updates a reservations persons, seats and price
     * @param reservationToMake the reservation to update
     * @throws SQLException 
     */
    void updateReservation(ReservationInterface reservationToMake)throws SQLException;
    
    /**
     * Adds a person to a reservation.
     *
     * @param reservationID   The reservation in the database to add to.
     * @param personToAdd     The person to add to the reservation.
     * @param reservationSpot The spot in the reservation to add the person
     *                        (first, second etc.)
     *
     * @throws java.sql.SQLException
     */
    void addPersonToReservation(String reservationID, Person personToAdd, String reservationSpot) throws SQLException;

    /**
     * Get all the available flights as an ArrayList.
     *
     * @return An ArrayList of all the available flights.
     *
     * @throws java.sql.SQLException An SQLException if something went wrong.
     */
    ArrayList<String> getAirportCitiesAsStrings() throws SQLException;

    /**
     * Check whether a given ID already exists.
     *
     * @param ID
     *
     * @return true if the ID exists.
     *
     * @throws java.sql.SQLException
     */
    boolean checkForID(int ID) throws SQLException;

    /**
     * Find all booked seats on a certain flight by searching through
     * reservations on that specific flight.
     *
     * @param FlightID the flight on which we want to see the booked seats
     *
     * @return an arrayList of seatId strings.
     */
    ArrayList<String> getAllBookedSeats(int flightID) throws SQLException;

    /**
     * Returns an array of persons who are kept in a reservation with ID
     * "reservationID"
     *
     * @param reservationID the reservationID to check for persons in
     *
     * @return an arrayList of persons.
     */
    ArrayList<Person> getBookedPersons(String reservationID) throws SQLException;

    /**
     * Find the booked seats on a specific reservation.
     *
     * @param reservationID the reservation we want to get the booked seats
     *                      from.
     *
     * @return an ArrayList of seatID strings
     */
    ArrayList<String> getBookedSeatsOnReservation(String reservationID) throws SQLException;

    /**
     * Get all matching Reservations from a CPR number.
     *
     * @param CPR The CPR to search with.
     *
     * @return An ArrayList of matching Reservations.
     */
    ArrayList<ReservationInterface> getReservationsFromCPR(String CPR);

    /**
     * Get all matching Reservations from an reservation ID.
     *
     * @param ID The ID to search with.
     *
     * @return An ArrayList of matching Reservations.
     */
    ArrayList<ReservationInterface> getReservationsFromID(String ID);
}
