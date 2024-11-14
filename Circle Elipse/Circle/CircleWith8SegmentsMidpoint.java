
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class CircleWith8SegmentsMidpoint extends JPanel {

    private final int radius;
    private final int centerX, centerY;
    private final int[][] sectorPoints;  // Array to store sector points
    private final String circleName;     // Name of the circle
    private final String[] symbols = {"--", "++", "**", "%%", "//", "\\\\", "~~", "~~"}; // Symbols for each segment

    public CircleWith8SegmentsMidpoint(int centerX, int centerY, int radius, String circleName) {
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
        drawTable(g);  // Draw the table of coordinates
    }

    // Method to draw the name of the circle
    private void drawCircleName(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(circleName, centerX - 50, 50);  // Draw the circle name above the circle
    }

    // Method to draw the circle using the midpoint circle algorithm
    private void drawCircle(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 1 - r;

        // Plot the initial points
        plotCirclePoints(g, xc, yc, x, y);

        while (x < y) {
            x++;
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                y--;
                d += 2 * (x - y) + 1;
            }
            plotCirclePoints(g, xc, yc, x, y);
        }
    }

    // Method to plot the circle points and fill segments with symbols
    private void plotCirclePoints(Graphics g, int xc, int yc, int x, int y) {
        // Draw and fill each segment
        drawAndFillSegment(g, xc, yc, x, y, 0); // Quadrant 1
        drawAndFillSegment(g, xc, yc, -x, y, 1); // Quadrant 2
        drawAndFillSegment(g, xc, yc, -x, -y, 2); // Quadrant 3
        drawAndFillSegment(g, xc, yc, x, -y, 3); // Quadrant 4
        drawAndFillSegment(g, xc, yc, y, x, 4); // Quadrant 5
        drawAndFillSegment(g, xc, yc, -y, x, 5); // Quadrant 6
        drawAndFillSegment(g, xc, yc, -y, -x, 6); // Quadrant 7
        drawAndFillSegment(g, xc, yc, y, -x, 7); // Quadrant 8
    }

    // Method to draw and fill a segment with symbols
    private void drawAndFillSegment(Graphics g, int cx, int cy, int x, int y, int sector) {
        g.drawLine(cx, cy, cx + x, cy + y); // Draw the line from center to the circumference
        sectorPoints[sector][0] = cx + x; // Store end point
        sectorPoints[sector][1] = cy + y; // Store end point

        // Fill the segment with the corresponding symbol
        fillSegmentWithSymbols(g, cx, cy, cx + x, cy + y, symbols[sector]);
    }

    // Method to fill the segment with symbols
    private void fillSegmentWithSymbols(Graphics g, int cx, int cy, int ex, int ey, String symbol) {
        int symbolWidth = g.getFontMetrics().stringWidth(symbol);
        int symbolHeight = g.getFontMetrics().getHeight();

        // Calculate positions to fill the segment with symbols
        int segmentCenterX = (cx + ex) / 2;
        int segmentCenterY = (cy + ey) / 2;

        for (int i = -radius / 4; i < radius / 4; i += symbolWidth) {
            for (int j = -radius / 4; j < radius / 4; j += symbolHeight) {
                g.drawString(symbol, segmentCenterX + i, segmentCenterY + j);
            }
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

            JFrame frame = new JFrame("Circle with 8 Segments (Midpoint Circle Algorithm)");
            CircleWith8SegmentsMidpoint panel = new CircleWith8SegmentsMidpoint(centerX, centerY, radius, "8 Segment Circle");
            frame.add(panel);
            frame.setSize(400, 600);  // Adjust height to accommodate the table for 8 sectors
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
