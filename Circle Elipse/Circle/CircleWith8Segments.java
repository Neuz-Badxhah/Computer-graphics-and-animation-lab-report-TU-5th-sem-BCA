
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class CircleWith8Segments extends JPanel {

    private final int radius;
    private final int centerX, centerY;
    private final int[][] sectorPoints;  // Array to store sector points
    private final String circleName;     // Name of the circle
  
    public CircleWith8Segments(int centerX, int centerY, int radius, String circleName) {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
        this.circleName = circleName;
        this.sectorPoints = new int[8][2];  // To store x and y coordinates of 8 sectors
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircleName(g);   // Draw the circle name
        drawCircle(g, centerX, centerY, radius);
        drawSectors(g, centerX, centerY, radius);
        drawTable(g);  // Draw the table of coordinates
    }

    // Method to draw the name of the circle
    private void drawCircleName(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(circleName, centerX - 50, 50);  // Draw the circle name above the circle
    }

    // Method to draw the circle
    private void drawCircle(Graphics g, int x, int y, int r) {
        g.drawOval(x - r, y - r, 2 * r, 2 * r);  // Circle centered at (x, y)
    }

    // Method to draw the sectors (lines from center to circumference) and fill with symbols
    private void drawSectors(Graphics g, int x, int y, int r) {
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * 45);  // 8 sectors: 45 degrees each
            int endX = (int) (x + r * Math.cos(angle));
            int endY = (int) (y + r * Math.sin(angle));
            g.drawLine(x, y, endX, endY);  // Line from center to point on circumference

           
            // Store the sector points (endX, endY)
            sectorPoints[i][0] = endX;
            sectorPoints[i][1] = endY;
        }
    }

    // Method to draw the table of coordinates
    private void drawTable(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Center Point: (" + centerX + ", " + centerY + ")", 10, 350);  // Display center point

        // Column headers
        g.drawString("Sector", 10, 370);
        g.drawString("X", 80, 370);
        g.drawString("Y", 130, 370);

        // Draw the sector points in tabular format
        for (int i = 0; i < 8; i++) {
            g.drawString("Sector " + (i + 1), 10, 390 + (i * 20));
            g.drawString(Integer.toString(sectorPoints[i][0]), 80, 390 + (i * 20));  // X coordinate
            g.drawString(Integer.toString(sectorPoints[i][1]), 130, 390 + (i * 20)); // Y coordinate
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the center x-coordinate of the circle: ");
            int centerX = sc.nextInt();
            System.out.print("Enter the center y-coordinate of the circle: ");
            int centerY = sc.nextInt();
            System.out.print("Enter the radius of the circle: ");
            int radius = sc.nextInt();

            JFrame frame = new JFrame("Circle with 8 Segments");
            CircleWith8Segments panel = new CircleWith8Segments(centerX, centerY, radius, "8 Segment Circle");
            frame.add(panel);
            frame.setSize(400, 600);  // Adjust height to accommodate the table for 8 sectors
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
