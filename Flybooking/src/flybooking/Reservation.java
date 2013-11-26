
package flybooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Create a reservation for a booking.
 *
 * @author Anders
 */
public class Reservation implements ReservationInterface {

    private ArrayList<Person> persons;
    private Flight flight;
    private String ID;
    private Date reservationDate;
    private String CPR;
    private Person payer;

    /**
     * Create a reservation, containing details about the journey, the plane and
     * the people on board.
     */
    public Reservation()
    {
        //RESERVATIONEN SKULLE IKKE OPRETTES MED NOGLE PARAMETRE, SÅ VIDT 
        //VI SNAKKEDE OM, VEL?
        //DEN SKULLE BARE HAVE SETTERS TIL ALLE FELTERNE, SÅ VI KAN OPRETTE EN 
        //TOM RESERVATION FØRST. 

        //We haven't paid yet. 
        paid = false;
        persons = new ArrayList<>();

        //VI SKAL HAVE IMPLEMENTERET ET KORREKT SYSTEM TIL AT GENERERE ID'S. 
        //DE SKAL LIGGE I KRONOLOGISK ORDEN, GÅR JEG UD FRA, 
        //SÅ VI SKAL HAVE FAT I DATABASEN OG FINDE DE TIDLIGERE RESERVATIONERS 
        //ID, OG SÅ LÆGGE EN TIL DET HØJESTE. EN SEPARAT "ID"-KLASSE VILLE MÅSKE
        //VÆRE EN RIGTIG GOD IDE.
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
    public boolean isPaid()
    {
        return paid;
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
    public void setReservationDate()
    {
        reservationDate = new Date();
    }

    @Override
    public void setCPR(String CPR)
    {
        this.CPR = CPR;
    }

    @Override
    public void setPaid(boolean paid)
    {
        this.paid = paid;
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

}
