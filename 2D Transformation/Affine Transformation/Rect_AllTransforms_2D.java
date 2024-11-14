
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Scanner;
import javax.swing.JFrame;

public class Rect_AllTransforms_2D extends Canvas {

    int x, y, width, height, tx, ty;
    double scaleX, scaleY, shearX, shearY, angle;

    public Rect_AllTransforms_2D() {
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
            System.out.println("Enter the shearing factor along the x-axis (shearX):");
            shearX = sc.nextDouble();
            System.out.println("Enter the shearing factor along the y-axis (shearY):");
            shearY = sc.nextDouble();
            System.out.println("Enter the translation along the x-axis (tx):");
            tx = sc.nextInt();
            System.out.println("Enter the translation along the y-axis (ty):");
            ty = sc.nextInt();
            System.out.println("Enter the rotation angle (in degrees):");
            angle = Math.toRadians(sc.nextDouble()); // Convert angle to radians
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
        Rectangle rect = new Rectangle(x, -y, width, height); // Use (x, y) for relative coordinates

        // Save the original transform to reset later
        AffineTransform originalTransform = g2d.getTransform();

        // Apply all transformations in the correct order:
        // 1. Translate the rectangle
        // 2. Rotate around the origin (midX, midY)
        // 3. Scale the rectangle
        // 4. Shear the rectangle
        // Apply translation
        AffineTransform transform = new AffineTransform();
        transform.translate(midX + tx, midY + ty);

        // Apply rotation (around the center of the rectangle)
        transform.rotate(angle, rect.getX() + rect.width / 2, rect.getY() + rect.height / 2);

        // Apply scaling
        transform.scale(scaleX, scaleY);

        // Apply shearing
        transform.shear(shearX, shearY);

        // Set the transformation
        g2d.setTransform(transform);

        // Draw the transformed rectangle
        g2d.setColor(Color.RED);
        g2d.draw(rect);
        g2d.drawString("Transformed", (int) rect.getX() + rect.width + 10, (int) rect.getY() + rect.height / 2);

        // Reset the transform to original to display the original rectangle
        g2d.setTransform(originalTransform);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(midX + x, midY - y, width, height);
        g2d.drawString("Original", midX + x + width + 10, midY - y + height / 2);

        // Display input values outside the graph area (e.g., top-left corner of canvas)
        g2d.setColor(Color.BLUE);
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x: " + x, 50, 70);
        g2d.drawString("y: " + y, 50, 90);
        g2d.drawString("Width: " + width, 50, 110);
        g2d.drawString("Height: " + height, 50, 130);
        g2d.drawString("Translation tx: " + tx, 50, 150);
        g2d.drawString("Translation ty: " + ty, 50, 170);
        g2d.drawString("Scale factor X: " + scaleX, 50, 190);
        g2d.drawString("Scale factor Y: " + scaleY, 50, 210);
        g2d.drawString("Shear factor X: " + shearX, 50, 230);
        g2d.drawString("Shear factor Y: " + shearY, 50, 250);
        g2d.drawString("Rotation angle (degrees): " + Math.toDegrees(angle), 50, 270);
    }

    public static void main(String[] args) {
        // Create a JFrame window to display the canvas
        JFrame frame = new JFrame("Rectangle Transformations (Scale, Rotate, Shear, Translate)");
        Rect_AllTransforms_2D canvas = new Rect_AllTransforms_2D();
        canvas.setSize(800, 600);
        frame.add(canvas); // Add the canvas to the frame
        frame.pack();
        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
    }
}
