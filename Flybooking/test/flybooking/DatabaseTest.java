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
     * Test of insertPerson method, of class Database.
     */
    @Test
    public void testInsertPerson()
    {
        System.out.println("insertPerson");
        Person expResult = new Person("Tester Fornavn", "Tester Efternavn", 123456, "Testervej", 0);
        String ReservationID = "testResID";
        Database instance = Database.getInstance();
        instance.insertPerson(expResult, ReservationID);
        Person result = instance.getPerson(123456);
        
        assertEquals(expResult.getFirstName(), result.getFirstName());
        assertEquals(expResult.getLastName(), result.getLastName());
        assertEquals(expResult.getID(), result.getID());
        assertEquals(expResult.getAdress(), result.getAdress());
        assertEquals(expResult.getGroupID(), result.getGroupID());
    }


    /**
     * Test of getFlight method, of class Database.
     */
    @Test
    public void testGetFlight()
    {
        System.out.println("getFlight");
        int flightID = 512;
        String planeID = "Y45658";
        Database instance = Database.getInstance();
        FlightInterface result = instance.getFlight(flightID);
        assertEquals(10000.0, result.getPrice(), 0.1);
        assertEquals(flightID, result.getID());
        assertEquals("CPH", result.getStartAirport().getID());
        assertEquals("Copenhagen", result.getStartAirport().getCity());
        assertEquals("Denmark", result.getStartAirport().getCountry());
        assertEquals("MOW", result.getEndAirport().getID());
        assertEquals("Moscow", result.getEndAirport().getCity());
        assertEquals("Russia", result.getEndAirport().getCountry());
        assertEquals("Y45658", result.getPlane().getID());
        // could also test endDate and startDate.
    }

    /**
     * Test of getFlightList method, of class Database.
     */
    @Test
    public void testGetFlightList()
    {
        System.out.println("getFlightList");
        Database instance = Database.getInstance();
        Date startDate = null;
        Date endDate = null;
        String startDestination = "Washington, D.C.";
        String endDestination = "Copenhagen";
        
        // tests for the correct flights if the user hasen't entered anything
        // it is known that only 1 flight exists with this demand
        ArrayList<FlightInterface> result = instance.getFlightList(startDate, endDate, startDestination, endDestination);
        assertEquals(9937, result.get(0).getID());
        assertEquals(3000, result.get(0).getPrice(), 0.1);
        assertEquals("W29472", result.get(0).getPlane().getID());
        assertEquals("IAD", result.get(0).getStartAirport().getID());
        assertEquals("Washington, D.C.", result.get(0).getStartAirport().getCity());
        assertEquals("USA", result.get(0).getStartAirport().getCountry());
        assertEquals("CPH", result.get(0).getEndAirport().getID());
        assertEquals("Copenhagen", result.get(0).getEndAirport().getCity());
        assertEquals("Denmark", result.get(0).getEndAirport().getCountry());
        
        startDestination = "Tokyo";
        endDestination = null;
        // tests for flights from Tokyo
        // it is known that only 1 flight exists with this demand
        result = instance.getFlightList(startDate, endDate, startDestination, endDestination);
        assertEquals(20, result.get(0).getID());
        assertEquals(3800, result.get(0).getPrice(), 0.1);
        assertEquals("S12947", result.get(0).getPlane().getID());
        assertEquals("HND", result.get(0).getStartAirport().getID());
        assertEquals("Tokyo", result.get(0).getStartAirport().getCity());
        assertEquals("Japan", result.get(0).getStartAirport().getCountry());
        assertEquals("IST", result.get(0).getEndAirport().getID());
        assertEquals("Istanbul", result.get(0).getEndAirport().getCity());
        assertEquals("Turkey", result.get(0).getEndAirport().getCountry());
        
        startDestination = "Tokyo";
        endDestination = "Tokyo";
        // tests for flights from Tokyo to Tokyo
        // it is known that no flights exists with this demand
        result = instance.getFlightList(startDate, endDate, startDestination, endDestination);
        assertEquals(0, result.size());
        
        startDestination = "Tokyo";
        endDestination = "Tokyo";
        // tests for flights from Tokyo to Tokyo
        // it is known that no flights exists with this demand
        result = instance.getFlightList(startDate, endDate, startDestination, endDestination);
        assertEquals(0, result.size());
    }

    /**
     * Test of getReservationList method, of class Database.
     */
    @Test
    public void testGetReservationList()
    {
        System.out.println("getReservationList");
        Database instance = Database.getInstance();
        ArrayList<ReservationInterface> result;
        String reservationID = null;
        String CPR = null;
        Date startDate = null;
        Date endDate = null;
        String startDestination = "Tokyo";
        String endDestination = "Tokyo";
        // tests if the given data results in 0 reservations
        result = instance.getReservationList(reservationID, CPR, 
                startDate, endDate, startDestination, endDestination);
        assertEquals(1, result.size());

        reservationID = "8092";
        CPR = null;
        startDate = new Date();
        endDate = new Date();
        startDestination = null;
        endDestination = null;
        // tests if the given data returns the correct reservation
        result = instance.getReservationList(reservationID, CPR, 
                startDate, endDate, startDestination, endDestination);
        assertEquals(1, result.size());
        assertEquals(4000, result.get(0).getPrice(),0.1);
        assertEquals("1234567890", result.get(0).getCPR());
        assertEquals(45787, result.get(0).getFlight().getID());
        assertEquals("RØN", result.get(0).getFlight().getStartAirport().getID());
        assertEquals("TOM", result.get(0).getFlight().getEndAirport().getID());
        
        reservationID = null;
        CPR = "1234567890";
        startDate = new Date();
        endDate = new Date();
        startDestination = null;
        endDestination = null;
                result = instance.getReservationList(reservationID, CPR, 
                startDate, endDate, startDestination, endDestination);
        assertEquals(1, result.size());
        assertEquals(4000, result.get(0).getPrice(),0.1);
        assertEquals("1234567890", result.get(0).getCPR());
        assertEquals(45787, result.get(0).getFlight().getID());
        assertEquals("RØN", result.get(0).getFlight().getStartAirport().getID());
        assertEquals("TOM", result.get(0).getFlight().getEndAirport().getID());
        
        // There are 21 different combinations of search cases so not all are 
        // tested here, but all are possible
    }

    /**
     * Test of removeReservation method, of class Database.
     */
    @Test
    public void testInsertGetAndRemoveReservation()
    {
        System.out.println("removeReservation");
        Database instance = Database.getInstance();
        String reservationID = "testResID";
        // create a reservation that has an ID reservationID
        ReservationInterface resTest = new Reservation();
        resTest.setID(reservationID);
        resTest.setCPR("1234567890");
        resTest.setFlight((Flight)instance.getFlight(512));
        resTest.bookSeats(new ArrayList<String>());
        resTest.bookPersons(new ArrayList<Person>());
        // insert it into the database
        instance.newReservation(resTest);
        
        // check that the database has one entry with that reservation ID
        ArrayList<ReservationInterface> resTestList 
                = instance.getReservationList(reservationID,
                null, null, null, null, null);
        assertEquals(1, resTestList.size());
        // remove that reservation using that reservationID
        instance.removeReservation(reservationID);
        // check that the database now has 0 entries with that reservationID
        resTestList = instance.getReservationList(reservationID,
                null, null, null, null, null);
        assertEquals(0, resTestList.size());
        // the reservationIs now created and removed from the database.
        
    }

    /**
     * Test of getAirportCitiesAsStrings method, of class Database.
     */
    @Test
    public void testGetAirportCitiesAsStrings()
    {
        System.out.println("getAirportCitiesAsStrings");
        Database instance = Database.getInstance();
        ArrayList<String> result = instance.getAirportCitiesAsStrings();
        // tests that the ArrayList includes the right amount of cities.
        assertEquals(16, result.size());
    }


    /**
     * Test of checkForID method, of class Database.
     */
    @Test
    public void testCheckForID()
    {
        System.out.println("checkForID");
        int ID = 92779;
        Database instance = Database.getInstance();
        boolean expResult = false;
        boolean result = instance.checkForID(ID);
        assertEquals(expResult, result);

    }
    
}
