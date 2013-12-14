/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flybooking;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anders Wind Steffensen - Awis@itu.dk
 */
public class AirportTest
{

    public AirportTest()
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
     * Test of getID method, of class Airport.
     */
    @Test
    public void testGetID()
    {
        System.out.println("getID");

        Airport instance = new Airport("RLX", "Denmark", "Rønne");
        String expResult = "RLX";
        String result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCountry method, of class Airport.
     */
    @Test
    public void testGetCountry()
    {
        System.out.println("getCountry");
        Airport instance = new Airport("RLX", "Denmark", "Rønne");
        String expResult = "Denmark";
        String result = instance.getCountry();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCity method, of class Airport.
     */
    @Test
    public void testGetCity()
    {
        System.out.println("getCity");
        Airport instance = new Airport("RLX", "Denmark", "Rønne");
        String expResult = "Rønne";
        String result = instance.getCity();
        assertEquals(expResult, result);
    }
}