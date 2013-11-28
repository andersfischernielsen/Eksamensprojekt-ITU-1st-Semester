/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flybooking;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Anders Wind Steffensen - awis@itu.dk
 */
public class ProgramStorage implements DatabaseInterface
{

    ArrayList<Flight> flightList = new ArrayList<Flight>();
    ArrayList<ReservationInterface> reservationList = new ArrayList<ReservationInterface>();
    ArrayList<Plane> planeList = new ArrayList<Plane>();
    ArrayList<Airport> airportList = new ArrayList<Airport>();
    ArrayList<Person> personList = new ArrayList<Person>();
    ArrayList<String> takenSeatsList = new ArrayList<String>();
    private static ProgramStorage instance = null;

    public static ProgramStorage getInstance()
    {
        if (instance == null)
        {
            instance = new ProgramStorage();
        }

        return instance;
    }

    /**
     * The constructor adds data to all the ArrayLists.
     */
    private ProgramStorage()
    {
        planeList.add(new Plane("Y312432", 6, 20));
        planeList.add(new Plane("H99342", 5, 25));
        planeList.add(new Plane("J22426", 6, 27));
        planeList.add(new Plane("A41376", 6, 20));

        airportList.add(new Airport("CPH", "Danmark", "Copenhagen"));
        airportList.add(new Airport("LAX", "USA", "Los Angeles"));
        airportList.add(new Airport("OSL", "Norway", "Oslo"));
        airportList.add(new Airport("MOW", "Russia", "Moscow"));
        airportList.add(new Airport("BER", "Germany", "Berlin"));

        // København til Berlin
        flightList.add(new Flight(600, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(500, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(750, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(800, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(400, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(600, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(500, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(750, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(800, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(400, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));

        // København til Moscow
        flightList.add(new Flight(1200, 1, planeList.get(0), new Timestamp(new Long("1386000000000")), new Timestamp(new Long("1388650000000")), airportList.get(0), airportList.get(3)));
        // København til Los Angelos
        flightList.add(new Flight(2000, 1, planeList.get(0), new Timestamp(new Long("1388000000000")), new Timestamp(new Long("1388500000000")), airportList.get(0), airportList.get(1)));

    }

    @Override
    public Plane getPlane(String PlaneID) throws SQLException
    {
        for (Plane plane : planeList)
        {
            if (plane.getID() == PlaneID)
            {
                return plane;
            }
        }
        return null; // Normally casts an exception here
    }

    @Override
    public ArrayList<Flight> getFlight(Date departureDate, String startDestination, String endDestination) throws SQLException
    {
        ArrayList<Flight> flightsToReturn = new ArrayList<Flight>();
        /**
         * DOES NOT TAKE DATE INTO ACCOUNT YET
         */
        if (departureDate != null && startDestination != null && endDestination != null)
        {
            for (Flight flight : flightList)
            {
                if (flight.getEndAirport().getCity().equals(endDestination) && flight.getStartAirport().getCity().equals(startDestination))
                {
                    flightsToReturn.add(flight);
                }
            }
        }
        return flightsToReturn;
    }

    @Override
    public ArrayList<ReservationInterface> getReservations(String ReservationID, String CPR)
    {
        ArrayList<ReservationInterface> reservationsToReturnList = new ArrayList<ReservationInterface>();
        /**
         * Does not take date into account yet
         */
        if (ReservationID != null)
        {
            if (CPR != null)
            {
                for (ReservationInterface reservation : reservationsToReturnList)
                {
                    if (reservation.getID().equals(ReservationID) && reservation.getCPR().equals(CPR))
                    {
                        reservationsToReturnList.add(reservation);
                    }
                }
            }
            else
            {
                for (ReservationInterface reservation : reservationsToReturnList)
                {
                    if (reservation.getID().equals(ReservationID))
                    {
                        reservationsToReturnList.add(reservation);
                    }
                }
            }

        }
        else
        {
            if (CPR != null)
            {
                for (ReservationInterface reservation : reservationsToReturnList)
                {
                    if (reservation.getCPR().equals(CPR))
                    {
                        reservationsToReturnList.add(reservation);
                    }
                }
            }

            // handle if none of the fields are filled
        }
        return reservationsToReturnList;
    }

    @Override
    public void newReservation(Flight flight, Person[] persons, String CPR, double Price)
    {
        ReservationInterface reservationToAdd = new Reservation();
        reservationToAdd.setCPR(CPR);
        reservationToAdd.setFlight(flight);
        reservationToAdd.setPayer(persons[0]);
        reservationToAdd.setPrice(Price);
        reservationList.add(reservationToAdd);
    }

    @Override
    public void newReservation(Reservation reservationToMake)
    {
        reservationList.add(reservationToMake);
    }

    @Override
    public void removeReservation(String reservationID) throws SQLException
    {
        for (ReservationInterface reservation : reservationList)
        {
            if (reservation.getID().equals(reservationID))
            {
                reservationList.remove(reservation);
            }
        }
    }

    @Override
    public void addPersonToReservation(String reservationID, Person personToAdd, String reservationSpot) throws SQLException
    {
        for (ReservationInterface reservation : reservationList)
        {
            if (reservation.getID().equals(reservationID))
            {
                reservation.addPerson(personToAdd);
                // something with reservationSpot.
            }
        }
    }

    @Override
    public ArrayList<String> getAirportCitiesAsStrings() throws SQLException
    {
        ArrayList<String> stringListToReturn = new ArrayList<String>();
        for (Airport airport : airportList)
        {
            stringListToReturn.add(airport.getCity());
        }
        return stringListToReturn;
    }

    @Override
    public boolean checkForID(String ID) throws SQLException
    {
        // maybe not neccesary
        for (ReservationInterface reservation : reservationList)
        {
            if (reservation.getID().equals(reservation))
            {
                return false;
            }
        }
        for (Person person : personList)
        {
            if (("" + person.getID()).equals(ID))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> getAllBookedSeats(int flightID)
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<String>();
        for (ReservationInterface reservation : reservationList)
        {
            if (reservation.getFlight().getID() == (flightID))
            {
                for (String seatIDs : reservation.getBookedSeats())
                {
                    seatIDsToReturn.add(seatIDs);
                }
                return seatIDsToReturn;
            }
        }
        System.out.println("Found no reservation with that flight");
        return null;
    }

    @Override
    public ArrayList<String> getBookedSeatsOnReservation(String reservationID)
    {
        for (ReservationInterface reservation : reservationList)
        {
            if (reservation.getID().equals(reservationID))
            {
                return reservation.getBookedSeats();
            }
        }
        System.out.println("Found no reservation with that ID");
        return null;
    }
}
