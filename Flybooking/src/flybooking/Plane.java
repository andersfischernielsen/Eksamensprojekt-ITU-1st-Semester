package flybooking;

/**
 * Create a plane with an ID and a number of seats, rows and columns.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class Plane
{

    private final String ID;
    private final int numberOfSeats;
    public final int rows, columns;
    private final Seat[][] seats;

    public Plane(String ID, int rows, int columns)
    {
        this.ID = ID;
        this.rows = rows;
        this.columns = columns;
        seats = new Seat[columns][rows];
        numberOfSeats = columns * rows;
        initiate();
    }

    /**
     * Initiates all the seats in the plane
     */
    private void initiate()
    {
        for (int i = 0; i < columns; i++)
        {

            for (int j = 0; j < rows; j++)
            {
                
                if (i == columns / 10)
                {
                    seats[i][j] = new Seat(SeatIDGenerator(i, j), 2);
                }
                else if (i == columns / 4)
                {
                    seats[i][j] = new Seat(SeatIDGenerator(i, j), 1);
                }
                else
                {
                    seats[i][j] = new Seat(SeatIDGenerator(i, j), 0);
                }
            }
        }
    }

    public String SeatIDGenerator(int col, int row)
    {
        int theCol = col + 1;
        String seatIDString = "" + theCol;
        switch (row)
        {
            case 0:
                seatIDString += "A";
                break;
            case 1:
                seatIDString += "B";
                break;
            case 2:
                seatIDString += "C";
                break;
            case 3:
                seatIDString += "D";
                break;
            case 4:
                seatIDString += "E";
                break;
            case 5:
                seatIDString += "F";
                break;
        }
        return seatIDString;
    }

    /**
     *
     * @return the ID of the plane
     */
    public String getID()
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
     * @param seatID The seat ID to get.
     *
     * @return The seat with the given seat ID. If not found, return null.
     */
    public Seat getSeat(String seatID)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID().equals(seatID))
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
    public boolean getSeatAvailability(String seatID)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID().equals(seatID))
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
    public void setSeatAvailability(String seatID, boolean availability)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID().equals(seatID))
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
