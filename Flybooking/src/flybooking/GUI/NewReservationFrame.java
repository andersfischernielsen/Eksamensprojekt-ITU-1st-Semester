
package flybooking.GUI;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Anders
 */
public class NewReservationFrame extends JFrame {

    private JComboBox dateDropdown, peopleDropdown, startDestDropdown, endDestDropdown;
    private JButton searchButton;
    private JCheckBox checkbox;
    private FlightList flightList;
    private Container content, topContainer, bottomContainer, filler;
    private JLabel departureLabel, peopleLabel, startLabel, endLabel;

    public NewReservationFrame() throws HeadlessException
    {
        String[] testStrings = {"So wow", "much wow", "so string", "much dropdown"};
         
        setDefaultCloseOperation(NewReservationFrame.EXIT_ON_CLOSE);
        content = getContentPane();
        topContainer = new Container();
        topContainer.setPreferredSize(new Dimension(400, 300));
        topContainer.setLayout(new GridLayout(3, 3));
        bottomContainer = new Container();
        //bottomContainer.add(flightList);
        content.add(topContainer, BorderLayout.NORTH);
        filler = new Container();
        
        departureLabel = new JLabel("Departure date:");
        dateDropdown = new JComboBox(testStrings);
        topContainer.add(departureLabel);
        topContainer.add(dateDropdown);

        peopleLabel = new JLabel("People:");
        peopleDropdown = new JComboBox(testStrings);
        topContainer.add(peopleLabel);
        topContainer.add(peopleDropdown);

        startLabel = new JLabel("Start destination:");
        startDestDropdown = new JComboBox(testStrings);
        topContainer.add(startLabel);
        topContainer.add(startDestDropdown);
        
        topContainer.add(filler);
        
        checkbox = new JCheckBox("Next to: ");
        topContainer.add(checkbox);
        
        endLabel = new JLabel("End destination:");
        endDestDropdown = new JComboBox(testStrings);
        topContainer.add(endLabel);
        topContainer.add(endDestDropdown);
        
        topContainer.add(filler);
        topContainer.add(filler);
        
        searchButton = new JButton("Search");
        topContainer.add(searchButton);
        
        pack();
        setVisible(true);
    }

    public static void main(String[] args)
    {
        new NewReservationFrame();
    }
}
