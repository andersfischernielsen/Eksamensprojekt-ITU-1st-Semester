
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

/**
 * Create a frame for showing the information in the reservation, 
 * and confirm the booking.
 * @author Anders Wind Steffensen, Chris Forup & Anders Fischer-Nielsen
 */
public class PaymentFrame extends JFrame {

    private ControllerInterface controller;
    private DatabaseInterface database;
    private ReservationInterface currentReservation;
    private ArrayList<Person> peopleInReservation;
    private JLabel peopleLabel, CPRLabel;
    private JTextField peopleField, CPRField;
    private JTextArea receiptArea;
    private JPanel topPanel;
    private Container contentPane;
    private JButton confirmButton;
    private JScrollPane scrollpane;
    private Printer printer;

    public PaymentFrame()
    {
        database = Database.getInstance();
        controller = Controller.getInstance();
        currentReservation = controller.getWorkingOnReservation();
        peopleInReservation = currentReservation.getPersons();

        drawFrame();
        addActionListeners();
    }

    private void drawFrame()
    {
        contentPane = getContentPane();
        topPanel = new JPanel();
        printer = new Printer(currentReservation);
        controller.setReservationIDToCome(Converter.createReservationID());
        receiptArea = new JTextArea(printer.print());
        receiptArea.setBorder(new EmptyBorder(0, 10, 0, 10));

        peopleLabel = new JLabel("People in the reservation: ");
        peopleField = new JTextField();
        peopleField.setColumns(10);
        peopleField.setEditable(false);
        peopleField.setText("" + peopleInReservation.size());
        peopleField.setHorizontalAlignment(SwingConstants.RIGHT);

        CPRLabel = new JLabel("Payers CPR: ");
        CPRField = new JTextField("");

        if (currentReservation.getCPR() != null) {
            CPRField.setText(currentReservation.getCPR());
        }

        CPRField.setColumns(10);

        confirmButton = new JButton("Confirm Booking");
        confirmButton.setDefaultCapable(true);

        setLayout(new BorderLayout());
        topPanel.setLayout(new MigLayout("", "[] 100 []", ""));

        topPanel.add(peopleLabel);
        topPanel.add(peopleField, "wrap");
        topPanel.add(CPRLabel);
        topPanel.add(CPRField, "wrap");
        topPanel.add(confirmButton);

        
        //------------------BOTTOM FRAME CODE -------------------//
        
        scrollpane = new JScrollPane(receiptArea);

        contentPane.add(topPanel, BorderLayout.PAGE_START);
        contentPane.add(scrollpane, BorderLayout.CENTER);
        contentPane.add(confirmButton, BorderLayout.PAGE_END);

        getRootPane().setDefaultButton(confirmButton);
        setPreferredSize(new Dimension(440, 310));
        setLocation(new Point(600, 40));

        pack();
        setVisible(true);
    }

    private void addActionListeners()
    {
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //If the CPRField is empty and the button is pressed:
                if (CPRField.getText().equals("")) {
                    //Change the text to:
                    CPRField.setText("CPR missing!");
                    //And make the background red to make the user notice this.
                    CPRField.setBackground(Color.RED);
                } else {
                    currentReservation.setCPR(CPRField.getText());
                    boolean savedSuccessfully = false;
                    savedSuccessfully = controller.saveReservation();
                    if (savedSuccessfully) {
                        setVisible(false);
                        dispose();
                    } else {
                        receiptArea.setText("ERROR! COULDN'T SAVE RESERVATION");
                        receiptArea.setBackground(Color.red);
                    }
                }
            }
        });
    }
}
