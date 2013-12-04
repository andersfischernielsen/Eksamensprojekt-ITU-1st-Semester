
package flybooking;

import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Controller implements ControllerInterface {

    private DatabaseInterface database;
    private static Controller instance = null;
    private ReservationInterface workingOnReservation;

    private Controller()
    {
        database = Database.getInstance();
    }

    @Override
    public void createReservation()
    {
        workingOnReservation = new Reservation();
    }

    @Override
    public boolean saveReservation()
    {
        database.removeReservation(workingOnReservation.getID());
        workingOnReservation.setID();
        boolean savedSuccessfully = database.newReservation(workingOnReservation);
        resetController();
        return savedSuccessfully;
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
    public void printReceipt(ReservationInterface reservation, ReceiptPrinter printer)
    {
        /**
         * We need to be sure that the printer has been constructed with the
         * right reservation.
         */
        printer.print();
    }

    @Override
    public int getPrice(Calculator calculator, Reservation reservation)
    {
        //Do nothing.    
        return -1;
    }

    @Override
    public void search()
    {
        //Do nothing.    
    }

    @Override
    public boolean hasPersonNextTo(Person person)
    {
        //Do nothing.    
        return false;
    }

    @Override
    public Person personNextTo(Person person)
    {
        //Do nothing.    
        return null;
    }

    @Override
    public int getNumberOfDestinations()
    {
        // RET HER
        ArrayList<String> destinations = database.getAirportCitiesAsStrings();
        int number = 0;

        for (String d : destinations) {
            number++;
        }

        return number;
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

        return new ArrayList<String>();
    }

    @Override
    public void resetController()
    {
        workingOnReservation = new Reservation();
    }

    @Override
    public ArrayList<Person> getBookedPersons()
    {
        if (database.getAllBookedSeats(workingOnReservation.getFlight().getID()) != null) {
            return database.getBookedPersons(workingOnReservation.getID());
        }

        return new ArrayList<Person>();
    }

    @Override
    public ArrayList<FlightInterface> getFlightList(Date chosenDate, String chosenStartDestination, String chosenEndDestination)
    {
        return database.getFlightList(chosenDate, chosenStartDestination, chosenEndDestination);
    }
}
