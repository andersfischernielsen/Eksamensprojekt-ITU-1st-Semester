
package flybooking;

import java.util.ArrayList;
import java.util.Date;

/**
 * Create a reservation for a booking.
 *
 * @author Anders
 */
public class Reservation implements ReservationInterface {

    private ArrayList<Person> persons;
    private Flight flight;
    private int ID;
    private Date reservationDate;
    private Date startDate;
    private Date endDate;
    private int CPR;
    private boolean paid;

    @Override
    public ArrayList<Person> getPersons()
    {
        return persons;
    }

    @Override
    public Flight getFlight()
    {
        return flight;
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public Date getReservationDate()
    {
        return reservationDate;
    }

    @Override
    public Date getStartDate()
    {
        return startDate;
    }

    @Override
    public Date getEndDate()
    {
        return endDate;
    }

    @Override
    public int getCPR()
    {
        return CPR;
    }

    @Override
    public boolean isPaid()
    {
        return paid;
    }

}
