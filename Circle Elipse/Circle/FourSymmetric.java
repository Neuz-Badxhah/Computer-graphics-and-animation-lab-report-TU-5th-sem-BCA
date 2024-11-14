
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class FourSymmetric extends JPanel {

    private final int circleRadius;
    private final int circleCenterX;
    private final int circleCenterY;
    private final Color circleColor = Color.RED;
    private final Color axisColor = Color.BLACK;
    private final int gapSize = 10;

    public FourSymmetric(int centerX, int centerY, int radius) {
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
        graphics.drawLine(0, 200, 400, 200); // X-axis
        graphics.drawLine(200, 0, 200, 400); // Y-axis
    }

    private void drawCircle(Graphics graphics, int centerX, int centerY, int radius) {
        graphics.setColor(circleColor);
        for (int x = 0; x <= radius; x++) {
            int y = (int) Math.sqrt(radius * radius - x * x);
            drawCircleSymmetry(graphics, centerX, centerY, x, y);
        }
        drawRadiusLines(graphics, centerX, centerY, radius);
    }

    private void drawCircleSymmetry(Graphics graphics, int centerX, int centerY, int x, int y) {
        graphics.setColor(circleColor);
        // Fill the top and bottom quadrants
        if (x <= gapSize) {
            // Draw filled lines for the top and bottom
            graphics.drawLine(centerX - x, centerY - y, centerX + x, centerY - y); // Top filled line
            graphics.drawLine(centerX - x, centerY + y, centerX + x, centerY + y); // Bottom filled line
        } else {
            // Draw only points for the left and right sides
            graphics.drawRect(centerX + x, centerY + y, 1, 1); // Bottom-right
            graphics.drawRect(centerX - x, centerY + y, 1, 1); // Bottom-left
            graphics.drawRect(centerX + x, centerY - y, 1, 1); // Top-right
            graphics.drawRect(centerX - x, centerY - y, 1, 1); // Top-left
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
        int centerX, centerY, radius;

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter the center X coordinate: ");
                centerX = scanner.nextInt();
                System.out.print("Enter the center Y coordinate: ");
                centerY = scanner.nextInt();
                System.out.print("Enter the radius of the circle: ");
                radius = scanner.nextInt();

                if (radius > 0) {
                    break;
                }
                System.out.println("Radius must be a positive integer. Please try again.");
            }
        }

        JFrame frame = new JFrame("4 Symmetric Circle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        FourSymmetric circlePanel = new FourSymmetric(centerX + 200, 200 - centerY, radius);
        frame.add(circlePanel);
        frame.setVisible(true);
    }
}
