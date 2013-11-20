
package flybooking;

import java.util.Date;

/**
 * Create a journey.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Flight implements FlightInterface {

    private double price;
    private final int ID;
    private Plane plane;
    private int seatsLeft;
    private Date startDate;
    private Date endDate;
    private Airport startAirport;
    private Airport endAirport;

    public Flight(double price, int ID, Plane plane, Date startDate, Date endDate)
    {
        this.price = price;
        this.ID = ID;
        this.plane = plane;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public double getPrice()
    {
        return price;
    }

    @Override
    public int getID()
    {
        return ID;
    }

    @Override
    public Plane getPlane()
    {
        return plane;
    }

    @Override
    public int getSeatsLeft()
    {
        return seatsLeft;
    }

    @Override
    public Date getStartDate()
    {
        return startDate;
    }

    @Override
    public Date getEndDate()
    {
        return endDate;
    }

    @Override
    public Airport getStartAirport()
    {
        return startAirport;
    }

    @Override
    public Airport getEndAirport()
    {
        return endAirport;
    }

    @Override
    public boolean getSeatAvailability(int seatID)
    {
        return plane.getSeatAvailability(seatID);
    }

    @Override
    public void setSeatAvailability(int seatID, boolean availability)
    {
        plane.setSeatAvailability(seatID, availability);
    }
    
}
