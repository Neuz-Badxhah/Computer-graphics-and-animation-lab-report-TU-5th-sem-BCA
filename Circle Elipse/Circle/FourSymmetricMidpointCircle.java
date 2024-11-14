
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class FourSymmetricMidpointCircle extends JPanel {

    private final int centerX;
    private final int centerY;
    private final int radius;
    private final Color circleColor = Color.RED;
    private final Color axisColor = Color.BLACK;

    public FourSymmetricMidpointCircle(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawCoordinateAxes(graphics);
        drawCircle(graphics, centerX, centerY, radius);
    }

    private void drawCoordinateAxes(Graphics graphics) {
        graphics.setColor(axisColor);
        graphics.drawLine(0, 300, 600, 300); // X-axis
        graphics.drawLine(300, 0, 300, 600); // Y-axis
    }

    private void drawCircle(Graphics graphics, int centerX, int centerY, int radius) {
        graphics.setColor(circleColor);

        // Starting points
        int x = 0;
        int y = radius;
        int d = 1 - radius; // Initial decision parameter

        drawCircleSymmetry(graphics, centerX, centerY, x, y);

        while (x < y) {
            if (d < 0) {
                // Choose the east pixel
                d += 2 * x + 3;
            } else {
                // Choose the south-east pixel
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;
            drawCircleSymmetry(graphics, centerX, centerY, x, y);
        }
    }

    private void drawCircleSymmetry(Graphics graphics, int centerX, int centerY, int x, int y) {
        // Draws 4 symmetric points (top-right, top-left, bottom-right, bottom-left)
        graphics.drawRect(centerX + x, centerY + y, 1, 1); // Bottom-right
        graphics.drawRect(centerX - x, centerY + y, 1, 1); // Bottom-left
        graphics.drawRect(centerX + x, centerY - y, 1, 1); // Top-right
        graphics.drawRect(centerX - x, centerY - y, 1, 1); // Top-left
    }

    public static void main(String[] args) {
        int centerX, centerY, radius;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the center X coordinate: ");
            centerX = scanner.nextInt();
            System.out.print("Enter the center Y coordinate: ");
            centerY = scanner.nextInt();
            System.out.print("Enter the radius of the circle: ");
            radius = scanner.nextInt();
        }

        JFrame frame = new JFrame("4-Symmetric Midpoint Circle Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        FourSymmetricMidpointCircle circlePanel = new FourSymmetricMidpointCircle(centerX + 300, 300 - centerY, radius);
        frame.add(circlePanel);
        frame.setVisible(true);
    }
}
