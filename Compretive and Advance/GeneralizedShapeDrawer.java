
import java.awt.*;
import javax.swing.*;

public class GeneralizedShapeDrawer extends JPanel {

    private static final int SHAPE_RADIUS = 100;
    private static final int ELLIPSE_A = 150;
    private static final int ELLIPSE_B = 100;
    private static final int CENTER_X = 400;
    private static final int CENTER_Y = 300;

    private JTextArea textArea;

    public GeneralizedShapeDrawer() {
        setLayout(new BorderLayout());
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Example calls to the generalized drawing method
        drawShape(g, SHAPE_RADIUS, 0, CENTER_X - 150, CENTER_Y - 150, true, false); // Circle
        drawShape(g, ELLIPSE_A, ELLIPSE_B, CENTER_X + 150, CENTER_Y - 150, false, true); // Ellipse
    }

    private void drawShape(Graphics g, int param1, int param2, int x0, int y0, boolean isCircle, boolean isEllipse) {
        if (isCircle) {
            drawCircle(g, param1, x0, y0);
        } else if (isEllipse) {
            drawEllipse(g, param1, param2, x0, y0);
        }
    }

    private void drawCircle(Graphics g, int radius, int x0, int y0) {
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

    private void drawEllipse(Graphics g, int a, int b, int x0, int y0) {
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
        JFrame frame = new JFrame("Generalized Shape Drawer");
        GeneralizedShapeDrawer panel = new GeneralizedShapeDrawer();
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
