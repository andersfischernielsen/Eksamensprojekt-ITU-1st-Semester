
package flybooking;

import java.util.Date;

/**
 * An interface for creating journeys.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface FlightInterface
{
    
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
     * Set the availability of the seat "seatID" to "availability" 
     * @param seatID the seatID which needs to change to.
     * @param availability the availability to which the seat needs to be set to.
     */
    public void setSeatAvailability(String seatID, boolean availability);
    
    /**
     * 
     * @return 
     */
    public Date getStartDate();
    
    /**
     * 
     * @return 
     */
    public Date getEndDate();
    
    /**
     * 
     * @return 
     */
    public Airport getStartAirport();
    
    /**
     * 
     * @return 
     */
    public Airport getEndAirport();
}
