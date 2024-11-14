
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class MidpointCircleDrawing extends JPanel {

    private final int x_center, y_center, radius;

    public MidpointCircleDrawing(int x_center, int y_center, int radius) {
        this.x_center = x_center;
        this.y_center = y_center;
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g, x_center, y_center, radius);
        drawCenterPoint(g, x_center, y_center);
        displayInfo(g, x_center, y_center, radius);
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
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);
        g.fillRect(xc + y, yc + x, 1, 1);
        g.fillRect(xc - y, yc + x, 1, 1);
        g.fillRect(xc + y, yc - x, 1, 1);
        g.fillRect(xc - y, yc - x, 1, 1);

        // Draw axis
        g.setColor(Color.GRAY); // color for axes
        g.drawLine(0, 400, 800, 400);
        g.drawLine(400, 0, 400, 800);
        g.setColor(Color.BLACK); // color for other drawings
    }

    private void drawCenterPoint(Graphics g, int xc, int yc) {
        g.setColor(Color.RED); // the color of the center point
        g.fillOval(xc - 5, yc - 5, 10, 10);
        g.setColor(Color.BLACK); // color for circle
    }

    private void displayInfo(Graphics g, int xc, int yc, int r) {

        String info = String.format("Center: (%d, %d) | Radius: %d", xc, yc, r);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        // Determine the position to display the text
        // For example, display it at the top-left corner with some padding
        int padding = 10;
        g.drawString(info, padding, padding + g.getFontMetrics().getAscent());

    }

    public static void main(String[] args) {
        try (// Taking user input
                Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter x_center: ");
            int x_center = sc.nextInt();
            System.out.print("Enter y_center: ");
            int y_center = sc.nextInt();
            System.out.print("Enter radius: ");
            int radius = sc.nextInt();

            JFrame frame = new JFrame("Midpoint Circle Drawing");
            MidpointCircleDrawing panel = new MidpointCircleDrawing(x_center, y_center, radius);
            frame.add(panel);
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
