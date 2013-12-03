
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Create a custom list of reservations.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class ReservationList extends JList {

    //The array of reservations to show in the list.
    private ArrayList<ReservationInterface> reservations;
    private DefaultListModel<ReservationInterface> model;
    private Reservation selectedReservation;

    /**
     * Create a new ReservationList, containing a given ArrayList of
     * reservations.
     *
     * @param reservations The reservations to show in the list.
     */
    public ReservationList(ArrayList<ReservationInterface> reservations) throws SQLException
    {
        this.reservations = reservations;
        model = new DefaultListModel<>();
        selectedReservation = new Reservation();

        //Add all the reservations to the list model.
        for (ReservationInterface r : reservations) {
            model.addElement(r);
        }

        //Set the custom cell renderer.
        setCellRenderer(new ReservationCellRenderer());

        //Set the model to show the reservations.
        setModel(model);

        //Repaint the list.
        repaint();
    }

    /**
     * Get the currently selected Reservation.
     *
     * @return The currently selected reservation.
     */
    public Reservation getSelectedReservation()
    {
        return selectedReservation;
    }
    
    public void update() {
        setModel(model);
        revalidate();
        repaint();
    }

    private class ReservationCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean hasFocus)
        {
            Reservation res = (Reservation) value;
            setFocusable(true);

            //All the panels and textfields to create the cell.
            final JPanel panel, topCellContent, bottomCellContent;
            JLabel topCellTextLeft, topCellTextMiddle, topCellTextRight;
            JLabel bottomCellTextLeft, bottomCellTextMiddle, bottomCellTextRight;

            //Instantiate the panels and textfields.
            panel = new JPanel();
            topCellContent = new JPanel();
            bottomCellContent = new JPanel();

            //Set their layout.
            topCellContent.setLayout(new BorderLayout());
            bottomCellContent.setLayout(new BorderLayout());
            panel.setLayout(new BorderLayout());

            //Fill the top part of the cell with reservation information and lay it out.
            topCellTextLeft = new JLabel("Booked the: "
                    + Calculator.convertDateToHourString(res.getReservationDate()));

            topCellTextMiddle = new JLabel(
                    res.getFlight().getStartAirport().getID() + " > "
                    + res.getFlight().getEndAirport().getID());
            topCellTextMiddle.setHorizontalAlignment(JLabel.CENTER);

            topCellTextRight = new JLabel(
                    res.getFlight().getPlane().getID());

            //Then the bottom part.
            bottomCellTextLeft = new JLabel("Paid by: "
                    + res.getPayer().getFirstName());

            bottomCellTextMiddle = new JLabel(
                    res.getBookedPersons().size() + "people");
            bottomCellTextMiddle.setHorizontalAlignment(JLabel.CENTER);

            bottomCellTextRight = new JLabel(
                    res.getPrice() + ",-");

            //Add all of the information to the top and bottom aprt of the cell.
            topCellContent.add(topCellTextLeft, BorderLayout.LINE_START);
            topCellContent.add(topCellTextMiddle, BorderLayout.CENTER);
            topCellContent.add(topCellTextRight, BorderLayout.LINE_END);
            bottomCellContent.add(bottomCellTextLeft, BorderLayout.LINE_START);
            bottomCellContent.add(bottomCellTextMiddle, BorderLayout.CENTER);
            bottomCellContent.add(bottomCellTextRight, BorderLayout.LINE_END);

            //Make a bit of padding.
            bottomCellContent.setBorder(new EmptyBorder(0, 0, 5, 0));
            topCellContent.setBorder(new EmptyBorder(5, 0, 0, 0));

            //Add the top and bottom part to the main panel in the cell.
            panel.add(topCellContent, BorderLayout.NORTH);
            panel.add(bottomCellContent, BorderLayout.SOUTH);

            //Alternate coloration of rows.
            if (index % 2 == 0) {
                topCellContent.setBackground(Color.WHITE);
                bottomCellContent.setBackground(Color.WHITE);
            } else {
                topCellContent.setBackground(new Color(100, 100, 100));
                bottomCellContent.setBackground(new Color(100, 100, 100));
            }

            //Color the selected cell, and set is as the currently selected.
            if (isSelected) {
                topCellContent.setBackground(new Color(160, 160, 160));
                bottomCellContent.setBackground(new Color(160, 160, 160));
                selectedReservation = res;
            }

            //Return the finished panel.
            return panel;
        }
    }
}
