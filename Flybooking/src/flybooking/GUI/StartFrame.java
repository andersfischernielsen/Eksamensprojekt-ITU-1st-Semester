
package flybooking.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import flybooking.*;
import java.net.URL;
import java.sql.SQLException;
import net.miginfocom.swing.*;

/**
 *
 * @author Anders
 */
public class StartFrame extends JFrame {

    Container content;
    Container empty;
    Container innerCont;
    JPanel buttonPanel;
    JButton newBookingButton;
    JButton editBookingButton;
    private static DatabaseInterface database;
    private static ControllerInterface controller;
    private static StartFrame instance = null;

    public static void main(String[] args)
    {
        database = ProgramStorage.getInstance();
        controller = Controller.getInstance(database);
        StartFrame.getInstance();
    }

    private StartFrame() throws HeadlessException
    {
        drawFrame();
        addActionListeners();
    }

    public static StartFrame getInstance()
    {
        if (instance == null) {
            instance = new StartFrame();
        }

        instance.setVisible(true);
        return instance;
    }

    private void drawFrame()
    {
        setTitle("Flight Booking");
        setDefaultCloseOperation(StartFrame.EXIT_ON_CLOSE);

        //Load graphics.
        URL resource = ClassLoader.getSystemResource("Splash.png");
        JLabel background = new JLabel(new ImageIcon(resource));
        add(background);
        background.setLayout(new BorderLayout());

        //Set up the bottom panel.
        JPanel buttonPanelCont = new JPanel();
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new MigLayout("", "[] 50 []", ""));

        //Initialize the buttons.
        newBookingButton = new JButton("New Booking");
        newBookingButton.setDefaultCapable(true);
        editBookingButton = new JButton("Edit Booking");
        newBookingButton.setMinimumSize(new Dimension(200, 20));
        editBookingButton.setMinimumSize(new Dimension(200, 20));

        buttonPanel.add(newBookingButton);
        buttonPanel.add(editBookingButton);
        buttonPanelCont.add(buttonPanel);
	background.add(buttonPanelCont, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(newBookingButton);
        setResizable(false);
        pack();
        setVisible(true);
    }

    private void addActionListeners()
    {
        newBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    NewReservationFrame.getInstance();
                } catch (SQLException ex) {
                }
            }
        });

        editBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                EditReservationFrame.getInstance(controller);
            }
        });
    }
}
