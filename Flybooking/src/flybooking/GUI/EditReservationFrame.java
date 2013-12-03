
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class EditReservationFrame extends JFrame {

    private Container content, top, topContent, filler, filler2, filler3, filler4;
    private JButton searchButton, editButton, deleteButton;
    private JLabel resLabel, CPRLabel;
    private JTextField resField, CPRField;
    private static EditReservationFrame instance = null;
    private ControllerInterface controller;
    private DatabaseInterface database;
    private ReservationList reservationList;
    private ArrayList<ReservationInterface> searchResults;
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
        database = Database.getInstance();
        this.controller = controller;
        setTitle("Edit Booking");

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
                "0 [] 0 [] 38 [] 0 [] 5"));

        resLabel = new JLabel(" Reservation ID: ");
        resField = new JTextField(10);

        CPRLabel = new JLabel(" CPR #: ");
        CPRField = new JTextField(10);

        searchButton = new JButton("Search");
        searchButton.setDefaultCapable(true);
        searchButton.setMinimumSize(new Dimension(133, 20));

        editButton = new JButton("Edit");
        editButton.setMinimumSize(new Dimension(133, 20));
        deleteButton = new JButton("Delete");
        deleteButton.setMinimumSize(new Dimension(133, 20));

        filler = new JPanel();
        filler2 = new JPanel();
        filler3 = new JPanel();
        filler4 = new JPanel();
        
        topContent.add(resLabel);
        topContent.add(filler);
        topContent.add(CPRLabel, "wrap");
        topContent.add(resField);
        topContent.add(filler2);
        topContent.add(CPRField, "wrap");
        topContent.add(editButton);
        topContent.add(filler3, "span 2, wrap");
        topContent.add(deleteButton);
        topContent.add(filler4);
        topContent.add(searchButton);

        top.add(topContent);
        content.add(top, BorderLayout.NORTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    if (CPRField.getText().equals("")) {
                        performIDSearch(resField.getText());
                        reservationList.update();
                    }

                    if (resField.getText().equals("")) {
                        performCPRSearch(CPRField.getText());
                        reservationList.update();
                    }
                } catch (SQLException ex) {
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Test deleteButton");
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Test editButton");
            }
        });
    }

    private void createBottomContent()
    {
        reservationList = new ReservationList(searchResults);

        scrollpane = new JScrollPane();
        scrollpane.setViewportView(reservationList);

        content.add(scrollpane, BorderLayout.CENTER);
    }

    private void performCPRSearch(String CPR) throws SQLException
    {
        controller.getReservations(null, CPR);
    }

    private void performIDSearch(String ID) throws SQLException
    {
        searchResults = controller.getReservations(ID, null);
    }
}
