
import java.awt.*;
import javax.swing.*;

public class RotatedEllipseDrawer extends JPanel {

    private static final int ELLIPSE_A = 150;
    private static final int ELLIPSE_B = 100;
    private static final int CENTER_X = 400;
    private static final int CENTER_Y = 300;
    private static final double ROTATION_ANGLE = Math.toRadians(30); // Rotation angle in radians

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawRotatedEllipse(g, ELLIPSE_A, ELLIPSE_B, CENTER_X, CENTER_Y, ROTATION_ANGLE);
    }

    private void drawRotatedEllipse(Graphics g, int a, int b, int x0, int y0, double angle) {
        int x = 0;
        int y = b;
        double d1 = (b * b) - (a * a * b) + (0.25 * a * a);
        plotEllipsePoints(g, x, y, x0, y0, angle);

        while ((a * a * (y - 0.5)) > (b * b * (x + 1))) {
            if (d1 < 0) {
                d1 += b * b * (2 * x + 3);
            } else {
                d1 += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
            }
            x++;
            plotEllipsePoints(g, x, y, x0, y0, angle);
        }

        double d2 = (b * b * (x + 0.5) * (x + 0.5)) + (a * a * (y - 1) * (y - 1)) - (a * a * b * b);
        while (y > 0) {
            if (d2 < 0) {
                d2 += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
                d2 += a * a * (-2 * y + 3);
            }
            y--;
            plotEllipsePoints(g, x, y, x0, y0, angle);
        }
    }

    private void plotEllipsePoints(Graphics g, int x, int y, int x0, int y0, double angle) {
        // Rotate and plot points for each quadrant
        plotRotatedPoint(g, x0 + x, y0 + y, x0, y0, angle);
        plotRotatedPoint(g, x0 - x, y0 + y, x0, y0, angle);
        plotRotatedPoint(g, x0 + x, y0 - y, x0, y0, angle);
        plotRotatedPoint(g, x0 - x, y0 - y, x0, y0, angle);
    }

    private void plotRotatedPoint(Graphics g, int px, int py, int x0, int y0, double angle) {
        // Translate point to origin
        int tx = px - x0;
        int ty = py - y0;

        // Apply rotation transformation
        int rotatedX = (int) (tx * Math.cos(angle) - ty * Math.sin(angle));
        int rotatedY = (int) (tx * Math.sin(angle) + ty * Math.cos(angle));

        // Translate point back to its original position
        g.fillRect(x0 + rotatedX, y0 + rotatedY, 1, 1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotated Ellipse Drawer");
        RotatedEllipseDrawer panel = new RotatedEllipseDrawer();
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
