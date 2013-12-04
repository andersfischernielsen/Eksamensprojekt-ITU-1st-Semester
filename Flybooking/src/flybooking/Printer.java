package flybooking;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

/**
 * Print a receipt for a given reservation.
 *
 * @author Anders
 */
public class Printer implements ReceiptPrinter
{

    private ReservationInterface reservation;
    private SimpleDateFormat simpleDate;
    private ControllerInterface controller;

    public Printer(ReservationInterface reservation)
    {
        this.reservation = reservation;
        controller = Controller.getInstance();
        simpleDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    }

    @Override
    public String print()
    {
        String string = "-------------------------------------------------";
                        string += "\n";
                        string += createReservationIDDetails();
                        string += "\n";
                        string += createAirportDetails();
                        string += "\n";
                        string += createPeopleDetails();
                        string += "\n";
                        string += createPlaneDetails();
                        string += "\n";
                        string += "\n";
                        string += createPriceDetails();
                        string += "\n";
              string += "-------------------------------------------------";

        return string;
    }

    @Override
    public String createAirportDetails()
    {
        String finalString;
        finalString = "Departure: "
                + simpleDate.format(reservation.getFlight().getStartDate())
                + " " + reservation.getFlight().getStartAirport().getID();
        finalString += "\nArrival: "
                + simpleDate.format(reservation.getFlight().getEndDate())
                + " " + reservation.getFlight().getEndAirport().getID();

        return finalString;
    }

    @Override
    public String createPeopleDetails()
    {
        String finalString = "Passengers: \n";
        for (Person p : reservation.getPersons())
        {
            finalString += "        " + p.getFirstName() + " " + p.getLastName() + "\n";
        }

        return finalString;
    }

    @Override
    public String createPlaneDetails()
    {
        String finalString;
        finalString = "Plane: " + reservation.getFlight().getPlane().getID();
        return finalString;
    }

    public String createPriceDetails()
    {
        String finalString;
        finalString = "Price: " + reservation.getPrice() + ",-";
        return finalString;
    }

    public String createReservationIDDetails()
    {
        String finalString;
        finalString = "Reservation ID:            " + controller.getReservationIDToCome();
        return finalString;
    }

}
