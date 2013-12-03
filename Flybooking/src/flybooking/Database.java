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
        } catch (SQLException e)
        {
            showMessageDialog(null, "Couldn't connect to the database!");
        }
    }

    // ------------------Simple objects --------
    @Override
    public Plane getPlane(String PlaneID) throws SQLException
    {
        ResultSet rs = null;
        try
        {
            rs = statement.executeQuery("SELECT * FROM Plane WHERE ID = '" + PlaneID + "'");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        rs.next();
        return new Plane(rs.getString("ID"), rs.getInt("rows"), rs.getInt("columns"));
    }

    @Override
    public Airport getAirport(String AirportCityID) throws SQLException
    {
        ResultSet rs = null;
        try
        {
            rs = statement.executeQuery("SELECT * FROM Airport WHERE Airport.code = '" + AirportCityID + "'");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        rs.next();
        return new Airport(AirportCityID, rs.getString("Country"), rs.getString("City"));
    }

    public String getAirportID(String AirportCityName) throws SQLException
    {
        ResultSet rs = null;
        try
        {
            rs = statement.executeQuery("SELECT code FROM Airport WHERE city = '" + AirportCityName + "'");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        rs.next();
        return rs.getString("code");
    }

    @Override
    public Person getPerson(int PersonID) throws SQLException
    {
        ResultSet rs = statement.executeQuery("SELECT * FROM People WHERE People.ID = " + PersonID);
        return new Person(rs.getString("Firstname"), rs.getString("Lastname"), rs.getInt("PersonID"), rs.getString("Address"), rs.getInt("groupID"));
    }

    public void insertPerson(Person person, String ReservationID) throws SQLException
    {
        try
        {
            statement.executeUpdate("INSERT INTO People (ID, ReservationID, firstName, lastName, address, groupID) "
                    + "VALUES (" + person.getID() + ", '" + ReservationID + "', '" + person.getFirstName() + "' , '"
                    + person.getLastName() + "' , '" + person.getAdress() + "' ," + person.getGroupID() + ")");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertSeat(String seatID, String ReservationID) throws SQLException
    {
        try
        {
            statement.executeUpdate("INSERT INTO Seat (SeatID, ReservationID) "
                    + "VALUES ( '" + seatID + "' , '" + ReservationID + "')");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // ----------- advanced objects --------------
    @Override
    public FlightInterface getFlight(int flightID)
    {
        ResultSet rs;
        FlightInterface flight = null;
        try
        {
            rs = statement.executeQuery("SELECT * FROM Flight WHERE flight.ID = '" + flightID + "'");
            rs.next();
            flight = new Flight(rs.getDouble("price"), rs.getInt("ID"), getPlane(rs.getString("plane")), new Date(), new Date(), getAirport(rs.getString("startAirport")), getAirport(rs.getString("endAirport")));
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return flight;
    }

    @Override
    public ArrayList<Flight> getFlightList(Date departureDate,
                                           String startDestination, String endDestination) throws SQLException
    {
        ArrayList<Flight> flights = new ArrayList<>();
        ResultSet rs = null;
        try
        {
            rs = statement.executeQuery("SELECT * FROM Flight WHERE endAirport = '" + getAirportID(endDestination) + "' AND startAirport = '" + getAirportID(startDestination) + "'"); // + departureDate + " AND startAirport = " + startDestination 
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        while (!rs.isClosed() && rs.next())
        {
            flights.add(new Flight(rs.getDouble("price"), rs.getInt("ID"), getPlane(rs.getString("plane")), new Date(), new Date(), getAirport(getAirportID(startDestination)), getAirport(getAirportID(endDestination))));
        }
        return flights;

    }

    @Override
    public ArrayList<ReservationInterface> getReservationList(String reservationID, String CPR)
    {
        //Create a new empty ArrayList of reservations to avoid nullpointers.
        ArrayList<ReservationInterface> reservations = new ArrayList<>();
        ResultSet rsReservation = null;

        //If the reservationsID given is null or an empty String, don't search for it.
        //Search for CPR instead.
        if (reservationID == null || reservationID.equals(""))
        {
            try
            {
                rsReservation = statement.executeQuery("SELECT * FROM Reservation WHERE ID = '" + CPR + "';");
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        //If the CPR given is null or an empty String, don't search for it.
        //Search for reservationID instead.
        else if (CPR == null || CPR.equals(""))
        {
            try
            {
                rsReservation = statement.executeQuery("SELECT * FROM Reservation WHERE ID = '" + reservationID + "';");
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        try
        {
            //If the resultset isn't null, then there are some results. 
            if (rsReservation != null)
            {
                //If there are no more results, break.
                while (!rsReservation.isClosed() && rsReservation.next())
                {
                    //Go through the results, and create reservations.
                    //Add these to the list of found reservations.
                    ReservationInterface r = new Reservation();
                    //Set the reservation details from the database info.
                    r.setCPR(rsReservation.getString("CPR"));
                    r.setFlight((Flight) getFlight(rsReservation.getInt("flight")));
                    r.setPrice(rsReservation.getDouble("price"));
                    r.setReservationDate(new Date());

                    //Add the finished reservation to the list for each found res.
                    reservations.add(r);
                }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        //Return the 
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
        try
        {
            statement.executeUpdate("INSERT INTO Reservation (ID, flight, CPR) "
                    + "VALUES ('" + reservationToMake.getID() + "', " + reservationToMake.getFlight().getID() + ", '" + reservationToMake.getCPR() + "')");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
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
        statement.executeQuery("DELETE FROM Reservation WHERE Reservation.ID = " + reservationID);
        statement.executeQuery("DELETE FROM Seat WHERE Seat.ReservationID = " + reservationID);
        statement.executeQuery("DELETE FROM Person WHERE Person.ReservationID = " + reservationID);
    }

    @Override
    public void updateReservation(ReservationInterface reservationToMake) throws SQLException
    {
        statement.executeQuery("DELETE FROM Seat WHERE Seat.ReservationID = " + reservationToMake.getID());
        statement.executeQuery("DELETE FROM Person WHERE Person.ReservationID = " + reservationToMake.getID());
        statement.executeQuery("UPDATE Rerservation SET price= " + reservationToMake.getPrice() + "WHERE Reservation.ID = " + reservationToMake.getID());
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
    public void addPersonToReservation(String reservationID, Person person, String reservationSpot) throws SQLException
    {
        // USE UPDATERESERVATION INSTEAD
        statement.executeQuery("INSERT INTO People (ID, firstName, lastName, address, groupID) VALUES ('"
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
        // HAR IKKE KIGGET PÅ DENNE
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
    public boolean checkForID(int ID)
    {
        // IKKE NØDVENDIG MERE
        if (ID <= 9999)
        {
            try
            {
                ResultSet matchingIDs = statement.executeQuery("SELECT * FROM Reservation WHERE " + ID + " IN(ID)");
                if (matchingIDs.next())
                {
                    return false;
                }
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        if (ID > 9999)
        {
            try
            {
                ResultSet matchingIDs = statement.executeQuery("SELECT * FROM People WHERE " + ID + " IN(ID)");
                if (matchingIDs.next())
                {
                    return false;
                }
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public ArrayList<String> getAllBookedSeats(int flightID) throws SQLException
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<>();
        ArrayList<String> reservationsOnThisFlight = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM Reservation");
        while (rs.next())
        {
            if (rs.getInt("flight") == flightID)
            {
                reservationsOnThisFlight.add(rs.getString("ID"));
            }
        }
        for (String reservationID : reservationsOnThisFlight)
        {
            rs = statement.executeQuery("SELECT * FROM Seat WHERE seat.reservationID =" + reservationID + ")");
            while (rs.next())
            {
                seatIDsToReturn.add(rs.getString("seatID"));
            }
        }
        return seatIDsToReturn;
    }

    @Override
    public ArrayList<String> getBookedSeatsOnReservation(String reservationID) throws SQLException
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM Seat WHERE seat.reservationID =" + reservationID + ")");
        while (rs.next())
        {
            seatIDsToReturn.add(rs.getString("seatID"));
        }
        return seatIDsToReturn;
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
    public ArrayList<Person> getBookedPersons(String reservationID) throws SQLException
    {
        ArrayList<Person> personsToReturn = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM Person WHERE person.reservationID =" + reservationID + ")");
        while (rs.next())
        {
            personsToReturn.add(getPerson(rs.getInt("ID")));
        }
        return personsToReturn;
    }

    public static void main(String[] args)
    {
        DatabaseInterface d = new Database("AACBookingDB", "AACBooking", "AACDB");
        //d.getAirport(null)
    }
}
