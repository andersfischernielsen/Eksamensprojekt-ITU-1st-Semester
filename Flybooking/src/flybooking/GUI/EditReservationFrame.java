
package flybooking.GUI;

import flybooking.ControllerInterface;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.event.*;

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
        
        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Search Button Pressed");
            }
        });
        
        searchButton.setDefaultCapable(true);
        searchButton.setAlignmentX(RIGHT_ALIGNMENT);
        bottomRight.add(filler);
        bottomRight.add(searchButton);
        bottomRight.setLayout(new FlowLayout());

        topContent.add(topLeft);
        topContent.add(topRight);
        topContent.add(bottomLeft);
        topContent.add(bottomRight);

        content.add(topContent, BorderLayout.NORTH);
    }

    private void createBottomContent()
    {
        bottomContent = new Container();
        bottomContent.setPreferredSize(new Dimension(300, 400));

        content.add(bottomContent, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args)
    {
        EditReservationFrame.getInstance(null);
    }
}
