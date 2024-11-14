
import java.awt.*;
import javax.swing.*;

public class example extends JPanel {

    // Clipping window boundaries
    int left, right, top, bottom;
    double x1, y1, x2, y2;
    double clippedX1, clippedY1, clippedX2, clippedY2;
    boolean isClipped = false;

    public example() {
        // Get user input for clipping window and line segment coordinates
        try {
            left = Integer.parseInt(JOptionPane.showInputDialog("Enter left boundary of clipping window:"));
            right = Integer.parseInt(JOptionPane.showInputDialog("Enter right boundary of clipping window:"));
            top = Integer.parseInt(JOptionPane.showInputDialog("Enter top boundary of clipping window:"));
            bottom = Integer.parseInt(JOptionPane.showInputDialog("Enter bottom boundary of clipping window:"));

            x1 = Double.parseDouble(JOptionPane.showInputDialog("Enter x1 of the line segment:"));
            y1 = Double.parseDouble(JOptionPane.showInputDialog("Enter y1 of the line segment:"));
            x2 = Double.parseDouble(JOptionPane.showInputDialog("Enter x2 of the line segment:"));
            y2 = Double.parseDouble(JOptionPane.showInputDialog("Enter y2 of the line segment:"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Using default values.");
            // Default values if input is invalid
            left = 50;
            right = 300;
            top = 50;
            bottom = 200;
            x1 = 30;
            y1 = 30;
            x2 = 350;
            y2 = 250;
        }

        // Perform Liang-Barsky clipping
        clipLine();

        // Set the panel's background color
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the clipping rectangle
        g2d.drawRect(left, top, right - left, bottom - top);
        g2d.drawString("Clipping Window", right + 10, top + (bottom - top) / 2);

        // Draw the original line segment
        g2d.setColor(Color.RED);
        g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        g2d.drawString("Original Line", (int) x2 + 10, (int) y2);

        // Draw the clipped line segment
        if (isClipped) {
            g2d.setColor(Color.BLUE);
            g2d.drawLine((int) clippedX1, (int) clippedY1, (int) clippedX2, (int) clippedY2);
            g2d.drawString("Clipped Line", (int) clippedX2 + 10, (int) clippedY2);
        }
    }

    private void clipLine() {
        double p[] = new double[4];
        double q[] = new double[4];

        p[0] = x1 - x2; // Left boundary
        p[1] = x2 - x1; // Right boundary
        p[2] = y1 - y2; // Bottom boundary
        p[3] = y2 - y1; // Top boundary

        q[0] = x1 - left; // Left boundary
        q[1] = right - x1; // Right boundary
        q[2] = bottom - y1; // Bottom boundary
        q[3] = y1 - top; // Top boundary

        double t0 = 0.0, t1 = 1.0;
        double t;
        double p0, q0;

        for (int i = 0; i < 4; i++) {
            p0 = p[i];
            q0 = q[i];

            if (p0 == 0) {
                if (q0 < 0) {
                    // Line segment is parallel and outside the boundary
                    isClipped = false;
                    return;
                }
            } else {
                t = q0 / p0;

                if (p0 < 0) {
                    if (t > t1) {
                        // Line segment is outside the clipping window
                        isClipped = false;
                        return;
                    } else if (t > t0) {
                        t0 = t;
                    }
                } else {
                    if (t < t0) {
                        // Line segment is outside the clipping window
                        isClipped = false;
                        return;
                    } else if (t < t1) {
                        t1 = t;
                    }
                }
            }
        }

        // Compute clipped endpoints
        clippedX1 = x1 + t0 * (x2 - x1);
        clippedY1 = y1 + t0 * (y2 - y1);
        clippedX2 = x1 + t1 * (x2 - x1);
        clippedY2 = y1 + t1 * (y2 - y1);

        isClipped = true;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Liang-Barsky Line Clipping");
        example panel = new example();
        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
