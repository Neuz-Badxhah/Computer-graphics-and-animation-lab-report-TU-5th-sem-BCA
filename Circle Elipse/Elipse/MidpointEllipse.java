
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class MidpointEllipse extends JPanel {

    private final int xc, yc, rx, ry;

    public MidpointEllipse(int xc, int yc, int rx, int ry) {
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

        // Region 1: where the slope of the curve is less than 1
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

        // Region 2: where the slope of the curve is greater than or equal to 1
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
        try ( // Example usage with user-defined center and axis lengths
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the center of the ellipse (xc, yc):");
            int xc = scanner.nextInt();
            int yc = scanner.nextInt();

            System.out.println("Enter the length of the semi-major axis (rx):");
            int rx = scanner.nextInt();

            System.out.println("Enter the length of the semi-minor axis (ry):");
            int ry = scanner.nextInt();

            JFrame frame = new JFrame("Midpoint Ellipse Drawing");
            MidpointEllipse ellipsePanel = new MidpointEllipse(xc, yc, rx, ry);
            frame.add(ellipsePanel);
            frame.setSize(600, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
