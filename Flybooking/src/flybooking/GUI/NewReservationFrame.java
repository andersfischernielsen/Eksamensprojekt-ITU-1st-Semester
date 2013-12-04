
package flybooking.GUI;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import flybooking.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import net.miginfocom.swing.MigLayout;

/**
 * Create a frame for finding and creating reservations
 *
 * @author Anders Fischer-Nielsen
 */
public class NewReservationFrame extends JFrame {

    //All of the Swing components needed to draw the interface.
    private JComboBox dateDropdown, peopleDropdown, startDestDropdown, endDestDropdown;
    private JButton searchButton, doneButton;
    private FlightList flightList;
    private JPanel top, topContent, filler, filler2, filler3;
    private JLabel dateLabel, peopleLabel, startLabel, endLabel;
    private ControllerInterface controller;
    private static NewReservationFrame instance = null;
    private JScrollPane scrollpane;
    private String[] people = {"1", "2", "3", "4", "5"};

    //All of the search variables in the interface.
    private ArrayList<Flight> searchResults;
    private Date chosenDate;
    private int chosenPeople;
    private String chosenStartDestination;
    private String chosenEndDestination;
    private boolean nextTo;
    private double chosenPrice;

    /**
     * Create a frame for finding and creating reservations.
     *
     * @throws HeadlessException
     */
    private NewReservationFrame() throws HeadlessException
    {
        //Create a new Array ready to receive search results.
        searchResults = new ArrayList<>();
        //Get an instance of teh controller to be able to search and save results.
        controller = Controller.getInstance();
        //Draw the GUI.
        drawFrame();
    }

    /**
     * Get an instance of the Frame.
     *
     * @return An instance of the frame.
     */
    public static NewReservationFrame getInstance()
    {
        if (instance == null) {
            instance = new NewReservationFrame();
        }

        instance.setVisible(true);
        return instance;
    }

    /**
     * Draw the GUI.
     * 
     */
    public void drawFrame()
    {
        setTitle("New Reservation");

        //Draw the top and bottom part of the GUI.
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        getContentPane().setLayout(new BorderLayout());
        drawTopContent();
        drawBottomContent();
        addActionListeners();

        //Get the default values from the GUI, so we won't get an exception 
        //if nothing is clicked before searching.
        chosenStartDestination = (String) startDestDropdown.getSelectedItem();
        chosenEndDestination = (String) endDestDropdown.getSelectedItem();
        chosenPeople = 1;
        chosenDate = new Date();

        //Set the default button.
        getRootPane().setDefaultButton(searchButton);

        //Pack everything and show the frame.
        pack();
        setMinimumSize(new Dimension(560, 480));
        setVisible(true);
    }

    /**
     * Draw the top part of the window, containing input and search buttons.
     */
    private void drawTopContent()
    {
        //Create the top content panel, and set its layout and inner spacing..
        top = new JPanel();
        topContent = new JPanel();
        topContent.setLayout(new MigLayout(
                "",
                "0 [] 90 [] 90 [] 0",
                "0 [] 0  [] 5 [] 0 [] 20 [] 5"));
        filler = new JPanel();
        filler2 = new JPanel();
        filler3 = new JPanel();

        //Create all of the components.
        dateDropdown = new JComboBox(drawDates());
        peopleDropdown = new JComboBox(people);
        startDestDropdown = new JComboBox(drawDestinations());
        endDestDropdown = new JComboBox(drawDestinations());
        dateLabel = new JLabel(" Departure date:");
        peopleLabel = new JLabel(" Passengers:");
        startLabel = new JLabel(" Start destination:");
        endLabel = new JLabel(" End destination:");
        searchButton = new JButton("Search");
        doneButton = new JButton("Book Flight");
        doneButton.setMinimumSize(new Dimension(133, 20));
        doneButton.setEnabled(false);
        
        //Set the sizes and indexes of specific components.
        searchButton.setMinimumSize(new Dimension(130, 20));
        searchButton.setDefaultCapable(true);
        peopleDropdown.setMinimumSize(new Dimension(80, 20));
        dateDropdown.setSelectedIndex(7);
        startDestDropdown.setMaximumSize(new Dimension(130, 25));
        endDestDropdown.setMaximumSize(new Dimension(130, 25));

        //Add the components so they show up in the right places.
        topContent.add(dateLabel);
        topContent.add(peopleLabel);
        topContent.add(startLabel, "wrap");
        topContent.add(dateDropdown);
        topContent.add(peopleDropdown);
        topContent.add(startDestDropdown, "wrap");
        topContent.add(filler, "span 2");
        topContent.add(endLabel, "wrap");
        topContent.add(filler2, "span 2");
        topContent.add(endDestDropdown, "wrap");
        topContent.add(doneButton);
        topContent.add(filler3, "span 1");
        topContent.add(searchButton);

        //Add the finished top panel to the main frame.
        top.add(topContent);
        getContentPane().add(top, BorderLayout.PAGE_START);
    }

    /**
     * Draw the bottom part of the window, containing flights found.
     */
    private void drawBottomContent()
    {
        {
            //Initialize a ned FLightList, and add the search results to it.
            flightList = new FlightList(searchResults);

            //Add the FlightList to the scrollpane.
            scrollpane = new JScrollPane();
            scrollpane.setViewportView(flightList);

            //Add the scrollpane and button to the frame.
            getContentPane().add(scrollpane, BorderLayout.CENTER);          
        }
    }

    /**
     * Get the dates as a String array between a week ago and a week forward
     * from the current date.
     *
     * @return A string array of the dates a week ago and up until a week from
     * now.
     */
    private String[] drawDates()
    {
        //Initialize an empty string to return.
        String[] array = new String[14];
        //Get the current date.
        Date today = new Date();

        //Go through the array, adding the dates a week before and after today.
        int j = 0;
        for (int i = 7; i > 0; i--) {
            //today is in milliseconds, so we calculate the date a week from now
            //(7 * milliseconds on a day, subtracting one day for each loop.)
            array[j] = Calculator.convertDateToString(new Date(today.getTime() - i * 24 * 60 * 60 * 1000));
            //Then increase the position in the array by one, so the dates are
            //in proper order.
            j++;
        }

        for (int i = 0; i < 7; i++) {
            //We do the same here, just the other way around. 
            array[j] = Calculator.convertDateToString(new Date(today.getTime() + i * 24 * 60 * 60 * 1000));
            j++;
        }

        //Then when the array has been made, we return it.
        return array;
    }

    /**
     * Get the possible destinations from the controller as a string array.
     *
     * @return A string array of possible destinations.
     */
    private String[] drawDestinations()
    {
        return controller.getDestinationsAsStrings();
    }

    /**
     * Add all of the ActionListener code to the ComboBoxes and Buttons.
     */
    private void addActionListeners()
    {
        //Adds an ActionListener that changes chosenDate to the date chosen when clicked.
        dateDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //Creates a temporary ComboBox, that contains the click source.
                JComboBox cb = (JComboBox) e.getSource();
                try {
                    //Then convert the ComboBox into a date using the Calculator.
                    chosenDate = Calculator.convertStringToDate(cb.getSelectedItem().toString());
                } catch (ParseException ex) {
                }
            }
        });

        //Add an ActionListener that changes the chosenPeople to the value chosen when clicked.
        peopleDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JComboBox cb = (JComboBox) e.getSource();
                //Same as above just with an integer.
                chosenPeople = Integer.parseInt(cb.getSelectedItem().toString());
            }
        });

        //Adds an ActionListener that changes chosenStartDestination to the value chosen when clicked.
        startDestDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JComboBox cb = (JComboBox) e.getSource();
                //Creates a temporary Airport and get it's ID. Assigns this to chosenStartDest.
                chosenStartDestination = (String) cb.getSelectedItem();
            }
        });

        //Same as above.
        endDestDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JComboBox cb = (JComboBox) e.getSource();
                chosenEndDestination = (String) cb.getSelectedItem();
            }
        });

        //Add an ActionListener that runs a search when the button is clicked.
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                performSearch();
            }
        });
        
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    sendOnData();
            }
        });

        flightList.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (flightList.getSelectedIndex() > -1) {
                    doneButton.setEnabled(true);

                    if (e.getClickCount() > 1) {
                        sendOnData();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                //Do nothing.
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                //Do nothing.
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                //Do nothing.
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                //Do nothing.
            }
        });
    }

    /**
     * Perform a search for flights with the specified search options.
     */
    private void performSearch()
    {
        searchResults = Database.getInstance().getFlightList(chosenDate, chosenStartDestination, chosenEndDestination);
        Flight[] convertedArray = new Flight[searchResults.size()];

        int i = 0;
        for (Flight f : searchResults) {
            convertedArray[i] = f;
            i++;
        }

        flightList.setListData(convertedArray);
    }

    /**
     * Send the data to the controller, and open the next window in the booking
     * work flow.
     *
     * @throws ParseException
     */
    private void sendOnData()
    {
        ReservationInterface reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setFlight(flightList.getSelectedFlight());

        if (reservation.getFlight().getPlane() != null) {
            controller.setWorkingOnReservation(reservation);
            setVisible(false);

            new PersonAndSeatFrame();
        }
    }
}
