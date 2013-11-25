
package flybooking.GUI;

import flybooking.Plane;
import flybooking.Seat;
import java.awt.Component;

/**
 * A custom graphics component that can draw available seats on a plane. 
 * @author Anders
 */
public class PlaneGraphicsComponent extends Component {
    private int rows;
    private int columns;
    
    public boolean getAvalable(Seat seat) {
        return seat.getAvailability();
    }
    
    public void drawSeats(Plane plane) {
        rows = plane.getRows();
        columns = plane.getColumns();
        
        //KODE TIL AT TEGNE SÆDER HER.
    }
}
