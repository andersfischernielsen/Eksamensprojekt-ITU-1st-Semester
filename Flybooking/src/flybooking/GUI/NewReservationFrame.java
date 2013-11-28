
package flybooking.GUI;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import flybooking.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;

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
    private Container content, topContainer, bottomContainer,
            top1x1Container, top1x2Container, top1x3Container,
            top2x1Container, top2x2Container, top2x3Container,
            top3x1Container, top3x2Container, top3x3Container, padding;
    private ArrayList<Container> topContainers;
    private JLabel departureLabel, peopleLabel, startLabel, endLabel;
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

        //Get the contentpane to add components to and set it up.
        Container c = getContentPane();
        content = new JPanel();
        content.setLayout(new BorderLayout());
        c.add(content);

        //Draw the top and bottom part of the GUI.
        drawTopContent();
        drawBottomContent();

        //Get the default values from the GUI, so we won't get an exception 
        //if nothing is clicked before searching.
        chosenStartDestination = (String) startDestDropdown.getSelectedItem();
        chosenEndDestination = (String) endDestDropdown.getSelectedItem();
        chosenPeople = 1;
        chosenDate = new Date();

        //Pack everything and show the frame.
        pack();
        setSize(new Dimension(500, 500));
        setVisible(true);
    }

    /**
     * Draw the top part of the window, containing input and search buttons.
     */
    private void drawTopContent() throws SQLException
    {
        //Create an empty container and set the layout to a GridLayout.
        topContainer = new Container();
        topContainer.setLayout(new GridLayout(3, 3));
        topContainers = new ArrayList<>();

        //Add all of the containers for each grid to an ArrayList and initialize
        //them.
        topContainers.add(top1x1Container = new Container());
        topContainers.add(top1x2Container = new Container());
        topContainers.add(top1x3Container = new Container());
        topContainers.add(top2x1Container = new Container());
        topContainers.add(top2x2Container = new Container());
        topContainers.add(top2x3Container = new Container());
        topContainers.add(top3x1Container = new Container());
        topContainers.add(top3x2Container = new Container());
        topContainers.add(top3x3Container = new Container());

        //Add all of the labels to each grid container.
        departureLabel = new JLabel("Departure date:");
        dateDropdown = new JComboBox(drawDates());
        dateDropdown.setSelectedIndex(7);
        top1x1Container.add(departureLabel);
        top1x1Container.add(dateDropdown);

        peopleLabel = new JLabel("People:");
        peopleDropdown = new JComboBox(people);
        top1x2Container.add(peopleLabel);
        top1x2Container.add(peopleDropdown);

        startLabel = new JLabel("Start destination:");
        startDestDropdown = new JComboBox(drawDestinations());
        startDestDropdown.setPreferredSize(new Dimension(130, 25));
        top1x3Container.add(startLabel);
        top1x3Container.add(startDestDropdown);

        checkbox = new JCheckBox("Next to: ");
        checkbox.setHorizontalTextPosition(SwingConstants.LEFT);
        top2x2Container.add(checkbox);

        endLabel = new JLabel("End destination:");
        endDestDropdown = new JComboBox(drawDestinations());
        endDestDropdown.setPreferredSize(new Dimension(130, 25));
        top2x3Container.add(endLabel);
        top2x3Container.add(endDestDropdown);

        searchButton = new JButton("Search");
        top3x3Container.add(searchButton);

        //Go through all of the grid containers and set their layout, size and
        //finally add them to the topContainer.
        for (Container c : topContainers) {
            c.setLayout(new FlowLayout());
            c.setPreferredSize(new Dimension(150, 60));
            topContainer.add(c);
        }
        
        //Add the finished Container to the frame.
        content.add(topContainer, BorderLayout.PAGE_START);
    }

    /**
     * Draw the bottom part of the window, containing flights found.
     */
    private void drawBottomContent()
    {
        {
            bottomContainer = new JPanel();
            bottomContainer.setLayout(new FlowLayout());
            flightList = new FlightList(searchResults);
            scrollpane = new JScrollPane();
            flightList.setPreferredSize(new Dimension(490, 290));
            scrollpane.setPreferredSize(new Dimension(490, 290));

            padding = new JPanel();
            padding.setPreferredSize(new Dimension(20, 500));
            flightList = new FlightList(searchResults);
            flightList.setSize(new Dimension(490, 240));

            doneButton = new JButton("I WANT THIS FLIGHT!");
            doneButton.setMaximumSize(new Dimension(300, 100));
            
            scrollpane.setViewportView(flightList);
            bottomContainer.add(scrollpane);

            content.add(bottomContainer, BorderLayout.LINE_START);
            content.add(bottomContainer, BorderLayout.LINE_END);
            content.add(bottomContainer, BorderLayout.CENTER);
            content.add(doneButton, BorderLayout.PAGE_END);
            
            addActionListeners();
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
     * Send the data to the controller, and open the next window in the
     * booking work flow.
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
