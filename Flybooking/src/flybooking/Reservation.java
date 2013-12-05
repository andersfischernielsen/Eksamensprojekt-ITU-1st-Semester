
package flybooking;

import java.util.ArrayList;
import java.util.Date;

/**
 * Create a reservation for a booking.
 * @author Anders Wind Steffenson, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Reservation implements ReservationInterface {

    //The people in the reservation.
    private ArrayList<Person> persons;
    //The seat IDs reserved in the reservation.
    private ArrayList<String> seatIDs;
    //The flight booked in the reservation.
    private Flight flight;
    //The ID of the reservation.
    private String ID;
    //The date the reservation was placed.
    private Date reservationDate;
    //The CPR of the person that paid for the reservation.
    private String CPR;
    //The price of the reservation.
    private double price;

    /**
     * Create a reservation, containing details about the journey, the plane and
     * the people on board.
     */
    public Reservation()
    {
        persons = new ArrayList<>();
    }

    @Override
    public ArrayList<Person> getPersons()
    {
        return persons;
    }
    
    @Override
    public void clearPersonList()
    {
        persons = new ArrayList<>();
    }

    @Override
    public Flight getFlight()
    {
        return flight;
    }

    @Override
    public String getID()
    {
        return ID;
    }

    @Override
    public Date getReservationDate()
    {
        return reservationDate;
    }

    @Override
    public String getCPR()
    {
        return CPR;
    }

    @Override
    public void addPerson(Person person)
    {
        persons.add(person);
    }

    @Override
    public void removePerson(Person person)
    {
        for (Person p : persons) {
            p.equals(person);
        }
    }

    @Override
    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }

    @Override
    public void setReservationDate(Date date)
    {
        reservationDate = new Date();
    }

    @Override
    public void setCPR(String CPR)
    {
        this.CPR = CPR;
    }
    
    @Override
    public void setPrice(double price)
    {
        this.price = price;
    }

    @Override
    public double getPrice()
    {
        return price;
    }

    @Override
    public void bookSeats(ArrayList<String> seatIDs)
    {
        this.seatIDs = seatIDs;
        flight.getPlane().bookTakenSeats(seatIDs);
    }

    @Override
    public ArrayList<String> getBookedSeats()
    {
        return seatIDs;
    }

    @Override
    public void bookPersons(ArrayList<Person> persons)
    {
        this.persons = persons;
    }

    @Override
    public ArrayList<Person> getBookedPersons()
    {
        return persons;
    }

    @Override
    public void setID()
    {
        ID = Converter.createReservationID();
    }

    @Override
    public void setID(String IDToSet)
    {
        ID = IDToSet;
    }
}
