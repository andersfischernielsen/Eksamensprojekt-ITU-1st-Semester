
package flybooking;

import java.util.*;

/**
 * A controller for controlling the booking of flights and communicating with
 * the database.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Controller implements ControllerInterface {

    //The database to communicate with.
    private DatabaseInterface database;
    //Initialize the controller (for the singleton instance).
    private static Controller instance = null;
    //The current reservation being worked on.
    private ReservationInterface workingOnReservation;
    //The reservationID to save to the database.
    private String reservationIDToCome;

    private Controller()
    {
        database = Database.getInstance();
    }
    
    @Override
    public void createReservation()
    {
        // resets the controller and make a new reservation.
        resetController();
        workingOnReservation = new Reservation();
    }

    @Override
    public boolean saveReservation()
    {
        //If this reservation is already in the database, remove it.
        database.removeReservation(workingOnReservation.getID());
        //Generate an ID for the reservation.
        workingOnReservation.setID(reservationIDToCome);
        //Save to the databse and return whether it saved succesfully.
        boolean savedSuccessfully = database.newReservation(workingOnReservation);
        //Reset the controller so it's ready for the next reservation.
        resetController();
        
        return savedSuccessfully;
    }

    @Override
    public String getReservationIDToCome()
    {
        return reservationIDToCome;
    }

    @Override
    public void setReservationIDToCome(String reservationIDToCome)
    {
        this.reservationIDToCome = reservationIDToCome;
    }

    @Override
    public ArrayList<ReservationInterface> getReservations(String reservationID, String CPR)
    {
        return database.getReservationList(reservationID, CPR);
    }

    @Override
    public void deleteReservation(String reservationID)
    {
        database.removeReservation(reservationID);
    }

    @Override
    public int getNumberOfDestinations()
    {
        return database.getAirportCitiesAsStrings().size();
    }

    @Override
    public String[] getDestinationsAsStrings()
    {
        String[] destinations = new String[getNumberOfDestinations()];
        ArrayList<String> strings = database.getAirportCitiesAsStrings();

        for (int i = 0; i < destinations.length; i++) {
            destinations[i] = strings.get(i);
        }

        return destinations;
    }

    /**
     * Get the singleton instance of the Controller.
     * @return The current instance of the controller.
     */
    public static Controller getInstance()
    {
        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    @Override
    public boolean checkForID(int ID)
    {
        return database.checkForID(ID);
    }

    @Override
    public void bookSeats(ArrayList<String> seatIDs)
    {
        workingOnReservation.bookSeats(seatIDs);
    }

    @Override
    public ReservationInterface getWorkingOnReservation()
    {
        return workingOnReservation;
    }

    @Override
    public void setWorkingOnReservation(ReservationInterface reservation)
    {
        workingOnReservation = reservation;
    }

    @Override
    public ArrayList<String> getBookedSeats()
    {
        if (database.getAllBookedSeats(workingOnReservation.getFlight().getID()) != null) {
            return database.getAllBookedSeats(workingOnReservation.getFlight().getID());
        }

        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getBookedThisResSeats()
    {
        if (database.getBookedSeatsOnReservation(workingOnReservation.getID()) != null) {
            return database.getBookedSeatsOnReservation(workingOnReservation.getID());
        }
        
        return new ArrayList<>();
    }

    @Override
    public void resetController()
    {
        reservationIDToCome = null;
        workingOnReservation = new Reservation();
    }

    @Override
    public ArrayList<Person> getBookedPersons()
    {
        if (database.getAllBookedSeats(workingOnReservation.getFlight().getID()) != null) {
            return database.getBookedPersons(workingOnReservation.getID());
        }

        return new ArrayList<>();
    }

    @Override
    public ArrayList<FlightInterface> getFlightList(Date chosenDate, String chosenStartDestination, String chosenEndDestination)
    {
        return database.getFlightList(chosenDate, chosenStartDestination, chosenEndDestination);
    }
}
