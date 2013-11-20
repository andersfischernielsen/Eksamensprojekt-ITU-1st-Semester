package flybooking;

/**
 * Create a plane with an ID and a number of seats, rows and columns.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Plane
{

    private final int ID;
    private final int numberOfSeats;
    public final int rows;
    public final int columns;
    private final Seat[][] seats;

    public Plane(int ID, int numberOfSeats, int rows, int columns)
    {
        this.ID = ID;
        this.numberOfSeats = numberOfSeats;
        this.rows = rows;
        this.columns = columns;
        seats = new Seat[columns][rows];
        numberOfSeats = columns * rows;
    }

    /**
     *
     * @return the ID of the plane
     */
    public int getID()
    {
        return ID;
    }

    /**
     *
     * @return the number of seats.
     */
    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    /**
     *
     * @return the number of Rows
     */
    public int getRows()
    {
        return rows;
    }

    /**
     *
     * @return the number of columns
     */
    public int getColumns()
    {
        return columns;
    }

    /**
     * Return a seat with the given seat ID. If no seat was found, return null.
     *
     * @param ID The seat ID to get.
     *
     * @return The seat with the given seat ID. If not found, return null.
     */
    public Seat getSeat(int seatID)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; i < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID() == seatID)
                {
                    return seats[i][j];
                }
            }
        }

        return null;
    }

    /**
     * Returns the availability of the seat ID "seatID". It does so by going
     * through all the seats in the plane and checks if their seatID matches the
     * parameter. If no seat was found, return false.
     *
     * @param ID The seat ID to get.
     *
     * @return The availability of the seat with the given seat ID. If not
     *         found, return false.
     */
    public boolean getSeatAvailability(int seatID)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; i < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID() == seatID)
                {
                    return seats[i][j].getAvailability();
                }
            }
        }
        return false; // returns false if i cannot find seatID
    }

    /**
     * Set the availability of a seat on the plane from a given seat ID.
     *
     * @param seatID       The seat ID to change
     * @param availability Whether the seat is available or not.
     */
    public void setSeatAvailability(int seatID, boolean availability)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; i < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID() == seatID)
                {
                    seats[i][j].setAvailability(availability);
                }
            }
        }

        //VI SKAL LAVE ERROR-HANDLING HER:
        //Hvis vi ikke kan finde seat ID, 
        //eller vi af en eller anden grund ikke ku' ændre sædet.
    }
}
