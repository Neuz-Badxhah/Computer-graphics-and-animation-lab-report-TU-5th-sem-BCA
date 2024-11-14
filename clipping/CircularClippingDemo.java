
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class CircularClippingDemo extends JPanel {

    private final String text = "Clipped text in circular boundary.";
    private final Ellipse2D originalCircle = new Ellipse2D.Double(50, 50, 200, 200); // Original text circle
    private final Ellipse2D clippedCircle = new Ellipse2D.Double(300, 50, 200, 200); // Clipped text circle

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw original text within the first circle (no clipping)
        g2d.setColor(Color.LIGHT_GRAY); 
        g2d.draw(originalCircle); // Draw boundary
        g2d.setColor(Color.BLUE);
        g2d.setFont(new Font("Serif", Font.PLAIN, 16));
        g2d.drawString("Original Text", 50, 30);
        drawText(g2d, text, originalCircle.getBounds(), false);

        // Draw clipped text within the second circle
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.draw(clippedCircle); // Draw boundary

        // Apply clipping on the second circle
        g2d.setClip(clippedCircle);
        g2d.setColor(Color.RED);
        g2d.drawString("Clipped Text", 300, 30);
        drawText(g2d, text, clippedCircle.getBounds(), true);

        // Reset the clipping region after drawing
        g2d.setClip(null);
    }

    // Method to draw text (both clipped and unclipped)
    private void drawText(Graphics2D g2d, String text, Rectangle clipBounds, boolean clipped) {
        FontMetrics fm = g2d.getFontMetrics();
        int lineHeight = fm.getHeight();
        int y = clipBounds.y + fm.getAscent(); // Start drawing text from the top of the clipping rectangle

        // Draw each line of text
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (y > clipBounds.y + clipBounds.height) {
                break; // Stop if the text goes beyond the clipping boundary
            }
            if (clipped) {
                // When clipping, only draw the text that fits within the circular boundary
                drawClippedLine(g2d, line, clipBounds.x, y, clipBounds.width);
            } else {
                // Draw original text normally without any clipping
                g2d.drawString(line, clipBounds.x, y);
            }
            y += lineHeight; // Move to the next line
        }
    }

    // Method to draw a line of text, considering clipping
    private void drawClippedLine(Graphics2D g2d, String line, int x, int y, int maxWidth) {
        FontMetrics fm = g2d.getFontMetrics();
        int startX = x;

        // Draw each character and check if it fits within the circle
        for (char c : line.toCharArray()) {
            int charWidth = fm.charWidth(c);
            if (startX + charWidth > x + maxWidth) {
                break; // Stop if the character is outside the clipping width
            }
            g2d.drawString(Character.toString(c), startX, y);
            startX += charWidth;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circular Clipping");
        CircularClippingDemo panel = new CircularClippingDemo();
        frame.add(panel);
        frame.setSize(550, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
