/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flybooking;

import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anders Wind Steffensen - awis@itu.dk
 */
public class DatabaseTest
{
    
    public DatabaseTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getPlane method, of class Database.
     */
    @Test
    public void testGetPlane()
    {
        System.out.println("getPlane");
        String PlaneID = "C10284";
        Database instance = Database.getInstance();
        Plane expResult = new Plane(PlaneID, 6, 28);        
        Plane result = instance.getPlane(PlaneID);
        
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getRows(), result.getRows());
        assertEquals(expResult.getColumns(), result.getColumns());
    }

    /**
     * Test of getAirport method, of class Database.
     */
    @Test
    public void testGetAirport()
    {
        System.out.println("getAirport");
        String AirportCityID = "LAX";
        Database instance = Database.getInstance();
        Airport expResult = new Airport(AirportCityID, "USA", "Los Angeles");
        Airport result = instance.getAirport(AirportCityID);
        
        assertEquals(expResult.getCity(), result.getCity());
        assertEquals(expResult.getCountry(), result.getCountry());
        assertEquals(expResult.getID(), result.getID());
    }

    /**
     * Test of getAirportID method, of class Database.
     */
    @Test
    public void testGetAirportID()
    {
        System.out.println("getAirportID");
        String AirportCityName = "Berlin";
        Database instance = Database.getInstance();
        String expResult = "BER";
        String result = instance.getAirportID(AirportCityName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getPerson method, of class Database.
     */
    @Test
    public void testGetPerson()
    {
        System.out.println("getPerson");
        int PersonID = 63766;
        Database instance = Database.getInstance();
        Person expResult = new Person("Anders", "Wind", PersonID, "", 0);
        Person result = instance.getPerson(PersonID);
        
        assertEquals(expResult.getFirstName(), result.getFirstName());
        assertEquals(expResult.getLastName(), result.getLastName());
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getAdress(), result.getAdress());
        assertEquals(expResult.getGroupID(), result.getGroupID());
    }

    /**
     * Test of insertPerson method, of class Database.
     */
    @Test
    public void testInsertPerson()
    {
        System.out.println("insertPerson");
        Person expResult = new Person("Tester Fornavn", "Tester Efternavn", 1234567890, "Testervej", 0);
        String ReservationID = "0987654321";
        Database instance = Database.getInstance();
        instance.insertPerson(expResult, ReservationID);
        Person result = instance.getPerson(1234567890);
        
        assertEquals(expResult.getFirstName(), result.getFirstName());
        assertEquals(expResult.getLastName(), result.getLastName());
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getAdress(), result.getAdress());
        assertEquals(expResult.getGroupID(), result.getGroupID());
    }

    /**
     * Test of insertSeat method, of class Database.
     */
    @Test
    public void testInsertSeat()
    {
        System.out.println("insertSeat");
        String seatID = "A21";
        String ReservationID = "0987654321";
        Database instance = Database.getInstance();
        instance.insertSeat(seatID, ReservationID);
    }

    /**
     * Test of getFlight method, of class Database.
     */
    @Test
    public void testGetFlight()
    {
        System.out.println("getFlight");
        int flightID = 0;
        Database instance = null;
        FlightInterface expResult = null;
        FlightInterface result = instance.getFlight(flightID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlightList method, of class Database.
     */
    @Test
    public void testGetFlightList()
    {
        System.out.println("getFlightList");
        Date departureDate = null;
        String startDestination = "";
        String endDestination = "";
        Database instance = null;
        ArrayList<FlightInterface> expResult = null;
        ArrayList<FlightInterface> result = instance.getFlightList(departureDate, startDestination, endDestination);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReservationList method, of class Database.
     */
    @Test
    public void testGetReservationList()
    {
        System.out.println("getReservationList");
        String reservationID = "";
        String CPR = "";
        Database instance = null;
        ArrayList<ReservationInterface> expResult = null;
        ArrayList<ReservationInterface> result = instance.getReservationList(reservationID, CPR);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newReservation method, of class Database.
     */
    @Test
    public void testNewReservation()
    {
        System.out.println("newReservation");
        ReservationInterface reservationToMake = null;
        Database instance = null;
        boolean expResult = false;
        boolean result = instance.newReservation(reservationToMake);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeReservation method, of class Database.
     */
    @Test
    public void testRemoveReservation()
    {
        System.out.println("removeReservation");
        String reservationID = "";
        Database instance = null;
        instance.removeReservation(reservationID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAirportCitiesAsStrings method, of class Database.
     */
    @Test
    public void testGetAirportCitiesAsStrings()
    {
        System.out.println("getAirportCitiesAsStrings");
        Database instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getAirportCitiesAsStrings();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInstance method, of class Database.
     */
    @Test
    public void testGetInstance()
    {
        System.out.println("getInstance");
        Database expResult = null;
        Database result = Database.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkForID method, of class Database.
     */
    @Test
    public void testCheckForID()
    {
        System.out.println("checkForID");
        int ID = 0;
        Database instance = null;
        boolean expResult = false;
        boolean result = instance.checkForID(ID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllBookedSeats method, of class Database.
     */
    @Test
    public void testGetAllBookedSeats()
    {
        System.out.println("getAllBookedSeats");
        int flightID = 0;
        Database instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getAllBookedSeats(flightID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBookedSeatsOnReservation method, of class Database.
     */
    @Test
    public void testGetBookedSeatsOnReservation()
    {
        System.out.println("getBookedSeatsOnReservation");
        String reservationID = "";
        Database instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getBookedSeatsOnReservation(reservationID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBookedPersons method, of class Database.
     */
    @Test
    public void testGetBookedPersons()
    {
        System.out.println("getBookedPersons");
        String reservationID = "";
        Database instance = null;
        ArrayList<Person> expResult = null;
        ArrayList<Person> result = instance.getBookedPersons(reservationID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
