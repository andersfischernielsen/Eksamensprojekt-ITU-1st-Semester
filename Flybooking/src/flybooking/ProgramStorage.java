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
public class ProgramStorage implements DatabaseInterface {

    ArrayList<FlightInterface> flightList = new ArrayList<>();
    ArrayList<ReservationInterface> reservationList = new ArrayList<>();
    ArrayList<Plane> planeList = new ArrayList<>();
    ArrayList<Airport> airportList = new ArrayList<>();
    ArrayList<Person> personList = new ArrayList<>();
    ArrayList<String> takenSeatsList = new ArrayList<>();
    private static ProgramStorage instance = null;

    public static ProgramStorage getInstance()throws SQLException
    {
        if (instance == null) {
            instance = new ProgramStorage();
        }

        return instance;
    }

    /**
     * The constructor adds data to all the ArrayLists.
     */
    private ProgramStorage()throws SQLException
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
        flightList.add(new Flight(500, 2, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(750, 3, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(800, 4, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(400, 5, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(600, 6, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(500, 7, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(750, 8, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(800, 9, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        flightList.add(new Flight(400, 10, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));

        // København til Moscow
        flightList.add(new Flight(1200, 11, planeList.get(1), new Timestamp(new Long("1386000000000")), new Timestamp(new Long("1388650000000")), airportList.get(0), airportList.get(3)));
        // København til Los Angelos
        flightList.add(new Flight(2000, 12, planeList.get(2), new Timestamp(new Long("1388000000000")), new Timestamp(new Long("1388500000000")), airportList.get(0), airportList.get(1)));

        Reservation res1 = new Reservation();
        res1.setCPR("060606-0606");
        res1.setFlight(new Flight(600, 1, planeList.get(0), new Date(), new Date(), airportList.get(0), airportList.get(4)));
        res1.setPrice(18000);
        res1.setReservationDate(new Date());
        reservationList.add(res1);

    }

    @Override
    public Plane getPlane(String PlaneID)
    {
        for (Plane plane : planeList) {
            if (plane.getID().equals(PlaneID)) {
                return plane;
            }
        }
        return null; // Normally casts an exception here
    }

    @Override
    public ArrayList<FlightInterface> getFlightList(Date departureDate, String startDestination, String endDestination)
    {
        ArrayList<FlightInterface> flightsToReturn = new ArrayList<>();
        /**
         * DOES NOT TAKE DATE INTO ACCOUNT YET
         */
        if (departureDate != null && startDestination != null && endDestination != null) {
            for (FlightInterface flight : flightList) {
                if (flight.getEndAirport().getCity().equals(endDestination) && flight.getStartAirport().getCity().equals(startDestination)) {
                    flightsToReturn.add(flight);
                }
            }
        }
        return flightsToReturn;
    }

    @Override
    public ArrayList<ReservationInterface> getReservationList(String ReservationID, String CPR)
    {
        ArrayList<ReservationInterface> reservationsToReturnList = new ArrayList<>();
        /**
         * Does not take date into account yet
         */
        if (ReservationID != null) {
            if (CPR != null) {
                for (ReservationInterface reservation : reservationsToReturnList) {
                    if (reservation.getID().equals(ReservationID) && reservation.getCPR().equals(CPR)) {
                        reservationsToReturnList.add(reservation);
                    }
                }
            } else {
                for (ReservationInterface reservation : reservationsToReturnList) {
                    if (reservation.getID().equals(ReservationID)) {
                        reservationsToReturnList.add(reservation);
                    }
                }
            }

        } else {
            if (CPR != null) {
                for (ReservationInterface reservation : reservationsToReturnList) {
                    if (reservation.getCPR().equals(CPR)) {
                        reservationsToReturnList.add(reservation);
                    }
                }
            }

            // handle if none of the fields are filled
        }
        return reservationsToReturnList;
    }

    @Override
    public boolean newReservation(ReservationInterface reservationToMake)
    {
        reservationList.add(reservationToMake);
        return true;
    }

    @Override
    public void removeReservation(String reservationID)
    {
        for (ReservationInterface reservation : reservationList) {
            if (reservation.getID().equals(reservationID)) {
                reservationList.remove(reservation);
            }
        }
    }

    public void addPersonToReservation(String reservationID, Person personToAdd, String reservationSpot)
    {
        for (ReservationInterface reservation : reservationList) {
            if (reservation.getID().equals(reservationID)) {
                reservation.addPerson(personToAdd);
                // something with reservationSpot.
            }
        }
    }

    @Override
    public ArrayList<String> getAirportCitiesAsStrings()
    {
        ArrayList<String> stringListToReturn = new ArrayList<>();
        for (Airport airport : airportList) {
            stringListToReturn.add(airport.getCity());
        }
        return stringListToReturn;
    }

    @Override
    public boolean checkForID(int ID)
    {
        // maybe not neccesary
        for (ReservationInterface reservation : reservationList) {
            if (reservation.getID().equals(reservation)) {
                return false;
            }
        }
        for (Person person : personList) {
            if (("" + person.getID()).equals(ID)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> getAllBookedSeats(int flightID)
    {
        ArrayList<String> seatIDsToReturn = new ArrayList<>();
        for (ReservationInterface reservation : reservationList) {
            if (reservation.getFlight().getID() == (flightID)) {
                for (String seatIDs : reservation.getBookedSeats()) {
                    seatIDsToReturn.add(seatIDs);
                }
            }
        }
        return seatIDsToReturn;
    }

    @Override
    public ArrayList<String> getBookedSeatsOnReservation(String reservationID)
    {
        for (ReservationInterface reservation : reservationList) {
            if (reservationID != null && reservation.getID() != null && reservation.getID().equals(reservationID)) {
                return reservation.getBookedSeats();
            }
        }
        return null;
    }

    public ArrayList<ReservationInterface> getReservationsFromCPR(String CPR)
    {
        ArrayList results = new ArrayList<>();

        for (ReservationInterface r : reservationList) {
            if (r.getCPR().equals(CPR)) {
                results.add(r);
            }
        }

        return results;
    }

    
    public ArrayList<ReservationInterface> getReservationsFromID(String ID)
    {
        if (ID == null) {
            return new ArrayList<>();
        } else {
            ArrayList results = new ArrayList<>();

            for (ReservationInterface r : reservationList) {
                if (r.getID().equals(ID)) {
                    results.add(r);
                }
            }

            return results;
        }
    }

    @Override
    public ArrayList<Person> getBookedPersons(String reservationID)
    {
        for (ReservationInterface reservation : reservationList) {
            if (reservation.getID() != null && reservationID != null && reservation.getID().equals(reservationID)) {
                return reservation.getBookedPersons();
            }
        }
        return new ArrayList<Person>();
    }

    @Override
    public Airport getAirport(String AirportID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person getPerson(int PersonID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FlightInterface getFlight(int flightID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
