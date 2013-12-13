/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flybooking;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anders
 */
public class PlaneTest {
    
    public PlaneTest()
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
     * Test of SeatIDGenerator method, of class Plane.
     */
    @Test
    public void testSeatIDGenerator()
    {
        System.out.println("SeatIDGenerator");
        
        int col = 0;
        int row = 0;
        
        String expResult = "1A";
        String result = Plane.SeatIDGenerator(col, row);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getID method, of class Plane.
     */
    @Test
    public void testGetID()
    {
        System.out.println("getID");
        
        Plane instance = new Plane("AAC", 4, 20);
        
        String expResult = "AAC";
        String result = instance.getID();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfSeats method, of class Plane.
     */
    @Test
    public void testGetNumberOfSeats()
    {
        System.out.println("getNumberOfSeats");
        Plane instance = new Plane("AAC", 4, 20);
        
        int expResult = 80;
        int result = instance.getNumberOfSeats();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getRows method, of class Plane.
     */
    @Test
    public void testGetRows()
    {
        System.out.println("getRows");
        Plane instance = new Plane("AAC", 4, 20);
        
        int expResult = 4;
        int result = instance.getRows();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getColumns method, of class Plane.
     */
    @Test
    public void testGetColumns()
    {
        System.out.println("getColumns");
        Plane instance = new Plane("AAC", 4, 20);
        
        int expResult = 20;
        int result = instance.getColumns();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getSeat method, of class Plane.
     */
    @Test
    public void testGetSeat()
    {
        System.out.println("getSeat");
        String seatID = "1A";
        Plane instance = new Plane("AAC", 4, 20);
        
        String expResult = "1A";
        Seat result = instance.getSeat(seatID);
        
        assertEquals(result.getID(), expResult);
    }

    /**
     * Test of getSeatAvailability method, of class Plane.
     */
    @Test
    public void testGetSeatAvailability()
    {
        System.out.println("getSeatAvailability");
        
        String seatID = "1A";
        Plane instance = new Plane("AAC", 4, 20);
        
        boolean expResult = true;
        boolean result = instance.getSeatAvailability(seatID);
        
        assertEquals(expResult, result);
    }

    /**
     * Test of setSeatAvailability method, of class Plane.
     */
    @Test
    public void testSetSeatAvailability()
    {
        System.out.println("setSeatAvailability");
        String seatID = "1A";
        Plane instance = new Plane("AAC", 4, 20);
        
        instance.setSeatAvailability(seatID);
        
        assertFalse(instance.getSeatAvailability(seatID));
    }

    /**
     * Test of bookTakenSeats method, of class Plane.
     */
    @Test
    public void testBookTakenSeats()
    {
        System.out.println("bookTakenSeats");
        ArrayList<String> seatIDs = new ArrayList<>();
        
        seatIDs.add("1A");
        
        Plane instance = new Plane("AAC", 4, 20);;
        instance.bookTakenSeats(seatIDs);
        
        assertFalse(instance.getSeatAvailability("1A"));
    }
}
