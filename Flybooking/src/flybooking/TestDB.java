
package flybooking;

import java.sql.*;

/**
 *
 * @author Anders
 */
public class TestDB {

    public static void main(String[] args)
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql.itu.dk:3306/AACPlaneDB", "AAC", "AACDB");
            System.out.println("OK!");
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't connect!", e);
        }
    }
}
