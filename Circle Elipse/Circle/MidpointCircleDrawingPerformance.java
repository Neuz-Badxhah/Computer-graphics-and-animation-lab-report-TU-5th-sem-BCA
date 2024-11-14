
import java.awt.*;
import javax.swing.*;

public class MidpointCircleDrawingPerformance extends JPanel {

    private final int radius;

    public MidpointCircleDrawingPerformance(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dynamically calculate the center of the panel
        int x_center = getWidth() / 2;
        int y_center = getHeight() / 2;

        // Draw the axes before drawing the circle
        drawAxes(g, x_center, y_center);

        // Draw the circle using the midpoint algorithm
        drawCircle(g, x_center, y_center, radius);

        // Mark the center of the circle
        drawCenterPoint(g, x_center, y_center);

        // Display center and radius info
        displayInfo(g, x_center, y_center, radius);
    }

    private void drawCircle(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 1 - r; // Initial decision parameter

        // Plot initial points in all octants
        plotCirclePoints(g, xc, yc, x, y);

        while (x < y) {
            x++;
            if (d < 0) {
                d += 2 * x + 1; // Only change in x
            } else {
                y--;
                d += 2 * (x - y) + 1; // Change in both x and y
            }
            plotCirclePoints(g, xc, yc, x, y); // Plot points for each iteration
        }
    }

    private void plotCirclePoints(Graphics g, int xc, int yc, int x, int y) {
        // Plot points in all eight octants
        g.fillRect(xc + x, yc + y, 2, 2);  // Octant 1
        g.fillRect(xc - x, yc + y, 2, 2);  // Octant 4
        g.fillRect(xc + x, yc - y, 2, 2);  // Octant 8
        g.fillRect(xc - x, yc - y, 2, 2);  // Octant 5
        g.fillRect(xc + y, yc + x, 2, 2);  // Octant 2
        g.fillRect(xc - y, yc + x, 2, 2);  // Octant 3
        g.fillRect(xc + y, yc - x, 2, 2);  // Octant 7
        g.fillRect(xc - y, yc - x, 2, 2);  // Octant 6
    }

    // Draw axes dynamically centered in the panel
    private void drawAxes(Graphics g, int xc, int yc) {
        g.setColor(Color.GRAY);

        // Draw X and Y axes relative to the center of the panel
        g.drawLine(0, yc, getWidth(), yc);  // X-axis
        g.drawLine(xc, 0, xc, getHeight()); // Y-axis

        g.setColor(Color.BLACK); // Reset the color for further drawings
    }

    private void drawCenterPoint(Graphics g, int xc, int yc) {
        g.setColor(Color.RED); // The color of the center point
        g.fillOval(xc - 5, yc - 5, 10, 10); // Draw a small red circle at the center
        g.setColor(Color.BLACK); // Reset color for the circle
    }

    private void displayInfo(Graphics g, int xc, int yc, int r) {
        String info = String.format("Center: (%d, %d) | Radius: %d", xc, yc, r);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        // Display the text at the top-left corner with some padding
        int padding = 10;
        g.drawString(info, padding, padding + g.getFontMetrics().getAscent());
    }

    public static void main(String[] args) {
        // Example radius for the circle
        int radius = 100;

        JFrame frame = new JFrame("Midpoint Circle Drawing Performance");
        MidpointCircleDrawingPerformance panel = new MidpointCircleDrawingPerformance(radius);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }
}
