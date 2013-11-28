package flybooking.GUI;

import flybooking.Controller;
import flybooking.Person;
import flybooking.Plane;
import flybooking.ProgramStorage;
import flybooking.ReservationInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

/**
 *
 * @author Anders
 */
public class PersonAndSeatFrame extends JFrame
{

    // denne er bare til test.
    private Plane planeToDraw;

    private GraphicsComponent graphics;
    private Controller controller;
    private ReservationInterface reservation;
    private ArrayList<String> seatIDsThisRes;
    private int amtOfPersons;
    private JComboBox personComboBox, ageGroupComboBox;
    private JComponent planeDrawingComp;

    public PersonAndSeatFrame() throws HeadlessException
    {
        graphics = new GraphicsComponent();
        controller = Controller.getInstance(ProgramStorage.getInstance());
        reservation = controller.getWorkingOnReservation();
        planeToDraw = reservation.getFlight().getPlane();
        planeToDraw.bookTakenSeats(controller.getBookedSeats());
        seatIDsThisRes = new ArrayList<String>();
        for (Person person : reservation.getPersons())
        {
            amtOfPersons++;
        }
        
        setTitle("Seats and Passengers");
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        // setDefaultCloseOperation(EXIT_ON_CLOSE); // ved godt det ikke skal være sådan.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        // laver første opdeling
        JPanel botPanel = new JPanel();
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        // TOP PANEL's Panels
        // top left panel
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new BorderLayout());
        topPanel.add(topLeftPanel, BorderLayout.WEST);
        //
        JPanel topLeftWestPanel = new JPanel();
        JPanel topLeftEastPanel = new JPanel();
        topLeftPanel.add(topLeftEastPanel, BorderLayout.EAST);
        topLeftPanel.add(topLeftWestPanel, BorderLayout.WEST);
        topLeftWestPanel.add(Box.createRigidArea(new Dimension(200, 0)));
        topLeftEastPanel.setLayout(new BoxLayout(topLeftEastPanel, BoxLayout.PAGE_AXIS));
        // top right panel
        JPanel topRightPanel = new JPanel();
        topPanel.add(topRightPanel, BorderLayout.EAST);
        topRightPanel.setLayout(new BorderLayout());
        //
        JPanel topRightEastPanel = new JPanel();
        topRightEastPanel.add(Box.createRigidArea(new Dimension(200, 0)));
        topRightPanel.add(topRightEastPanel, BorderLayout.EAST);
        //
        JPanel topRightWestPanel = new JPanel();
        topRightWestPanel.setLayout(new BoxLayout(topRightWestPanel, BoxLayout.PAGE_AXIS));
        topRightPanel.add(topRightWestPanel, BorderLayout.WEST);
        // top bot panel
        JPanel topBotPanel = new JPanel();
        topPanel.add(topBotPanel, BorderLayout.SOUTH);
        // Top top panel
        JPanel topTopPanel = new JPanel();
        topPanel.add(topTopPanel, BorderLayout.NORTH);

        //tilføjer panels til main panel
        mainPanel.add(botPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // -------------------- CONTENT VENSTRE DEL TOP --------------------
        // FirstName text
        JTextField firstNameText = new JTextField("First name");
        firstNameText.setEditable(false);
        firstNameText.setBorder(BorderFactory.createEmptyBorder());
        topLeftEastPanel.add(firstNameText);
        // FirstName textField
        JTextField firstNameTextField = new JTextField("Put first name here...");
        firstNameTextField.setForeground(Color.gray);
        firstNameTextField.setPreferredSize(new Dimension(0, 22));
        topLeftEastPanel.add(firstNameTextField);
        topLeftEastPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // LastName text
        JTextField lastNameText = new JTextField("Last name");
        lastNameText.setEditable(false);
        lastNameText.setBorder(BorderFactory.createEmptyBorder());
        topLeftEastPanel.add(lastNameText);
        // LastName textField
        JTextField lastNameTextField = new JTextField("Put last name here...");
        lastNameTextField.setForeground(Color.gray);
        lastNameTextField.setPreferredSize(new Dimension(0, 22));
        topLeftEastPanel.add(lastNameTextField);

        // AgeGroup combobox
        String[] ageGroups =
        {
            "Child",
            "Adult",
            "Elder"
        };
        ageGroupComboBox = new JComboBox(ageGroups);
        topLeftEastPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topLeftEastPanel.add(ageGroupComboBox);

        // Persons ComboBox
        personComboBox = new JComboBox();
        updatePersonComboBox();
        topLeftEastPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        topLeftEastPanel.add(personComboBox);

        // -------------------- CONTENT HØJRE DEL TOP ----------------------
        // Address text
        JTextField AddressText = new JTextField("Address");
        AddressText.setEditable(false);
        AddressText.setBorder(BorderFactory.createEmptyBorder());
        topRightWestPanel.add(AddressText);
        // Street textField
        JTextField streetTextField = new JTextField("Street...");
        streetTextField.setForeground(Color.gray);
        streetTextField.setPreferredSize(new Dimension(100, 22));
        topRightWestPanel.add(streetTextField);
        topRightWestPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        // City textField
        JTextField cityTextField = new JTextField("ZIP, City, Country");
        cityTextField.setForeground(Color.gray);
        cityTextField.setPreferredSize(new Dimension(100, 22));
        topRightWestPanel.add(cityTextField);
        topRightWestPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        // delete person button
        JButton deletePersonButton = new JButton("Delete Person");
        deletePersonButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent pressed)
            {
                deletePerson();
            }
        });
        topRightWestPanel.add(deletePersonButton);
        topRightWestPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        //save Person Button
        JButton savePersonButton = new JButton("Save Person");
        savePersonButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent pressed)
            {
                savePerson();
            }
        });
        topRightWestPanel.add(savePersonButton);

        // -------------------- CONTENT TOP TOP ----------------------
        pack();
        topTopPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        topTopPanel.add(graphics.paintHeader(20, getWidth() - 40));
        topTopPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // -------------------- CONTENT TOP BUND ----------------------
        // back button
        topBotPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent pressed)
            {
                back();
            }
        });
        topBotPanel.add(backButton);
        // book button
        JButton bookButton = new JButton("> Book");
        bookButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent pressed)
            {
                confirmBooking();
            }
        });
        topBotPanel.add(bookButton);

        // -------------------- CONTENT BUNDEN ----------------------
        // the plane drawing
        planeDrawingComp = graphics.paintPlaneSeats(planeToDraw);
        planeDrawingComp.addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                seatIDsThisRes = graphics.getSeatIDsThisRes();
                planeDrawingComp = graphics.paintPlaneSeats(planeToDraw, e.getX(), e.getY(), seatIDsThisRes);
                repaint();
                pack();
                for (Iterator<String> it = seatIDsThisRes.iterator(); it.hasNext();)
                {
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
                //
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                //
            }
        });
        botPanel.add(planeDrawingComp);

        pack();
        setVisible(true);
    }

    private void updatePersonComboBox()
    {
        for (int i = 1; i <= this.amtOfPersons; i++)
        {
            personComboBox.addItem("Person " + (i));
        }
        personComboBox.addItem("Add another person");

    }

    /**
    private void changeSeatAvailability(int x, int y)
    {
        planeDrawingComp
    }
    */
    

    private void savePerson()
    {
        System.out.println("Save person");
    }

    private void deletePerson()
    {
        System.out.println("Delete person");
    }

    private void confirmBooking()
    {
        reservation.bookSeats(seatIDsThisRes);
        controller.setWorkingOnReservation(reservation);
        controller.saveReservation(ProgramStorage.getInstance());
        reservation.getFlight().getPlane().resetSeats();
        this.setVisible(false); 
        this.dispose();
    }

    private void back()
    {
        System.out.println("Back");
    }
}
