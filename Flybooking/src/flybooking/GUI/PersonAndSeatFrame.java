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
    Plane planeToDraw = new Plane("5eerrg2", 6, 20);
    

    private int amtOfPersons;
    private JComboBox personComboBox, ageGroupComboBox;

    public PersonAndSeatFrame(int amtOfPersons) throws HeadlessException
    {
        planeToDraw.setSeatAvailability("5A", false);
        planeToDraw.setSeatAvailability("10B", false);
        planeToDraw.setSeatAvailability("6F", false);
        planeToDraw.setSeatAvailability("6E", false);
        planeToDraw.setSeatAvailability("6C", false);
        
        this.amtOfPersons = amtOfPersons;
        setTitle("Pick seats and passengers");
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE); // ved godt det ikke skal være sådan.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        // laver første opdeling
        JPanel botPanel = new JPanel();
        JPanel topPanel = new JPanel();
            // TOP PANEL's Panels
                // top left panels
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new BoxLayout(topLeftPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(topLeftPanel, BorderLayout.WEST);
                // top right panels
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(topRightPanel, BorderLayout.EAST);
                // top bot panels
        JPanel topBotPanel = new JPanel();
        topPanel.add(topBotPanel, BorderLayout.SOUTH);
        //tilføjer panels til main panel
        mainPanel.add(botPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // FirstName text
        JTextField firstNameText = new JTextField("First name");
        firstNameText.setEditable(false);
        topLeftPanel.add(firstNameText);
        // FirstName textField
        JTextField firstNameTextField = new JTextField("Put last name here...");
        firstNameTextField.setForeground(Color.gray);
        topLeftPanel.add(firstNameTextField);
        topLeftPanel.add(Box.createRigidArea(new Dimension(0,5)));
        
        // LastName text
        JTextField lastNameText = new JTextField("Last name");
        lastNameText.setEditable(false);
        topLeftPanel.add(lastNameText);
        // LastName textField
        JTextField lastNameTextField = new JTextField("Put last name here...");
        lastNameTextField.setForeground(Color.gray);
        topLeftPanel.add(lastNameTextField);
        
        // Address text
        JTextField AddressText = new JTextField("Address");
        AddressText.setEditable(false);
        topRightPanel.add(AddressText);
        // Street textField
        JTextField streetTextField = new JTextField("Street...");
        streetTextField.setForeground(Color.gray);
        topRightPanel.add(streetTextField);
        topRightPanel.add(Box.createRigidArea(new Dimension(0,25)));
        // City textField
        JTextField cityTextField = new JTextField("ZIP, City, Country");
        cityTextField.setForeground(Color.gray);
        topRightPanel.add(cityTextField);
        topRightPanel.add(Box.createRigidArea(new Dimension(0,0)));

        // AgeGroup combobox
        JMenuItem[] ageGroups =
        {
            new JMenuItem("Children"),
            new JMenuItem("Adult"),
            new JMenuItem("Elder")
        };
        ageGroupComboBox = new JComboBox(ageGroups);

        // Persons ComboBox
        personComboBox = new JComboBox();
        updatePersonComboBox();

        // the plane drawing
        PlaneGraphicsComponent PlaneDrawing = new PlaneGraphicsComponent(planeToDraw);
        botPanel.add(PlaneDrawing);
        PlaneDrawing.repaint();
                
        pack();
        setVisible(true);
    }

    private void updatePersonComboBox()
    {
        for (int i = 1; i <= this.amtOfPersons; i++)
        {
            personComboBox.addItem(new JMenuItem("Person " + (i)));
        }
        personComboBox.addItem(new JMenuItem("Add another person"));

    }

    public static void main(String[] args)
    {
        new PersonAndSeatFrame(3);
    }
}
