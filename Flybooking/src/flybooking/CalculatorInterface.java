
package flybooking;

import java.text.DateFormat;
import java.util.Date;

/**
 * An interface for creating a converter and calculator.
 * @author Anders Fischer-Nielsen
 */
public interface CalculatorInterface {

    /**
     * Add two numbers and get the result.
     * @param n1 The first number to add.
     * @param n2 The second number to add.
     * @return The sum of the two numbers.
     */
    int add(int n1, int n2);

    /**
     * Subtract two numbers and get the result.
     * @param n1 The first number to subtract.
     * @param n2 The second number to subtract.
     * @return The sum of the two numbers.
     */
    int subtract(int n1, int n2);

    /**
     * Multiply two numbers and get the result.
     * @param n1 The first number to multiply.
     * @param n2 The second number to multiply.
     * @return The sum of the two numbers.
     */
    int multiply(int n1, int n2);
    
    /**
     * Convert a given date into a string.
     * @param date The date to parse.
     * @return The parsed Date as a string.
     */
    String convertDate(Date date);
}
