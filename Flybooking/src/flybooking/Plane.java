package flybooking;

import java.util.ArrayList;

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
     * Create all the seats in the plane.
     */
    private void initiate()
    {
        for (int i = 0; i < columns; i++)
        {

            for (int j = 0; j < rows; j++)
            {

                if (i < columns / 10)
                {
                    seats[i][j] = new Seat(SeatIDGenerator(i, j), 2);
                }
                else if (i < columns / 4)
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

    /**
     * Generate IDs for all the seats.
     * @param col The column of the seat.
     * @param row The row of the seat.
     * @return The generated ID.
     */
    public static String SeatIDGenerator(int col, int row)
    {
        //Due to the way an array is stored, add one, so the ID won't be 0A.
        int theCol = col + 1;
        //Generate an empty string.
        String seatIDString = "" + theCol;
        //Depending on the row, generate a letter.
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
        
        //Return the finished string.
        return seatIDString;
    }

    /**
     * Get the ID of the plane.
     * 
     * @return the ID of the plane
     */
    public String getID()
    {
        return ID;
    }

    /**
     * Get the number of seats in the plane.
     * @return The number of seats in the plane.
     */
    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    /**
     * Get the number of rows in the plane.
     * @return The number of rows on the plane.
     */
    public int getRows()
    {
        return rows;
    }

    /**
     * Get the number of rows 
     * @return The number of columns on the plane.
     */
    public int getColumns()
    {
        return columns;
    }

    /**
     * Return a seat with the given seat ID. If no seat was found, return null.
     *
     * @param seatID The seat ID to get.
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
     * @param seatID The seat ID to get.
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
     * Change the availability of a given seat.
     *
     * @param seatID The seat ID to change
     */
    public void setSeatAvailability(String seatID)
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (seats[i][j] != null && seats[i][j].getID().equals(seatID))
                {
                    seats[i][j].setAvailability(
                                   !getSeatAvailability(SeatIDGenerator(i, j)));
                }
            }
        }
    }

    /**
     * Takes a string ArrayList (with seatID strings) as parameter that it goes
     * through and calls the corresponding setAvailability method of the seat.
     *
     * @param seatIDs the ArrayList to run through for seatIDs
     */
    public void bookTakenSeats(ArrayList<String> seatIDs)
    {
        for (Seat[] seatI : seats)
        {
            for (Seat seat : seatI)
            {
                seat.setAvailability(true);
            }
        }
        if (seatIDs != null && seatIDs.isEmpty()) {
        } else {
            for (String seatID : seatIDs)
            {
                setSeatAvailability(seatID);
            }
        }
    }

    /**
     * Make every seat on the plane available.
     */
    public void resetSeats()
    {
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                seats[i][j].setAvailability(true);
            }
        }
    }
}
