
import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class CombinedTransformations extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Create a rectangle
        Rectangle rect = new Rectangle(100, 100, 100, 60);

        // Draw the original rectangle
        g2d.draw(rect);
        g2d.drawString("Original", rect.x + rect.width + 10, rect.y + rect.height / 2);
        g2d.drawLine(0, 400, 800, 400); // X-axis
        g2d.drawLine(400, 0, 400, 800); // Y-axis

        // Draw labels for axes at the start of the lines
        g2d.drawString("x", 780, 315);  // End of the X-axis
        g2d.drawString("x'", 10, 315);  // Start of the X-axis
        g2d.drawString("y'", 415, 20);  // Start of the Y-axis
        g2d.drawString("y", 415, 790);  // End of the Y-axis

        // Create an AffineTransform instance
        AffineTransform transform = new AffineTransform();

        // Apply Translation (move the rectangle)
        transform.translate(150, 50);

        // Apply Scaling (scale the rectangle)
        transform.scale(1.5, 1.5);

        // Apply Rotation (rotate the rectangle)
        transform.rotate(Math.toRadians(45), rect.getX() + rect.width / 2, rect.getY() + rect.height / 2);

        // Apply Shearing (shear the rectangle)
        transform.shear(0.2, 0.0);

        // Set the transformation and draw the transformed rectangle
        g2d.setTransform(transform);
        g2d.setColor(Color.RED);
        g2d.draw(rect);

        g2d.drawString("Transformed", rect.x + rect.width + 10, rect.y + rect.height / 2);

        // Reset the transformation for further drawing
        g2d.setTransform(new AffineTransform());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Combined 2D Transformations");
        CombinedTransformations panel = new CombinedTransformations();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
