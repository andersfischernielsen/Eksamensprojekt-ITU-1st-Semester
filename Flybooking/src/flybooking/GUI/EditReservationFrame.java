
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

    private Container content, top, topContent, filler, filler2, filler3,
            filler4;
    private JButton searchButton, editButton, deleteButton;
    private JLabel resLabel, CPRLabel;
    private JTextField resField, CPRField;
    private static EditReservationFrame instance = null;
    private ControllerInterface controller;
    private DatabaseInterface database;
    private ReservationList reservationList;
    private ArrayList<ReservationInterface> searchResults;
    private JScrollPane scrollpane;

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
        database = Database.getInstance();
        this.controller = controller;
        setTitle("Edit Booking");

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
                "[] 120 []",
                "0 [] 0 [] 38 [] 0 [] 5"));

        //Create and set labels for above the textfields.
        resLabel = new JLabel(" Reservation ID: ");
        resField = new JTextField(10);

        CPRLabel = new JLabel(" CPR #: ");
        CPRField = new JTextField(10);

        //Create the default search button.
        searchButton = new JButton("Search");
        searchButton.setDefaultCapable(true);
        searchButton.setMinimumSize(new Dimension(133, 20));

        //Create the two buttons for editing and deleting reservations.
        editButton = new JButton("Edit");
        editButton.setMinimumSize(new Dimension(133, 20));
        editButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        deleteButton.setMinimumSize(new Dimension(133, 20));
        deleteButton.setEnabled(false);

        //Create fillers.
        filler = new JPanel();
        filler2 = new JPanel();
        filler3 = new JPanel();
        filler4 = new JPanel();

        //Add the components in the correct order to the top contents.
        topContent.add(resLabel);
        topContent.add(filler);
        topContent.add(CPRLabel, "wrap");
        topContent.add(resField);
        topContent.add(filler2);
        topContent.add(CPRField, "wrap");
        topContent.add(editButton);
        topContent.add(filler3, "span 2, wrap");
        topContent.add(deleteButton);
        topContent.add(filler4);
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
                //If the CPRField is empty, perform an ID search.
                if (CPRField.getText().equals("")) {
                    performIDSearch(resField.getText());
                    reservationList.setListData(searchResults.toArray());
                }

                if (resField.getText().equals("")) {
                    //If the resField is empty, perform a CPR search.
                    performCPRSearch(CPRField.getText());
                    reservationList.setListData(searchResults.toArray());
                }

                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
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
     * Find reservations from a given CPR.
     *
     * @param CPR The CPR to search for.
     */
    private void performCPRSearch(String CPR)
    {
        searchResults = controller.getReservations(null, CPR);
    }

    /**
     * Find reservations from a given reservation ID.
     *
     * @param CPR The ID to search for.
     */
    private void performIDSearch(String ID)
    {
        searchResults = controller.getReservations(ID, null);
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
