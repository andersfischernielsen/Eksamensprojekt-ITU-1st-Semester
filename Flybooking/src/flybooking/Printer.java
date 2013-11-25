
package flybooking;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

/**
 *
 * @author Anders
 */
public class Printer implements ReceiptPrinter {

    private Reservation reservation;
    private SimpleDateFormat simpleDate;

    public Printer(Reservation reservation)
    {
        this.reservation = reservation;
        simpleDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
    }

    @Override
    public void print()
    {
        System.out.println("-------------------------------------------------");
        System.out.println("");
        System.out.println(createAirportDetails());
        System.out.println("");
        System.out.println(createPeopleDetails());
        System.out.println("");
        System.out.println(createPlaneDetails());
        System.out.println("");
        System.out.println(createPayerDetails());
        System.out.println("-------------------------------------------------");
    }

    @Override
    public String createAirportDetails()
    {
        String finalString;
        finalString = "Departure:  "
                + simpleDate.format(reservation.getFlight().getStartDate())
                + " " + reservation.getFlight().getStartAirport().getName();
        finalString += "\nArrival:    "
                + simpleDate.format(reservation.getFlight().getEndDate())
                + " " + reservation.getFlight().getEndAirport().getName();

        return finalString;
    }

    @Override
    public String createPeopleDetails()
    {
        String finalString = "Seats:      ";
        for (Person p : reservation.getPersons()) {
            finalString += p.getFirstName() + " " + p.getLastName() + "\n            ";
        }

        return finalString;
    }

    @Override
    public String createPlaneDetails()
    {
        String finalString;
        finalString = "Plane:      " + reservation.getFlight().getPlane().getID();
        return finalString;
    }

    @Override
    public String createPayerDetails()
    {
        String finalString;
        finalString = "Payer:      " + reservation.getCPR() + "        " + 
                      reservation.getPayer().getFirstName() + " " + 
                      reservation.getPayer().getLastName();
        return finalString;
    }

}
