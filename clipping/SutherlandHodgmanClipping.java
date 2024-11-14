import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SutherlandHodgmanClipping extends JPanel {

    // Clipping window boundaries
    int left = 100, right = 300, top = 100, bottom = 300;
    List<Point> originalPolygon = new ArrayList<>();
    List<Point> clippedPolygon;

    public SutherlandHodgmanClipping() {
        // Define the polygon vertices (original)
        originalPolygon.add(new Point(50, 150));
        originalPolygon.add(new Point(150, 50));
        originalPolygon.add(new Point(250, 100));
        originalPolygon.add(new Point(200, 200));

        // Perform clipping
        clippedPolygon = sutherlandHodgmanClip(new ArrayList<>(originalPolygon), left, right, top, bottom);

        // Set the panel's background color
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Draw the "Before" (unclipped) polygon on the left half
        g2d.setColor(Color.RED);
        g2d.drawString("Before (Unclipped)", 80, 50);
        g2d.drawRect(left, top, right - left, bottom - top);  // Clipping window
        drawPolygon(g2d, originalPolygon);

        // Translate to draw the "After" (clipped) polygon on the right half
        g2d.translate(panelWidth / 2, 0);  // Move origin to the middle of the panel

        g2d.setColor(Color.BLUE);
        g2d.drawString("After (Clipped)", 80, 50);
        g2d.drawRect(left, top, right - left, bottom - top);  // Clipping window
        drawPolygon(g2d, clippedPolygon);
    }

    private List<Point> sutherlandHodgmanClip(List<Point> subjectPolygon, int left, int right, int top, int bottom) {
        List<Point> clippedPolygon = new ArrayList<>(subjectPolygon);

        // Clip against each boundary of the clipping window
        clippedPolygon = clipAgainstEdge(clippedPolygon, 1, left);   // Clip against left
        clippedPolygon = clipAgainstEdge(clippedPolygon, -1, right); // Clip against right
        clippedPolygon = clipAgainstEdge(clippedPolygon, 2, top);    // Clip against top
        clippedPolygon = clipAgainstEdge(clippedPolygon, -2, bottom); // Clip against bottom

        return clippedPolygon;
    }

    private List<Point> clipAgainstEdge(List<Point> polygon, int edgeType, int value) {
        List<Point> clipped = new ArrayList<>();
        Point prevVertex = polygon.get(polygon.size() - 1);

        for (Point currentVertex : polygon) {
            boolean prevInside = isInside(prevVertex, edgeType, value);
            boolean currInside = isInside(currentVertex, edgeType, value);

            if (currInside) {
                if (!prevInside) {
                    clipped.add(intersect(prevVertex, currentVertex, edgeType, value));
                }
                clipped.add(currentVertex);
            } else if (prevInside) {
                clipped.add(intersect(prevVertex, currentVertex, edgeType, value));
            }

            prevVertex = currentVertex;
        }

        return clipped;
    }

    private boolean isInside(Point p, int edgeType, int value) {
        if (edgeType == 1) { // Left edge
            return p.x >= value;
        } else if (edgeType == -1) { // Right edge
            return p.x <= value;
        } else if (edgeType == 2) { // Top edge
            return p.y >= value;
        } else if (edgeType == -2) { // Bottom edge
            return p.y <= value;
        }
        return false;
    }

    private Point intersect(Point p1, Point p2, int edgeType, int value) {
        double x1 = p1.x, y1 = p1.y;
        double x2 = p2.x, y2 = p2.y;

        if (edgeType == 1 || edgeType == -1) { // Vertical edges (left or right)
            double newY = y1 + (value - x1) * (y2 - y1) / (x2 - x1);
            return new Point(value, (int) newY);
        } else { // Horizontal edges (top or bottom)
            double newX = x1 + (value - y1) * (x2 - x1) / (y2 - y1);
            return new Point((int) newX, value);
        }
    }

    private void drawPolygon(Graphics2D g2d, List<Point> polygon) {
        if (!polygon.isEmpty()) {
            Point prev = polygon.get(polygon.size() - 1);
            for (Point p : polygon) {
                g2d.drawLine(prev.x, prev.y, p.x, p.y);
                prev = p;
            }
            // Connect the last point to the first point
            g2d.drawLine(prev.x, prev.y, polygon.get(0).x, polygon.get(0).y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sutherland-Hodgman Clipping: Before and After");
        SutherlandHodgmanClipping panel = new SutherlandHodgmanClipping();
        frame.add(panel);
        frame.setSize(800, 400); // Set size to be wide enough for side-by-side view
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
