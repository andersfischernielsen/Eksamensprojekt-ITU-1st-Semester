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
public class Database implements DatabaseInterface
{

    private String name, login, password;
    private Connection con;
    Statement statement;
    private static Database instance = null;

    private Database(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/" + name, login, password);
            statement = con.createStatement();
        }
        catch (SQLException e)
        {
            showMessageDialog(null, "Couldn't connect to the database!");
        }
    }

    // ------------------Simple objects --------
    @Override
    public Plane getPlane(String PlaneID) throws SQLException
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM Plane WHERE Plane.id = " + PlaneID);
        return new Plane(rs.getString("id"), rs.getInt("rows"), rs.getInt("columns"));
    }

    @Override
    public Airport getAirport(String AirportCityID) throws SQLException
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM Airport WHERE Airport.code = " + AirportCityID);
        return new Airport(rs.getString("Code"), rs.getString("Country"), rs.getString("City"));
    }

    @Override
    public Person getPerson(int PersonID) throws SQLException
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM People WHERE People.ID = " + PersonID);
        return new Person(rs.getString("Firstname"), rs.getString("Lastname"), rs.getInt("PersonID"), rs.getString("Address"), rs.getInt("groupID"));
    }
    
    public void insertPerson(Person person, String ReservationID) throws SQLException
    {
        statement.executeQuery("INSERT INTO People (ID, ReservationID, firstName, lastName, address, groupID) " + 
        "VALUES (" + person.getID() + "," + ReservationID + "," + person.getFirstName() + "," + 
                person.getLastName() + "," + person.getAdress() + "," + person.getGroupID() + ")");
    }
    
    public void insertSeat(String seatID, String ReservationID) throws SQLException
    {
        statement.executeQuery("INSERT INTO Seat (SeatID, ReservationID) " + 
        "VALUES (" + seatID + "," + ReservationID + ")");
    }

    // ----------- advanced objects --------------
    @Override
    public FlightInterface getFlight(int flightID) throws SQLException
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM Flight WHERE flight.ID = " + flightID);
        return new Flight(rs.getDouble("price"), rs.getInt("ID"), getPlane(rs.getString("plane")), rs.getDate("startDate"), rs.getDate("endDate"), getAirport(rs.getString("startAirport")), getAirport(rs.getString("endAirport")));
    }

    @Override
    public ArrayList<Flight> getFlightList(Date departureDate,
                                           String startDestination, String endDestination) throws SQLException
    {
        ArrayList<Flight> flights = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM Flight WHERE endAirport = " + endDestination + "AND WHERE startAirport = " + startDestination); // + departureDate + " AND startAirport = " + startDestination 

        while (rs.next())
        {
            flights.add(new Flight(rs.getDouble("price"), rs.getInt("ID"), getPlane(rs.getString("plane")), rs.getDate("startDate"), rs.getDate("endDate"), getAirport(rs.getString("startAirport")), getAirport(rs.getString("endAirport"))));
        }
        return flights;
    }

    @Override
    public ArrayList<ReservationInterface> getReservationList(String reservationID, String CPR) throws SQLException
    {
        ArrayList<ReservationInterface> reservations = new ArrayList<>();
        ResultSet rsReservation;

        if (reservationID != null || !reservationID.equals(""))
        {
            rsReservation = statement.executeQuery("SELECT * FROM Reservation WHERE Reservation.ID = " + reservationID);
        }
        else if (CPR != null || !CPR.equals(""))
        {
            rsReservation = statement.executeQuery("SELECT * FROM Reservation WHERE Reservation.CPR = " + CPR);
        }
        else
        {
            return reservations;
        }

        while (rsReservation.next())
        {
            ReservationInterface r = new Reservation();
            r.setFlight((Flight) getFlight(rsReservation.getInt("flight"))); // ATM CASTS FLIGHTINTERFACE TO FLIGHT
            r.setCPR(rsReservation.getString("CPR"));
            r.setPrice(rsReservation.getInt("price"));
            r.setReservationDate(rsReservation.getDate("reservationDate"));

            // adds the seats to the reservation.
            ResultSet rsSeatID = statement.executeQuery("SELECT * FROM Seat WHERE Seat.ReservationID = " + reservationID);
            ArrayList<String> seatIDThisRes = new ArrayList<String>();
            while (rsSeatID.next())
            {
                seatIDThisRes.add(rsSeatID.getString("seatID"));
            }
            r.bookSeats(seatIDThisRes);

            ResultSet rsPersonID = statement.executeQuery("SELECT * FROM Person WHERE Person.ReservationID = " + reservationID);
            ArrayList<Person> PersonsThisRes = new ArrayList<Person>();
            while (rsPersonID.next())
            {
                PersonsThisRes.add(getPerson(rsPersonID.getInt("ID")));
            }
            r.bookPersons(PersonsThisRes);
            // adds the persons to the reservation.

            reservations.add(new Reservation());
        }
        return reservations;
    }

    @Override
    public void newReservation(Flight flight, Person[] persons, String CPR, double Price)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newReservation(ReservationInterface reservationToMake) throws SQLException
    {
        // save the reservation.
        statement.executeQuery("INSERT INTO Reservation (flight, reservationDate, CPR) " + 
        "VALUES (" + reservationToMake.getFlight().getID() + "," + reservationToMake.getReservationDate() + "," + reservationToMake.getCPR() + ")");
        // save the seats in the reservation
        for (String seatID : reservationToMake.getBookedSeats())
        {
            insertSeat(seatID, reservationToMake.getID());
        }
        // save the persons in the reservation
        for (Person person : reservationToMake.getBookedPersons())
        {
            insertPerson(person, reservationToMake.getID());
        }
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

        while (rs.next())
        {
            airports.add(rs.getString("city"));
        }

        return airports;
    }

    public static Database getInstance()
    {
        if (instance == null)
        {
            instance = new Database("AACBookingDB", "AACBooking", "AACDB");
        }

        return instance;
    }

    @Override
    public boolean checkForID(int ID) throws SQLException
    {
        if (ID <= 9999)
        {
            ResultSet matchingIDs = statement.executeQuery("SELECT * FROM Reservation WHERE " + ID + " IN(ID)");
            if (matchingIDs.next())
            {
                return false;
            }
        }

        if (ID > 9999)
        {
            ResultSet matchingIDs = statement.executeQuery("SELECT * FROM People WHERE " + ID + " IN(ID)");
            if (matchingIDs.next())
            {
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

    @Override
    public ArrayList<ReservationInterface> getReservationsFromCPR(String CPR)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ReservationInterface> getReservationsFromID(String ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Person> getBookedPersons(String reservationID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args)
    {
        DatabaseInterface d = new Database("AACBookingDB", "AACBooking", "AACDB");
        //d.getAirport(null)
    }

}
