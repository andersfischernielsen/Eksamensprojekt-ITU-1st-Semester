
package flybooking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Create a reservation for a booking.
 * @author Anders Wind Steffenson, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Reservation implements ReservationInterface {

    private ArrayList<Person> persons;
    private ArrayList<String> seatIDs;
    private Flight flight;
    private String ID;
    private Date reservationDate;
    private String CPR;
    private Person payer;
    private double price;

    /**
     * Create a reservation, containing details about the journey, the plane and
     * the people on board.
     */
    public Reservation() throws SQLException
    {
        //RESERVATIONEN SKULLE IKKE OPRETTES MED NOGLE PARAMETRE, SÅ VIDT 
        //VI SNAKKEDE OM, VEL?
        //DEN SKULLE BARE HAVE SETTERS TIL ALLE FELTERNE, SÅ VI KAN OPRETTE EN 
        //TOM RESERVATION FØRST. 

        //VI SKAL HAVE IMPLEMENTERET ET KORREKT SYSTEM TIL AT GENERERE ID'S. 
        //DE SKAL LIGGE I KRONOLOGISK ORDEN, GÅR JEG UD FRA, 
        //SÅ VI SKAL HAVE FAT I DATABASEN OG FINDE DE TIDLIGERE RESERVATIONERS 
        //ID, OG SÅ LÆGGE EN TIL DET HØJESTE. EN SEPARAT "ID"-KLASSE VILLE MÅSKE
        //VÆRE EN RIGTIG GOD IDE.
        persons = new ArrayList<>();
        ID = Calculator.createReservationID();
    }

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
    public Person getPayer()
    {
        return payer;
    }

    @Override
    public void setPayer(Person payer)
    {
        this.payer = payer;
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
        // skal måske ændres til at den ligger den til.
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
}
