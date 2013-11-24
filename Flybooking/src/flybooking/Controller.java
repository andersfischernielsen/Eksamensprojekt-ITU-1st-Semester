
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
        database.newEntry();
    }

    @Override
    public void getReservation(DatabaseInterface database)
    {
        database.getEntry();
    }

    @Override
    public void deleteReservation(DatabaseInterface database)
    {
        database.removeEntry();
    }

    @Override
    public void printReceipt(ReservationInterface reservation, ReceiptPrinter printer)
    {
        
    }

    @Override
    public int getPrice()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void search(DatabaseInterface database)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasPersonNextTo(Person person)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void personNextTo(Person person)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
