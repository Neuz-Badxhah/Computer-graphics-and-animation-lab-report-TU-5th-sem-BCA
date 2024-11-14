
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class FourSymmetricEllipse extends JPanel {

    private final int radiusX;  // Semi-major axis (horizontal)
    private final int radiusY;  // Semi-minor axis (vertical)
    private final int centerX;
    private final int centerY;
    private final Color ellipseColor = Color.RED;
    private final Color axisColor = Color.BLACK;

    public FourSymmetricEllipse(int centerX, int centerY, int radiusX, int radiusY) {
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
        graphics.drawLine(0, 200, 400, 200); // X-axis
        graphics.drawLine(200, 0, 200, 400); // Y-axis
    }

    private void drawEllipse(Graphics graphics, int centerX, int centerY, int radiusX, int radiusY) {
        graphics.setColor(ellipseColor);
        for (int x = 0; x <= radiusX; x++) {
            // Calculate the y-coordinate for the given x using the ellipse equation
            int y = (int) (radiusY * Math.sqrt(1 - (x * x) / (double) (radiusX * radiusX)));
            drawEllipseSymmetry(graphics, centerX, centerY, x, y);
        }
    }

    private void drawEllipseSymmetry(Graphics graphics, int centerX, int centerY, int x, int y) {
        graphics.setColor(ellipseColor);
        // Draw 4 symmetric points (top-right, top-left, bottom-right, bottom-left)
        graphics.drawRect(centerX + x, centerY + y, 1, 1); // Bottom-right
        graphics.drawRect(centerX - x, centerY + y, 1, 1); // Bottom-left
        graphics.drawRect(centerX + x, centerY - y, 1, 1); // Top-right
        graphics.drawRect(centerX - x, centerY - y, 1, 1); // Top-left
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

        JFrame frame = new JFrame("4-Symmetric Ellipse Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        FourSymmetricEllipse ellipsePanel = new FourSymmetricEllipse(centerX + 200, 200 - centerY, radiusX, radiusY);
        frame.add(ellipsePanel);
        frame.setVisible(true);
    }
}
