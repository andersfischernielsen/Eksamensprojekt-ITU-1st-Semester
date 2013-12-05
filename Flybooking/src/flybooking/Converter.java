package flybooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author Anders Fischer-Nielsen
 */
public class Converter
{

    private static Random randomGen = new Random();

    /**
     * Convert the final price for a reservation.
     *
     * @param flightPrice The price of the flight
     * @param persons The persons in the reservation. 
     *
     * @return The sum of the two numbers.
     */
    public static double getFinalPrice(double flightPrice, ArrayList<Person> persons)
    {
        //Initialize the final calculated price.
        double finalPrice;
        //Calculate the final price from the amount of people.
        finalPrice = flightPrice * persons.size();
        //Initialize the savings discount.
        double savings = 0;
        //Check the people in the reservation for their age group.
        for (Person person : persons)
        {
            switch (person.getGroupID())
            {
                //If the person is an adult, give no discount.
                case 0:
                {
                    break;
                }
                //If the person is a child, give a 20% discount.
                case 1:
                {
                    savings += flightPrice/20;
                    break;
                }
                //If the person is elderly, give a 10% discount.
                case 2:
                {
                    savings += flightPrice/10;
                    break;
                }
            }
        }
        //Subtract the savings from the calculated price.
        finalPrice -=savings;
        
        return finalPrice;
    }

    /**
     * Convert a given date into a string (in the format "dd/MM-yyyy").
     *
     * @param date The date to parse.
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
     * @return The given string as a Date.
     */
    public static Date convertStringToDate(String string)
    {
        try {
            return new SimpleDateFormat("dd/MM-yyyy").parse(string);
        } catch (ParseException ex) { ex.printStackTrace(); }
        
        return null;
    }

    /**
     * Generate a person ID for creating a new Person.
     *
     * @return A new ID.
     */
    public static int createPersonID()
    {
        //Keep generating IDs until a valid one is generated.
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
        //Keep generating IDs until a valid one is generated.
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
