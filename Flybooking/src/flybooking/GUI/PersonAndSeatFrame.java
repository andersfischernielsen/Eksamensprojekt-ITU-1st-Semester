
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * Create a frame for editing passengers and booking seats.
 *
 * @author Anders Wind Steffensen, Anders Fischer-Nielsen
 */
public class PersonAndSeatFrame extends JFrame {

    private Plane planeToDraw; //The plane to draw.
    private GraphicsComponent graphics; //The graphics to use.
    private Controller controller;
    private ReservationInterface reservation; //The current reservation.
    private ArrayList<String> seatIDsThisRes, seatIDsNotInThisRes;
    private int amtOfPersons; //The amount of passengers in the reservation.
    private ArrayList<Person> persons; //The passengers in the reservation.
    private JComboBox personComboBox, ageGroupComboBox; //Comboboxes for the UI.
    private JComponent planeDrawingComp;
    private JLabel firstNameLabel, lastNameLabel, addressLabel, zipLabel; //Text labels for the UI.
    private JTextField firstNameField, lastNameField, addressField, zipField; //Input fields for the UI.
    private JButton bookButton, addButton, deleteButton; //Buttons for the UI.
    private JPanel top, topContent, filler, filler2, filler3, graphicsPanel; //Panels for setting up the UI.
    private JScrollPane scrollpane; //A scrollpane for the graphics component.
    private String addItem; //A string for the person combobox.

    public PersonAndSeatFrame() throws HeadlessException,  SQLException 
    {
        //Initialize the controller, database and get the current reservation and its plane.
        graphics = new GraphicsComponent();
        controller = Controller.getInstance();
        reservation = controller.getWorkingOnReservation();
        planeToDraw = reservation.getFlight().getPlane();
        seatIDsNotInThisRes = controller.getBookedSeats();
        seatIDsThisRes = controller.getBookedThisResSeats();
        persons = controller.getBookedPersons();
        for (String seatIDNotThisRes : seatIDsNotInThisRes)
        {
            for (String seatIDthisRes : seatIDsThisRes)
            {
                if (seatIDNotThisRes.equals(seatIDthisRes))
                {
                    seatIDsNotInThisRes.remove(seatIDNotThisRes);
                }
            }
        }
        planeToDraw.bookTakenSeats(seatIDsNotInThisRes);

        //Initialize empty ArrayLists
        
        persons = controller.getBookedPersons();

        //Set up the frame.
        countPeople();
        drawTop();
        drawBottom();
        addListeners();
        getRootPane().setDefaultButton(bookButton);
        setMinimumSize(new Dimension(560, 480));

        //Show the frame.
        pack();
        setSize(new Dimension(560, 480));
        setVisible(true);
    }

    /**
     * Draw the top part of the window.
     */
    private void drawTop()
    {
        setTitle("Edit Passengers");

        top = new JPanel();
        topContent = new JPanel();
        topContent.setLayout(new MigLayout("",
                "0 [] 50 [] 50 [] 0",
                "5 [] 0 [] 5 [] 0 [] 5 []"));

        //Initialize all labels.
        firstNameLabel = new JLabel(" First name:");
        lastNameLabel = new JLabel(" Last name:");
        addressLabel = new JLabel(" Address:");
        zipLabel = new JLabel(" ZIP, city & country");

        //Initialize all text fields.
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        addressField = new JTextField();
        zipField = new JTextField();
        firstNameField.setColumns(13);
        lastNameField.setColumns(13);
        addressField.setColumns(13);
        zipField.setColumns(13);

        //Initialize all fillers.
        filler = new JPanel();
        filler2 = new JPanel();
        filler3 = new JPanel();

        //Initialize all buttons.
        addButton = new JButton("Add");
        deleteButton = new JButton("Remove");
        bookButton = new JButton("Book");
        bookButton.setMinimumSize(new Dimension(170, 20));
        bookButton.setDefaultCapable(true);

        //Initialize all dropdowns.
        String[] ages
                = {
                    "Adult", "Child", "Elderly"
                };
        ageGroupComboBox = new JComboBox(ages);
        personComboBox = new JComboBox(getPeopleAsArray());
        personComboBox.setMaximumSize(new Dimension(100, 25));

        //Add the first and second line of components.
        topContent.add(firstNameLabel);
        topContent.add(filler);
        topContent.add(addressLabel, "wrap");
        topContent.add(firstNameField);
        topContent.add(ageGroupComboBox);
        topContent.add(addressField, "wrap");

        //Add the third and fourth line of components.
        topContent.add(lastNameLabel);
        topContent.add(filler2);
        topContent.add(zipLabel, "wrap");
        topContent.add(lastNameField);
        topContent.add(personComboBox);
        topContent.add(zipField, "wrap");

        //Add the last line of components.
        topContent.add(addButton, "split 2");
        topContent.add(deleteButton, "gapleft -30");
        topContent.add(filler3);
        topContent.add(bookButton);

        //Add everything to the top part of the main frame.
        top.add(topContent);
        add(top, BorderLayout.NORTH);
    }

    /**
     * Draw the bottom part of the window.
     */
    private void drawBottom()
    {
        //Initialize the scrollpane, the inner panel and the graphics to add.
        scrollpane = new JScrollPane();
        graphicsPanel = new JPanel();
        graphicsPanel.setBackground(Color.WHITE);
        planeDrawingComp = graphics.paintPlaneSeats(planeToDraw, seatIDsThisRes);

        //Set up the layout, and add the components.
        graphicsPanel.setLayout(new GridBagLayout());
        graphicsPanel.add(planeDrawingComp);
        scrollpane.setViewportView(graphicsPanel);

        //Add the finished pane to the frame.
        add(scrollpane, BorderLayout.CENTER);
    }

    private void changeSeatAvailability(int x, int y)
    {
        //NOT IMPLEMENTED YET. NOT NEEDED
        System.out.println("changeSeatAvailability isn't implemented yet!");
    }

    /**
     * Add a person to the reservation.
     */
    private void addPerson() throws SQLException
    {
        //Count the number of people, and add a new person to the list of passengers.
        countPeople();
        persons.add(new Person(firstNameField.getText(), lastNameField.getText(), Calculator.createPersonID(), addressField.getText(), getGroupID(ageGroupComboBox)));
        //Update the personComboBox to make sure it shows the current passengers.
        updatePersonComboBox();
        emptyTextFields();
    }

    /**
     * Update the personCombobox to show all the persons currently added to the
     * reservation.
     */
    private void updatePersonComboBox()
    {
        //Set the content of the ComboBox. 
        personComboBox.setModel(new DefaultComboBoxModel(getPeopleAsArray()));
        //Add the "Add another..." item.
        addItem = "Add another...";
        personComboBox.addItem(addItem);
        //Reset the addButton to its default state.
        addButton.setText("Add");
    }

    public void emptyTextFields()
    {
        firstNameField.setText("");
        lastNameField.setText("");
        addressField.setText("");
        zipField.setText("");
    }

    /**
     * Remove a person from the reservation.
     */
    private void deletePerson()
    {
        //Remove the person from the ArrayList and update the personComboBox.
        persons.remove(personComboBox.getSelectedIndex());
        updatePersonComboBox();
    }

    /**
     * Confirm the reservation, saving it in the booking system
     */
    private void confirmReservation() throws SQLException
    {
        if (seatIDsThisRes.size() != persons.size()) {
            JOptionPane.showMessageDialog(null, "You havent booked the same amount of seats as the amounts of persons this booking!", "You havent booked the same amount of seats as the amounts of persons this booking!", JOptionPane.ERROR_MESSAGE);
        }
        else if (seatIDsThisRes.size() == 0 || persons.size() == 0){ // last statement not neccesary
                JOptionPane.showMessageDialog(null, "You havent booked any persons or seats", "You havent booked any persons or seats", JOptionPane.ERROR_MESSAGE);
        } else {
            reservation.bookSeats(seatIDsThisRes);
            
            
            //Add all the people to the reservation.
            for (Person p : persons) {
                reservation.addPerson(p);
            }
            
            //Save the reservation.
            controller.setWorkingOnReservation(reservation);
            new PaymentFrame();
            setVisible(false);
            dispose();
        }
    }

    /**
     * Count all the passengers currently in the reservation.
     */
    private void countPeople()
    {
        amtOfPersons = 0;
        for (Person person : persons) {
            amtOfPersons++;
        }
    }

    /**
     * Get the names of the passengers in the reservation as an array.
     *
     * @return An array of names of passengers.
     */
    private String[] getPeopleAsArray()
    {
        //Get all of the people in the reservation as an array (not an ArrayList).
        Object[] personsAsArray = persons.toArray();
        //Init a list of strings for the names of people.
        String[] peopleInReservation = new String[personsAsArray.length];

        if (personsAsArray.length > 0) {
            //For every person, get his/her first name and add it to the string array.
            for (int i = 0; i < personsAsArray.length; i++) {
                Person temp = (Person) personsAsArray[i];
                peopleInReservation[i] = temp.getFirstName() + " " + temp.getLastName();
            }
        }

        //Return the finished string array.
        return peopleInReservation;
    }

    /**
     * Add Action- and MouseListeners to the components in the frame.
     */
    private void addListeners() 
    {
        planeDrawingComp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                seatIDsThisRes = graphics.getSeatIDsThisRes();
                planeDrawingComp = graphics.paintPlaneSeats(planeToDraw, e.getX(), e.getY(), seatIDsThisRes);
                repaint();
                pack();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
            }
        });

        //Add an ActionListener to the bookButton to confirm reservations.
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // FIX THIS LORTE SQL EXCEPTIONS
                try
                {
                    confirmReservation();
                }
                catch (SQLException ex)
                {
                    Logger.getLogger(PersonAndSeatFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Add an ActionListener to the addButton to add people.
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    addPerson();
                } catch (SQLException ex) {
                }
            }
        });

        //Add an ActionListener to the removeButton to remove people.
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                deletePerson();
            }
        });

        //Add an ActionListener to the personComboBox to keep track of people.
        personComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //If the clicked item is the addItem, then empty all of the text fields.
                if (personComboBox.getSelectedItem().equals(addItem)) {
                    emptyTextFields();

                    //And then end the method.
                    return;
                }

                //If the clicked item isn't the addItem it must be a person.
                Person temp = persons.get(personComboBox.getSelectedIndex());

                //Then set the fields with that persons information.
                //First we get the id of the selected person.
                int ID = temp.getID();

                //Then we go through all the persons, see if the ID's are 
                //matching, and if it is get that person's info.
                for (Person p : persons) {
                    if (p.getID() == ID) {
                        firstNameField.setText(p.getFirstName());
                        lastNameField.setText(p.getLastName());
                        addressField.setText(p.getAdress());
                    }
                }
            }
        });
    }

    /**
     * Convert the persons age group into an integer value. 0 for adult, 1 for
     * child and 2 for elderly.
     *
     * @param combobox The JComboBox to check.
     *
     * @return 0 if adult is selected, 1 if child is selected and 2 if elderly
     * is selected.
     */
    private int getGroupID(JComboBox combobox)
    {
        // If the person is a child, set the ID to 1.
        if (combobox.getSelectedItem().equals("Child")) {
            return 1;
        }

        //If the person is elderly, set the ID to 2.
        if (combobox.getSelectedItem().equals("Elderly")) {
            return 2;
        }

        //If none of the above, the person is an Adult, and therefore has ID 0.
        return 0;
    }
    
    @Override 
    public void pack() {
        
    }
}
