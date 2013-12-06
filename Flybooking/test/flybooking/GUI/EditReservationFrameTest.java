/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flybooking.GUI;

import flybooking.ControllerInterface;
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
public class EditReservationFrameTest {
    
    public EditReservationFrameTest()
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
     * Test of getInstance method, of class EditReservationFrame.
     */
    @Test
    public void testGetInstance()
    {
        System.out.println("getInstance");
        ControllerInterface controller = null;
        EditReservationFrame expResult = null;
        EditReservationFrame result = EditReservationFrame.getInstance(controller);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendOnData method, of class EditReservationFrame.
     */
    @Test
    public void testSendOnData()
    {
        System.out.println("sendOnData");
        EditReservationFrame instance = null;
        instance.sendOnData();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
