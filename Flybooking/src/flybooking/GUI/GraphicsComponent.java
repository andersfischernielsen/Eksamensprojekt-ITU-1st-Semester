package flybooking.GUI;

import flybooking.Plane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;

/**
 * A custom graphics component that can be used for drawing different things.
 *
 * @author Anders
 */
public class GraphicsComponent
{

    private PlaneGraphicsComponent currentPlaneGraphics;
    private final int unit = 24, padding = 10; // for seats.

    public GraphicsComponent()
    {
    }

    /**
     * The paintPlaneSeats() method constructs and returns a new
     * PlaneGraphicsComponent, which is an inner class in graphics component.
     *
     * @param planeToDraw
     *
     * @return a component who draws planeToDraw
     */
    public PlaneGraphicsComponent paintPlaneSeats(Plane planeToDraw, ArrayList<String> seatIDsThisRes)
    {
        currentPlaneGraphics = new PlaneGraphicsComponent(planeToDraw);
        currentPlaneGraphics.setSeatIDsThisRes(seatIDsThisRes);
        return currentPlaneGraphics;
    }

    /**
     * Returns a planegrahicscomponent on which a seat now has changed
     * availability. This method is used for mouseListeners event.
     *
     * @param planeToDraw the plane to which the seat availability needs to
     *                    change.
     * @param mouseX      the x coordinat that the mouse pressed
     * @param mouseY      the y coordinat that the mouse pressed
     *
     * @return
     */
    public PlaneGraphicsComponent paintPlaneSeats(Plane planeToDraw, int mouseX, int mouseY, ArrayList<String> seatIDsThisRes)
    {
        // create a new PlaneGraphicsComponent;
        PlaneGraphicsComponent planeToWork = new PlaneGraphicsComponent(planeToDraw);
        // give it the seats which are taken on this res.
        planeToWork.setSeatIDsThisRes(seatIDsThisRes);
        // change the availability of a seat, given the mouse
        planeToWork.setSeatAvailability(mouseX, mouseY);
        // make the graphics component remember the last PlaneGraphicsComponent
        // it returned
        currentPlaneGraphics = planeToWork;
        return planeToWork;
    }

    /**
     * Get the SeatIDthisRes on the graphicscomponents own
     * PlaneGraphicsComponent. Which is ) to the one in the last
     * PlaneGraphicsComponent it returned.
     *
     * @return an ArrayList of String which = seatID strings.
     */
    public ArrayList<String> getSeatIDsThisRes()
    {
        return currentPlaneGraphics.getSeatIDsThisRes();
    }

    /**
     * Paints a blue header with a height and width.
     *
     * @param height the height of the header
     * @param width  the width of the header
     *
     * @return a HeaderGraphicsComponent.
     */
    public HeaderGraphicsComponent paintHeader(int height, int width)
    {
        return new HeaderGraphicsComponent(height, width);
    }

    /**
     * PlaneGraphicsComponent, used for drawing a plane's seat, also handles
     * clicks
     */
    private class PlaneGraphicsComponent extends JComponent
    {

        private Plane planeToDraw;
        private int rows, cols; // of the flight
        // fixDistance is used to create the visual representation of isles, and
        // distance between first-, business-, and economy class
        private int fixdistanceX = 25, fixdistanceY = 25;
        // the seatID string that the reservation has booked
        private ArrayList<String> seatIDsThisRes;

        public PlaneGraphicsComponent(Plane planeToDraw)
        {
            this.planeToDraw = planeToDraw;
            rows = planeToDraw.getRows();
            cols = planeToDraw.getColumns();
            seatIDsThisRes = new ArrayList<>();
        }

        /**
         * paints the plane.
         *
         * @param g
         */
        @Override
        public void paint(Graphics g)
        {
            int fixdistanceX = 25 * 2;
            int fixdistanceY = 25 * 2;

            for (int i = 0; i < cols; i++)
            {
                /*
                 * If the seat is after the first 1/10 seats then add more
                 * distance to the rest of the seats (making a visual indicator
                 * of where the firstclass seats are.)
                 */
                if (i == cols / 10)
                {
                    fixdistanceY = 40 * 2;
                }
                /*
                 * same as before just for the business class which is between
                 * 1/10 and 1/4.
                 */
                if (i == cols / 4)
                {
                    fixdistanceY = 55 * 2;
                }
                for (int j = 0; j < rows; j++)
                {
                    /*
                     * Creates the illusion of a isle in the middle of the
                     * plane.
                     */
                    if (j == rows / 2)
                    {
                        fixdistanceX = 40 * 2;
                    }
                    // checks the availability of the seat. and change the color
                    if (planeToDraw.getSeatAvailability(Plane.SeatIDGenerator(i, j)))
                    {
                        // if its available make it green
                        g.setColor(Color.GREEN);
                    }
                    else
                    {
                        // if its taken make it red
                        g.setColor(Color.RED);
                    }
                    for (String seatID : seatIDsThisRes)
                    {
                        if (seatID.equals(Plane.SeatIDGenerator(i, j)))
                        {
                            // if the seat is one of the seats in this 
                            //Reservation: make blue
                            g.setColor(Color.BLUE);
                        }
                    }

                    // Draw the seats.
                    g.fillRect((i * unit) + padding + fixdistanceY,
                            (j * unit) + padding + fixdistanceX,
                            unit - padding, unit - padding);
                    g.setColor(Color.BLACK);
                    // Draw a black rectangle around each seat.
                    g.drawRect((i * unit) + padding + fixdistanceY,
                            (j * unit) + padding + fixdistanceX,
                            unit - padding, unit - padding);
                }
                // return the fixdistance to the default.
                fixdistanceX = 25 * 2;
            }
            g.setColor(Color.BLACK);
            // creates a 2dGraphics which can rotate and draw strings.
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            // creates the font to draw the classes strings with.
            Font classFont = new Font("Monospaced", Font.BOLD, 12);
            g2.setFont(classFont);
            // rotates the 2DGraphics object.
            g2.rotate(0.7);
            // if the flight has less than 10 columns, there is no firstclass.
            if (cols >= 10)
            {
                // draw the firstclass
                g2.drawString("F. class", cols * unit / 10.9f, -rows * unit / 9.8f);
            }
            // draw the other classes strings
            g2.drawString("B. class", cols * unit / 4, -rows * unit / 10 - cols * unit / 8);
            g2.drawString("E. class", cols * unit / 1.45f, -rows * unit / 10 - cols * unit / 2f);

        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(cols * unit + 80 * 2, rows * unit + 65 * 2);
        }

        @Override
        public Dimension getMinimumSize()
        {
            return getPreferredSize();
        }

        /**
         * Set the seatIDsThisRes ArrayList to the given.
         *
         * @param seatIDsThisRes
         */
        public void setSeatIDsThisRes(ArrayList<String> seatIDsThisRes)
        {
            this.seatIDsThisRes = seatIDsThisRes;
        }

        /**
         * Get the seatIDsThisRes arrayList of seatID strings.
         *
         * @return the seatIDsThisRes.
         */
        public ArrayList<String> getSeatIDsThisRes()
        {
            return seatIDsThisRes;
        }

        /**
         * Calculates if the click with coordinates (mouseX, mouseY) is on a
         * seat and if it is check if its taken or a part of the seatIDsThisRes
         * If its not taken, make it a part of the seatIDsThisRes. If it is
         * taken do nothing. If it is a part of the seatIDsThisRes then remove
         * it from the list.
         *
         * @param mouseX the x coordinat of the mouse click
         * @param mouseY the y coordinat of the mouse click.
         */
        public void setSeatAvailability(int mouseX, int mouseY)
        {
            int firstCol, firstRow;
            int mouseCol, mouseRow;
            //Convert from coordinat to the seat column number.
            firstCol = (mouseX - padding - fixdistanceX) / (unit);
            mouseCol = firstCol;
            if (firstCol >= cols / 10 && !(firstCol >= cols / 4))
            {
                // after the first class
                fixdistanceX = 40;
                mouseCol = (mouseX - padding - fixdistanceX) / (unit);
            }
            if (firstCol >= cols / 4)
            {
                // after the business class
                fixdistanceX = 55;
                mouseCol = (mouseX - padding - fixdistanceX) / (unit);
            }

            // Convert the coordinat to the seat Row number
            firstRow = (mouseY - 35) / (24);
            mouseRow = firstRow;
            if (firstRow >= rows / 2)
            {
                // after the isle
                fixdistanceY = 40;
                mouseRow = (mouseY - padding - fixdistanceY) / (unit);
            }

            // checks if it returns a valid seat position 
            if (mouseCol < 0 || mouseCol > cols || mouseRow < 0 || mouseRow > rows)
            {
                fixdistanceX = 25;
                fixdistanceY = 25;
                return;
            }

            // checks if the click is within the seat boxes.
            if (mouseY > (mouseRow * unit) + padding + fixdistanceY
                    && mouseY < (mouseRow * unit) + padding + fixdistanceY + unit
                    - padding)
            {
                if (mouseX > (mouseCol * unit) + padding
                        + fixdistanceX && mouseX < (mouseCol * unit) + padding
                        + fixdistanceX + unit - padding)
                {
                    // if it is then check if the seat is already taken.
                    if (planeToDraw.getSeatAvailability(planeToDraw.SeatIDGenerator(mouseCol, mouseRow)) == true)
                    {
                        if (!(seatIDsThisRes.isEmpty()))
                        {
                            // go through the seaIDsThisRes to check if the click
                            // is on one of those seats
                            int i = 1;
                            for (Iterator<String> it = seatIDsThisRes.iterator();
                                 it.hasNext();)
                            {
                                String seatID = it.next();
                                if (seatID.equals(Plane.SeatIDGenerator(mouseCol, mouseRow)))
                                {
                                    // if it is then remove it from that list.
                                    seatIDsThisRes.remove(Plane.SeatIDGenerator(mouseCol, mouseRow));
                                    break;
                                }
                                if (i == seatIDsThisRes.size())
                                {
                                    // if it wasen't then add it.
                                    seatIDsThisRes.add(Plane.SeatIDGenerator(mouseCol, mouseRow));
                                    break;
                                }
                                i++;
                            }
                        }
                        else
                        {
                            // if the seatIDsThisRes was empty just add the seat.
                            seatIDsThisRes.add(Plane.SeatIDGenerator(mouseCol, mouseRow));
                        }
                    }
                }
            }

            // return the fixdistances to the original state.
            fixdistanceX = 25;
            fixdistanceY = 25;
        }
    }

    /**
     * The header graphics component. Used for drawing a blue filled rect, and
     * some text. Is not used in the final project.
     */
    private class HeaderGraphicsComponent extends JComponent
    {
        private int height, width;

        public HeaderGraphicsComponent(int height, int width)
        {
            this.height = height;
            this.width = width;
        }

        @Override
        public void paint(Graphics g)
        {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.GRAY);
            g.drawRect(0, 0, width, height + 20);
            Font headerFont = new Font("Monospaced", Font.BOLD, 16);
            g.setColor(Color.BLACK);
            g.setFont(headerFont);
            g.drawString("AND AND & FORUP PLANE BOOKING", width - 350, 35);
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(width + 1, height + 21);
        }

        @Override
        public Dimension getMinimumSize()
        {
            return getPreferredSize();
        }
    }
}
