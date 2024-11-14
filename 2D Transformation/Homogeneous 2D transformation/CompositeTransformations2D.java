
import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;

public class CompositeTransformations2D extends Canvas {

    int x, y, width, height;
    double theta, scaleX, scaleY, shx, shy; // Rotation angle, scaling factors, shear factors

    public CompositeTransformations2D() {
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
            System.out.println("Enter the scaling factor along x-axis (scaleX):");
            scaleX = sc.nextDouble();
            System.out.println("Enter the scaling factor along y-axis (scaleY):");
            scaleY = sc.nextDouble();
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

        // Draw the axes
        drawAxes(g2d, midX, midY);

        // Original rectangle
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

        // Apply transformations: rotation -> scaling -> shearing
        int[][] transformedVertices = applyTransformations(originalVertices);

        // Draw the transformed rectangle
        g2d.setColor(Color.RED);
        drawRectangle(g2d, transformedVertices, midX, midY);
        g2d.drawString("Transformed", midX + transformedVertices[0][0] + width / 2, midY - transformedVertices[0][1] + height / 2);

        // Display input values
        displayInputValues(g2d);
    }

    private int[][] applyTransformations(int[][] vertices) {
        // Apply rotation
        for (int i = 0; i < 4; i++) {
            vertices[i] = multiplyMatrixWithRotation(vertices[i], theta);
        }

        // Apply scaling
        for (int i = 0; i < 4; i++) {
            vertices[i] = multiplyMatrixWithScaling(vertices[i], scaleX, scaleY);
        }

        // Apply shearing
        for (int i = 0; i < 4; i++) {
            vertices[i] = multiplyMatrixWithShearing(vertices[i], shx, shy);
        }

        return vertices;
    }

    // Method to multiply a point with the rotation matrix
    private int[] multiplyMatrixWithRotation(int[] vertex, double theta) {
        int[] result = new int[3];
        result[0] = (int) (vertex[0] * Math.cos(theta) - vertex[1] * Math.sin(theta));
        result[1] = (int) (vertex[0] * Math.sin(theta) + vertex[1] * Math.cos(theta));
        result[2] = 1;
        return result;
    }

    // Method to multiply a point with the scaling matrix
    private int[] multiplyMatrixWithScaling(int[] vertex, double scaleX, double scaleY) {
        int[] result = new int[3];
        result[0] = (int) (vertex[0] * scaleX);
        result[1] = (int) (vertex[1] * scaleY);
        result[2] = 1;
        return result;
    }

    // Method to multiply a point with the shearing matrix
    private int[] multiplyMatrixWithShearing(int[] vertex, double shx, double shy) {
        int[] result = new int[3];
        result[0] = (int) (vertex[0] + vertex[1] * shx);
        result[1] = (int) (vertex[1] + vertex[0] * shy);
        result[2] = 1;
        return result;
    }

    // Method to draw the rectangle by connecting vertices
    private void drawRectangle(Graphics2D g2d, int[][] vertices, int midX, int midY) {
        g2d.drawLine(midX + vertices[0][0], midY - vertices[0][1], midX + vertices[1][0], midY - vertices[1][1]);
        g2d.drawLine(midX + vertices[1][0], midY - vertices[1][1], midX + vertices[2][0], midY - vertices[2][1]);
        g2d.drawLine(midX + vertices[2][0], midY - vertices[2][1], midX + vertices[3][0], midY - vertices[3][1]);
        g2d.drawLine(midX + vertices[3][0], midY - vertices[3][1], midX + vertices[0][0], midY - vertices[0][1]);
    }

    private void drawAxes(Graphics2D g2d, int midX, int midY) {
        int shortLength = 100; // Length for the axes
        g2d.drawLine(midX, midY, getWidth(), midY); // Positive X-axis
        g2d.drawLine(midX, midY, midX - shortLength, midY); // Negative X-axis
        g2d.drawLine(midX, midY, midX, 0); // Positive Y-axis
        g2d.drawLine(midX, midY, midX, midY + shortLength); // Negative Y-axis
        g2d.drawString("X", getWidth() - 20, midY - 10); // Positive X
        g2d.drawString("X'", midX - shortLength - 20, midY - 10); // Negative X
        g2d.drawString("Y", midX + 10, 20); // Positive Y
        g2d.drawString("Y'", midX + 10, midY + shortLength + 10); // Negative Y
    }

    private void displayInputValues(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x: " + x, 50, 70);
        g2d.drawString("y: " + y, 50, 90);
        g2d.drawString("Width: " + width, 50, 110);
        g2d.drawString("Height: " + height, 50, 130);
        g2d.drawString("Rotation angle (theta): " + Math.toDegrees(theta), 50, 150);
        g2d.drawString("Scaling factors: (scaleX: " + scaleX + ", scaleY: " + scaleY + ")", 50, 170);
        g2d.drawString("Shear factors: (shx: " + shx + ", shy: " + shy + ")", 50, 190);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Composite Transformations using Homogeneous Coordinates");
        CompositeTransformations2D canvas = new CompositeTransformations2D();
        canvas.setSize(800, 600);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
