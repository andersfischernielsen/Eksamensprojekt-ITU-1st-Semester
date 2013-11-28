
package flybooking.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import flybooking.*;
import java.sql.SQLException;

/**
 *
 * @author Anders
 */
public class StartFrame extends JFrame {

    Container content;
    Container empty;
    Container innerCont;
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

    public static StartFrame getInstance() {
        if (instance == null) {
            instance = new StartFrame();
        }
        
        instance.setVisible(true);
        return instance;
    }
    
    private void drawFrame()
    {
        setTitle("Flybooking");
        this.setDefaultCloseOperation(StartFrame.EXIT_ON_CLOSE);
        content = this.getContentPane();
        innerCont = new Container();
        empty = new Container();
        content.add(innerCont);
        innerCont.setLayout(new FlowLayout());
        newBookingButton = new JButton("New booking");
        editBookingButton = new JButton("Edit booking");
        innerCont.add(newBookingButton);
        innerCont.add(editBookingButton);

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
                } catch (SQLException ex) {}
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
