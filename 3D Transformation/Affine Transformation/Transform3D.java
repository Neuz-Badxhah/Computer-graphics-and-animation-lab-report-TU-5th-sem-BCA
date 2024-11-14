
import java.awt.*;
import javax.swing.*;

public class Transform3D extends JPanel {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Cube vertices
    private final double[][] vertices = {
        {-0.5, -0.5, -0.5},
        {0.5, -0.5, -0.5},
        {0.5, 0.5, -0.5},
        {-0.5, 0.5, -0.5},
        {-0.5, -0.5, 0.5},
        {0.5, -0.5, 0.5},
        {0.5, 0.5, 0.5},
        {-0.5, 0.5, 0.5}
    };

    // Cube edges
    private final int[][] edges = {
        {0, 1}, {1, 2}, {2, 3}, {3, 0}, // Front face
        {4, 5}, {5, 6}, {6, 7}, {7, 4}, // Back face
        {0, 4}, {1, 5}, {2, 6}, {3, 7} // Connecting edges
    };

    private double[][] transformVertices;

    public Transform3D() {
        transformVertices = new double[vertices.length][3];

        // Initialize transformed vertices
        for (int i = 0; i < vertices.length; i++) {
            System.arraycopy(vertices[i], 0, transformVertices[i], 0, 3);
        }

        // Apply transformations
        applyTransformations();

        // Setup JFrame
        JFrame frame = new JFrame("3D Transformations");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(this);
        frame.setVisible(true);
    }

    private void applyTransformations() {
        // Translation
        double tx = 200;
        double ty = 100;
        double tz = 0;

        // Scaling
        double sx = 100;
        double sy = 100;
        double sz = 100;

        // Rotation angles (in radians)
        double angleX = Math.toRadians(30);
        double angleY = Math.toRadians(45);
        double angleZ = Math.toRadians(60);

        // Rotation matrices
        double[][] rotationX = {
            {1, 0, 0},
            {0, Math.cos(angleX), -Math.sin(angleX)},
            {0, Math.sin(angleX), Math.cos(angleX)}
        };

        double[][] rotationY = {
            {Math.cos(angleY), 0, Math.sin(angleY)},
            {0, 1, 0},
            {-Math.sin(angleY), 0, Math.cos(angleY)}
        };

        double[][] rotationZ = {
            {Math.cos(angleZ), -Math.sin(angleZ), 0},
            {Math.sin(angleZ), Math.cos(angleZ), 0},
            {0, 0, 1}
        };

        // Apply rotations
        transformVertices = multiplyMatrices(rotationZ, transformVertices);
        transformVertices = multiplyMatrices(rotationY, transformVertices);
        transformVertices = multiplyMatrices(rotationX, transformVertices);

        // Apply scaling
        for (int i = 0; i < transformVertices.length; i++) {
            transformVertices[i][0] *= sx;
            transformVertices[i][1] *= sy;
            transformVertices[i][2] *= sz;
        }

        // Apply translation
        for (int i = 0; i < transformVertices.length; i++) {
            transformVertices[i][0] += tx;
            transformVertices[i][1] += ty;
            transformVertices[i][2] += tz;
        }
    }

    private double[][] multiplyMatrices(double[][] matrix, double[][] vectors) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int numVectors = vectors[0].length;

        double[][] result = new double[rows][numVectors];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < numVectors; j++) {
                result[i][j] = 0;
                for (int k = 0; k < cols; k++) {
                    result[i][j] += matrix[i][k] * vectors[k][j];
                }
            }
        }

        return result;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Center of the panel
        int cx = WIDTH / 2;
        int cy = HEIGHT / 2;

        // Draw edges
        g2d.setColor(Color.BLACK);
        for (int[] edge : edges) {
            int x1 = (int) transformVertices[edge[0]][0] + cx;
            int y1 = (int) transformVertices[edge[0]][1] + cy;
            int x2 = (int) transformVertices[edge[1]][0] + cx;
            int y2 = (int) transformVertices[edge[1]][1] + cy;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Transform3D::new);
    }
}
