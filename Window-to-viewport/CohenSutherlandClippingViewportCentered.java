
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class CohenSutherlandClippingViewportCentered extends JPanel {

    // Clipping window boundaries
    private int left, right, top, bottom;
    // Viewport dimensions
    private int viewportWidth, viewportHeight;
    // Line segment coordinates
    private double x1, y1, x2, y2;
    // Clipped line segment coordinates
    private double clippedX1, clippedY1, clippedX2, clippedY2;

    public CohenSutherlandClippingViewportCentered() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for clipping window boundaries
        System.out.print("Enter left boundary of clipping window: ");
        left = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter right boundary of clipping window: ");
        right = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter top boundary of clipping window: ");
        top = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter bottom boundary of clipping window: ");
        bottom = Integer.parseInt(scanner.nextLine());

        // Prompt user for viewport dimensions
        System.out.print("Enter viewport width: ");
        viewportWidth = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter viewport height: ");
        viewportHeight = Integer.parseInt(scanner.nextLine());

        // Prompt user for line segment coordinates
        System.out.print("Enter x1 of the line segment: ");
        x1 = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter y1 of the line segment: ");
        y1 = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter x2 of the line segment: ");
        x2 = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter y2 of the line segment: ");
        y2 = Double.parseDouble(scanner.nextLine());

        // Set the panel size to accommodate both windows
        setPreferredSize(new Dimension(800, 600));

        // Perform Cohen-Sutherland clipping
        clipLine(x1, y1, x2, y2);
    }

    private int computeOutcode(double x, double y) {
        int outcode = 0;
        if (x < left) {
            outcode |= 1; // Left

                }if (x > right) {
            outcode |= 2; // Right

                }if (y < top) {
            outcode |= 4; // Top

                }if (y > bottom) {
            outcode |= 8; // Bottom

                }return outcode;
    }

    private void clipLine(double x1, double y1, double x2, double y2) {
        int outcode1 = computeOutcode(x1, y1);
        int outcode2 = computeOutcode(x2, y2);
        boolean accept = false;

        while (true) {
            if ((outcode1 | outcode2) == 0) {
                // Both points are inside the clipping window
                accept = true;
                break;
            } else if ((outcode1 & outcode2) != 0) {
                // Both points are outside the same boundary
                break;
            } else {
                // Line segment is partially inside
                int outcodeOut = outcode1 != 0 ? outcode1 : outcode2;
                double x = 0, y = 0;

                // Calculate intersection point
                if ((outcodeOut & 8) != 0) { // Bottom
                    x = x1 + (x2 - x1) * (bottom - y1) / (y2 - y1);
                    y = bottom;
                } else if ((outcodeOut & 4) != 0) { // Top
                    x = x1 + (x2 - x1) * (top - y1) / (y2 - y1);
                    y = top;
                } else if ((outcodeOut & 2) != 0) { // Right
                    y = y1 + (y2 - y1) * (right - x1) / (x2 - x1);
                    x = right;
                } else if ((outcodeOut & 1) != 0) { // Left
                    y = y1 + (y2 - y1) * (left - x1) / (x2 - x1);
                    x = left;
                }

                // Update the endpoint outside the clipping window
                if (outcodeOut == outcode1) {
                    x1 = x;
                    y1 = y;
                    outcode1 = computeOutcode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    outcode2 = computeOutcode(x2, y2);
                }
            }
        }

        // Store the clipped coordinates for drawing
        if (accept) {
            this.clippedX1 = x1;
            this.clippedY1 = y1;
            this.clippedX2 = x2;
            this.clippedY2 = y2;
        } else {
            System.out.println("Line is completely outside the clipping window.");
            this.clippedX1 = this.clippedY1 = this.clippedX2 = this.clippedY2 = Double.NaN;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw clipping window
        g.setColor(Color.BLUE);
        g.drawRect(50, 50, right - left, bottom - top);
        g.drawString("Clipping Window", 50 + 10, 50 - 10);

        // Draw the original line segment in the clipping window
        g.setColor(Color.BLACK);
        g.drawLine((int) x1 + 50, (int) y1 + 50, (int) x2 + 50, (int) y2 + 50);

        // Draw the viewport
        g.setColor(Color.RED);
        int viewportX = 400; // Positioning viewport next to the clipping window
        int viewportY = 50; // Same Y position as clipping window
        g.drawRect(viewportX, viewportY, viewportWidth, viewportHeight);
        g.drawString("Viewport", viewportX + 10, viewportY - 10);

        // Draw the clipped line segment in the viewport
        if (!Double.isNaN(clippedX1) && !Double.isNaN(clippedY1)
                && !Double.isNaN(clippedX2) && !Double.isNaN(clippedY2)) {
            // Map the clipped line to the viewport
            double scaleX = (double) viewportWidth / (right - left);
            double scaleY = (double) viewportHeight / (bottom - top);

            // Translate to viewport coordinates
            int viewportClippedX1 = (int) (viewportX + (clippedX1 - left) * scaleX);
            int viewportClippedY1 = (int) (viewportY + (clippedY1 - top) * scaleY);
            int viewportClippedX2 = (int) (viewportX + (clippedX2 - left) * scaleX);
            int viewportClippedY2 = (int) (viewportY + (clippedY2 - top) * scaleY);

            // Adjusting viewport to center on the clipped segment
            int centerX = (viewportX + viewportWidth / 2);
            int centerY = (viewportY + viewportHeight / 2);

            // Calculate offsets
            int offsetX = centerX - viewportClippedX1;
            int offsetY = centerY - viewportClippedY1;

            // Draw the clipped line centered in the viewport
            g.setColor(Color.GREEN);
            g.drawLine(viewportClippedX1 + offsetX, viewportClippedY1 + offsetY,
                    viewportClippedX2 + offsetX, viewportClippedY2 + offsetY);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cohen-Sutherland Clipping to Viewport");
        CohenSutherlandClippingViewportCentered panel = new CohenSutherlandClippingViewportCentered();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
