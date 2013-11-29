
package flybooking.GUI;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import flybooking.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.border.EmptyBorder;
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
    private JCheckBox checkbox;
    private FlightList flightList;
    private JPanel topContent, bottomContent, topLeft, topMiddle, topMiddleContent, topRight, topRightContent, topRightTopContent, topRightBottomContent;
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

    public static void main(String[] args) throws SQLException
    {
        NewReservationFrame.getInstance();
    }

    /**
     * Create a frame for finding and creating reservations.
     *
     * @throws HeadlessException
     */
    private NewReservationFrame() throws HeadlessException, SQLException
    {
        //Create a new Array ready to receive search results.
        searchResults = new ArrayList<>();
        //Get an instance of teh controller to be able to search and save results.
        controller = Controller.getInstance(ProgramStorage.getInstance());
        //Draw the GUI.
        drawFrame();
    }

    /**
     * Get an instance of the Frame.
     *
     * @return An instance of the frame.
     */
    public static NewReservationFrame getInstance() throws SQLException
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
     * @throws SQLException
     */
    private void drawFrame() throws SQLException
    {
        //Set up the main frame.
        setTitle("New Reservation");
        setResizable(false);

        //Draw the top and bottom part of the GUI.
        drawTopContent();
        drawBottomContent();
        //addActionListeners();

        //Get the default values from the GUI, so we won't get an exception 
        //if nothing is clicked before searching.
        //chosenStartDestination = (String) startDestDropdown.getSelectedItem();
        //chosenEndDestination = (String) endDestDropdown.getSelectedItem();
        //chosenPeople = 1;
        //chosenDate = new Date();
        //Pack everything and show the frame.
        pack();
        setSize(new Dimension(560, 480));
        setVisible(true);
    }

    /**
     * Draw the top part of the window, containing input and search buttons.
     */
    private void drawTopContent() throws SQLException
    {
        //Create the three top panels and the comboboxes.
        topContent = new JPanel();
        topLeft = new JPanel();
        topMiddleContent = new JPanel();
        topMiddle = new JPanel();
        topRightTopContent = new JPanel();
        topRightBottomContent = new JPanel();
        topRightContent = new JPanel();        
        topRight = new JPanel();

        //Create all of the components.
        dateDropdown = new JComboBox(drawDates());
        peopleDropdown = new JComboBox(people);
        startDestDropdown = new JComboBox(drawDestinations());
        endDestDropdown = new JComboBox(drawDestinations());
        dateLabel = new JLabel("Departure date: ");
        peopleLabel = new JLabel("Passengers: ");
        startLabel = new JLabel("Start destination: ");
        endLabel = new JLabel("End destination: ");
        doneButton = new JButton("Search");
        
        //Set the layouts for the top panels.
        topLeft.setPreferredSize(new Dimension(150, 60));
        topMiddleContent.setLayout(new MigLayout());
        topMiddleContent.setPreferredSize(new Dimension(80, 50));
        peopleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topMiddle.setPreferredSize(new Dimension(150, 60));
        topRightTopContent.setLayout(new BorderLayout());
        topRightBottomContent.setLayout(new BorderLayout());
        topRightContent.setLayout(new BorderLayout());
        endDestDropdown.setBorder(new EmptyBorder(0, 0, 20, 0));

        //Add the components to the top panels
        topLeft.add(dateLabel);
        topLeft.add(dateDropdown);
        
        //Add middle content panel, and add components to it.
        topMiddleContent.add(peopleLabel, BorderLayout.PAGE_START);
        topMiddleContent.add(peopleDropdown, BorderLayout.PAGE_END);
        topMiddle.add(topMiddleContent);

        
        //ALLE SKAL LAVES PÅ NEDENSTÅENDE MÅDE!
        
        //Add top content panel, topTop panel and topBottom panel.
        //Then add components to them.
        topRightContent.add(topRightTopContent);
        topRightContent.add(topRightBottomContent);
        topRightTopContent.add(startLabel, BorderLayout.PAGE_START);
        topRightTopContent.add(startDestDropdown, BorderLayout.PAGE_END);
        topRightBottomContent.add(endLabel, BorderLayout.PAGE_START);
        topRightBottomContent.add(endDestDropdown, BorderLayout.PAGE_END);
        topRightContent.add(topRightTopContent, BorderLayout.PAGE_START);
        topRightContent.add(topRightBottomContent, BorderLayout.PAGE_END);
        topRight.add(topRightContent);


        //Add the panels to the top part of the window.
        topContent.add(topLeft, BorderLayout.LINE_START);
        topContent.add(topMiddle, BorderLayout.CENTER);
        topContent.add(topRight, BorderLayout.LINE_END);
        
        add(topContent, BorderLayout.PAGE_START);
    }

    /**
     * Draw the bottom part of the window, containing flights found.
     */
    private void drawBottomContent()
    {
        {
            bottomContent = new JPanel();

            flightList = new FlightList(searchResults);
            flightList.setSize(new Dimension(490, 240));

            doneButton = new JButton("Book Flight");
            doneButton.setMaximumSize(new Dimension(300, 100));

            scrollpane = new JScrollPane();
            scrollpane.setViewportView(flightList);

            add(bottomContent, BorderLayout.PAGE_END);
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
    private String[] drawDestinations() throws SQLException
    {
        return controller.getDestinationsAsStrings();
    }

    /**
     * Add all of the ugly ActionListener code to the ComboBoxes and Buttons.
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
                try {
                    performSearch();
                } catch (SQLException ex) {
                }
            }
        });

        //Add an ActionListener that changes the boolean nextTo when clicked.
        checkbox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                nextTo = !nextTo;
            }
        });

        //Add an ActionListener that sends the data to the next window in the system.
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    NewReservationFrame.getInstance().sendOnData();
                } catch (ParseException | SQLException ex) {
                }
            }
        });
    }

    /**
     * Perform a search for flights with the specified search options.
     */
    private void performSearch() throws SQLException
    {
        searchResults = ProgramStorage.getInstance().getFlight(chosenDate, chosenStartDestination, chosenEndDestination);
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
    private void sendOnData() throws ParseException
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
