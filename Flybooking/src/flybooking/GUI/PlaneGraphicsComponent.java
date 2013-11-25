package flybooking.GUI;

import flybooking.Plane;
import flybooking.Seat;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * A custom graphics component that can draw available seats on a plane.
 *
 * @author Anders
 */
public class PlaneGraphicsComponent extends JComponent
{

    private final int unit = 12, padding = 4;
    private final Plane planeToDraw;
    private int rows, cols;

    public PlaneGraphicsComponent(Plane planeToDraw)
    {
        this.planeToDraw = planeToDraw;
        rows = planeToDraw.getRows();
        cols = planeToDraw.getColumns();
        repaint();
    }

    public boolean getAvalable(Seat seat)
    {
        return seat.getAvailability();
    }

    @Override
    public void paint(Graphics g)
    {
        g.drawRect(0, 0, cols * unit + 49, rows * unit + 49);

        for (int i = 0; i < cols; i++)
        {
            for (int j = 0; j < rows; j++)
            {
                if (planeToDraw.getSeatAvailability(planeToDraw.SeatIDGenerator(i, j)))
                {
                    g.setColor(Color.GREEN);
                }
                else
                {
                    g.setColor(Color.RED);
                }

                g.fillRect((i * unit) + padding + 25, (j * unit) + padding + 25, unit - padding, unit - padding);
            }
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(cols * unit + 50, rows * unit + 50);
    }

    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
}
