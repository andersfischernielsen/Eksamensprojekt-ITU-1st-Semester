
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Create a custom list of flights.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class FlightList extends JList {

    //The array of flights to show in the list.
    private ArrayList<Flight> flights;
    private DefaultListModel<Flight> model;
    private Flight selectedFlight;

    /**
     * Create a new FlightList, containing a given ArrayList of flights.
     *
     * @param flights The flights to show in the list.
     */
    public FlightList(ArrayList<Flight> flights)
    {
        this.flights = flights;
        model = new DefaultListModel<>();
        selectedFlight = new Flight(0, 0, null, null, null, null, null);

        //Add all the flights to the list model.
        for (Flight f : flights) {
            model.addElement(f);
        }

        //Set the custom cell renderer.
        setCellRenderer(new FlightCellRenderer());
        
        //Set the model to show the flights.
        setModel(model);
        
        //Repaint the list.
        repaint();
    }
    
    /**
     * Get the currently selected Flight.
     * @return The currently selected flight.
     */
    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    private class FlightCellRenderer extends DefaultListCellRenderer {
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean hasFocus)
        {
            Flight flight = (Flight) value;
            
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

            //Fill the top part of the cell with flight information and lay it out.
            topCellTextLeft = new JLabel(
                    Calculator.convertDateToHourString(flight.getStartDate()));

            topCellTextMiddle = new JLabel(
                    flight.getStartAirport().getID() + " > "
                    + flight.getEndAirport().getID());
            topCellTextMiddle.setHorizontalAlignment(JLabel.CENTER);

            topCellTextRight = new JLabel(
                    flight.getPlane().getID());

            //Then the bottom part.
            bottomCellTextLeft = new JLabel(
                    Calculator.convertDateToHourString(flight.getEndDate()));

            bottomCellTextMiddle = new JLabel(
                    Calculator.convertDateToString(flight.getStartDate()));
            bottomCellTextMiddle.setHorizontalAlignment(JLabel.CENTER);

            bottomCellTextRight = new JLabel(
                    flight.getPrice() + " DKK");

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
            
            //Color the selected cell, and set is as the currently selected.
            if (isSelected) {
                topCellContent.setBackground(new Color(135, 206, 250));
                bottomCellContent.setBackground(new Color(135, 206, 250));
                selectedFlight = flight;
            }
            
            //Return the finished panel.
            return panel;
        }
    }
}
