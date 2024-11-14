
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Scanner;
import javax.swing.JFrame;

public class Rect_Scaling_2D extends Canvas {

    int x, y, width, height;
    double scaleX, scaleY; // Scaling factors for X and Y axes

    public Rect_Scaling_2D() {
        // Get input values from the user
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the x-coordinate of the rectangle:");
            x = sc.nextInt();
            System.out.println("Enter the y-coordinate of the rectangle:");
            y = sc.nextInt();
            System.out.println("Enter the width of the rectangle:");
            width = sc.nextInt();
            System.out.println("Enter the height of the rectangle:");
            height = sc.nextInt();
            System.out.println("Enter the scaling factor along the x-axis (scaleX):");
            scaleX = sc.nextDouble();
            System.out.println("Enter the scaling factor along the y-axis (scaleY):");
            scaleY = sc.nextDouble();
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Set the midpoint of the canvas as the origin (400, 300 for 800x600 window)
        int midX = getWidth() / 2;
        int midY = getHeight() / 2;

        // Reset transformation to draw the axes correctly
        g2d.setTransform(new AffineTransform());

        // Define the lengths for the positive and negative axes
        int shortLength = 100; // Short length for X' and Y'
        int longLengthX = getWidth(); // Full length for X-axis
        int longLengthY = getHeight(); // Full length for Y-axis

        // Draw positive X-axis (long) and negative X' (short)
        g2d.drawLine(midX, midY, getWidth(), midY); // X-axis (positive long)
        g2d.drawLine(midX, midY, midX - shortLength, midY); // X' (negative short)

        // Draw positive Y-axis (long) and negative Y' (short)
        g2d.drawLine(midX, midY, midX, 0); // Y-axis (positive long)
        g2d.drawLine(midX, midY, midX, midY + shortLength); // Y' (negative short)

        // Label X and Y axes
        g2d.drawString("X", getWidth() - 20, midY - 10); // Label for positive X
        g2d.drawString("X'", midX - shortLength - 20, midY - 10); // Label for negative X (X')
        g2d.drawString("Y", midX + 10, 20); // Label for positive Y
        g2d.drawString("Y'", midX + 10, midY + shortLength + 10); // Label for negative Y (Y')

        // Create a rectangle object with user input values relative to the graph center
        Rectangle rect = new Rectangle(midX + x, midY - y, width, height); // Adjust to origin at (midX, midY)

        // Draw the original rectangle
        g2d.setColor(Color.BLACK);
        g2d.draw(rect);
        g2d.drawString("Original", rect.x + rect.width + 10, rect.y + rect.height / 2);

        // Apply the scaling transformation (scaleX, scaleY) to the rectangle
        AffineTransform scale = new AffineTransform();
        scale.translate(midX, midY); // Translate to the origin (midpoint) before scaling
        scale.scale(scaleX, scaleY); // Apply the scaling transformation
        scale.translate(-midX, -midY); // Translate back after scaling
        g2d.setTransform(scale); // Set the transformation
        g2d.setColor(Color.RED);
        g2d.draw(rect); // Draw the scaled rectangle
        g2d.drawString("Scaled", rect.x + rect.width + 10, rect.y + rect.height / 2);

        // Reset the transform for the input values display
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.BLUE);

        // Display input values outside the graph area (e.g., top-left corner of canvas)
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x: " + x, 50, 70);
        g2d.drawString("y: " + y, 50, 90);
        g2d.drawString("Width: " + width, 50, 110);
        g2d.drawString("Height: " + height, 50, 130);
        g2d.drawString("Scale factor X: " + scaleX, 50, 150);
        g2d.drawString("Scale factor Y: " + scaleY, 50, 170);
    }

    public static void main(String[] args) {
        // Create a JFrame window to display the canvas
        JFrame frame = new JFrame("Rectangle Scaling in 2D");
        Rect_Scaling_2D canvas = new Rect_Scaling_2D();
        canvas.setSize(800, 600);
        frame.add(canvas); // Add the canvas to the frame
        frame.pack();
        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
    }
}
