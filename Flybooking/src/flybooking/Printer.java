
package flybooking;

import java.util.ArrayList;

/**
 *
 * @author Anders
 */
public class Printer implements ReceiptPrinter {

    private Reservation reservation;

    public Printer(Reservation reservation)
    {
        this.reservation = reservation;
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
        System.out.println(createPlaneDetails());
        System.out.println("-------------------------------------------------");
    }

    @Override
    public String createAirportDetails()
    {
        String finalString;
        finalString = "Departure:  "
                + reservation.getFlight().getStartDate().toString()
                + reservation.getFlight().getStartAirport().toString();
        finalString += "\n Arrival:    "
                + reservation.getFlight().getStartDate().toString()
                + reservation.getFlight().getStartAirport().toString();

        return finalString;
    }

    @Override
    public String createPeopleDetails()
    {
        String finalString = "Seats:       ";
        for (Person p : reservation.getPersons()) {
            finalString += " \n            " + p.getFirstName() + p.getLastName();
        }

        return finalString;
    }

    @Override
    public String createPlaneDetails()
    {
        String finalString;
        finalString = "Plane:      " + reservation.getFlight().getPlane().toString();
        return finalString;
    }

    @Override
    public String createPayerDetails()
    {
        String finalString;
        finalString = "Payer:      " + reservation.getCPR() + "        " + 
                      reservation.getPayer().toString();
        return finalString;
    }

}
