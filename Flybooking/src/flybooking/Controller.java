
package flybooking;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Controller implements ControllerInterface {

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

}
