
package flybooking;

import java.util.Date;

/**
 * An interface for creating journeys.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface FlightInterface
{
    /**
     * Return the price of the journey.
     * @return The price of the journey.
     */
    public double getPrice();
    
    /**
     * Return the ID of the journey.
     * @return The ID of the journey.
     */
    public int getID();
    
    /**
     * Return the plane on the journey.
     * @return The plane on the journey.
     */
    public Plane getPlane();
    
    /**
     * Return the number of seats available on the plane.
     * @return The number of seats available on the plane.
     */
    public int getSeatsLeft();
    
     /**
     * Gets the seat availability of the seat with ID "seatID"
     * @param seatID the seatID to return the availability of.
     */
    public boolean getSeatAvailability(String seatID);
    
    /**
     * Get the departure date for the flight.
     * @return The date the plane in the flight departs.
     */
    public Date getStartDate();
    
    /**
     * Get the arrival date for the flight.
     * @return The date the plane in the flight arrives at the destination.
     */
    public Date getEndDate();
    
    /**
     * Get the departure airport for the flight.
     * @return The departure airport for the flight.
     */
    public Airport getStartAirport();
    
    /**
     * Get the arrival (or end) airport for the flight.
     * @return The arrival (or end) airport for the flight.
     */
    public Airport getEndAirport();
}
