//package Elipse;

import java.awt.*;
import javax.swing.*;

public class GeneralpointEllipse extends JPanel {

    private final int xc, yc, rx, ry;

    public GeneralpointEllipse(int xc, int yc, int rx, int ry) {
        this.xc = xc;
        this.yc = yc;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEllipse(g, xc, yc, rx, ry);
        drawCenterPoint(g, xc, yc);
        displayInfo(g, xc, yc, rx, ry);
    }

    private void drawEllipse(Graphics g, int xc, int yc, int rx, int ry) {
        int x = 0;
        int y = ry;

        // Initial decision parameters
        int rxSq = rx * rx;
        int rySq = ry * ry;
        int twoRxSq = 2 * rxSq;
        int twoRySq = 2 * rySq;

        // Initial decision parameter for region 1
        int p1 = (int) (rySq - (rxSq * ry) + (0.25 * rxSq));

        int px = 0;
        int py = twoRxSq * y;

        // Region 1
        while (px < py) {
            plotEllipsePoints(g, xc, yc, x, y);

            x++;
            px += twoRySq;

            if (p1 < 0) {
                p1 += rySq + px;
            } else {
                y--;
                py -= twoRxSq;
                p1 += rySq + px - py;
            }
        }

        // Initial decision parameter for region 2
        int p2 = (int) (rySq * (x + 0.5) * (x + 0.5) + rxSq * (y - 1) * (y - 1) - rxSq * rySq);

        // Region 2
        while (y > 0) {
            plotEllipsePoints(g, xc, yc, x, y);

            y--;
            py -= twoRxSq;

            if (p2 > 0) {
                p2 += rxSq - py;
            } else {
                x++;
                px += twoRySq;
                p2 += rxSq - py + px;
            }
        }
    }

    private void plotEllipsePoints(Graphics g, int xc, int yc, int x, int y) {
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);

        // Draw the fixed axes
        g.setColor(Color.GRAY); // color for axes
        g.drawLine(0, 400, 800, 400); // Fixed X-axis at y = 400
        g.drawLine(400, 0, 400, 800); // Fixed Y-axis at x = 400
        g.setColor(Color.BLACK); // color for other drawings
    }

    private void drawCenterPoint(Graphics g, int xc, int yc) {
        g.setColor(Color.RED); // the color of the center point
        g.fillOval(xc - 5, yc - 5, 10, 10);
        g.setColor(Color.BLACK); // color for ellipse
    }

    private void displayInfo(Graphics g, int xc, int yc, int a, int b) {
        String info = String.format("Center: (%d, %d) | Major Axis: %d | Minor Axis: %d", xc, yc, a, b);

        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);

        // Display it at the top-left corner with some padding
        int padding = 10;
        g.drawString(info, padding, padding + g.getFontMetrics().getAscent());
    }

    public static void main(String[] args) {
        // Example usage with arbitrary center and axis lengths
        int xc = 300; // x-coordinate of the center
        int yc = 200; // y-coordinate of the center
        int rx = 150; // Semi-major axis length
        int ry = 100; // Semi-minor axis length

        JFrame frame = new JFrame("General Ellipse Drawing");
        GeneralpointEllipse ellipsePanel = new GeneralpointEllipse(xc, yc, rx, ry);
        frame.add(ellipsePanel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
