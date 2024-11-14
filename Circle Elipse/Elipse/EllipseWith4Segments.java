import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class EllipseWith4Segments extends JPanel {

    private final int radiusX, radiusY;   // Horizontal and vertical radii
    private final int centerX, centerY;
    private final int[][] segmentPoints;  // Array to store segment points
    private final String ellipseName;     // Name of the ellipse

    public EllipseWith4Segments(int centerX, int centerY, int radiusX, int radiusY, String ellipseName) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.centerX = centerX;
        this.centerY = centerY;
        this.ellipseName = ellipseName;
        this.segmentPoints = new int[4][2];  // To store x and y coordinates of 4 segments
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEllipseName(g);   // Draw the ellipse name
        drawEllipse(g, centerX, centerY, radiusX, radiusY);
        drawSegments(g, centerX, centerY, radiusX, radiusY);
        drawTable(g);  // Draw the table of coordinates
    }

    // Method to draw the name of the ellipse
    private void drawEllipseName(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(ellipseName, centerX - 50, 50);  // Draw the ellipse name above the ellipse
    }

    // Method to draw the ellipse
    private void drawEllipse(Graphics g, int x, int y, int rx, int ry) {
        g.drawOval(x - rx, y - ry, 2 * rx, 2 * ry);  // Ellipse centered at (x, y)
    }

    // Method to draw the segments (lines from center to circumference of the ellipse) and fill with symbols
    private void drawSegments(Graphics g, int x, int y, int rx, int ry) {
        for (int i = 0; i < 4; i++) {
            double angle = Math.toRadians(i * 90);  // 4 segments: 90 degrees each
            int endX = (int) (x + rx * Math.cos(angle));
            int endY = (int) (y + ry * Math.sin(angle));
            g.drawLine(x, y, endX, endY);  // Line from center to point on circumference of the ellipse

            // Store the segment points (endX, endY)
            segmentPoints[i][0] = endX;
            segmentPoints[i][1] = endY;
        }
    }

    // Method to draw the table of coordinates
    private void drawTable(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Center Point: (" + centerX + ", " + centerY + ")", 10, 350);  // Display center point

        // Column headers
        g.drawString("Segment", 10, 370);
        g.drawString("X", 80, 370);
        g.drawString("Y", 130, 370);

        // Draw the segment points in tabular format
        for (int i = 0; i < 4; i++) {
            g.drawString("Segment " + (i + 1), 10, 390 + (i * 20));
            g.drawString(Integer.toString(segmentPoints[i][0]), 80, 390 + (i * 20));  // X coordinate
            g.drawString(Integer.toString(segmentPoints[i][1]), 130, 390 + (i * 20)); // Y coordinate
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the center x-coordinate of the ellipse: ");
            int centerX = sc.nextInt();
            System.out.print("Enter the center y-coordinate of the ellipse: ");
            int centerY = sc.nextInt();
            System.out.print("Enter the horizontal radius (radiusX) of the ellipse: ");
            int radiusX = sc.nextInt();
            System.out.print("Enter the vertical radius (radiusY) of the ellipse: ");
            int radiusY = sc.nextInt();

            JFrame frame = new JFrame("Ellipse with 4 Segments");
            EllipseWith4Segments panel = new EllipseWith4Segments(centerX, centerY, radiusX, radiusY, "4 Segment Ellipse");
            frame.add(panel);
            frame.setSize(400, 600);  // Adjust height to accommodate the table for 4 segments
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
