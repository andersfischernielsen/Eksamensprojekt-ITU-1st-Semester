
package flybooking.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import flybooking.*;
import java.net.URL;
import net.miginfocom.swing.*;

/**
 * Create the first frame in the booking system.
 *
 * @author Anders Wind Steffensen, Chris Forup & Anders Fischer-Nielsen
 */
public class StartFrame extends JFrame {

    Container content;
    Container empty;
    Container innerCont;
    JPanel buttonPanel;
    JButton newBookingButton;
    JButton editBookingButton;
    private ControllerInterface controller;
    private static StartFrame instance = null;

    public static void main(String[] args)
    {
        StartFrame.getInstance();
    }

    /**
     * Initialize the controller and database, draw the frame and add
     * ActionListeners.
     *
     * @throws HeadlessException If the frame is initialized on a computer with
     * no keyboard, monitor or mouse.
     */
    private StartFrame() throws HeadlessException
    {
        controller = Controller.getInstance();
        drawFrame();
        addActionListeners();
    }   

    /**
     * Get the singleton instance of the frame.
     *
     * @return The instance of the frame.
     */
    public static StartFrame getInstance()
    {
        if (instance == null) {
            instance = new StartFrame();
        }

        instance.setVisible(true);
        return instance;
    }

    /**
     * Draw the frame.
     */
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
        newBookingButton = new JButton("New Reservation");
        newBookingButton.setDefaultCapable(true);
        editBookingButton = new JButton("Edit Reservation");
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
                //Create a new empty reservation in the controller.
                controller.createReservation();
                
                //Initialize the NewReservationFrame.
                NewReservationFrame.getInstance();
            }
        });

        editBookingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Initialize the EditReservationFrame.
                EditReservationFrame.getInstance(controller);
            }
        });
    }
}
