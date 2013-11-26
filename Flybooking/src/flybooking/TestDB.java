package flybooking;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

/**
 * A test to see if the database is working.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public class TestDB implements ActionListener
{

    public static String name;
    public static String login;
    public static String pass;
    public static JTextField nameField, loginField, connectionField;
    public static JPasswordField passField;

    public static void main(String[] args) throws SQLException
    {
        TestDB tester = new TestDB();
        tester.createFrame();
    }

    public static void testDB(String databaseName, String databaseLogin, String databasePassword)
    {
        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/" + databaseName, databaseLogin, databasePassword);
            connectionField.setText("Forbindelse OK!");
        }catch (SQLException e)
        {
            connectionField.setText("Ingen forbindelse!");
        }
    }


    public void createFrame()
    {
        JFrame frame = new JFrame("Databasetester");
        Container content = frame.getContentPane();
        Container topContainer, centerContainer, bottomContainer, westContainer, eastContainer, connContainer;

        JLabel nameLabel, loginLabel, passLabel;
        westContainer = new Container();
        westContainer.setLayout(new BoxLayout(westContainer, BoxLayout.Y_AXIS));

        nameLabel = new JLabel("Databasenavn:");
        nameField = new JTextField();
        nameField.setColumns(10);
        topContainer = new Container();
        topContainer.add(nameLabel);
        topContainer.add(nameField);
        topContainer.setLayout(new FlowLayout());
        westContainer.add(topContainer);

        loginLabel = new JLabel("Login");
        loginField = new JTextField();
        loginField.setColumns(10);
        centerContainer = new Container();
        centerContainer.add(loginLabel);
        centerContainer.add(loginField);
        centerContainer.setLayout(new FlowLayout());
        westContainer.add(centerContainer, BorderLayout.WEST);

        passLabel = new JLabel("Password");
        passField = new JPasswordField();
        passField.setColumns(10);
        bottomContainer = new Container();
        bottomContainer.add(passLabel);
        bottomContainer.add(passField);
        bottomContainer.setLayout(new FlowLayout());
        westContainer.add(bottomContainer, BorderLayout.WEST);

        eastContainer = new Container();
        eastContainer.setLayout(new BoxLayout(eastContainer, BoxLayout.Y_AXIS));
        JButton button = new JButton("Test");
        eastContainer.add(button);
        button.addActionListener(this);

        connContainer = new Container();
        eastContainer.add(connContainer);
        connContainer.setLayout(new FlowLayout());
        connectionField = new JTextField("Skriv navn, login osv.");
        connContainer.add(connectionField);

        content.add(eastContainer, BorderLayout.EAST);
        content.add(westContainer, BorderLayout.WEST);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        testDB(nameField.getText(), loginField.getText(), passField.getText());
    }
}
