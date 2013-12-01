
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 * Create a frame for editing passengers and booking seats.
 *
 * @author Anders Wind Steffensen, Anders Fischer-Nielsen
 */
public class PersonAndSeatFrame extends JFrame {

    private Plane planeToDraw;

    private GraphicsComponent graphics;
    private Controller controller;
    private ReservationInterface reservation;
    private ArrayList<String> seatIDsThisRes;
    private int amtOfPersons;
    private ArrayList<Person> persons;
    private JComboBox personComboBox, ageGroupComboBox;
    private JComponent planeDrawingComp;
    private JLabel firstNameLabel, lastNameLabel, addressLabel, zipLabel;
    private JTextField firstNameField, lastNameField, addressField, zipField;
    private JButton bookButton, addButton, deleteButton;
    private JPanel top, topContent, filler, filler2, filler3, graphicsPanel;
    private JScrollPane scrollpane;
    private String addItem;

    public PersonAndSeatFrame() throws HeadlessException
    {
        //initialize the controller, database and get the current reservation and its plane.
        graphics = new GraphicsComponent();
        controller = Controller.getInstance(ProgramStorage.getInstance());
        reservation = controller.getWorkingOnReservation();
        planeToDraw = reservation.getFlight().getPlane();
        planeToDraw.bookTakenSeats(controller.getBookedSeats());

        seatIDsThisRes = new ArrayList<>();
        persons = new ArrayList<>();

        countPeople();
        drawTop();
        drawBottom();
        addListeners();

        getRootPane().setDefaultButton(bookButton);
        setMinimumSize(new Dimension(560, 480));

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
        String[] ages = {"Adult", "Child", "Elderly"};
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
        scrollpane = new JScrollPane();
        graphicsPanel = new JPanel();
        graphicsPanel.setBackground(Color.WHITE);
        planeDrawingComp = graphics.paintPlaneSeats(planeToDraw);

        graphicsPanel.setLayout(new GridBagLayout());
        graphicsPanel.add(planeDrawingComp);
        scrollpane.setViewportView(graphicsPanel);

        add(scrollpane, BorderLayout.CENTER);
    }

    private void changeSeatAvailability(int x, int y)
    {
        //NOT IMPLEMENTED YET.
        System.out.println("changeSeatAvailability isn't implemented yet!");
    }

    /**
     * Add a person to the reservation.
     */
    private void addPerson() throws SQLException
    {
        countPeople();
        persons.add(new Person(firstNameField.getText(), lastNameField.getText(), Calculator.createPersonID(), addressField.getText(), getGroupID(ageGroupComboBox)));
        updatePersonComboBox();
    }

    /**
     * Update the personCombobox to show all the persons currently added to the
     * reservation.
     */
    private void updatePersonComboBox()
    {
        personComboBox.setModel(new DefaultComboBoxModel(getPeopleAsArray()));
        addItem = "Add another...";
        personComboBox.addItem(addItem);
        addButton.setText("Add");
    }

    /**
     * Remove a person from the reservation.
     */
    private void deletePerson()
    {
        persons.remove(personComboBox.getSelectedIndex());
        updatePersonComboBox();
    }

    /**
     * Confirm the reservation, saving it in the booking system
     */
    private void confirmReservation()
    {
        reservation.bookSeats(seatIDsThisRes);
        controller.setWorkingOnReservation(reservation);
        controller.saveReservation(ProgramStorage.getInstance());
        reservation.getFlight().getPlane().resetSeats();
        setVisible(false);
        dispose();
    }

    /**
     * Count all the passengers currently in the reservation.
     */
    private void countPeople()
    {
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
        countPeople();
        Object[] personsAsArray = persons.toArray();
        String[] peopleInReservation = new String[personsAsArray.length];

        if (personsAsArray.length > 0) {
            for (int i = 0; i < personsAsArray.length; i++) {
                Person temp = (Person) personsAsArray[i];
                peopleInReservation[i] = temp.getFirstName();
            }
        }

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
                for (Iterator<String> it = seatIDsThisRes.iterator(); it.hasNext();) {
                    String s = it.next();
                    System.out.println(s);
                }

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

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                confirmReservation();
            }
        });

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

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                deletePerson();
            }
        });
        
        personComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //If the clicked item is the addItem, then empty all of the text fields.
                if (personComboBox.getSelectedItem().equals(addItem)) {
                    firstNameField.setText("");
                    lastNameField.setText("");
                    addressField.setText("");
                    zipField.setText("");
                    //And then end the method.
                    return;
                }

                //If the clicked item isn't the addItem it must be a person.
                Person temp = persons.get(personComboBox.getSelectedIndex());

                //Then set the fields with that persons information.
                firstNameField.setText(temp.getFirstName());
                lastNameField.setText(temp.getLastName());
                addressField.setText(temp.getAdress());
                addButton.setText("Save");
            }
        });

    }

    /**
     * Convert the persons age group into an integer value. 0 for adult, 1 for
     * child and 2 for elderly.
     *
     * @param combobox The JComboBox to check.
     * @return 0 if adult is selected, 1 if child is selected and 2 if elderly
     * is selected.
     */
    private int getGroupID(JComboBox combobox)
    {
        if (combobox.getSelectedItem().equals("Child")) {
            return 1;
        }

        if (combobox.getSelectedItem().equals("Elderly")) {
            return 2;
        }

        return 0;
    }
}
