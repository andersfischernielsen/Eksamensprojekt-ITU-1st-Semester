package flybooking;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

/**
 * Print a receipt for a given reservation.
 *
 * @author Anders
 */
public class Printer implements PrinterInterface
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
        string += createSeatDetails();
        string += "\n";
        string += createPlaneDetails();
        string += "\n\n";
        string += createPriceDetails();
        string += "\n";
        string += "-------------------------------------------------";

        return string;
    }

    @Override
    public String createReservationIDDetails()
    {
        String finalString;
        finalString = "Reservation ID:           " + 
                                     controller.getReservationIDToCome() + "\n";
        
        return finalString;
    }

    @Override
    public String createAirportDetails()
    {
        String finalString;
        finalString = "Departure:                " + 
                simpleDate.format(reservation.getFlight().getStartDate())
                + " " + reservation.getFlight().getStartAirport().getID();
        finalString += "\nArrival:                  " + 
                simpleDate.format(reservation.getFlight().getEndDate())
                + " " + reservation.getFlight().getEndAirport().getID() + "\n";

        return finalString;
    }

    @Override
    public String createPeopleDetails()
    {
        String finalString = "Passengers: \n";
        String groupIDText = "";
        for (Person p : reservation.getPersons())
        {
            // depending on what age the person is print the group they belong to.
            switch (p.getGroupID())
            {
                case 0:
                {
                    groupIDText = "   (Adult)";
                    break;
                }
                case 1:
                {
                    groupIDText = "   (Child)";
                    break;
                }
                case 2:
                {
                    groupIDText = "   (Elder)";
                    break;
                }
            }
            finalString += "             " + p.getFirstName() + " " + p.getLastName() + " " + groupIDText + "\n";
        }

        return finalString;
    }

    @Override
    public String createSeatDetails()
    {
        String finalString = "Seats: \n             ";
 
        for (String seatID : reservation.getBookedSeats())
        {
            finalString += seatID + "\n             ";
        }
        return finalString;
    }
    
    @Override
    public String createPlaneDetails()
    {
        String finalString;
        finalString = "Plane:                    " + 
                                    reservation.getFlight().getPlane().getID();
        return finalString;
    }

    @Override
    public String createPriceDetails()
    {
        String finalString;
        finalString = "Price:                    " + 
                                                reservation.getPrice() + "DKK";
        return finalString;
    }
}
