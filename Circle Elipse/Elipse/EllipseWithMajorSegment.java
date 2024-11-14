import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class EllipseWithMajorSegment extends JPanel {

    private final int radiusX, radiusY;   // Horizontal and vertical radii
    private final int centerX, centerY;
    private final String ellipseName;     // Name of the ellipse

    public EllipseWithMajorSegment(int centerX, int centerY, int radiusX, int radiusY, String ellipseName) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.centerX = centerX;
        this.centerY = centerY;
        this.ellipseName = ellipseName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEllipseName(g);   // Draw the ellipse name
        drawEllipse(g, centerX, centerY, radiusX, radiusY);
        drawMajorSegment(g, centerX, centerY, radiusX, radiusY);
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

    // Method to draw the major segment of the ellipse
    private void drawMajorSegment(Graphics g, int x, int y, int rx, int ry) {
        // Define the chord for the major segment
        int chordStartX = x - rx;  // Leftmost point of the ellipse (on the major axis)
        int chordEndX = x + rx;    // Rightmost point of the ellipse (on the major axis)
        int chordY = y;            // Chord is drawn horizontally at the center of the ellipse (along the major axis)

        // Draw the chord (horizontal line)
        g.drawLine(chordStartX, chordY, chordEndX, chordY);

        // Fill the major segment (the top half of the ellipse)
        // Use Graphics2D to fill the shape
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(200, 200, 255));  // Light blue color for the segment

        // Draw the major segment as a filled arc
        g2d.fillArc(x - rx, y - ry, 2 * rx, 2 * ry, 0, 180);  // Fill the top half (arc from 0 to 180 degrees)
        
        g2d.setColor(Color.BLACK);  // Reset the color to black for outline
        g.drawArc(x - rx, y - ry, 2 * rx, 2 * ry, 0, 180);   // Outline the top half of the ellipse
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

            JFrame frame = new JFrame("Ellipse with Major Segment");
            EllipseWithMajorSegment panel = new EllipseWithMajorSegment(centerX, centerY, radiusX, radiusY, "Major Segment Ellipse");
            frame.add(panel);
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
