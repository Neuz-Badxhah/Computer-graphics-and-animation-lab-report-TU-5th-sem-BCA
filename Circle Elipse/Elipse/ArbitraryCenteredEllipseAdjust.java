
import java.awt.*;
import javax.swing.*;

public class ArbitraryCenteredEllipseAdjust extends JPanel {

    private final int a, b;   // Major and minor axes
    private final int x0, y0; // Center of the ellipse

    public ArbitraryCenteredEllipseAdjust(int a, int b, int x0, int y0) {
        this.a = a;
        this.b = b;
        this.x0 = x0;
        this.y0 = y0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEllipse(g, a, b, x0, y0);
        drawCenterPoint(g, x0, y0);
        displayInfo(g, x0, y0, a, b);
    }

    private void drawEllipse(Graphics g, int a, int b, int x0, int y0) {
        int x = 0;
        int y = b;
        double d1 = (b * b) - (a * a * b) + (0.25 * a * a);
        plotEllipsePoints(g, x, y, x0, y0);

        // Region 1
        while ((a * a * (y - 0.5)) > (b * b * (x + 1))) {
            if (d1 < 0) {
                d1 += b * b * (2 * x + 3);
            } else {
                d1 += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
            }
            x++;
            plotEllipsePoints(g, x, y, x0, y0);
        }

        // Region 2
        double d2 = (b * b * (x + 0.5) * (x + 0.5)) + (a * a * (y - 1) * (y - 1)) - (a * a * b * b);
        while (y > 0) {
            if (d2 < 0) {
                d2 += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
                d2 += a * a * (-2 * y + 3);
            }
            y--;
            plotEllipsePoints(g, x, y, x0, y0);
        }
    }

    private void plotEllipsePoints(Graphics g, int x, int y, int x0, int y0) {
        // Plot the four symmetric points around the center (x0, y0)
        g.fillRect(x0 + x, y0 + y, 1, 1);   // Quadrant 1
        g.fillRect(x0 - x, y0 + y, 1, 1);   // Quadrant 2
        g.fillRect(x0 + x, y0 - y, 1, 1);   // Quadrant 4
        g.fillRect(x0 - x, y0 - y, 1, 1);   // Quadrant 3

       
         // Draw the fixed axes
    g.setColor(Color.GRAY); // color for axes
    g.drawLine(0, 400, 800, 400); // Fixed X-axis at y = 400
    g.drawLine(400, 0, 400, 800); // Fixed Y-axis at x = 400
    g.setColor(Color.BLACK); // color for other drawings
    }

    private void drawCenterPoint(Graphics g, int xc, int yc) {
        g.setColor(Color.RED); // the color of the center point
        g.fillOval(xc - 5, yc - 5, 10, 10);
        g.setColor(Color.BLACK); // color for ellipse
    }

    private void displayInfo(Graphics g, int xc, int yc, int a, int b) {
        String info = String.format("Center: (%d, %d) | Major Axis: %d | Minor Axis: %d", xc, yc, a, b);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        // Display it at the top-left corner with some padding
        int padding = 10;
        g.drawString(info, padding, padding + g.getFontMetrics().getAscent());
    }

    public static void main(String[] args) {
        // Example input values
        int a = 150;   // Major axis length
        int b = 100;   // Minor axis length
        int x0 = 200;  // Center X
        int y0 = 200;  // Center Y

        JFrame frame = new JFrame("Midpoint Ellipse Drawing with Arbitrary Center");
        ArbitraryCenteredEllipseAdjust panel = new ArbitraryCenteredEllipseAdjust(a, b, x0, y0);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
