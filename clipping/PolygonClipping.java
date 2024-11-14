import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class PolygonClipping extends JPanel {

    // Clipping window boundaries
    int left = 100, right = 300, top = 100, bottom = 300;
    List<Point> originalPolygon = new ArrayList<>();
    List<Point> clippedPolygon;

    public PolygonClipping() {
        // Define the polygon vertices (Example: Concave and Convex)
        originalPolygon.add(new Point(50, 150));
        originalPolygon.add(new Point(150, 50));
        originalPolygon.add(new Point(250, 100));
        originalPolygon.add(new Point(200, 200));

        // Perform clipping
        clippedPolygon = sutherlandHodgmanClip(originalPolygon, left, right, top, bottom);

        // Set the panel's background color
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the left side (Original polygon)
        g2d.setColor(Color.BLACK);
        g2d.drawString("Original Polygon", 50, 50);
        g2d.setColor(Color.RED);
        drawPolygon(g2d, originalPolygon, 0);  // No offset for the original polygon

        // Draw the clipping window for original polygon
        g2d.setColor(Color.BLACK);
        g2d.drawRect(left, top, right - left, bottom - top);

        // Draw the right side (Clipped polygon)
        g2d.translate(350, 0);  // Shift to the right for the clipped polygon
        g2d.setColor(Color.BLACK);
        g2d.drawString("Clipped Polygon", 50, 50);
        g2d.setColor(Color.GREEN);
        drawPolygon(g2d, clippedPolygon, 0);  // No offset for the clipped polygon

        // Draw the clipping window for the clipped polygon
        g2d.setColor(Color.BLACK);
        g2d.drawRect(left, top, right - left, bottom - top);
    }

    // Sutherland-Hodgman Polygon Clipping Algorithm
    private List<Point> sutherlandHodgmanClip(List<Point> subjectPolygon, int left, int right, int top, int bottom) {
        List<Point> clippedPolygon = new ArrayList<>(subjectPolygon);
        clippedPolygon = clipAgainstEdge(clippedPolygon, left, top, right, top);     // Top edge
        clippedPolygon = clipAgainstEdge(clippedPolygon, right, top, right, bottom); // Right edge
        clippedPolygon = clipAgainstEdge(clippedPolygon, right, bottom, left, bottom); // Bottom edge
        clippedPolygon = clipAgainstEdge(clippedPolygon, left, bottom, left, top);   // Left edge
        return clippedPolygon;
    }

    // Clip polygon against a specific edge
    private List<Point> clipAgainstEdge(List<Point> polygon, int x1, int y1, int x2, int y2) {
        List<Point> clipped = new ArrayList<>();
        Point prevVertex = polygon.get(polygon.size() - 1);

        for (Point currentVertex : polygon) {
            boolean prevInside = isInside(prevVertex, x1, y1, x2, y2);
            boolean currInside = isInside(currentVertex, x1, y1, x2, y2);

            if (currInside) {
                if (!prevInside) {
                    clipped.add(intersect(prevVertex, currentVertex, x1, y1, x2, y2));
                }
                clipped.add(currentVertex);
            } else if (prevInside) {
                clipped.add(intersect(prevVertex, currentVertex, x1, y1, x2, y2));
            }

            prevVertex = currentVertex;
        }

        return clipped;
    }

    // Check if the point is inside the edge based on the direction
    private boolean isInside(Point p, int x1, int y1, int x2, int y2) {
        if (x1 == x2) { // Vertical edge (left or right)
            return (x1 == left) ? p.x >= x1 : p.x <= x1;
        } else { // Horizontal edge (top or bottom)
            return (y1 == top) ? p.y >= y1 : p.y <= y1;
        }
    }

    // Calculate the intersection of the polygon edge with the clipping boundary
    private Point intersect(Point p1, Point p2, int x1, int y1, int x2, int y2) {
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;

        double m = dy / dx;
        if (x1 == x2) { // Vertical clipping boundary (left or right)
            int x = x1;
            int y = (int) (p1.y + m * (x - p1.x));
            return new Point(x, y);
        } else { // Horizontal clipping boundary (top or bottom)
            int y = y1;
            int x = (int) (p1.x + (1 / m) * (y - p1.y));
            return new Point(x, y);
        }
    }

    // Draw polygon by connecting vertices
    private void drawPolygon(Graphics2D g2d, List<Point> polygon, int xOffset) {
        if (!polygon.isEmpty()) {
            Point prev = polygon.get(polygon.size() - 1);
            for (Point p : polygon) {
                g2d.drawLine(prev.x + xOffset, prev.y, p.x + xOffset, p.y);
                prev = p;
            }
            // Connect the last point to the first point
            g2d.drawLine(prev.x + xOffset, prev.y, polygon.get(0).x + xOffset, polygon.get(0).y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polygon Clipping");

        PolygonClipping panel = new PolygonClipping();
        frame.add(panel);

        frame.setSize(800, 400); // Wider frame to fit both original and clipped polygons
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
