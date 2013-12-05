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
     * returns the plane object with ID PlaneID
     *
     * @param PlaneID the planeID to look for in the database
     *
     * @return a plane object according to that of plane table with ID=PlaneID
     *         in the database
     */
    Plane getPlane(String PlaneID);

    /**
     * Returns the airport object with the airportID from the databse
     *
     * @param AirportID the id of the airport we creates.
     *
     * @return a plane object according to that of Airport table with
     *         ID=AirportID in the database
     */
    Airport getAirport(String AirportID);

    /**
     * Returns a person object created from the database's information
     *
     * @param PersonID the personID to look for
     *
     * @return a Person object according to that of People table with
     *         ID=PersonID in the database
     */
    Person getPerson(int PersonID);

    /**
     * Returns the flight object with flightID from the databse
     *
     * @param flightID the flightID of the wanted flight
     *
     * @return a flight object according to that of Flight table with
     *         ID=flightID in the database
     */
    FlightInterface getFlight(int flightID);

    /**
     * Returns an ArrayList of flight objects from the database which goes from
     * statDestination to endDestination and eventually within a timeframe og 4
     * days from the given depatureDate.
     *
     * @param departureDate    the depatureDate to search for, if null dont
     *                         search for date.
     * @param startDestination the startDestination the flight needs to have
     * @param endDestination   the endDestination the flight needs to have
     *
     * @return An array of flights that match the specified parameters.
     */
    ArrayList<FlightInterface> getFlightList(Date departureDate,
                                             String startDestination, String endDestination);

    /**
     * Returns an arrayList of reservations which, if CPR = null, has
     * reservationID or if reservationID = null has CPR.
     *
     * @param reservationID The ID to search for.
     * @param CPR           The CPR to search for.
     *
     * @return an ArrayList of reservation Objects.
     */
    ArrayList<ReservationInterface> getReservationList(String reservationID, String CPR);

    /**
     * Save a given reservation to the database by calling the insertSeat
     * method(If the class which implements this interface is a database),
     * insertPerson method and inserting the reservation's data in the
     * corresponding columns.
     *
     * @param reservationToMake the reservation to put into the database. This
     *                          object has seats in.
     *
     * @return true if the reservation was saved successfully.
     */
    boolean newReservation(ReservationInterface reservationToMake);

    /**
     * remove the reservation with ID reservationID by searching for it inthe
     * database.
     *
     * @param reservationID the ID to look for inthe database
     */
    void removeReservation(String reservationID);

    /**
     * Get all the available flights as an ArrayList.
     *
     * @return An ArrayList of all the available flights.
     */
    ArrayList<String> getAirportCitiesAsStrings();

    /**
     * Check whether a given ID already exists.
     *
     * @param ID
     *
     * @return true if the ID exists. 
     */
    boolean checkForID(int ID);

    /**
     * Find all booked seats on a certain flight by searching through
     * reservations on that specific flight.
     *
     * @param FlightID the flight on which we want to see the booked seats
     *
     * @return an arrayList of seatId strings.
     */
    ArrayList<String> getAllBookedSeats(int flightID);

    /**
     * Returns an array of persons who are kept in a reservation with ID
     * "reservationID"
     *
     * @param reservationID the reservationID to check for persons in
     *
     * @return an arrayList of persons.
     */
    ArrayList<Person> getBookedPersons(String reservationID);

    /**
     * Find the booked seats on a specific reservation.
     *
     * @param reservationID the reservation we want to get the booked seats
     *                      from.
     *
     * @return an ArrayList of seatID strings
     */
    ArrayList<String> getBookedSeatsOnReservation(String reservationID);
}
