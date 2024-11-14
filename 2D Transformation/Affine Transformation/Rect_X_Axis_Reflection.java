import java.awt.*;
import javax.swing.JFrame;
import java.awt.geom.AffineTransform;
import java.util.Scanner;

public class Rect_X_Axis_Reflection extends Canvas {

    int x, y, width, height;

    public Rect_X_Axis_Reflection() {
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

        // Draw the axes
        g2d.drawLine(midX, midY, getWidth(), midY); // X-axis (positive)
        g2d.drawLine(midX, midY, 0, midY); // X-axis (negative)
        g2d.drawLine(midX, midY, midX, 0); // Y-axis (positive)
        g2d.drawLine(midX, midY, midX, getHeight()); // Y-axis (negative)

        // Create a rectangle object with user input values relative to the graph center
        Rectangle rect = new Rectangle(x, -y, width, height); // Original rectangle

        // Draw the original rectangle in black
        g2d.setColor(Color.BLACK);
        g2d.drawRect(midX + x, midY - y, width, height);
        g2d.drawString("Original", midX + x + width + 10, midY - y + height / 2);

        // Draw the reflected rectangle in red
        g2d.setColor(Color.RED);
        g2d.drawRect(midX + x, midY + y, width, height); // Reflection across X-axis
        g2d.drawString("Reflected", midX + x + width + 10, midY + y + height / 2);
    }

    public static void main(String[] args) {
        // Create a JFrame window to display the canvas
        JFrame frame = new JFrame("Rectangle X-axis Reflection");
        Rect_X_Axis_Reflection canvas = new Rect_X_Axis_Reflection();
        canvas.setSize(800, 600);
        frame.add(canvas); // Add the canvas to the frame
        frame.pack();
        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
    }
}
