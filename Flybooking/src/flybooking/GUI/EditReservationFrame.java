
package flybooking.GUI;

import flybooking.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;
import java.util.*;

/**
 *
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class EditReservationFrame extends JFrame {

    private Container content, topContent, bottomContent, topLeft, topRight, bottomLeft, bottomRight, filler;
    private JButton searchButton;
    private JLabel resLabel, CPRLabel;
    private JTextField resField, CPRField;
    private static EditReservationFrame instance = null;
    private ControllerInterface controller;
    private ReservationList reservationList;
    private ArrayList<Reservation> searchResults;
    
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
        pack();
        setVisible(true);
    }

    private void createTopContent()
    {
        topContent = new Container();
        topContent.setLayout(new GridLayout(2, 2));

        topLeft = new Container();
        resLabel = new JLabel("Reservation ID: ");
        resField = new JTextField(10);
        topLeft.add(resLabel);
        topLeft.add(resField);
        topLeft.setLayout(new FlowLayout());

        topRight = new Container();
        CPRLabel = new JLabel("CPR #: ");
        CPRField = new JTextField(10);
        topRight.add(CPRLabel);
        topRight.add(CPRField);
        topRight.setLayout(new FlowLayout());

        bottomLeft = new Container();

        bottomRight = new Container();
        filler = new Container();
        filler.setPreferredSize(new Dimension(100, 30));
        searchButton = new JButton("Search");
        
        searchButton.setDefaultCapable(true);
        searchButton.setAlignmentX(RIGHT_ALIGNMENT);
        bottomRight.add(filler);
        bottomRight.add(searchButton);
        bottomRight.setLayout(new FlowLayout());

        topContent.add(topLeft);
        topContent.add(topRight);
        topContent.add(bottomLeft);
        topContent.add(bottomRight);
        
        searchButton.addActionListener(new ActionListener()
        {
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

        content.add(topContent, BorderLayout.NORTH);
    }

    private void createBottomContent()
    {
        bottomContent = new JPanel();
        bottomContent.setPreferredSize(new Dimension(500, 300));
        reservationList = new ReservationList(searchResults);
        reservationList.setPreferredSize(new Dimension(500, 300));
        bottomContent.add(reservationList);

        content.add(bottomContent, BorderLayout.SOUTH);
    }
    
    private void performCPRSearch(String CPR) {
        ProgramStorage.getInstance().getReservationsFromCPR(CPR);
    }
    
    private void performIDSearch(String ID) {
        ProgramStorage.getInstance().getReservationsFromID(ID);
    }
}
