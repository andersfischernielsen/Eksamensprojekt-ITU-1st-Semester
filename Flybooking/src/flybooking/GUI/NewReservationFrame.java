
package flybooking.GUI;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import flybooking.*;
import java.awt.event.*;
import java.text.ParseException;
import net.miginfocom.swing.MigLayout;

/**
 * Create a frame for finding and creating reservations
 *
 * @author Anders Fischer-Nielsen
 */
public class NewReservationFrame extends JFrame {

    //All of the Swing components needed to draw the interface.
    private JComboBox startDestDropdown, endDestDropdown;
    private JButton searchButton, doneButton;
    private FlightList flightList;
    private JPanel top, topContent, filler, filler2;
    private JLabel dateLabel, startLabel, endLabel;
    private JTextField dateField;
    private ControllerInterface controller;
    private static NewReservationFrame instance = null;
    private JScrollPane scrollpane;

    //All of the search variables in the interface.
    private ArrayList<FlightInterface> searchResults;
    private Date chosenDate;
    private String chosenStartDestination;
    private String chosenEndDestination;

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
                "0 [] 260 [] 0",
                "0 [] 0  [] 5 [] 0 [] 32 [] 5"));
        filler = new JPanel();
        filler2 = new JPanel();

        //Create all of the components.
        dateField = new JTextField("dd/mm-yyyy");
        startDestDropdown = new JComboBox(drawDestinations());
        endDestDropdown = new JComboBox(drawDestinations());
        dateLabel = new JLabel(" Departure date:");
        startLabel = new JLabel(" Start destination:");
        endLabel = new JLabel(" End destination:");
        searchButton = new JButton("Search");
        doneButton = new JButton("Book Flight");
        doneButton.setMinimumSize(new Dimension(133, 20));
        doneButton.setEnabled(false);

        //Set the sizes and indexes of specific components.
        searchButton.setMinimumSize(new Dimension(130, 20));
        searchButton.setDefaultCapable(true);
        //peopleDropdown.setMinimumSize(new Dimension(80, 20));
        startDestDropdown.setMaximumSize(new Dimension(130, 25));
        endDestDropdown.setMaximumSize(new Dimension(130, 25));
        dateField.setColumns(10);

        //Add the components so they show up in the right places.
        topContent.add(dateLabel);
        topContent.add(filler);
        topContent.add(startLabel, "wrap");
        topContent.add(dateField);
        topContent.add(filler2);
        topContent.add(startDestDropdown, "wrap");
        topContent.add(filler);
        topContent.add(endLabel, "wrap");
        topContent.add(filler2);
        topContent.add(endDestDropdown, "wrap");
        topContent.add(doneButton);
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

        dateField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e)
            {
                dateField.setSelectionStart(0);
                dateField.setSelectionEnd(dateField.getText().length());
                dateField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                dateField.setForeground(Color.LIGHT_GRAY);

                if (!dateField.getText().equals("dd/mm-yyyy")) {

                }

                if (dateField.getText().equals("")) {
                    dateField.setText("dd/mm-yyyy");
                }
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
        if (dateField.getText().equals("dd/mm-yyyy")) {
            chosenDate = null;
        } else {
            chosenDate = Converter.convertStringToDate(dateField.getText());
        }

        searchResults = controller.getFlightList(chosenDate, chosenStartDestination, chosenEndDestination);

        flightList.setListData(searchResults.toArray());
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
