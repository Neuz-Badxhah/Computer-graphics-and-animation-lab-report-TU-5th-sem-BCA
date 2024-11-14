import java.awt.*;
import java.util.*;
import javax.swing.*;

public class CircleDrawing extends JPanel {

    private final int x_center, y_center, radius;
    private final java.util.List<Point> circlePoints;

    public CircleDrawing(int x_center, int y_center, int radius) {
        this.x_center = x_center;
        this.y_center = y_center;
        this.radius = radius;
        this.circlePoints = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g, x_center, y_center, radius);
        drawCenterPoint(g, x_center, y_center);
        displayInfo(g, x_center, y_center, radius);
        drawRadiusLines(g);  // Draw radius lines connecting to the circle
        displayCirclePoints(g);  // Display the coordinates of the circle points
    }

    private void drawCircle(Graphics g, int xc, int yc, int r) {
        int x = 0, y = r;
        int d = 1 - r;

        while (x < y) {
            plotCirclePoints(g, xc, yc, x, y);
            x++;
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                y--;
                d += 2 * (x - y) + 1;
            }
        }
        // Plot the final points
        plotCirclePoints(g, xc, yc, x, y);
    }

    private void plotCirclePoints(Graphics g, int xc, int yc, int x, int y) {
        // Store the circle points for display
        addCirclePoint(xc + x, yc + y);
        addCirclePoint(xc - x, yc + y);
        addCirclePoint(xc + x, yc - y);
        addCirclePoint(xc - x, yc - y);
        addCirclePoint(xc + y, yc + x);
        addCirclePoint(xc - y, yc + x);
        addCirclePoint(xc + y, yc - x);
        addCirclePoint(xc - y, yc - x);

        // Draw the circle points
        drawPoint(g, xc + x, yc + y);
        drawPoint(g, xc - x, yc + y);
        drawPoint(g, xc + x, yc - y);
        drawPoint(g, xc - x, yc - y);
        drawPoint(g, xc + y, yc + x);
        drawPoint(g, xc - y, yc + x);
        drawPoint(g, xc + y, yc - x);
        drawPoint(g, xc - y, yc - x);
    }

    private void addCirclePoint(int x, int y) {
        circlePoints.add(new Point(x, y));
    }

    private void drawPoint(Graphics g, int x, int y) {
        g.fillRect(x, y, 1, 1);
    }

    private void drawRadiusLines(Graphics g) {
        g.setColor(Color.BLUE); // Color for radius lines
        for (Point p : circlePoints) {
            g.drawLine(x_center, y_center, p.x, p.y); // Draw line from center to circle point
        }
        g.setColor(Color.BLACK); // Reset color for other drawings
    }

    private void drawCenterPoint(Graphics g, int xc, int yc) {
        g.setColor(Color.RED);
        g.fillOval(xc - 5, yc - 5, 10, 10);
        g.setColor(Color.BLACK);
    }

    private void displayInfo(Graphics g, int xc, int yc, int r) {
        String info = String.format("Center: (%d, %d) | Radius: %d", xc, yc, r);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        int padding = 10;
        g.drawString(info, padding, padding + g.getFontMetrics().getAscent());
    }

    private void displayCirclePoints(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Point", 10, 450);
        g.drawString("X", 80, 450);
        g.drawString("Y", 130, 450);

        // Display the points with a vertical offset to avoid overlapping
        for (int i = 0; i < circlePoints.size(); i++) {
            Point p = circlePoints.get(i);
            g.drawString("P" + (i + 1), 10, 470 + (i * 20)); // Label the point
            g.drawString(Integer.toString(p.x), 80, 470 + (i * 20)); // X coordinate
            g.drawString(Integer.toString(p.y), 130, 470 + (i * 20)); // Y coordinate
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter x_center: ");
            int x_center = sc.nextInt();
            System.out.print("Enter y_center: ");
            int y_center = sc.nextInt();
            System.out.print("Enter radius: ");
            int radius = sc.nextInt();

            JFrame frame = new JFrame("Circle Drawing with Radius Lines");
            CircleDrawing panel = new CircleDrawing(x_center, y_center, radius);
            frame.add(panel);
            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
