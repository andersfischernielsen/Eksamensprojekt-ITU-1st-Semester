
package flybooking.GUI;

import java.awt.*;
import javax.swing.*;

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

    public StartFrame() throws HeadlessException
    {
        setTitle("Flybooking");
        content = this.getContentPane();
        innerCont = new Container();
        empty = new Container();
        content.add(innerCont);
        innerCont.setLayout(new FlowLayout());
        newBookingButton = new JButton("New booking");
        editBookingButton = new JButton("Edit booking");
        innerCont.add(newBookingButton);
        innerCont.add(editBookingButton);

        this.pack();
        this.setVisible(true);
    }
}
