
package flybooking;

import java.util.*;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Controller implements ControllerInterface {

    private DatabaseInterface database;

    public Controller(DatabaseInterface database)
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
    public void deleteReservation(DatabaseInterface database
    )
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
    public int getNumberOfFlights()
    {
        ArrayList<Flight> flights = database.getFlights();
        int number = 0;

        for (Flight f : flights) {
            number++;
        }

        return number;
    }

    @Override
    public Flight[] getAllFlights()
    {
        Flight[] flights = new Flight[getNumberOfFlights()];
        int i = 0;
        
        for (Flight f : database.getFlights()) {
            flights[i] = f;
            i++;
        }
       
        return flights;
    }

}
