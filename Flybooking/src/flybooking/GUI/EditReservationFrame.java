
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class EditReservationFrame extends JFrame {

    private Container content, top, topContent, filler, filler2, filler3;
    private JButton searchButton, doneButton;
    private JLabel resLabel, CPRLabel;
    private JTextField resField, CPRField;
    private static EditReservationFrame instance = null;
    private ControllerInterface controller;
    private ReservationList reservationList;
    private ArrayList<Reservation> searchResults;
    private JScrollPane scrollpane;


    public static EditReservationFrame getInstance(ControllerInterface controller)
    {
        if (instance == null) {
            instance = new EditReservationFrame(controller);
        }

        instance.setVisible(true);
        return instance;
    }

    private EditReservationFrame(ControllerInterface controller) throws HeadlessException
    {
        this.controller = controller;
        setTitle("Edit Reservation");

        searchResults = new ArrayList<>();
        content = getContentPane();

        createTopContent();
        createBottomContent();

        getRootPane().setDefaultButton(searchButton);
        setMinimumSize(new Dimension(560, 480));
        pack();
        setVisible(true);
    }

    private void createTopContent()
    {
        top = new JPanel();
        topContent = new JPanel();
        topContent.setLayout(new MigLayout("",
                "[] 120 []",
                "5 [] 0 [] 70 [] 5"));

        resLabel = new JLabel("Reservation ID: ");
        resField = new JTextField(10);

        CPRLabel = new JLabel("CPR #: ");
        CPRField = new JTextField(10);

        searchButton = new JButton("Search");
        searchButton.setDefaultCapable(true);
        searchButton.setMinimumSize(new Dimension(133, 20));

        filler = new JPanel();
        filler2 = new JPanel();
        filler3 = new JPanel();

        topContent.add(resLabel);
        topContent.add(filler);
        topContent.add(CPRLabel, "wrap");
        topContent.add(resField);
        topContent.add(filler2);
        topContent.add(CPRField, "wrap");
        topContent.add(filler3, "span 2");
        topContent.add(searchButton);

        top.add(topContent);
        content.add(top, BorderLayout.NORTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (CPRField.getText() == "") {
                    performIDSearch(resField.getText());
                }

                if (resField.getText() == "") {
                    performCPRSearch(CPRField.getText());
                }
            }
        });
    }

    private void createBottomContent()
    {
        reservationList = new ReservationList(searchResults);
        doneButton = new JButton("Edit this flight");

        scrollpane = new JScrollPane();
        scrollpane.setViewportView(reservationList);

        content.add(scrollpane, BorderLayout.CENTER);
        content.add(doneButton, BorderLayout.PAGE_END);
    }

    private void performCPRSearch(String CPR)
    {
        ProgramStorage.getInstance().getReservationsFromCPR(CPR);
    }

    private void performIDSearch(String ID)
    {
        ProgramStorage.getInstance().getReservationsFromID(ID);
    }
}
