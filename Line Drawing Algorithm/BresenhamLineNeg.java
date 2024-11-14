import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class BresenhamLineNeg extends JPanel {

    private int x0, y0, x1, y1;

    public BresenhamLineNeg(int x0, int y0, int x1, int y1) {
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
        drawPoint(g, x0, y0, Color.GREEN, "Starting Point (" + x0 + ", " + y0 + ")"); // Start point with coordinates
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
        boolean isSteep = dy > dx;

        // If the line is steep, swap x and y
        if (isSteep) {
            int temp = dx;
            dx = dy;
            dy = temp;
        }

        int err = dx / 2;

        for (int i = 0; i <= dx; i++) {
            // Translate for the center origin
            int translatedX = x0 + getWidth() / 2;
            int translatedY = getHeight() / 2 - y0; // Invert y for screen coordinates

            // Plot the point
            if (isSteep) {
                g.drawLine(translatedY, translatedX, translatedY, translatedX); // Swap back when plotting
            } else {
                g.drawLine(translatedX, translatedY, translatedX, translatedY);
            }

            // Check and update the error term
            err -= dy;
            if (err < 0) {
                if (isSteep) {
                    x0 += sx; // Adjust x for steep lines
                } else {
                    y0 += sy; // Adjust y for shallow lines
                }
                err += dx;
            }

            // Move along the line
            if (isSteep) {
                y0 += sy; // Move y for steep lines
            } else {
                x0 += sx; // Move x for shallow lines
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
        g.drawString(label, translatedX + 5, translatedY); // Draw label with coordinates
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

        JFrame frame = new JFrame("Bresenham Line Drawing with Negative Slopes");
        BresenhamLineNeg line = new BresenhamLineNeg(x0, y0, x1, y1);
        frame.add(line);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
