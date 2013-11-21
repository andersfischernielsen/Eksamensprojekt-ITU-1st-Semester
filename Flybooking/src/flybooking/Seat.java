package flybooking;

/**
 * A plane seat. HEJEHEJEHEJ
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Seat
{

    private final int ID;
    private boolean available;
    //What class the seat is. 0 = economy, 1 = business , 2 = firstclass. 
    private final int group;

    /**
     * Construct a seat in a plane. It can be different kind of classes
     * depending on the parameter group.
     *
     * @param ID        The ID of the seat, used for finding specific seats.
     * @param available Describes whether the seat is available.
     * @param group     The group this seat belongs to. 0 = economy, 1 =
     *                  business , 2 = first class.
     */
    public Seat(int ID, boolean available, int group)
    {
        this.ID = ID;
        this.available = available;
        this.group = group; // CHECK AT DEN ER 0, 1 ELLER 2.
    }

    /**
     * Return whether the seat is available.
     * @return True if the seat is available, false if not.
     */
    public boolean getAvailability()
    {
        return available;
    }

    /**
     * Set the availability of the seat.
     * @param Whether the seat is available or not.
     */
    public void setAvailability(boolean available)
    {
        this.available = available;
    }

    /**
     * Return what group the seat is in. 0 = economy, 1 = business, 
     * 2 = first class.
     * @return What group the seat is in. 0 = economy, 1 = business, 2 = first. 
     */
    public int getGroup()
    {
        return group;
    }

    /**
     * Return the ID of the seat.
     * @return The ID of the seat.
     */
    public int getID()
    {
        return ID;
    }
}
