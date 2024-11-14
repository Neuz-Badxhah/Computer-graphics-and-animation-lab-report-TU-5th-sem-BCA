
import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;

public class Rect_HomogeneousRotation_2D extends Canvas {

    int x, y, width, height;
    double theta; // Rotation angle in degrees

    public Rect_HomogeneousRotation_2D() {
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
            System.out.println("Enter the rotation angle (theta) in degrees:");
            theta = Math.toRadians(sc.nextDouble()); // Convert degrees to radians
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Set the midpoint of the canvas as the origin (400, 300 for 800x600 window)
        int midX = getWidth() / 2;
        int midY = getHeight() / 2;

        // Define the lengths for the positive and negative axes
        int shortLength = 100; // Short length for X' and Y'

        // Draw the axes
        g2d.drawLine(midX, midY, getWidth(), midY); // X-axis
        g2d.drawLine(midX, midY, midX - shortLength, midY); // Negative X-axis
        g2d.drawLine(midX, midY, midX, 0); // Y-axis
        g2d.drawLine(midX, midY, midX, midY + shortLength); // Negative Y-axis

        // Label X and Y axes
        g2d.drawString("X", getWidth() - 20, midY - 10); // Positive X
        g2d.drawString("X'", midX - shortLength - 20, midY - 10); // Negative X
        g2d.drawString("Y", midX + 10, 20); // Positive Y
        g2d.drawString("Y'", midX + 10, midY + shortLength + 10); // Negative Y

        // Original rectangle (without rotation)
        int[][] originalVertices = {
            {x, y, 1},
            {x + width, y, 1},
            {x + width, y - height, 1},
            {x, y - height, 1}
        };

        // Draw the original rectangle
        g2d.setColor(Color.BLACK);
        drawRectangle(g2d, originalVertices, midX, midY);
        // Position the "Original" label at the center of the original rectangle
        g2d.drawString("Original", midX + x + width / 2, midY - y + height / 2);

        // Apply homogeneous rotation matrix
        int[][] rotatedVertices = new int[4][3];
        for (int i = 0; i < 4; i++) {
            rotatedVertices[i] = multiplyMatrixWithRotation(originalVertices[i], theta);
        }

        // Draw the rotated rectangle
        g2d.setColor(Color.RED);
        drawRectangle(g2d, rotatedVertices, midX, midY);
        // Position the "Rotated" label at the center of the rotated rectangle
        g2d.drawString("Rotated", midX + rotatedVertices[0][0] + width / 2, midY - rotatedVertices[0][1] + height / 2);

        // Display input values
        g2d.setColor(Color.BLUE);
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x: " + x, 50, 70);
        g2d.drawString("y: " + y, 50, 90);
        g2d.drawString("Width: " + width, 50, 110);
        g2d.drawString("Height: " + height, 50, 130);
        g2d.drawString("Rotation angle (theta): " + Math.toDegrees(theta), 50, 150);
    }

    // Method to multiply a point with the rotation matrix
    private int[] multiplyMatrixWithRotation(int[] vertex, double theta) {
        int[] result = new int[3];

        // Apply 2D homogeneous rotation matrix
        result[0] = (int) (vertex[0] * Math.cos(theta) - vertex[1] * Math.sin(theta));
        result[1] = (int) (vertex[0] * Math.sin(theta) + vertex[1] * Math.cos(theta));
        result[2] = 1; // Homogeneous coordinate remains 1

        return result;
    }

    // Method to draw the rectangle by connecting vertices
    private void drawRectangle(Graphics2D g2d, int[][] vertices, int midX, int midY) {
        g2d.drawLine(midX + vertices[0][0], midY - vertices[0][1], midX + vertices[1][0], midY - vertices[1][1]);
        g2d.drawLine(midX + vertices[1][0], midY - vertices[1][1], midX + vertices[2][0], midY - vertices[2][1]);
        g2d.drawLine(midX + vertices[2][0], midY - vertices[2][1], midX + vertices[3][0], midY - vertices[3][1]);
        g2d.drawLine(midX + vertices[3][0], midY - vertices[3][1], midX + vertices[0][0], midY - vertices[0][1]);
    }

    public static void main(String[] args) {
        // Create a JFrame window to display the canvas
        JFrame frame = new JFrame("Rectangle Rotation using Homogeneous Transformation");
        Rect_HomogeneousRotation_2D canvas = new Rect_HomogeneousRotation_2D();
        canvas.setSize(800, 600);
        frame.add(canvas); // Add the canvas to the frame
        frame.pack();
        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
    }
}
