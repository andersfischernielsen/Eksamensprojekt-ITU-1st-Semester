
package flybooking.GUI;

import flybooking.*;

/**
 * An interface for creating a GUI.
 * @author Anders Wind Steffensen, Christoffer Forup & Anders Fischer-Nielsen
 */
public interface GUIInterface {
    void createStartFrame();
    void createNewReservationFrame(Controller controller);
    void createEditreservationFrame();
    void createPersonAndSeatFrame();
}