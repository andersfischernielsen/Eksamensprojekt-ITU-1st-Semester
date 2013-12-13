
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
    private JPanel top, topContent;
    private JLabel startDateLabel, endDateLabel, startLabel, endLabel;
    private JTextField startDateField, endDateField;
    private ControllerInterface controller;
    private static NewReservationFrame instance = null;
    private JScrollPane scrollpane;

    //All of the search variables in the interface.
    private ArrayList<FlightInterface> searchResults;


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

        //Create all of the textfields.
        startDateField = new JTextField("dd/mm-yyyy");
        endDateField = new JTextField("dd/mm-yyyy");
        
        //Create and fill the ComboBoxes.
        startDestDropdown = new JComboBox();
        fillComboBox(drawDestinations(), startDestDropdown);
        endDestDropdown = new JComboBox();
        fillComboBox(drawDestinations(), endDestDropdown);
        
        //Create all of the labels.
        startDateLabel = new JLabel(" Start date:");
        endDateLabel = new JLabel(" End date:");
        startLabel = new JLabel(" Start destination:");
        endLabel = new JLabel(" End destination:");
        
        //Create all of the buttons.
        searchButton = new JButton("Search");
        doneButton = new JButton("Book Flight");
        doneButton.setMinimumSize(new Dimension(133, 20));
        doneButton.setEnabled(false);
        
        //Set the sizes, colors and indexes of specific components.
        searchButton.setMinimumSize(new Dimension(130, 20));
        searchButton.setDefaultCapable(true);
        startDestDropdown.setMaximumSize(new Dimension(130, 25));
        endDestDropdown.setMaximumSize(new Dimension(130, 25));
        startDateField.setColumns(10);
        endDateField.setColumns(10);
        endDateField.setForeground(Color.LIGHT_GRAY);
        startDateField.setForeground(Color.LIGHT_GRAY);

        //Add the components so they show up in the right places.
        topContent.add(startDateLabel);
        topContent.add(startLabel, "wrap");
        topContent.add(startDateField);
        topContent.add(startDestDropdown, "wrap");
        topContent.add(endDateLabel);
        topContent.add(endLabel, "wrap");
        topContent.add(endDateField);
        topContent.add(endDestDropdown, "wrap");
        topContent.add(doneButton);
        topContent.add(searchButton);

        //Add the finished top panel to the main frame.
        top.add(topContent);
        getContentPane().add(top, BorderLayout.PAGE_START);
    }

    /**
     * Fill the given JComboBox with the given String array.
     */
    private void fillComboBox(String[] array, JComboBox comboBox)
    {
        //Create the ArrayList of items to put in the JComboBox.
        ArrayList<String> items = new ArrayList<>();

        items.add("None");
        //Add all of the strings in the array to the list of items.
        items.addAll(Arrays.asList(array));

        //Create the model to use for the given JComboBox.
        DefaultComboBoxModel model = new DefaultComboBoxModel(items.toArray());

        //Set the model for the JComboBox.
        comboBox.setModel(model);
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
        //Add an ActionListener to search when the search button is clicked.
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                performSearch(Converter.convertStringToDate(
                                                      startDateField.getText()), 
                              Converter.convertStringToDate(
                                                      endDateField.getText()), 
                              (String) startDestDropdown.getSelectedItem(), 
                              (String) endDestDropdown.getSelectedItem());
            }
        });

        doneButton.addActionListener(new ActionListener() {
            //Send the data to the next window if the done-button is pressed.
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendOnData();
            }
        });

        startDateField.addFocusListener(new FocusListener() {
            //Set the selection and color of the text field to give the user
            //hints about input.
            @Override
            public void focusGained(FocusEvent e)
            {
                startDateField.setSelectionStart(0);
                startDateField.setSelectionEnd(startDateField.getText().length());
                startDateField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                startDateField.setForeground(Color.LIGHT_GRAY);

                if (startDateField.getText().equals("")) {
                    startDateField.setText("dd/mm-yyyy");
                }
            }
        });
        
        endDateField.addFocusListener(new FocusListener() {
            //Set the selection and color of the text field to give the user
            //hints about input.
            @Override
            public void focusGained(FocusEvent e)
            {
                endDateField.setSelectionStart(0);
                endDateField.setSelectionEnd(endDateField.getText().length());
                endDateField.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                endDateField.setForeground(Color.LIGHT_GRAY);

                if (endDateField.getText().equals("")) {
                    endDateField.setText("dd/mm-yyyy");
                }
            }
        });

        flightList.addMouseListener(new MouseListener() {
            //Check for doubleclick on the flightlist to be able to double-
            //click a flight.
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
    private void performSearch(Date startDate, Date endDate, 
                                                        String startDestination,
                                                        String endDestination)
    {
        //If the user hasn't selected a start or end date, search for null.
        if (startDestination.equals("None")) {
            startDestination = null;
        }
        
        if (endDestination.equals("None")) {
            endDestination = null;
        }
        
        //Same as above with the start and end date text fields.
        if (startDateField.getText().equals("dd/mm-yyyy")) {
            startDate = null;
        } 
        
        if (endDateField.getText().equals("dd/mm-yyyy")) {
            endDate = null;
        } 

        searchResults = controller.getFlightList(startDate, endDate, startDestination, endDestination);

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
        //Create a new reservation, and fill the info we know about so far.
        ReservationInterface reservation = new Reservation();
        reservation.setReservationDate(new Date());
        reservation.setFlight(flightList.getSelectedFlight());

        //Then send the data to the controller, and create the next window.
        if (reservation.getFlight().getPlane() != null) {
            controller.setWorkingOnReservation(reservation);
            setVisible(false);

            new PersonAndSeatFrame();
        }
    }
}
