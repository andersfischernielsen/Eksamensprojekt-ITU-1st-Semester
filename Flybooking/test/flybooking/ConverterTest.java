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
 * @author Anders
 */
public class ConverterTest {
    
    public ConverterTest()
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
     * Test of getFinalPrice method, of class Converter.
     */
    @Test
    public void testGetFinalPrice()
    {
        System.out.println("getFinalPrice - tests all age groups and seat "
                + "classes");
        double flightPrice = 10.0;
        ArrayList<Person> persons = new ArrayList<>();
        
        //Add a normal person, a kid and a senior to the list of people.
        persons.add(new Person("Normal", " ", 1, " ", 0));
        persons.add(new Person("Kid", " ", 2, " ", 1));
        persons.add(new Person("Senior", " ", 3, " ", 2));
        
        //Add a normal, business and first class seat to the list of seats.
        ArrayList<Integer> seatsGroupID = new ArrayList<>();
        seatsGroupID.add(0);
        seatsGroupID.add(1);
        seatsGroupID.add(2);
        
        //Calculate the price for the seats.
        double priceForSeats = flightPrice + (flightPrice * 1.2) + (flightPrice * 1.5);
        //Calculate the savings.
        double savings = flightPrice/10 + flightPrice/20;
        //Calculate the expected price.
        double expResult = priceForSeats - savings;
        
        double result = Converter.getFinalPrice(flightPrice, persons, seatsGroupID);
        
        //Assert that the test gave the expected result.
        assertEquals(expResult, result, 0.0);
        
    }

    /**
     * Test of convertDateToString method, of class Converter.
     */
    @Test
    public void testConvertDateToString()
    {
        System.out.println("convertDateToString");
        
        //A test date.
        Date testDate = new Date(1387494000000L);
        
        //The expected string.
        String expResult = "20/12-2013";
        String result = Converter.convertDateToString(testDate);
        
        //Assert that the test gave the expected result.
        assertEquals(expResult, result);
    }

    /**
     * Test of convertDateToHourString method, of class Converter.
     */
    @Test
    public void testConvertDateToHourString()
    {
        System.out.println("convertDateToHourString");
        
        //A test date (the 20th of december 2013 00:00 
        //in milliseconds from 1970).
        Date testDate = new Date(1387494000000L);
        
        //The expected string.
        String expResult = "24:00";
        String result = Converter.convertDateToHourString(testDate);
        
        //Assert that the test gave the expected result.
        assertEquals(expResult, result);
    }

    /**
     * Test of convertStringToDate method, of class Converter.
     */
    @Test
    public void testConvertStringToDate()
    {
        System.out.println("convertStringToDate");
        
        //A test date (the 20th of december 2013 00:00 
        //in milliseconds from 1970).
        Date testDate = new Date(1387494000000L);
        
        //The expected date.
        Date expResult = testDate;
        Date result = Converter.convertStringToDate("20/12-2013");
        
        //Assert that the test gave the expected result.
        assertEquals(expResult, result);
    }

    /**
     * Test of createPersonID method, of class Converter.
     */
    @Test
    public void testCreatePersonID()
    {
        System.out.println("createPersonID");
        
        //Create an ID.
        int result = Converter.createPersonID();
        
        //Assert that the generated ID is between the allowed numbers.
        for (int i = 0; i < 10000; i++) {
            assertTrue((result < 99999 && result > 9999));
        }
    }
}
