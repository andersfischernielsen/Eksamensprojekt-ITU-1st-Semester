
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

    private Controller(DatabaseInterface database)
    {
        this.database = database;
    }

    @Override
    public void createReservation()
    {
        //WHAT TO DO? 
    }

    @Override
    public void saveReservation(DatabaseInterface database)
    {
        //Do nothing.
    }

    @Override
    public void getReservation(DatabaseInterface database)
    {
        //Do nothing.    
    }

    @Override
    public void deleteReservation(DatabaseInterface database)
    {
        //Do nothing.    
    }

    @Override
    public void printReceipt(ReservationInterface reservation, ReceiptPrinter printer)
    {
        //Do nothing.
    }

    @Override
    public int getPrice(CalculatorInterface calculator, Reservation reservation)
    {
        //Do nothing.    
        return -1;
    }

    @Override
    public void search(DatabaseInterface database
    )
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
    public int getNumberOfDestinations() throws SQLException
    {
        ArrayList<String> destinations = database.getAirportCitiesAsStrings();
        int number = 0;

        for (String d : destinations) {
            number++;
        }

        return number;
    }

    @Override
    public String[] getDestinationsAsStrings() throws SQLException 
    {
        String[] destinations = new String[getNumberOfDestinations()];
        ArrayList<String> strings = database.getAirportCitiesAsStrings();
        
        for (int i = 0; i < destinations.length; i++ ) {
            destinations[i] = strings.get(i);
        }
        
        return destinations;
    }

    public static Controller getInstance(DatabaseInterface database)
    {
        if (instance == null) {
            instance = new Controller(database);
        }
        
        return instance;
    }

    @Override
    public boolean checkForID(String ID) throws SQLException
    {
        database.checkForID(ID);
        
        return false;
    }

}
