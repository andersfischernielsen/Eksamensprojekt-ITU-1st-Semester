package flybooking.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;

/**
 *
 * @author Anders
 */
public class EditReservationFrame extends JFrame {

    Container content;
    Container innerCont;
    Container innerRes;
    JButton searchButton;
    JLabel label;
    JLabel label2;
    JTextField textField;
    JTextField textField2;

    public EditReservationFrame() throws HeadlessException {
        setTitle("Rediger Reservation");
        content = this.getContentPane();
        innerCont = new Container();
        innerRes = new Container();
        content.add(innerRes);
        content.add(innerCont);
        
        innerCont.setLayout(new FlowLayout());
        
        label = new JLabel("Reservation ID:");
        innerCont.add(label);
        label2 = new JLabel("CPR #:");
        innerCont.add(label2);
        
        searchButton = new JButton("Search");
        innerCont.add(searchButton);
        
        textField = new JTextField("Reservation ID:", 10);
        innerCont.add(textField);
        textField2 = new JTextField("CPR #:", 10);
        innerCont.add(textField2);
                
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new EditReservationFrame();
    }
}