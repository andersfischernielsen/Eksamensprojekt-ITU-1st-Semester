
package flybooking.GUI;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import flybooking.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 *
 * @author Anders
 */
public class NewReservationFrame extends JFrame {

    private JComboBox dateDropdown, peopleDropdown, startDestDropdown, endDestDropdown;
    private JButton searchButton;
    private JCheckBox checkbox;
    private FlightList flightList;
    private Container content, topContainer, bottomContainer,
            top1x1Container, top1x2Container, top1x3Container,
            top2x1Container, top2x2Container, top2x3Container,
            top3x1Container, top3x2Container, top3x3Container;
    private ArrayList<Container> topContainers;
    private JLabel departureLabel, peopleLabel, startLabel, endLabel;
    private DatabaseInterface flightDatabase;
    private ControllerInterface controller;
    private static NewReservationFrame instance = null;
    private String[] people = {"1", "2", "3", "4", "5"};
    
    private Date chosenDate;
    private int chosenPeople;
    private String chosenStartDestination;
    private String chosenEndDestination;
    
    /**
     * Create a frame for finding and creating reservations.
     *
     * @throws HeadlessException
     */
    private NewReservationFrame(ControllerInterface controller) throws HeadlessException
    {
        drawFrame();
        this.controller = controller;
    }
    
    /**
     * Get an instance of the Frame. (Singleton)
     *
     * @param controller The controller to get information from for the user
     * interface.
     * @return An instance of the frame.
     */
    public static NewReservationFrame getInstance(ControllerInterface controller)
    {
        if (instance == null) {
            instance = new NewReservationFrame(controller);
        }

        return instance;
    }

    public final void drawFrame()
    {
        setDefaultCloseOperation(NewReservationFrame.EXIT_ON_CLOSE);
        setTitle("New Reservation");
        setResizable(false);
        
        content = getContentPane();
        topContainers = new ArrayList<>();

        chosenPeople = 1;
        Airport tempDest = (Airport) startDestDropdown.getSelectedItem();
        chosenStartDestination = tempDest.getID();
        
        drawTopContent();
        drawBottomContent();

        content.repaint();
        setSize(new Dimension(540, 500));
        setVisible(true);
    }

    /**
     * Draw the top part of the window, containing input and search buttons.
     */
    private void drawTopContent()
    {
        //Create an empty container and set the layout to a GridLayout.
        topContainer = new Container();
        topContainer.setLayout(new GridLayout(3, 3));

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
        startDestDropdown = new JComboBox(drawStartDestinations());
        top1x3Container.add(startLabel);
        top1x3Container.add(startDestDropdown);

        checkbox = new JCheckBox("Next to: ");
        checkbox.setHorizontalTextPosition(SwingConstants.LEFT);
        top2x2Container.add(checkbox);

        endLabel = new JLabel("End destination:");
        endDestDropdown = new JComboBox(drawEndDestinations());
        top2x3Container.add(endLabel);
        top2x3Container.add(endDestDropdown);

        searchButton = new JButton("Search");
        top3x3Container.add(searchButton);

        //Go through all of the grid containers and set their layout, size and
        //finally add them to the topContainer.
        for (Container c : topContainers) {
            c.setLayout(new FlowLayout());
            c.setPreferredSize(new Dimension(100, 60));
            topContainer.add(c);
        }

        addActionListeners();
        //Add the finished Container to the frame.
        content.add(topContainer, BorderLayout.NORTH);
    }

    /**
     * Draw the bottom part of the window, containing flights found.
     */
    private void drawBottomContent()
    {
        bottomContainer = new Container();

        content.add(bottomContainer, BorderLayout.SOUTH);
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
    private String[] drawStartDestinations()
    {
        String[] array = new String[controller.getNumberOfFlights()];
        Flight[] flights = controller.getAllFlights();
        
        for (int i = 0; i < array.length; i++) {
            array[i] = flights[i].getStartAirport().getID();
        }
        
        return array;
    }
    
    /**
     * Get the possible destinations from the controller as a string array.
     *
     * @return A string array of possible destinations.
     */
    private String[] drawEndDestinations()
    {
        String[] array = new String[controller.getNumberOfFlights()];
        Flight[] flights = controller.getAllFlights();
        
        for (int i = 0; i < array.length; i++) {
            array[i] = flights[i].getEndAirport().getID();
        }
        
        return array;
    }
    
    /**
     * Add all of the ugly ActionListener code to the ComboBoxes and Buttons. 
     */
    private void addActionListeners() {
        //Adds an ActionListener that changes chosenDate to the date chosen when clicked.
        dateDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { 
                //Creates a temporary ComboBox, that contains the click source.
                JComboBox cb = (JComboBox) e.getSource();
                try { 
                    //Then convert the ComboBox into a date using the Calculator.
                    chosenDate = Calculator.convertStringToDate(cb.getSelectedItem().toString());
                } catch (ParseException ex) {}}});
        
        //Add an ActionListener that changes the chosenPeople to the value chosen when clicked.
        peopleDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { JComboBox cb = (JComboBox) e.getSource();
            //Same as above just with an integer.
                chosenPeople = Integer.parseInt(cb.getSelectedItem().toString());
            }});
        
        //Adds an ActionListener that changes chosenStartDestination to the value chosen when clicked.
        startDestDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { JComboBox cb = (JComboBox) e.getSource();
                //Creates a temporary Airport and get it's ID. Assigns this to chosenStartDest.
                Airport temp = (Airport) cb.getSelectedItem();
                chosenStartDestination = temp.getID();
            }});
        
        //Same as above.
        endDestDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { JComboBox cb = (JComboBox) e.getSource();
                Airport temp = (Airport) cb.getSelectedItem();
                chosenEndDestination = temp.getID();
            }});
        
        //Add an ActionListener that runs a search when the button is clicked.
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { performSearch(); }});
    }
    
    /**
     * Perform a search for flights with the specified search options.
     */
    private void performSearch() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Searching.");
            System.out.println("Searching..");
            System.out.println("Searching...");
        }
    }
}
