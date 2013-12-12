package flybooking;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Create a database handler that inserts and retrieves data from a database.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Database implements DatabaseInterface
{

    private String name, login, password;
    private Connection con;
    private static Database instance = null;

    private Database(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;
        connectToDatabase();

    }

    private boolean connectToDatabase()
    {
        try
        {
            // make a connection to the database.
            con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/"
                    + name + "?autoReconnect=true", login, password);
            return true;
        }
        catch (SQLException e)
        {
            showMessageDialog(null, "Couldn't connect to the database!");
            return false;
        }
    }

    // ------------------Simple objects --------
    @Override
    public Plane getPlane(String PlaneID)
    {
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Plane "
                    + "WHERE ID = '" + PlaneID + "'");
            rs.next();

            // use the selected data from the database to create a plane.
            return new Plane(rs.getString("ID"),
                             rs.getInt("rows"),
                             rs.getInt("columns"));
        }
        catch (CommunicationsException e1)
        {
            if (connectToDatabase())
            {
                getPlane(PlaneID);
            }
            else
            {
                e1.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Airport getAirport(String AirportCityID)
    {
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Airport "
                    + "WHERE Airport.code = '"
                    + AirportCityID + "'");
            rs.next();

            // use the selected data from the database to create an airport object.
            return new Airport(AirportCityID, rs.getString("Country"),
                               rs.getString("City"));

        }
        catch (CommunicationsException e1)
        {
            if (connectToDatabase())
            {
                getAirport(AirportCityID);
            }
            else
            {
                e1.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns the Airports ID/code if the user only knows the name of the city.
     * It does so by going through all the aiports where their airportCity is
     * equal to AirportCityName in the database.
     *
     * @param AirportCityName the Airport's city name to find the code for
     *
     * @return a String which represents an airports code.
     */
    public String getAirportID(String AirportCityName)
    {
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT code FROM Airport "
                    + "WHERE city = '"
                    + AirportCityName + "'");

            rs.next();
            // returns the code
            return rs.getString("code");

        }
        catch (CommunicationsException e1)
        {
            if (connectToDatabase())
            {
                getAirportID(AirportCityName);
            }
            else
            {
                e1.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Person getPerson(int PersonID)
    {
        try
        {
            ResultSet rs = null;
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM People "
                    + "WHERE People.ID = " + PersonID);
            rs.next();
            // Create a person with the information from the database.
            return new Person(rs.getString("Firstname"),
                              rs.getString("Lastname"),
                              rs.getInt("ID"),
                              rs.getString("Address"),
                              rs.getInt("groupID"));

        }
        catch (CommunicationsException e1)
        {
            if (connectToDatabase())
            {
                getPerson(PersonID);
            }
            else
            {
                e1.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Inserts a Person object into the People table in the database.
     *
     * @param person        The person Object to put into the database
     * @param ReservationID The reservationID to save the person with so the
     *                      person can be found under that reservationID
     */
    public void insertPerson(Person person, String ReservationID)
    {
        try
        {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO People (ID, ReservationID, "
                    + "firstName, lastName, address, groupID) "
                    + "VALUES (" + person.getID() + ", '"
                    + ReservationID + "', '"
                    + person.getFirstName() + "' , '"
                    + person.getLastName() + "' , '"
                    + person.getAdress() + "' ,"
                    + person.getGroupID() + ")");
        }
        catch (CommunicationsException e1)
        {
            if (connectToDatabase())
            {
                insertPerson(person, ReservationID);
            }
            else
            {
                e1.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a seatID String into the Seat table in the database.
     *
     * @param seatID        The SeatID string to put into the database
     * @param ReservationID The reservationID to save the seatID with so the
     *                      seatID can be found under that reservationID
     */
    public void insertSeat(String seatID, String ReservationID)
    {
        try
        {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO Seat (SeatID, ReservationID) "
                    + "VALUES ( '" + seatID + "' , '" + ReservationID + "')");
        }
        catch (CommunicationsException e1)
        {
            if (connectToDatabase())
            {
                insertSeat(seatID, ReservationID);
            }
            else
            {
                e1.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // ----------- advanced objects --------------
    @Override
    public FlightInterface getFlight(int flightID)
    {
        FlightInterface flight = null;
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Flight "
                    + "WHERE ID = " + flightID + "");
            // go through the resultset to create a flight for each row
            // this is unnecesary since we should only find one flight object.
            while (!rs.isClosed() && rs.next())
            {
                //create the flight. Date is created by adding the time(hh, mm) 
                // and the date(dd, MM, yyyy) into the new Date().
                flight = new Flight(rs.getDouble("price"), rs.getInt("ID"),
                                    getPlane(rs.getString("plane")),
                                    new Date(rs.getDate("startDate").getTime()
                        + rs.getTime("startDate").getTime()),
                                    new Date(rs.getDate("endDate").getTime()
                        + rs.getTime("endDate").getTime()),
                                    getAirport(rs.getString("startAirport")),
                                    getAirport(rs.getString("endAirport")));
            }
        }
        catch (CommunicationsException connectionError)
            {
                if (connectToDatabase())
                {
                    getFlight(flightID);
                }
                else
                {
                    connectionError.printStackTrace();
                }
            }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return flight;
    }

    @Override
    public ArrayList<FlightInterface> getFlightList(Date departureDate,
                                                    String startDestination,
                                                    String endDestination)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        ArrayList<FlightInterface> flights = new ArrayList<>();
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            if (departureDate != null)
            {
                //  if a date is given.
                //Create a new Date called startDate which is departureDate -4 days. 
                //Create a new Date called endDate which is departureDate +4 days. 
                Date startDate = new Date(departureDate.getTime() - 4 * 24 * 60 * 60 * 1000);
                Date endDate = new Date(departureDate.getTime() + 4 * 24 * 60 * 60 * 1000);
                String startDateString = df.format(startDate);
                String endDateString = df.format(endDate);
                //Search betweeen these two dates:
                rs = statement.executeQuery("SELECT * FROM Flight "
                        + "WHERE endAirport = '"
                        + getAirportID(endDestination)
                        + "' AND startAirport = '"
                        + getAirportID(startDestination)
                        + "' AND startDate BETWEEN '"
                        + startDateString + "' AND '"
                        + endDateString + "'");
            }
            else
            {
                // if no date is given
                rs = statement.executeQuery("SELECT ID FROM Flight "
                        + "WHERE endAirport = '"
                        + getAirportID(endDestination)
                        + "' AND startAirport = '"
                        + getAirportID(startDestination)
                        + "'");
            }

            while (!rs.isClosed() && rs.next())
            {
                // goes through the resultset and calls the getFlight method 
                // with the ID picked from the resultset as parameter
                flights.add(getFlight(rs.getInt("ID")));

            }

        }
        catch (CommunicationsException connectionError)
        {
            if (connectToDatabase())
            {
                getFlightList(departureDate, startDestination, endDestination);
            }
            else
            {
                connectionError.printStackTrace();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return flights;

    }

    @Override
    public ArrayList<ReservationInterface> getReservationList(String reservationID,
                                                              String CPR)
    {
        //Create a new empty ArrayList of reservations to avoid nullpointers.
        ArrayList<ReservationInterface> reservations = new ArrayList<>();
        ResultSet rsReservation = null;

        //If the reservationsID given is null or an empty String, 
        // don't search for it. Search for CPR instead.
        if (reservationID == null || reservationID.equals(""))
        {
            try
            {
                Statement statement = con.createStatement();
                rsReservation = statement.executeQuery("SELECT * FROM Reservation "
                        + "WHERE CPR = '"
                        + CPR + "';");
            }
            catch (CommunicationsException connectionError)
            {
                if (connectToDatabase())
                {
                    getReservationList(reservationID,CPR);
                }
                else
                {
                    connectionError.printStackTrace();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        } //If the CPR given is null or an empty String, don't search for it.
        //Search for reservationID instead.
        else if (CPR == null || CPR.equals(""))
        {
            try
            {
                Statement statement = con.createStatement();
                rsReservation = statement.executeQuery("SELECT * FROM Reservation "
                        + "WHERE ID = '"
                        + reservationID + "';");
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        try
        {
            //If there are no more results, break.
            while (!rsReservation.isClosed() && rsReservation.next())
            {
                //Go through the results, and create reservations.
                //Add these to the list of found reservations.

                //Set the reservation details from the database info.
                ReservationInterface r = new Reservation();
                ArrayList<String> seatIDThisRes = new ArrayList<>();
                ArrayList<Person> personsThisRes = new ArrayList<>();
                r.setCPR(rsReservation.getString("CPR"));
                r.setFlight((Flight) getFlight(rsReservation.getInt("flight")));
                r.setID(rsReservation.getString("ID"));
                r.setPrice(rsReservation.getDouble("price"));
                //r.setPrice(rsReservation.getDouble("price"));
                r.setReservationDate(rsReservation.getDate("reservationDate"));

                try
                {
                    ResultSet rsSeat = null;
                    Statement statement2 = con.createStatement();
                    rsSeat = statement2.executeQuery("SELECT * FROM Seat "
                            + "WHERE ReservationID = '"
                            + rsReservation.getString("ID")
                            + "';");

                    while (!rsSeat.isClosed() && rsSeat.next())
                    {
                        seatIDThisRes.add(rsSeat.getString("seatID"));
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

                try
                {
                    ResultSet rsPerson = null;
                    Statement statement3 = con.createStatement();
                    rsPerson = statement3.executeQuery("SELECT * FROM People "
                            + "WHERE ReservationID = '"
                            + rsReservation.getString("ID")
                            + "';");
                    while (!rsPerson.isClosed() && rsPerson.next())
                    {
                        personsThisRes.add(getPerson(rsPerson.getInt("ID")));
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }

                r.bookSeats(seatIDThisRes);
                r.bookPersons(personsThisRes);
                //Add the finished reservation to the list for each found res.
                reservations.add(r);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        //Return the reservation
        return reservations;
    }

    @Override
    public boolean newReservation(ReservationInterface reservationToMake)
    {
        //If the CPR of the reservation is valid, continue.
        if (reservationToMake.getCPR().length() < 12)
        {
            try
            {
                Statement statement = con.createStatement();
                statement.executeUpdate("INSERT INTO Reservation "
                        + "(ID, flight, CPR, price) "
                        + "VALUES ('" + reservationToMake.getID()
                        + "', " + reservationToMake.getFlight().getID()
                        + ", '" + reservationToMake.getCPR()
                        + "' , " + reservationToMake.getPrice()
                        + ")");
            }
            catch (CommunicationsException connectionError)
            {
                if (connectToDatabase())
                {
                    newReservation(reservationToMake);
                }
                else
                {
                    connectionError.printStackTrace();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

            // save the seats in the reservation, if the 
            for (String seatID : reservationToMake.getBookedSeats())
            {
                insertSeat(seatID, reservationToMake.getID());
            }
            // save the persons in the reservation
            for (Person person : reservationToMake.getBookedPersons())
            {
                insertPerson(person, reservationToMake.getID());
            }

            //If the reservation was saved succesfully.
            return true;
        }

        //If the reservation wasn't saved succesfully.
        return false;
    }

    @Override
    public void removeReservation(String reservationID)
    {
        try
        {
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM Reservation "
                    + "WHERE ID = '" + reservationID + "'");
            statement.executeUpdate("DELETE FROM Seat "
                    + "WHERE ReservationID = '"
                    + reservationID + "'");
            statement.executeUpdate("DELETE FROM People "
                    + "WHERE ReservationID = '"
                    + reservationID + "'");
        }
        catch (CommunicationsException connectionError)
        {
            if (connectToDatabase())
            {
                removeReservation(reservationID);
            }
            else
            {
                connectionError.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getAirportCitiesAsStrings()
    {
        ArrayList<String> airports = new ArrayList<>();

        try
        {
            ResultSet rs = null;
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Airport");

            while (rs.next())
            {
                airports.add(rs.getString("city"));
            }

        }
        catch (CommunicationsException connectionError)
        {
            if (connectToDatabase())
            {
                getAirportCitiesAsStrings();
            }
            else
            {
                connectionError.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return airports;
    }

    /**
     * A signleton method used to make sure not two Database objects is created
     *
     * @return a new Database object if none exist or returns the already
     *         existing database.
     */
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
        if (ID <= 9999)
        {
            try
            {
                ResultSet matchingIDs = null;
                Statement statement = con.createStatement();
                // converts the ID to a string since reservation saves the ID
                // as a string.
                String idToString = "" + ID;
                matchingIDs = statement.executeQuery("SELECT * FROM Reservation "
                        + "WHERE ID = '" + idToString
                        + "'");
                if (matchingIDs.next())
                {
                    return false;
                }
            }
            catch (CommunicationsException connectionError)
            {
                if (connectToDatabase())
                {
                    checkForID(ID);
                }
                else
                {
                    connectionError.printStackTrace();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        if (ID > 9999)
        {
            try
            {
                ResultSet matchingIDs = null;
                Statement statement = con.createStatement();
                matchingIDs = statement.executeQuery("SELECT * FROM People "
                        + "WHERE " + ID + " IN(ID)");
                if (matchingIDs.next())
                {
                    return false;
                }
            }
            catch (CommunicationsException connectionError)
            {
                if (connectToDatabase())
                {
                    checkForID(ID);
                }
                else
                {
                    connectionError.printStackTrace();
                }
            }
            catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public ArrayList<String> getAllBookedSeats(int flightID)
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<>();
        ArrayList<String> reservationsOnThisFlight = new ArrayList<>();
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Reservation "
                    + "WHERE flight = " + flightID);
            while (!rs.isClosed() && rs.next())
            {
                // get all reservations on this flight.
                reservationsOnThisFlight.add(rs.getString("ID"));
            }

        }
        catch (CommunicationsException connectionError)
        {
            if (connectToDatabase())
            {
                getAllBookedSeats(flightID);
            }
            else
            {
                connectionError.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            ResultSet rs2 = null;
            for (String reservationID : reservationsOnThisFlight)
            {
                // for each reservation on the given flight.
                Statement statement = con.createStatement();
                rs2 = statement.executeQuery("SELECT seatID FROM Seat WHERE reservationID ='" + reservationID + "'");
                while (!rs2.isClosed() && rs2.next())
                {
                    // add the seats from this reservation
                    seatIDsToReturn.add(rs2.getString("seatID"));
                }
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return seatIDsToReturn;
    }

    @Override
    public ArrayList<String> getBookedSeatsOnReservation(String reservationID)
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<>();
        ResultSet rs = null;
        try
        {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Seat "
                    + "WHERE reservationID ='"
                    + reservationID + "'");
            while (!rs.isClosed() && rs.next())
            {
                seatIDsToReturn.add(rs.getString("seatID"));
            }

        }
        catch (CommunicationsException connectionError)
        {
            if (connectToDatabase())
            {
                getBookedSeatsOnReservation(reservationID);
            }
            else
            {
                connectionError.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return seatIDsToReturn;
    }

    @Override
    public ArrayList<Person> getBookedPersons(String reservationID)
    {
        ArrayList<Person> personsToReturn = new ArrayList<>();

        try
        {
            Statement statement = con.createStatement();
            ResultSet rs = null;
            rs = statement.executeQuery("SELECT * FROM People WHERE reservationID = '" + reservationID + "'");
            while (rs.next())
            {
                personsToReturn.add(getPerson(rs.getInt("ID")));
            }

            return personsToReturn;

        }
        catch (CommunicationsException connectionError)
        {
            if (connectToDatabase())
            {
                getBookedPersons(reservationID);
            }
            else
            {
                connectionError.printStackTrace();
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }
}
