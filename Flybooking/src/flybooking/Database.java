
package flybooking;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Anders
 */
public class Database implements DatabaseInterface {

    private String name, login, password;
    private Connection con;
    private static Database instance = null;

    private Database(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;

        try {
            con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/" + name, login, password);
        } catch (SQLException e) {
            showMessageDialog(null, "Couldn't connect to the database!");
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
    public ArrayList<String> getAirportCitiesAsStrings() throws SQLException
    {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Airport");
        ArrayList<String> airports = new ArrayList<>();

        while (rs.next()) {
            airports.add(rs.getString("city"));
        }

        return airports;
    }

    public static Database getInstance()
    {
        if (instance == null) {
            instance = new Database("AACBookingDB", "AACBooking", "AACDB");
        }
        
        return instance;
    }

}
