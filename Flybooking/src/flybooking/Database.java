
package flybooking;

import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.util.Calendar;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Create a database handler that connects to a database and does stuff.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Database implements DatabaseInterface {

    private String name, login, password;
    private Connection con;
    Statement statement;
    private static Database instance = null;

    private Database(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;

        try {
            con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/" + name, login, password);
            statement = con.createStatement();
        } catch (SQLException e) {
            showMessageDialog(null, "Couldn't connect to the database!");
        }
    }

    @Override
    public Plane getPlane(String PlaneID) throws SQLException
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM Plane WHERE Plane.id == " + PlaneID);
        System.out.println("id " + rs.getString("id"));
        return new Plane(rs.getString("id"), rs.getInt("rows"), rs.getInt("columns"));
    }


    @Override
    public ArrayList<Flight> getFlight(Date departureDate,
            String startDestination, String endDestination) throws SQLException
    {
        ArrayList<Flight> flights = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM Flight WHERE startDate = " + departureDate + " AND startAirport = " + startDestination + " AND endAirport = " + endDestination + "");

        while (rs.next()) {
            flights.add(new Flight(rs.getDouble("price"), rs.getInt("ID"), new Plane(rs.getString("plane"), 0, 0), rs.getDate("startDate"), rs.getDate("endDate"), new Airport(rs.getString("startAirport"), "", ""), new Airport(rs.getString("endAirport"), "", "")));
        }
        return flights;
    }

    @Override
    public ArrayList<ReservationInterface> getReservations(String reservationID, String CPR)
    {
        ArrayList<ReservationInterface> reservations = new ArrayList<>();
        Airport a1;
        Airport a2;
        Plane plane;
        ArrayList<Person> persons = new ArrayList<>();
        FlightInterface flight;
        ReservationInterface res;

        if (reservationID != null) {
            try {
                con.setAutoCommit(false);
                PreparedStatement s = con.prepareStatement("SELECT * FROM Reservation WHERE ID = ?");
                s.setString(1, reservationID);
                ResultSet rs = s.executeQuery();

                while (rs.next()) {
                    s = con.prepareStatement("SELECT * FROM Flight WHERE ID = ?");
                    s.setString(1, rs.getString("flight"));
                    ResultSet fs = s.executeQuery();

                    while (fs.next()) {
                        s = con.prepareStatement("SELECT * FROM Plane WHERE ID = ?");
                        s.setString(1, rs.getString("plane"));
                        ResultSet ps = s.executeQuery();
                        
                        while (ps.next()) {
                            plane = new Plane(ps.getString("ID"), ps.getInt("rows"), ps.getInt("columns"));
                        }
                    }
                    
                    s = con.prepareStatement("SELECT * FROM Flight WHERE ID = ?");
                    s.setString(1, "startAirport");
                    ResultSet a1s = s.executeQuery();
                    
                    while (a1s.next()) {
                        s = con.prepareStatement("SELECT * FROM Airport WHERE ID = ?");
                        s.setString(1, "ID");
                        ResultSet a1ss = s.executeQuery();
                        
                        while (a1ss.next()) {
                            a1 = new Airport(a1ss.getString("ID"), a1ss.getString("country"), a1ss.getString("city"));
                        }
                    }
                    
                    s = con.prepareStatement("SELECT * FROM Flight WHERE ID = ?");
                    s.setString(1, "endAirport");
                    ResultSet a2s = s.executeQuery();
                    
                    while (a2s.next()) {
                        s = con.prepareStatement("SELECT * FROM Airport WHERE ID = ?");
                        s.setString(1, "ID");
                        ResultSet a2ss = s.executeQuery();
                        
                        while (a2ss.next()) {
                            a2 = new Airport(a2ss.getString("ID"), a2ss.getString("country"), a2ss.getString("city"));
                        }
                    }
                    
                }
                con.setAutoCommit(true);

            } catch (SQLException ex) {
            }
        }

        return reservations;

    }

    @Override
    public void newReservation(Flight flight, Person[] persons, String CPR, double Price)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newReservation(ReservationInterface reservationToMake)
    {

    }

    @Override
    public void removeReservation(String reservationID) throws SQLException
    {
        statement.executeQuery("DELETE FROM " + name + ".Reservation WHERE Reservation.ID = " + reservationID);
    }

    @Override
    public void addPersonToReservation(String reservationID, Person person, String reservationSpot) throws SQLException
    {
        statement.executeQuery("INSERT INTO " + name + ".People (ID, firstName, lastName, address, groupID) VALUES ('"
                + person.getID() + "', '"
                + person.getFirstName() + "', '"
                + person.getLastName() + "', '"
                + person.getAdress() + "', '"
                + person.getGroupID() + "')");
        statement.executeQuery("UPDATE " + name + ".Reservation SET " + reservationSpot + "PersonID = " + person.getID() + " WHERE  Reservation.ID = " + reservationID + "");
    }

    @Override
    public ArrayList<String> getAirportCitiesAsStrings() throws SQLException
    {
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

    @Override
    public boolean checkForID(String ID) throws SQLException
    {
        if (ID.length() <= 4) {
            ResultSet matchingIDs = statement.executeQuery("SELECT * FROM Reservation WHERE " + ID + " IN(ID)");
            if (matchingIDs.next()) {
                return false;
            }
        }

        if (ID.length() > 4) {
            ResultSet matchingIDs = statement.executeQuery("SELECT * FROM People WHERE " + ID + " IN(ID)");
            if (matchingIDs.next()) {
                return false;
            }
        }

        return true;
    }
    

    @Override
    public ArrayList<String> getAllBookedSeats(int flightID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getBookedSeatsOnReservation(String reservationID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
