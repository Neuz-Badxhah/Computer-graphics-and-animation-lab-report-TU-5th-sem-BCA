
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class EightSymmetricEllipse extends JPanel {

    private final int radiusX;  // Semi-major axis
    private final int radiusY;  // Semi-minor axis
    private final int centerX;
    private final int centerY;
    private final Color ellipseColor = Color.RED;
    private final Color axisColor = Color.BLACK;

    public EightSymmetricEllipse(int centerX, int centerY, int radiusX, int radiusY) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        drawCoordinateAxes(graphics);
        drawEllipse(graphics, centerX, centerY, radiusX, radiusY);
    }

    private void drawCoordinateAxes(Graphics graphics) {
        graphics.setColor(axisColor);
        graphics.drawLine(0, 400, 800, 400); // X-axis
        graphics.drawLine(400, 0, 400, 800); // Y-axis
    }

    private void drawEllipse(Graphics graphics, int centerX, int centerY, int radiusX, int radiusY) {
        graphics.setColor(ellipseColor);
        for (int x = 0; x <= radiusX; x++) {
            // Adjust the calculation to handle different radii in x and y directions for an ellipse
            int y = (int) (radiusY * Math.sqrt(1 - (x * x) / (double) (radiusX * radiusX)));
            drawEllipseSymmetry(graphics, centerX, centerY, x, y);
        }
    }

    private void drawEllipseSymmetry(Graphics graphics, int centerX, int centerY, int x, int y) {
        graphics.setColor(ellipseColor);
        // Draw 4  symmetric points of the ellipse
        graphics.drawRect(centerX + y, centerY + x, 1, 1); // Right-bottom
        graphics.drawRect(centerX - y, centerY + x, 1, 1); // Left-bottom
        graphics.drawRect(centerX + y, centerY - x, 1, 1); // Right-top
        graphics.drawRect(centerX - y, centerY - x, 1, 1); // Left-top
    }

    public static void main(String[] args) {
        int centerX, centerY, radiusX, radiusY;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the center X coordinate (relative to the window): ");
            centerX = scanner.nextInt();
            System.out.print("Enter the center Y coordinate (relative to the window): ");
            centerY = scanner.nextInt();
            System.out.print("Enter the horizontal radius (semi-major axis) of the ellipse: ");
            radiusX = scanner.nextInt();
            System.out.print("Enter the vertical radius (semi-minor axis) of the ellipse: ");
            radiusY = scanner.nextInt();
        }

        JFrame frame = new JFrame("Symmetric Ellipse Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        EightSymmetricEllipse ellipsePanel = new EightSymmetricEllipse(centerX + 400, 400 - centerY, radiusX, radiusY);
        frame.add(ellipsePanel);
        frame.setVisible(true);
    }
}
