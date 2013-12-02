
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Anders
 */
public class PaymentFrame extends JFrame {

    private ControllerInterface controller;
    private DatabaseInterface database;
    private ReservationInterface currentReservation;
    private ArrayList<Person> peopleInReservation;
    private JLabel peopleLabel, CPRLabel;
    private JTextField peopleField, CPRField;
    private JTextArea receiptArea;
    private JPanel receiptPanel, topPanel, bottomPanel;
    private Container contentPane;
    private JButton confirmButton;

    public PaymentFrame()
    {
        database = ProgramStorage.getInstance();
        controller = Controller.getInstance(database);
        currentReservation = controller.getWorkingOnReservation();
        peopleInReservation = currentReservation.getPersons();

        drawFrame();
        addActionListeners();
    }

    private void drawFrame()
    {
        receiptPanel = new JPanel();
        contentPane = getContentPane();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        receiptArea = new JTextArea();

        peopleLabel = new JLabel("People in the reservation: ");
        peopleField = new JTextField("0");
        peopleField.setColumns(peopleInReservation.size());
        peopleField.setEditable(false);

        CPRLabel = new JLabel("Payers CPR: ");
        CPRField = new JTextField("");
        CPRField.setColumns(10);

        confirmButton = new JButton("Confirm");

        setLayout(new BorderLayout());
        contentPane.setLayout(new MigLayout("", "[] 120 []", ""));

        topPanel.add(peopleLabel);
        topPanel.add(peopleField, "wrap");
        topPanel.add(CPRLabel);
        topPanel.add(CPRField, "wrap");
        topPanel.add(confirmButton);

        //-------------------------------------//
        Printer printer = new Printer(currentReservation);
        receiptArea.setText(printer.print());
        receiptPanel.add(receiptArea);
        bottomPanel.add(receiptPanel);
        contentPane.add(topPanel, BorderLayout.PAGE_START);
        contentPane.add(bottomPanel, BorderLayout.CENTER);

        contentPane.add(confirmButton, BorderLayout.PAGE_END);

        pack();
        setVisible(true);
    }

    private void addActionListeners()
    {
        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (CPRField.getText().equals("") || CPRField.getText().equals("")) {
                    CPRField.setText("Input missing!");
                } else {
                    try {
                        Controller.getInstance(database).saveReservation(database);
                    } catch (SQLException ex) {
                    }
                }
            }
        });
    }
}
