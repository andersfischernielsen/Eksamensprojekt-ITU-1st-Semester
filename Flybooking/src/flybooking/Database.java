
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
    Statement statement2;
    private static Database instance = null;

    private Database(String name, String login, String password)
    {
        this.name = name;
        this.login = login;
        this.password = password;

        try {
            con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/" + name, login, password);
            statement = con.createStatement();
            statement2 = con.createStatement();
        } catch (SQLException e) {
            showMessageDialog(null, "Couldn't connect to the database!");
        }
    }

    // ------------------Simple objects --------
    @Override
    public Plane getPlane(String PlaneID)
    {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM Plane WHERE ID = '" + PlaneID + "'");
            rs.next();

            return new Plane(rs.getString("ID"), rs.getInt("rows"), rs.getInt("columns"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Airport getAirport(String AirportCityID)
    {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM Airport WHERE Airport.code = '" + AirportCityID + "'");
            rs.next();

            return new Airport(AirportCityID, rs.getString("Country"), rs.getString("City"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAirportID(String AirportCityName)
    {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT code FROM Airport WHERE city = '" + AirportCityName + "'");

            rs.next();
            return rs.getString("code");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Person getPerson(int PersonID)
    {
        try {
            ResultSet rs = null;
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM People WHERE People.ID = " + PersonID);
            rs.next();
            return new Person(rs.getString("Firstname"), rs.getString("Lastname"), rs.getInt("ID"), rs.getString("Address"), rs.getInt("groupID"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void insertPerson(Person person, String ReservationID)
    {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO People (ID, ReservationID, firstName, lastName, address, groupID) "
                    + "VALUES (" + person.getID() + ", '" + ReservationID + "', '" + person.getFirstName() + "' , '"
                    + person.getLastName() + "' , '" + person.getAdress() + "' ," + person.getGroupID() + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSeat(String seatID, String ReservationID)
    {
        try {
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO Seat (SeatID, ReservationID) "
                    + "VALUES ( '" + seatID + "' , '" + ReservationID + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------- advanced objects --------------
    @Override
    public FlightInterface getFlight(int flightID)
    {
        FlightInterface flight = null;
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Flight WHERE ID = " + flightID + "");
            while (!rs.isClosed() && rs.next()) {
                flight = new Flight(rs.getDouble("price"), rs.getInt("ID"), getPlane(rs.getString("plane")), new Date(), new Date(), getAirport(rs.getString("startAirport")), getAirport(rs.getString("endAirport")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    @Override
    public ArrayList<Flight> getFlightList(Date departureDate,
            String startDestination, String endDestination)
    {
        ArrayList<Flight> flights = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = statement.executeQuery("SELECT * FROM Flight WHERE endAirport = '" + getAirportID(endDestination) + "' AND startAirport = '" + getAirportID(startDestination) + "'"); // + departureDate + " AND startAirport = " + startDestination 

            while (!rs.isClosed() && rs.next()) {
                flights.add(new Flight(rs.getDouble("price"), rs.getInt("ID"), getPlane(rs.getString("plane")), new Date(), new Date(), getAirport(getAirportID(startDestination)), getAirport(getAirportID(endDestination))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;

    }

    @Override
    public ArrayList<ReservationInterface> getReservationList(String reservationID, String CPR)
    {
        //Create a new empty ArrayList of reservations to avoid nullpointers.
        ArrayList<ReservationInterface> reservations = new ArrayList<>();
        ArrayList<String> seatIDThisRes = new ArrayList<>();
        ArrayList<Person> personsThisRes = new ArrayList<>();
        ReservationInterface r = new Reservation();
        ResultSet rsReservation = null;

        //If the reservationsID given is null or an empty String, don't search for it.
        //Search for CPR instead.
        if (reservationID == null || reservationID.equals("")) {
            try {
                Statement statement = con.createStatement();
                rsReservation = statement.executeQuery("SELECT * FROM Reservation WHERE CPR = '" + CPR + "';");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } //If the CPR given is null or an empty String, don't search for it.
        //Search for reservationID instead.
        else if (CPR == null || CPR.equals("")) {
            try {
                Statement statement = con.createStatement();
                rsReservation = statement.executeQuery("SELECT * FROM Reservation WHERE ID = '" + reservationID + "';");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            //If there are no more results, break.
            while (!rsReservation.isClosed() && rsReservation.next()) {
                //Go through the results, and create reservations.
                //Add these to the list of found reservations.

                //Set the reservation details from the database info.
                r.setCPR(rsReservation.getString("CPR"));
                r.setFlight((Flight) getFlight(rsReservation.getInt("flight")));
                r.setID(rsReservation.getString("ID"));
                //r.setPrice(rsReservation.getDouble("price"));
                r.setReservationDate(new Date());

                try {
                    Statement statement2 = con.createStatement();
                    ResultSet rsSeat = statement2.executeQuery("SELECT * FROM Seat WHERE ReservationID = '" + rsReservation.getString("ID") + "';");

                    while (!rsSeat.isClosed() && rsSeat.next()) {
                        seatIDThisRes.add(rsSeat.getString("seatID"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                try {
                    Statement statement3 = con.createStatement();
                    ResultSet rsPerson = statement3.executeQuery("SELECT * FROM People WHERE ReservationID = '" + rsReservation.getString("ID") + "';");

                    while (!rsPerson.isClosed() && rsPerson.next()) {
                        personsThisRes.add(getPerson(rsPerson.getInt("ID")));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                r.bookSeats(seatIDThisRes);
                r.bookPersons(personsThisRes);
                //Add the finished reservation to the list for each found res.
                reservations.add(r);
            }
        } catch (SQLException ex) {
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
    public void newReservation(ReservationInterface reservationToMake)
    {
        // save the reservation.
        try {
            statement.executeUpdate("INSERT INTO Reservation (ID, flight, CPR) "
                    + "VALUES ('" + reservationToMake.getID() + "', " + reservationToMake.getFlight().getID() + ", '" + reservationToMake.getCPR() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // save the seats in the reservation
        for (String seatID : reservationToMake.getBookedSeats()) {
            insertSeat(seatID, reservationToMake.getID());
        }
        // save the persons in the reservation
        for (Person person : reservationToMake.getBookedPersons()) {
            insertPerson(person, reservationToMake.getID());
        }
    }

    @Override
    public void removeReservation(String reservationID)
    {
        try {
            Statement statement = con.createStatement();
            statement.executeQuery("DELETE FROM Reservation WHERE ID = '" + reservationID + "'");
            statement.executeQuery("DELETE FROM Seat WHERE ReservationID = '" + reservationID + "'");
            statement.executeQuery("DELETE FROM Person WHERE ReservationID = '" + reservationID + "'S");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateReservation(ReservationInterface reservationToMake)
    {
        try {
            statement.executeQuery("DELETE FROM Seat WHERE Seat.ReservationID = " + reservationToMake.getID());
            statement.executeQuery("DELETE FROM Person WHERE Person.ReservationID = " + reservationToMake.getID());
            statement.executeQuery("UPDATE Rerservation SET price= " + reservationToMake.getPrice() + "WHERE Reservation.ID = " + reservationToMake.getID());
            for (String seatID : reservationToMake.getBookedSeats()) {
                insertSeat(seatID, reservationToMake.getID());
            }
            // save the persons in the reservation
            for (Person person : reservationToMake.getBookedPersons()) {
                insertPerson(person, reservationToMake.getID());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addPersonToReservation(String reservationID, Person person, String reservationSpot)
    {
        // USE UPDATERESERVATION INSTEAD
        try {
            statement.executeQuery("INSERT INTO People (ID, firstName, lastName, address, groupID) VALUES ('"
                    + person.getID() + "', '"
                    + person.getFirstName() + "', '"
                    + person.getLastName() + "', '"
                    + person.getAdress() + "', '"
                    + person.getGroupID() + "')");
            statement.executeQuery("UPDATE " + name + ".Reservation SET " + reservationSpot + "PersonID = " + person.getID() + " WHERE  Reservation.ID = " + reservationID + "");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getAirportCitiesAsStrings()
    {
        ArrayList<String> airports = new ArrayList<>();

        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM Airport");

            while (rs.next()) {
                airports.add(rs.getString("city"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
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
    public boolean checkForID(int ID)
    {
        // IKKE NØDVENDIG MERE
        if (ID <= 9999) {
            try {
                ResultSet matchingIDs = statement.executeQuery("SELECT * FROM Reservation WHERE " + ID + " IN(ID)");
                if (matchingIDs.next()) {
                    return false;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (ID > 9999) {
            try {
                ResultSet matchingIDs = statement.executeQuery("SELECT * FROM People WHERE " + ID + " IN(ID)");
                if (matchingIDs.next()) {
                    return false;
                }
            } catch (SQLException ex) {
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
        try {
            rs = statement.executeQuery("SELECT * FROM Reservation WHERE flight = " + flightID);
            while (!rs.isClosed() && rs.next()) {
                reservationsOnThisFlight.add(rs.getString("ID"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            ResultSet rs2 = null;
            for (String reservationID : reservationsOnThisFlight) {
                Statement statement = con.createStatement();
                rs2 = statement.executeQuery("SELECT * FROM Seat WHERE reservationID ='" + reservationID + "'");
                while (!rs2.isClosed() && rs2.next()) {
                    seatIDsToReturn.add(rs2.getString("seatID"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return seatIDsToReturn;
    }

    @Override
    public ArrayList<String> getBookedSeatsOnReservation(String reservationID)
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<>();
        ResultSet rs = null;
        try {
            Statement statement = con.createStatement();
            rs = statement.executeQuery("SELECT * FROM Seat WHERE reservationID ='" + reservationID + "'");
            while (!rs.isClosed() && rs.next()) {
                seatIDsToReturn.add(rs.getString("seatID"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
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
    public ArrayList<Person> getBookedPersons(String reservationID)
    {
        ArrayList<Person> personsToReturn = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = null;
            rs = statement.executeQuery("SELECT * FROM People WHERE reservationID = '" + reservationID + "'");
            while (rs.next()) {
                personsToReturn.add(getPerson(rs.getInt("ID")));
            }

            return personsToReturn;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
