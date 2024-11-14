import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class MultilineTextClippingDemo extends JPanel {

    private final String text = "This is a long multiline text that might \n need clipping depending on the clipping area defined.\n"
                                + "Each line of this text should be clipped  \n individually if it falls outside the clipping region.\n"
                                + "This ensures that only the visible part \n the text is displayed within the boundary.";
    private final Rectangle clippingRect = new Rectangle(50, 50, 300, 200); // Define clipping region

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Get panel width and split into two for before and after clipping
        int midWidth = getWidth() / 2;

        // -------- Before Clipping (Left Side) --------
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(50, 50, 300, 200);  // Draw reference rectangle for Before Clipping
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.PLAIN, 20));
        drawFullMultilineText(g2d, text, 50, 50, 300);  // Draw full text without clipping

        g2d.drawString("Before Clipping", midWidth / 2 - 50, getHeight() - 20);  // Label for Before Clipping

        // -------- After Clipping (Right Side) --------
        g2d.translate(midWidth, 0);  // Move graphics to the right half of the panel
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.draw(clippingRect);  // Draw reference rectangle for After Clipping
        g2d.setColor(Color.BLACK);

        // Set clipping area
        g2d.setClip(clippingRect);
        drawClippedMultilineText(g2d, text, clippingRect);  // Draw clipped text inside rectangle
        g2d.setClip(null);  // Reset clipping to avoid affecting other drawing operations

        g2d.drawString("After Clipping", midWidth / 2 - 50, getHeight() - 20);  // Label for After Clipping
    }

    // Draw the full multiline text without clipping (for "Before Clipping")
    private void drawFullMultilineText(Graphics2D g2d, String text, int x, int y, int width) {
        FontMetrics fm = g2d.getFontMetrics();
        String[] lines = text.split("\n");

        int lineHeight = fm.getHeight();
        int textY = y + fm.getAscent();  // Start drawing text from the top of the area

        for (String line : lines) {
            g2d.drawString(line, x, textY);
            textY += lineHeight;
        }
    }

    // Draw the clipped multiline text (for "After Clipping")
    private void drawClippedMultilineText(Graphics2D g2d, String text, Rectangle clippingRect) {
        FontMetrics fm = g2d.getFontMetrics();
        String[] lines = text.split("\n");

        int lineHeight = fm.getHeight();
        int y = clippingRect.y + fm.getAscent();  // Start drawing text from the top of the clipping rectangle

        for (String line : lines) {
            if (y > clippingRect.y + clippingRect.height) break;  // Stop if the text is outside the clipping rectangle

            // Draw only the part of the line that fits inside the clipping region
            drawClippedLine(g2d, line, clippingRect.x, y, clippingRect.width);

            y += lineHeight;  // Move to the next line
        }
    }

    // Draw a clipped line within the given width
    private void drawClippedLine(Graphics2D g2d, String line, int x, int y, int maxWidth) {
        FontMetrics fm = g2d.getFontMetrics();
        int startX = x;

        // Measure the width of each character and draw only those that fit within the clipping width
        for (char c : line.toCharArray()) {
            int charWidth = fm.charWidth(c);
            Rectangle2D charBounds = fm.getStringBounds(Character.toString(c), g2d);

            if (startX + charWidth > x + maxWidth) break;  // Stop if the character is outside the clipping width

            g2d.drawString(Character.toString(c), startX, y);
            startX += charWidth;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Multiline Text Clipping Before and After Demo");
        MultilineTextClippingDemo panel = new MultilineTextClippingDemo();
        frame.add(panel);
        frame.setSize(800, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
