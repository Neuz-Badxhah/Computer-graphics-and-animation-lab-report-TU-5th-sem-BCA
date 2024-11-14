
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ArbitraryPolygonClipping extends JPanel {

    // Clipping region vertices (example arbitrary shape)
    List<Point> clippingRegion = new ArrayList<>();
    // Polygon to be clipped
    List<Point> originalPolygon = new ArrayList<>();
    // Resulting clipped polygon
    List<Point> clippedPolygon = new ArrayList<>();

    public ArbitraryPolygonClipping() {
        // Define the clipping region (example: an arbitrary shape)
        clippingRegion.add(new Point(100, 100));
        clippingRegion.add(new Point(200, 100));
        clippingRegion.add(new Point(250, 150));
        clippingRegion.add(new Point(200, 200));
        clippingRegion.add(new Point(100, 200));

        // Define the original polygon to be clipped
        originalPolygon.add(new Point(50, 150));
        originalPolygon.add(new Point(150, 50));
        originalPolygon.add(new Point(250, 100));
        originalPolygon.add(new Point(200, 200));

        // Perform clipping and store the result in clippedPolygon
        clippedPolygon = clipPolygonAgainstShape(new ArrayList<>(originalPolygon), clippingRegion);

        // Set the panel's background color
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the original polygon and clipping region on the left side
        g2d.setColor(Color.RED);
        g2d.drawString("Original Polygon & Clipping Region", 100, 50);
        drawPolygon(g2d, originalPolygon, 0, 0);  // Draw original polygon
        g2d.setColor(Color.BLUE);
        drawPolygon(g2d, clippingRegion, 0, 0);  // Draw clipping region

        // Draw the clipped polygon on the right side
        g2d.setColor(Color.GREEN);
        g2d.drawString("Clipped Polygon", 400, 50);
        drawPolygon(g2d, clippedPolygon, 300, 0);  // Draw clipped polygon with offset to the right
    }

    private List<Point> clipPolygonAgainstShape(List<Point> subjectPolygon, List<Point> clippingShape) {
        List<Point> outputList = new ArrayList<>(subjectPolygon);
        int numClipVertices = clippingShape.size();

        // Clip against each edge of the clipping shape
        for (int i = 0; i < numClipVertices; i++) {
            Point clipEdgeStart = clippingShape.get(i);
            Point clipEdgeEnd = clippingShape.get((i + 1) % numClipVertices);
            outputList = clipAgainstEdge(outputList, clipEdgeStart, clipEdgeEnd);
        }

        return outputList;
    }

    private List<Point> clipAgainstEdge(List<Point> polygon, Point edgeStart, Point edgeEnd) {
        List<Point> newPolygon = new ArrayList<>();

        if (polygon.isEmpty()) {
            return newPolygon;
        }

        Point prevVertex = polygon.get(polygon.size() - 1);  // Get the last point to loop

        for (Point currentVertex : polygon) {
            boolean prevInside = isInside(prevVertex, edgeStart, edgeEnd);
            boolean currInside = isInside(currentVertex, edgeStart, edgeEnd);

            if (currInside) {
                if (!prevInside) {
                    newPolygon.add(intersect(prevVertex, currentVertex, edgeStart, edgeEnd));  // Add intersection point
                }
                newPolygon.add(currentVertex);  // Add current vertex if inside
            } else if (prevInside) {
                newPolygon.add(intersect(prevVertex, currentVertex, edgeStart, edgeEnd));  // Add intersection point
            }

            prevVertex = currentVertex;
        }

        return newPolygon;
    }

    private boolean isInside(Point p, Point edgeStart, Point edgeEnd) {
        // Check if point is inside the clipping edge (using cross product to check relative position)
        return (edgeEnd.x - edgeStart.x) * (p.y - edgeStart.y) - (edgeEnd.y - edgeStart.y) * (p.x - edgeStart.x) >= 0;
    }

    private Point intersect(Point p1, Point p2, Point q1, Point q2) {
        // Find intersection of lines (p1, p2) and (q1, q2)
        double A1 = p2.y - p1.y;
        double B1 = p1.x - p2.x;
        double C1 = A1 * p1.x + B1 * p1.y;

        double A2 = q2.y - q1.y;
        double B2 = q1.x - q2.x;
        double C2 = A2 * q1.x + B2 * q1.y;

        double det = A1 * B2 - A2 * B1;
        if (det == 0) {
            // Lines are parallel; return the midpoint as a fallback
            return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        } else {
            int x = (int) ((B2 * C1 - B1 * C2) / det);
            int y = (int) ((A1 * C2 - A2 * C1) / det);
            return new Point(x, y);
        }
    }

    private void drawPolygon(Graphics2D g2d, List<Point> polygon, int offsetX, int offsetY) {
        if (!polygon.isEmpty()) {
            Point prev = polygon.get(polygon.size() - 1);
            for (Point p : polygon) {
                g2d.drawLine(prev.x + offsetX, prev.y + offsetY, p.x + offsetX, p.y + offsetY);
                prev = p;
            }
            // Connect the last point to the first point
            g2d.drawLine(prev.x + offsetX, prev.y + offsetY, polygon.get(0).x + offsetX, polygon.get(0).y + offsetY);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arbitrary Polygon Clipping");
        ArbitraryPolygonClipping panel = new ArbitraryPolygonClipping();
        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
