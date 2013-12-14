
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import net.miginfocom.swing.MigLayout;

/**
 * Find and edit an existing reservation.
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class EditReservationFrame extends JFrame {

    private Container content, top, topContent, filler, filler2, filler3, filler4;
    private JButton searchButton, editButton, deleteButton;
    private JComboBox startDestDropdown, endDestDropdown;
    private JLabel resLabel, CPRLabel, startDateLabel, endDateLabel, 
                        startDestLabel, endDestLabel;
    private JTextField resField, CPRField, startDateField, endDateField;
    private static EditReservationFrame instance = null;
    private ControllerInterface controller;
    private ReservationList reservationList;
    private ArrayList<ReservationInterface> searchResults;
    private JScrollPane scrollpane;
    private String chosenDate;

    public static EditReservationFrame getInstance(ControllerInterface controller)
    {
        if (instance == null) {
            instance = new EditReservationFrame(controller);
        }

        instance.setVisible(true);
        return instance;
    }

    /**
     * Create a new frame for finding and creating new reservations.
     *
     * @param controller The controller to use for creating, editing and saving
     * reservations.
     * @throws HeadlessException If the frame is created on a computer without a
     * keyboard, display or mouse.
     */
    private EditReservationFrame(ControllerInterface controller)
            throws HeadlessException
    {
        this.controller = controller;
        setTitle("Edit Reservation");

        searchResults = new ArrayList<>();
        content = getContentPane();

        createTopContent();
        createBottomContent();

        getRootPane().setDefaultButton(searchButton);
        setMinimumSize(new Dimension(560, 480));
        pack();
        setVisible(true);
    }

    /**
     * Create the top part of the frame.
     */
    private void createTopContent()
    {
        //Create a panel for the contents of the top part of the frame.
        top = new JPanel();

        //Create an inner panel for the top contents.
        topContent = new JPanel();
        topContent.setLayout(new MigLayout("",
                "0 [] 60 [] 60 [] 0",
                "0 [] 0 [] 0 [] 0 [] 15 [] -5 [] 0"));

        //Create and set labels for above the textfields.
        resLabel = new JLabel(" Reservation ID: ");
        resField = new JTextField(10);

        CPRLabel = new JLabel(" CPR #: ");
        CPRField = new JTextField(10);

        startDateLabel = new JLabel(" Start Date:");
        startDateField = new JTextField("dd/mm-yyyy");
        startDateField.setColumns(10);
        startDateField.setForeground(Color.LIGHT_GRAY);
        
        endDateLabel = new JLabel(" End Date:");
        endDateField = new JTextField("dd/mm-yyyy");
        endDateField.setColumns(10);
        endDateField.setForeground(Color.LIGHT_GRAY);
        
        startDestLabel = new JLabel(" Start Destination: ");
        endDestLabel = new JLabel(" End Destination: ");

        //Create the two Comboboxes for selecting dates.
        startDestDropdown = new JComboBox<>();
        endDestDropdown = new JComboBox<>();
        startDestDropdown.setMaximumSize(new Dimension(135, 25));
        endDestDropdown.setMaximumSize(new Dimension(135, 25));
        fillComboBox(drawDestinations(), startDestDropdown);
        fillComboBox(drawDestinations(), endDestDropdown);

        //Create the default search button.
        searchButton = new JButton("Search");
        searchButton.setDefaultCapable(true);
        searchButton.setMinimumSize(new Dimension(135, 20));

        //Create the two buttons for editing and deleting reservations.
        editButton = new JButton("Edit");
        editButton.setMinimumSize(new Dimension(135, 20));
        editButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        deleteButton.setMinimumSize(new Dimension(135, 20));
        deleteButton.setEnabled(false);

        //Create fillers.
        filler = new JPanel();
        filler2 = new JPanel();
        filler3 = new JPanel();
        filler4 = new JPanel();

        //Add the components in the correct order to the top contents.
        topContent.add(resLabel);
        topContent.add(startDateLabel);
        topContent.add(startDestLabel, "wrap");
        topContent.add(resField);
        topContent.add(startDateField);
        topContent.add(startDestDropdown, "wrap");
        topContent.add(CPRLabel);
        topContent.add(endDateLabel);
        topContent.add(endDestLabel, "wrap");
        topContent.add(CPRField);
        topContent.add(endDateField);
        topContent.add(endDestDropdown, "wrap");
        
        topContent.add(editButton);
        topContent.add(filler, "span 2, wrap");
        topContent.add(deleteButton);
        topContent.add(filler2);
        topContent.add(searchButton);

        //Add the finished panel to the top part of the frame.
        top.add(topContent);
        content.add(top, BorderLayout.NORTH);
    }

    /**
     * Create the bottom part of the frame containing results.
     */
    private void createBottomContent()
    {
        //Create the scrollpane showing the results.
        scrollpane = new JScrollPane();
        //Add the reservation list to the scrollpane.
        reservationList = new ReservationList(searchResults);
        scrollpane.setViewportView(reservationList);

        //Add all of the actionListeners to the components.
        addActionListeners();
        //Add the finished panel to the frame.
        content.add(scrollpane, BorderLayout.CENTER);
    }

    /**
     * Add all of the actionListeners to the frame.
     */
    private void addActionListeners()
    {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Perform a search with the given search parameters.
                performSearch(resField.getText(), 
                              CPRField.getText(), 
                              Converter.convertStringToDate(startDateField.getText()),
                              Converter.convertStringToDate(endDateField.getText()),
                              (String) startDestDropdown.getSelectedItem(), 
                              (String) endDestDropdown.getSelectedItem());
                
                //Set the list data as the search results.
                reservationList.setListData(searchResults.toArray());
                
                //If there were any search results, make the buttons enabled.
                if (!searchResults.isEmpty()) {
                    editButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Delete the selected reservation.
                deleteReservation(reservationList.
                        getSelectedReservation().getID());
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sendOnData();
            }
        });

        startDateField.addFocusListener(new FocusListener() {
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
        
        reservationList.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                //If the user has clicked on a valid index in the list, make the
                //buttons enabled.
                if (reservationList.getSelectedIndex() > -1) {
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);

                    //If the user doubleclicks a reservation in the list, fire 
                    //the editButton.
                    if (e.getClickCount() > 1) {
                        editButton.doClick();
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
        }
        );
    }

    /**
     * When done finding flights, create the next frame and send the current
     * half-finished reservation to the controller.
     */
    public void sendOnData()
    {
        ReservationInterface reservation
                = reservationList.getSelectedReservation();

        if (reservation.getFlight().getPlane() != null) {
            controller.setWorkingOnReservation(reservation);
            setVisible(false);
            PersonAndSeatFrame personAndSeatFrame = new PersonAndSeatFrame();
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
     * Find reservations from the given search parameters.
     * 
     * @param reservationID     The reservation ID to search for.
     * @param CPR               The CPR to search for.
     * @param date              The date to search for.
     * @param startDestination  The start destination to search for.
     * @param endDestination    The end destination to search for.
     *
     */
    private void performSearch(String reservationID, String CPR, Date startDate,
                                            Date endDate,String startDestination,
                                            String endDestination) {
        
        if (startDestination.equals("None")) {
            startDestination = null;
        }
        
        if (endDestination.equals("None")) {
            endDestination = null;
        }
        
        if (startDateField.getText().equals("dd/mm-yyyy")) {
            startDate = null;
        } 
        
        if (endDateField.getText().equals("dd/mm-yyyy")) {
            endDate = null;
        } 
        
        searchResults = controller.getReservations(reservationID, CPR, 
                                                       startDate, endDate,
                                                       startDestination, 
                                                       endDestination);
    }

    /**
     * Delete the selected reservation.
     *
     * @param reservationID
     */
    private void deleteReservation(String reservationID)
    {
        //Delete the reservation and click the searchButton to update the list.
        controller.deleteReservation(reservationID);
        searchButton.doClick();
    }
}
