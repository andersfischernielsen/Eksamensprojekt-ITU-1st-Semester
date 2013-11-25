package flybooking.GUI;

import flybooking.Plane;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Anders
 */
public class PersonAndSeatFrame extends JFrame
{

    // denne er bare til test.
    private Plane planeToDraw = new Plane("5eerrg2", 6, 20);

    GraphicsComponent graphics;
    private int amtOfPersons;
    private JComboBox personComboBox, ageGroupComboBox;

    public PersonAndSeatFrame(int amtOfPersons) throws HeadlessException
    {
        graphics = new GraphicsComponent();

        planeToDraw.setSeatAvailability("5A", false);
        planeToDraw.setSeatAvailability("10B", false);
        planeToDraw.setSeatAvailability("6F", false);
        planeToDraw.setSeatAvailability("6E", false);
        planeToDraw.setSeatAvailability("6C", false);

        this.amtOfPersons = amtOfPersons;
        setTitle("Seats and Passengers");
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE); // ved godt det ikke skal være sådan.
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
        firstNameTextField.setPreferredSize( new Dimension(0, 22));
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
        lastNameTextField.setPreferredSize( new Dimension(0, 22));
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
        streetTextField.setPreferredSize(new Dimension(100,22));
        topRightWestPanel.add(streetTextField);
        topRightWestPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        // City textField
        JTextField cityTextField = new JTextField("ZIP, City, Country");
        cityTextField.setForeground(Color.gray);
        cityTextField.setPreferredSize(new Dimension(100,22));
        topRightWestPanel.add(cityTextField);
        topRightWestPanel.add(Box.createRigidArea(new Dimension(0, 70)));
        //save Person Button
        JButton savePersonButton = new JButton("Save Person");
        topRightWestPanel.add(savePersonButton);
        

        // -------------------- CONTENT TOP TOP ----------------------
        pack();
        topTopPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        topTopPanel.add(graphics.paintHeader(20, getWidth()-40));
        topTopPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        // -------------------- CONTENT TOP BUND ----------------------
        
        topBotPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        JButton deletePersonButton = new JButton("Delete Person");
        topBotPanel.add(deletePersonButton);
        JButton bookButton = new JButton("> Book");
        topBotPanel.add(bookButton);
        
        // -------------------- CONTENT BUNDEN ----------------------
       // the plane drawing
        botPanel.add(graphics.paintPlaneSeats(planeToDraw));
        //PlaneDrawing.paintPlaneSeats(planeToDraw);

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

    public static void main(String[] args)
    {
        new PersonAndSeatFrame(5);
    }
}
