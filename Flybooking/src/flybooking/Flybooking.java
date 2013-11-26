
package flybooking;

import java.util.Date;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 * @mail awis@itu.dk, csof@itu.dk og afin@itu.dk
 */
public class Flybooking
{
    public static void main(String[] args)
    {
        Date date1 = new Date(2013-1900, 12, 12);
        Date date2 = new Date(2013-1900, 12, 12);
        Plane plane = new Plane("C2034X", 1, 2);
        Airport a1 = new Airport("CPH", "Denmark", "Copenhagen");
        Airport a2 = new Airport("BER", "Germany", "Berlin");
        Flight flight = new Flight(1, plane, date1, date2, a1, a2);
        Person payer = new Person("Anders", "Wind", 1, "ITU", 0);
        Person p1 = new Person("Anders", "Fischer", 2, "ITU", 0);
        Reservation r = new Reservation();
        r.setFlight(flight);
        r.setCPR(1006931111);
        r.setPaid(true);
        r.setPayer(payer);
        r.setReservationDate();
        r.addPerson(payer);
        r.addPerson(p1);
        Printer printer = new Printer(r);
        printer.print();
    }
}
