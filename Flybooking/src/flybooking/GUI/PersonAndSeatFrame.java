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
        this.amtOfPersons = amtOfPersons;
        setTitle("Pick seats and passengers");
        this.setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE); // ved godt det ikke skal være sådan.
        Container mainContainer = new Container();

        // FirstName text
        JTextField firstNameText = new JTextField("First name");
        firstNameText.setEditable(false);
        // FirstName textField
        JTextField firstNameTextField = new JTextField("Put last name here...");
        firstNameTextField.setForeground(Color.gray);

        // LastName text
        JTextField lastNameText = new JTextField("Last name");
        lastNameText.setEditable(false);
        // LastName textField
        JTextField lastNameTextField = new JTextField("Put last name here...");
        lastNameTextField.setForeground(Color.gray);

        // Address text
        JTextField AddressText = new JTextField("Address");
        lastNameText.setEditable(false);
        // Street textField
        JTextField streetTextField = new JTextField("Street...");
        streetTextField.setForeground(Color.gray);
        // City textField
        JTextField cityTextField = new JTextField("ZIP, City, Country");
        cityTextField.setForeground(Color.gray);

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
