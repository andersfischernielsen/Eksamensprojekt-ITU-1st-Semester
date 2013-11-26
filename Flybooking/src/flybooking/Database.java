
package flybooking;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

/**
 *
 * @author Anders
 */
public class Database implements DatabaseInterface {
    private String name, login, password;
    
    public Database(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;
        
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/" + name, login, password);
        } catch (SQLException e)
        {
            System.out.println("Ingen forbindelse!");
        }
    }

    @Override
    public Plane getPlane(String PlaneID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Seat> getTakenSeats(Flight flight, Person[] persons)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<FlightInterface> getFlight(Date DepartureDate, int amtOfPeople, Airport startDestination, Airport endDestination, boolean nextTo)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ReservationInterface> getReservation(String ReservationID, String CPR)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newReservation(Flight flight, Person[] persons, String CPR, double Price)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newReservation(Reservation reservationToMake)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeReservation(String reservationID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPerson(String reservationID, Person personToAdd)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Flight> getFlights()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
