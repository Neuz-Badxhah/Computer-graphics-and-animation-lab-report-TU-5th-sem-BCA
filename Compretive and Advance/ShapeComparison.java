import java.awt.*;
import javax.swing.*;

public class ShapeComparison extends JPanel {

    private static final int CIRCLE_RADIUS = 100;
    private static final int ELLIPSE_A = 150;
    private static final int ELLIPSE_B = 100;
    private static final int CENTER_X = 200;
    private static final int CENTER_Y = 200;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw circles
        drawMidpointCircle(g, CIRCLE_RADIUS, CENTER_X - 150, CENTER_Y);
        drawBresenhamCircle(g, CIRCLE_RADIUS, CENTER_X + 150, CENTER_Y);

        // Draw ellipses
        drawMidpointEllipse(g, ELLIPSE_A, ELLIPSE_B, CENTER_X - 150, CENTER_Y + 200);
        drawBresenhamEllipse(g, ELLIPSE_A, ELLIPSE_B, CENTER_X + 150, CENTER_Y + 200);
    }

    private void drawMidpointCircle(Graphics g, int radius, int x0, int y0) {
        int x = 0;
        int y = radius;
        int p = 1 - radius;
        plotCirclePoints(g, x, y, x0, y0);
        while (x < y) {
            x++;
            if (p < 0) {
                p += 2 * x + 1;
            } else {
                y--;
                p += 2 * (x - y) + 1;
            }
            plotCirclePoints(g, x, y, x0, y0);
        }
    }

    private void drawBresenhamCircle(Graphics g, int radius, int x0, int y0) {
        int x = radius;
        int y = 0;
        int p = 1 - radius;
        plotCirclePoints(g, x, y, x0, y0);
        while (x > y) {
            y++;
            if (p <= 0) {
                p += 2 * y + 1;
            } else {
                x--;
                p += 2 * (y - x) + 1;
            }
            plotCirclePoints(g, x, y, x0, y0);
        }
    }

    private void drawMidpointEllipse(Graphics g, int a, int b, int x0, int y0) {
        int x = 0;
        int y = b;
        double d1 = (b * b) - (a * a * b) + (0.25 * a * a);
        plotEllipsePoints(g, x, y, x0, y0);

        while ((a * a * (y - 0.5)) > (b * b * (x + 1))) {
            if (d1 < 0) {
                d1 += b * b * (2 * x + 3);
            } else {
                d1 += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
            }
            x++;
            plotEllipsePoints(g, x, y, x0, y0);
        }

        double d2 = (b * b * (x + 0.5) * (x + 0.5)) + (a * a * (y - 1) * (y - 1)) - (a * a * b * b);
        while (y > 0) {
            if (d2 < 0) {
                d2 += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else {
                d2 += a * a * (-2 * y + 3);
            }
            y--;
            plotEllipsePoints(g, x, y, x0, y0);
        }
    }

    private void drawBresenhamEllipse(Graphics g, int a, int b, int x0, int y0) {
        int x = 0;
        int y = b;
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;
        int p = (int) (b2 - a2 * b + 0.25 * a2);

        plotEllipsePoints(g, x, y, x0, y0);

        while (b2 * x < a2 * y) {
            x++;
            if (p < 0) {
                p += twoB2 * x + b2;
            } else {
                y--;
                p += twoB2 * x - twoA2 * y + b2;
            }
            plotEllipsePoints(g, x, y, x0, y0);
        }

        p = (int) (b2 * (x + 0.5) * (x + 0.5) + a2 * (y - 1) * (y - 1) - a2 * b2);
        while (y > 0) {
            y--;
            if (p > 0) {
                p += a2 - twoA2 * y;
            } else {
                x++;
                p += twoB2 * x - twoA2 * y + a2;
            }
            plotEllipsePoints(g, x, y, x0, y0);
        }
    }

    private void plotCirclePoints(Graphics g, int x, int y, int x0, int y0) {
        g.fillRect(x0 + x, y0 + y, 1, 1);
        g.fillRect(x0 - x, y0 + y, 1, 1);
        g.fillRect(x0 + x, y0 - y, 1, 1);
        g.fillRect(x0 - x, y0 - y, 1, 1);
        g.fillRect(x0 + y, y0 + x, 1, 1);
        g.fillRect(x0 - y, y0 + x, 1, 1);
        g.fillRect(x0 + y, y0 - x, 1, 1);
        g.fillRect(x0 - y, y0 - x, 1, 1);
    }

    private void plotEllipsePoints(Graphics g, int x, int y, int x0, int y0) {
        g.fillRect(x0 + x, y0 + y, 1, 1);
        g.fillRect(x0 - x, y0 + y, 1, 1);
        g.fillRect(x0 + x, y0 - y, 1, 1);
        g.fillRect(x0 - x, y0 - y, 1, 1);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Midpoint and Bresenham Algorithms Comparison");
        ShapeComparison panel = new ShapeComparison();
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
