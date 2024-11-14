
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class CircleWithBoundedHeight extends JPanel {

    private int circleRadius;
    private int circleCenterX;
    private int circleCenterY;
    private Color circleColor = Color.RED;
    private Color axisColor = Color.BLACK;
    private final int gapSize = 10;

    public CircleWithBoundedHeight(int centerX, int centerY, int radius) {
        this.circleCenterX = centerX;
        this.circleCenterY = centerY;
        this.circleRadius = radius;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawCoordinateAxes(graphics);
        drawCircle(graphics, circleCenterX, circleCenterY, circleRadius);
    }

    private void drawCoordinateAxes(Graphics graphics) {
        graphics.setColor(axisColor);
        graphics.drawLine(0, 400, 800, 400); // X-axis
        graphics.drawLine(400, 0, 400, 800); // Y-axis
    }

    private void drawCircle(Graphics graphics, int centerX, int centerY, int radius) {
        graphics.setColor(circleColor);

        // Calculate adjusted centerY so that top and bottom touch the edges of the drawing area
        int adjustedCenterY = 400 - radius; // Top point at 400
        int adjustedCenterX = centerX; // Keep the original X coordinate

        for (int x = 0; x <= radius; x++) {
            int y = (int) Math.sqrt(radius * radius - x * x);
            drawCircleSymmetry(graphics, adjustedCenterX, adjustedCenterY, x, y);
        }
        drawRadiusLines(graphics, adjustedCenterX, adjustedCenterY, radius);
    }

    private void drawCircleSymmetry(Graphics graphics, int centerX, int centerY, int x, int y) {
        graphics.setColor(circleColor);
        // Draw symmetric points of the circle for four-point symmetry
        if (!(Math.abs(x) <= gapSize || Math.abs(y) <= gapSize)) {
            graphics.drawRect(centerX + x, centerY + y, 1, 1); // First quadrant
            graphics.drawRect(centerX + x, centerY - y, 1, 1); // Second quadrant
            graphics.drawRect(centerX - x, centerY + y, 1, 1); // Fourth quadrant
            graphics.drawRect(centerX - x, centerY - y, 1, 1); // Third quadrant
        }
    }

    private void drawRadiusLines(Graphics graphics, int centerX, int centerY, int radius) {
        graphics.setColor(Color.RED);
        graphics.drawLine(centerX, centerY, centerX + radius, centerY); // Right
        graphics.drawLine(centerX, centerY, centerX - radius, centerY); // Left
        graphics.drawLine(centerX, centerY, centerX, centerY + radius); // Down
        graphics.drawLine(centerX, centerY, centerX, centerY - radius); // Up
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int centerX, centerY, radius;

        while (true) {
            System.out.print("Enter the center X coordinate (relative to the window): ");
            centerX = scanner.nextInt();
            System.out.print("Enter the center Y coordinate (relative to the window): ");
            centerY = scanner.nextInt();
            System.out.print("Enter the radius of the circle (positive integer): ");
            radius = scanner.nextInt();

            if (radius > 0) {
                break;
            }
            System.out.println("Radius must be a positive integer. Please try again.");
        }

        scanner.close();

        JFrame frame = new JFrame("Circle with Bounded Height");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        CircleWithBoundedHeight circlePanel = new CircleWithBoundedHeight(centerX + 400, 400 - centerY, radius);
        frame.add(circlePanel);
        frame.setVisible(true);
    }
}
