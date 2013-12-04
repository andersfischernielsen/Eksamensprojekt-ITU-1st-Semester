
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        confirmButton = new JButton("Confirm");
        confirmButton.setDefaultCapable(true);

        setLayout(new BorderLayout());
        topPanel.setLayout(new MigLayout("", "[] 100 []", ""));

        topPanel.add(peopleLabel);
        topPanel.add(peopleField, "wrap");
        topPanel.add(CPRLabel);
        topPanel.add(CPRField, "wrap");
        topPanel.add(confirmButton);

        //-------------------------------------//
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
                if (CPRField.getText().equals("") || CPRField.getText().equals("")) {
                    CPRField.setText("CPR missing!");
                    CPRField.setBackground(Color.RED);
                } else {
                    currentReservation.setCPR(CPRField.getText());
                    currentReservation.setID();
                    controller.saveReservation();
                    setVisible(false);
                    dispose();
                }
            }
        });
    }
}
