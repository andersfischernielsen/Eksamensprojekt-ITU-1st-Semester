
package flybooking;

/**
 * A plane seat.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Seat {

    //The ID of the seat.
    private final String ID;
    //Whether the seat is available or not
    private boolean available;
    //What class the seat is. 0 = economy, 1 = business , 2 = firstclass. 
    private final int group;

    /**
     * Construct a seat in a plane. It can be different kind of class depending
     * on the parameter "group".
     *
     * @param ID The ID of the seat, used for finding specific seats.
     * @param group The group this seat belongs to. 0 = economy, 1 = business, 2
     * = first class.
     */
    public Seat(String ID, int group)
    {
        this.ID = ID;
        available = true;

        //If the group ID is invalid set it to economy by default.
        if (group < 3) {
            this.group = group;
        } else {
            this.group = 0;
        }
    }

    /**
     * Return whether the seat is available.
     *
     * @return True if the seat is available, false if not.
     */
    public boolean getAvailability()
    {
        return available;
    }

    /**
     * Set the availability of the seat.
     *
     * @param available Whether the seat is available or not.
     */
    public void setAvailability(boolean available)
    {
        this.available = available;
    }

    /**
     * Return what group the seat is in. 0 = economy, 1 = business, 2 = first
     * class.
     *
     * @return What group the seat is in. 0 = economy, 1 = business, 2 = first.
     */
    public int getGroup()
    {
        return group;
    }

    /**
     * Return the ID of the seat.
     *
     * @return The ID of the seat.
     */
    public String getID()
    {
        return ID;
    }
}
