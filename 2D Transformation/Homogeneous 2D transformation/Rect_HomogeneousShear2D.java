
import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;

public class Rect_HomogeneousShear2D extends Canvas {

    int x, y, width, height;
    double shx, shy; // Shear factors

    public Rect_HomogeneousShear2D() {
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
            System.out.println("Enter the shear factor along x-axis (shx):");
            shx = sc.nextDouble();
            System.out.println("Enter the shear factor along y-axis (shy):");
            shy = sc.nextDouble();
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Set the midpoint of the canvas as the origin
        int midX = getWidth() / 2;
        int midY = getHeight() / 2;

// Define the lengths for the positive and negative axes
        int shortLength = 100; // Length for the axes

// Draw the axes
        g2d.drawLine(midX, midY, getWidth(), midY); // Positive X-axis
        g2d.drawLine(midX, midY, midX - shortLength, midY); // Negative X-axis
        g2d.drawLine(midX, midY, midX, 0); // Positive Y-axis
        g2d.drawLine(midX, midY, midX, midY + shortLength); // Negative Y-axis

// Label X and Y axes
        g2d.drawString("X", getWidth() - 20, midY - 10); // Positive X
        g2d.drawString("X'", midX - shortLength - 20, midY - 10); // Negative X
        g2d.drawString("Y", midX + 10, 20); // Positive Y
        g2d.drawString("Y'", midX + 10, midY + shortLength + 10); // Negative Y

        // Original rectangle (without shearing)
        int[][] originalVertices = {
            {x, y, 1},
            {x + width, y, 1},
            {x + width, y - height, 1},
            {x, y - height, 1}
        };

        // Draw the original rectangle
        g2d.setColor(Color.BLACK);
        drawRectangle(g2d, originalVertices, midX, midY);
        g2d.drawString("Original", midX + x + width / 2, midY - y + height / 2);

        // Apply homogeneous shearing matrix
        int[][] shearedVertices = new int[4][3];
        double[][] shearMatrix = {
            {1, shx, 0},
            {shy, 1, 0},
            {0, 0, 1}
        };

        // Apply shearing transformation to the rectangle's vertices
        for (int i = 0; i < 4; i++) {
            shearedVertices[i] = applyShearing(originalVertices[i], shearMatrix);
        }

        // Draw the sheared rectangle
        g2d.setColor(Color.RED);
        drawRectangle(g2d, shearedVertices, midX, midY);
        g2d.drawString("Sheared", midX + shearedVertices[0][0] + width / 2, midY - shearedVertices[0][1] + height / 2);

        // Display input values
        g2d.setColor(Color.BLUE);
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x: " + x, 50, 70);
        g2d.drawString("y: " + y, 50, 90);
        g2d.drawString("Width: " + width, 50, 110);
        g2d.drawString("Height: " + height, 50, 130);
        g2d.drawString("Shear factor shx: " + shx, 50, 150);
        g2d.drawString("Shear factor shy: " + shy, 50, 170);
    }

    // Method to apply shearing transformation using the shear matrix
    private int[] applyShearing(int[] vertex, double[][] matrix) {
        int[] result = new int[3];

        // Apply the 2D homogeneous shearing transformation
        result[0] = (int) (matrix[0][0] * vertex[0] + matrix[0][1] * vertex[1] + matrix[0][2] * vertex[2]);
        result[1] = (int) (matrix[1][0] * vertex[0] + matrix[1][1] * vertex[1] + matrix[1][2] * vertex[2]);
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
        JFrame frame = new JFrame("Rectangle Shearing using Homogeneous Transformation");
        Rect_HomogeneousShear2D canvas = new Rect_HomogeneousShear2D();
        canvas.setSize(800, 600);
        frame.add(canvas); // Add the canvas to the frame
        frame.pack();
        frame.setVisible(true); // Make the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the program when the window is closed
    }
}
