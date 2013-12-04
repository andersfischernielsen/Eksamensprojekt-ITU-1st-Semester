package flybooking;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Anders Fischer-Nielsen
 */
public class Calculator
{

    private static Random randomGen = new Random();

    /**
     * Add two numbers and get the result.
     *
     * @param flightPrice the price of the flight
     * @param n1 The first number to add.
     * @param n2 The second number to add.
     *
     * @return The sum of the two numbers.
     */
    public static double getprice(double flightPrice, ArrayList<Person> persons)
    {
        double finalPrice;
        finalPrice = flightPrice * persons.size();
        double savings = 0;
        for (Person person : persons)
        {
            switch (person.getGroupID())
            {
                case 0:
                {
                    break;
                }
                case 1:
                {
                    // childs 10% offer
                    savings += flightPrice/10;
                    break;
                }
                case 2:
                {
                    // Elder 10% offer
                    savings += flightPrice/10;
                    break;
                }
            }
        }
        finalPrice -=savings;
        return finalPrice;
    }

    public static int add(int n1, int n2)
    {
        return n1 + n2;
    }

    /**
     * Subtract two numbers and get the result.
     *
     * @param n1 The first number to subtract.
     * @param n2 The second number to subtract.
     *
     * @return The sum of the two numbers.
     */
    public static int subtract(int n1, int n2)
    {
        return n1 - n2;
    }

    /**
     * Multiply two numbers and get the result.
     *
     * @param n1 The first number to multiply.
     * @param n2 The second number to multiply.
     *
     * @return The sum of the two numbers.
     */
    public static int multiply(int n1, int n2)
    {
        return n1 * n2;
    }

    /**
     * Convert a given date into a string.
     *
     * @param date The date to parse.
     *
     * @return The parsed Date as a string.
     */
    public static String convertDateToString(Date date)
    {
        return new SimpleDateFormat("dd/MM-yyyy").format(date);
    }

    /**
     * Convert the time of the date into a string.
     *
     * @param date The date to convert.
     *
     * @return The hour of the date as a string.
     */
    public static String convertDateToHourString(Date date)
    {
        return new SimpleDateFormat("kk:mm").format(date);
    }

    /**
     * Convert a string (with the format "dd/MM-yyyy") into a date.
     *
     * @param string The string to convert
     *
     * @return The given string as a Date.
     *
     * @throws ParseException
     */
    public static Date convertStringToDate(String string) throws ParseException
    {
        return new SimpleDateFormat("dd/MM-yyyy").parse(string);
    }

    /**
     * Generate a person ID for creating a new Person.
     *
     * @return A new ID.
     */
    public static int createPersonID()
    {
        while (true)
        {
            int personID = randomGen.nextInt(99999);
            if (Database.getInstance().checkForID(personID))
            {
                return personID;
            }
        }
    }

    /**
     * Generate a reservation ID for creating a new Reservation.
     *
     * @return A new ID.
     */
    public static String createReservationID()
    {
        while (true)
        {
            int reservationID = randomGen.nextInt(9999);
            if (Database.getInstance().checkForID(reservationID))
            {
                return reservationID + "";
            }
        }
    }
}
