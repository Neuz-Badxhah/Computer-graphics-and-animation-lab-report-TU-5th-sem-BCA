
import java.awt.*;
import javax.swing.*;

public class MidpointCircleCenteredAtOrigin extends JPanel {

    private final int radius;

    public MidpointCircleCenteredAtOrigin(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        drawCircle(g, xCenter, yCenter, radius);
        drawCenterPoint(g, xCenter, yCenter);
        displayInfo(g, xCenter, yCenter, radius);
    }

    private void drawCircle(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 1 - r; // Initial decision parameter

        // Plot initial points
        plotCirclePoints(g, xc, yc, x, y);

        while (x < y) {
            x++;
            if (d < 0) {
                d += 2 * x + 1; // Only change in x
            } else {
                y--;
                d += 2 * (x - y) + 1; // Change in both x and y
            }
            plotCirclePoints(g, xc, yc, x, y);
        }
    }

    private void plotCirclePoints(Graphics g, int xc, int yc, int x, int y) {
        // Plot points in all eight octants
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);
        g.fillRect(xc + y, yc + x, 1, 1);
        g.fillRect(xc - y, yc + x, 1, 1);
        g.fillRect(xc + y, yc - x, 1, 1);
        g.fillRect(xc - y, yc - x, 1, 1);

        // Draw the axes
        g.setColor(Color.GRAY); // Color for axes
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2); // X-axis
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight()); // Y-axis
        g.setColor(Color.BLACK); // Reset color for other drawings
    }

    private void drawCenterPoint(Graphics g, int xc, int yc) {
        g.setColor(Color.RED); // The color of the center point
        g.fillOval(xc - 5, yc - 5, 10, 10);
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
        int radius = 100; // Example radius

        JFrame frame = new JFrame("Midpoint Circle Drawing Centered at Origin");
        MidpointCircleCenteredAtOrigin panel = new MidpointCircleCenteredAtOrigin(radius);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
