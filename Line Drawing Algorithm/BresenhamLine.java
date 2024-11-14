import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class BresenhamLine extends JPanel {

    private int x0, y0, x1, y1;

    public BresenhamLine(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the axes
        drawAxes(g);
        
        // Draw the line using Bresenham's algorithm
        drawLine(g, x0, y0, x1, y1);
        
        // Draw the starting and ending points
        drawPoint(g, x0, y0, Color.GREEN, "Starting Point(" + x0 + ", " + y0 + ")"); // Start point with coordinates
        drawPoint(g, x1, y1, Color.RED, "Ending Point (" + x1 + ", " + y1 + ")"); // End point with coordinates
    }

    // Method to draw the axes
    private void drawAxes(Graphics g) {
        // Draw x-axis
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawString("X", getWidth() - 20, getHeight() / 2 - 5);

        // Draw y-axis
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        g.drawString("Y", getWidth() / 2 + 5, 20);
        
        // Draw origin point
        g.drawString("O", getWidth() / 2 + 5, getHeight() / 2 + 15);
    }

    // Method to draw a line using Bresenham's Algorithm
    private void drawLine(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            // Translate the point for the center origin
            int translatedX = x0 + getWidth() / 2;
            int translatedY = getHeight() / 2 - y0; // Invert y for screen coordinates
            
            g.drawLine(translatedX, translatedY, translatedX, translatedY); // Draw the point

            if (x0 == x1 && y0 == y1) break;

            e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    // Method to draw a point with a label
    private void drawPoint(Graphics g, int x, int y, Color color, String label) {
        int translatedX = x + getWidth() / 2; // Translate for the center origin
        int translatedY = getHeight() / 2 - y; // Invert y for screen coordinates

        g.setColor(color);
        g.fillOval(translatedX - 3, translatedY - 3, 6, 6); // Draw point
        g.setColor(Color.BLACK);
        g.drawString(label, translatedX + 5, translatedY);
    }

    public static void main(String[] args) {
        int x0;
        int y0;
        int x1;
        int y1;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter x0: ");
            x0 = sc.nextInt();
            System.out.print("Enter y0: ");
            y0 = sc.nextInt();
            System.out.print("Enter x1: ");
            x1 = sc.nextInt();
            System.out.print("Enter y1: ");
            y1 = sc.nextInt();
        }

        JFrame frame = new JFrame("Bresenham Line Drawing");
        BresenhamLine line = new BresenhamLine(x0, y0, x1, y1);
        frame.add(line);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
