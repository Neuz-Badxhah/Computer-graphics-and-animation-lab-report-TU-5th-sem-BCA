import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class LiangBarskyClippingPanel extends JPanel {

    // Define clipping window boundaries
    int left, right, top, bottom;
    double x1, y1, x2, y2;
    double clippedX1, clippedY1, clippedX2, clippedY2;
    boolean isClipped = false;

    public LiangBarskyClippingPanel() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for clipping window and line segment coordinates
        try {
            System.out.print("Enter left boundary of clipping window: ");
            left = scanner.nextInt();
            System.out.print("Enter right boundary of clipping window: ");
            right = scanner.nextInt();
            System.out.print("Enter top boundary of clipping window: ");
            top = scanner.nextInt();
            System.out.print("Enter bottom boundary of clipping window: ");
            bottom = scanner.nextInt();

            System.out.print("Enter x1 of the line segment: ");
            x1 = scanner.nextDouble();
            System.out.print("Enter y1 of the line segment: ");
            y1 = scanner.nextDouble();
            System.out.print("Enter x2 of the line segment: ");
            x2 = scanner.nextDouble();
            System.out.print("Enter y2 of the line segment: ");
            y2 = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Invalid input. Using default values.");
            // Default values if input is invalid
            left = 100;
            right = 300;
            top = 100;
            bottom = 300;
            x1 = 50;
            y1 = 50;
            x2 = 350;
            y2 = 350;
        }

        // Perform Liang-Barsky clipping
        clipLine();
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Split the panel into two halves: left for "Before Clipping", right for "After Clipping"
        int midWidth = getWidth() / 2;

        // Draw the clipping rectangle in the left half (Before Clipping)
        g2d.setColor(Color.BLACK);
        g2d.drawRect(left, top, right - left, bottom - top);
        g2d.drawString("Before Clipping", midWidth / 2 - 50, getHeight() - 20);

        // Draw the original line in red
        g2d.setColor(Color.RED);
        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g2d.drawString("Original Line", (int) x2 - 50, (int) y2 + 10);

        // Draw the clipping rectangle in the right half (After Clipping)
        g2d.translate(midWidth, 0); // Move to the right half
        g2d.setColor(Color.BLACK);
        g2d.drawRect(left, top, right - left, bottom - top);
        g2d.drawString("After Clipping", midWidth / 2 - 50, getHeight() - 20);

        // Draw the clipped line in blue
        if (isClipped) {
            g2d.setColor(Color.BLUE);
            g2d.drawLine((int) clippedX1, (int) clippedY1, (int) clippedX2, (int) clippedY2);
            g2d.drawString("Clipped Line", (int) clippedX2 - 50, (int) clippedY2 + 10);
        } else {
            g2d.drawString("Line is outside clipping window", midWidth / 2 - 50, getHeight() / 2);
        }
    }

    private void clipLine() {
        double p[] = new double[4];
        double q[] = new double[4];

        p[0] = -(x2 - x1); // left boundary
        p[1] = x2 - x1; // right boundary
        p[2] = -(y2 - y1); // top boundary
        p[3] = y2 - y1; // bottom boundary

        q[0] = x1 - left; // left boundary
        q[1] = right - x1; // right boundary
        q[2] = y1 - top; // top boundary
        q[3] = bottom - y1; // bottom boundary

        double t0 = 0.0, t1 = 1.0;

        for (int i = 0; i < 4; i++) {
            if (p[i] == 0 && q[i] < 0) {
                isClipped = false;
                return; // Parallel and outside
            }
            double t = q[i] / p[i];
            if (p[i] < 0) { // Potentially entering
                t0 = Math.max(t0, t);
            } else if (p[i] > 0) { // Potentially leaving
                t1 = Math.min(t1, t);
            }
        }

        if (t0 > t1) {
            isClipped = false; // Line is outside the clipping window
        } else {
            // Compute the clipped endpoints
            clippedX1 = x1 + t0 * (x2 - x1);
            clippedY1 = y1 + t0 * (y2 - y1);
            clippedX2 = x1 + t1 * (x2 - x1);
            clippedY2 = y1 + t1 * (y2 - y1);
            isClipped = true;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Liang-Barsky Clipping Before and After");
        LiangBarskyClippingPanel panel = new LiangBarskyClippingPanel();
        frame.add(panel);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
