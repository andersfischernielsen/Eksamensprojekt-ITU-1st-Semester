
package flybooking.GUI;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import flybooking.*;
import java.sql.*;

/**
 *
 * @author Anders
 */
public class NewReservationFrame extends JFrame{ 

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

    /**
     * Get an instance of the Frame. (Singleton)
     *
     * @param controller The controller to get information from for the user interface.
     * @return An instance of the frame.
     */
    public static NewReservationFrame getInstance(ControllerInterface controller)
    {
        if (instance == null) {
            instance = new NewReservationFrame(controller);
        }

        return instance;
    }

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

    public void drawFrame() {
        setDefaultCloseOperation(NewReservationFrame.EXIT_ON_CLOSE);
        setTitle("New Reservation");
        setResizable(false);

        content = getContentPane();
        topContainers = new ArrayList<>();

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
        top1x1Container.add(departureLabel);
        top1x1Container.add(dateDropdown);

        peopleLabel = new JLabel("People:");
        peopleDropdown = new JComboBox(people);
        top1x2Container.add(peopleLabel);
        top1x2Container.add(peopleDropdown);

        startLabel = new JLabel("Start destination:");
        startDestDropdown = new JComboBox(drawDestinations());
        top1x3Container.add(startLabel);
        top1x3Container.add(startDestDropdown);

        checkbox = new JCheckBox("Next to: ");
        checkbox.setHorizontalTextPosition(SwingConstants.LEFT);
        top2x2Container.add(checkbox);

        endLabel = new JLabel("End destination:");
        endDestDropdown = new JComboBox(drawDestinations());
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
     * Get the dates as a String array between a week ago and a week 
     * forward from the current date.
     * @return A string array of the dates a week ago and up until a week from now.
     */
    private String[] drawDates() {
        String[] array = {};
        
        return array;
    }
    
    /**
     * Get the possible destinations from the controller as a string array.
     * @return A string array of possible destinations.
     */
    private String[] drawDestinations() {
        String[] array = {};
        
        return array;
    }
}
