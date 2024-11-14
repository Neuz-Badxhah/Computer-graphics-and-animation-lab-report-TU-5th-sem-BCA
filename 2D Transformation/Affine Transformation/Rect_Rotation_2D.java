
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Scanner;
import javax.swing.JFrame;

public class Rect_Rotation_2D extends Canvas {

    int x, y, width, height;
    double angle; // Angle of rotation in degrees

    public Rect_Rotation_2D() {
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
            System.out.println("Enter the angle of rotation (in degrees):");
            angle = Math.toRadians(sc.nextDouble()); // Convert degrees to radians for rotation
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

        // Apply the rotation to the rectangle
        AffineTransform rotate = new AffineTransform();
        rotate.rotate(angle, midX, midY); // Rotate around the center of the canvas
        g2d.setTransform(rotate); // Set the rotation transformation
        g2d.setColor(Color.RED);
        g2d.draw(rect); // Draw the rotated rectangle
        g2d.drawString("Rotated", rect.x + rect.width + 10, rect.y + rect.height / 2);

        // Reset the transform for the input values display
        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.BLUE);

        // Display input values outside the graph area (e.g., top-left corner of canvas)
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x: " + x, 50, 70);
        g2d.drawString("y: " + y, 50, 90);
        g2d.drawString("Width: " + width, 50, 110);
        g2d.drawString("Height: " + height, 50, 130);
        g2d.drawString("Rotation angle (degrees): " + Math.toDegrees(angle), 50, 150);
    }

    public static void main(String[] args) {
        // Create a JFrame window to display the canvas
        JFrame frame = new JFrame("Rectangle Rotation in 2D");
        Rect_Rotation_2D canvas = new Rect_Rotation_2D();
        canvas.setSize(800, 600);
        frame.add(canvas); // Add the canvas to the frame
        frame.pack();
        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
    }
}