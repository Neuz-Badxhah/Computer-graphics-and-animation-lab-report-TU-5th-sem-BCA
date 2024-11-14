import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

class Reflection3D extends Frame {

    int[][] originalVertices;
    int[][] reflectedVertices;

    public Reflection3D() {
        try (Scanner sc = new Scanner(System.in)) {
            // Input for the cuboid's front-top-left corner and dimensions
            System.out.println("Enter front-top-left x-coordinate of the original 3D cuboid:");
            int x = sc.nextInt();
            System.out.println("Enter front-top-left y-coordinate of the original 3D cuboid:");
            int y = sc.nextInt();
            System.out.println("Enter front-top-left z-coordinate of the original 3D cuboid:");
            int z = sc.nextInt();
            System.out.println("Enter width (x-direction) of the original 3D cuboid:");
            int width = sc.nextInt();
            System.out.println("Enter height (y-direction) of the original 3D cuboid:");
            int height = sc.nextInt();
            System.out.println("Enter depth (z-direction) of the original 3D cuboid:");
            int depth = sc.nextInt();

            this.setTitle("3D Reflection in Y-axis");
            this.setLayout(null);
            this.setBounds(100, 100, 800, 800);
            this.setVisible(true);

            // Define the vertices of the original cuboid in homogeneous coordinates
            originalVertices = new int[][]{
                {x, y, z, 1}, // front-top-left
                {x + width, y, z, 1}, // front-top-right
                {x + width, y + height, z, 1}, // front-bottom-right
                {x, y + height, z, 1}, // front-bottom-left
                {x, y, z + depth, 1}, // back-top-left
                {x + width, y, z + depth, 1}, // back-top-right
                {x + width, y + height, z + depth, 1}, // back-bottom-right
                {x, y + height, z + depth, 1} // back-bottom-left
            };

            // Apply reflection transformation across the Y-axis
            reflectedVertices = new int[8][4];
            for (int i = 0; i < 8; i++) {
                reflectedVertices[i] = applyReflectionY(originalVertices[i]);
            }
        }

        // Add a window listener to handle window closing
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    // Apply reflection across the Y-axis
    private int[] applyReflectionY(int[] vertex) {
        return new int[]{
            -vertex[0], vertex[1], vertex[2], vertex[3] // Reflect x-coordinate
        };
    }

    // Project a 3D point in homogeneous coordinates onto 2D space
    private int[] project(int x, int y, int z) {
        int[] point = new int[2];
        point[0] = (int) (x + z * 0.5); // Simple perspective projection
        point[1] = (int) (y + z * 0.5);
        return point;
    }

    // Draw the cuboid on the screen using the projected 2D points
    private void drawCuboid(Graphics g, int[][] vertices) {
        int[] p1, p2;

        // Draw front face
        p1 = project(vertices[0][0], vertices[0][1], vertices[0][2]);
        p2 = project(vertices[1][0], vertices[1][1], vertices[1][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        p1 = project(vertices[1][0], vertices[1][1], vertices[1][2]);
        p2 = project(vertices[2][0], vertices[2][1], vertices[2][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        p1 = project(vertices[2][0], vertices[2][1], vertices[2][2]);
        p2 = project(vertices[3][0], vertices[3][1], vertices[3][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        p1 = project(vertices[3][0], vertices[3][1], vertices[3][2]);
        p2 = project(vertices[0][0], vertices[0][1], vertices[0][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);

        // Draw back face
        p1 = project(vertices[4][0], vertices[4][1], vertices[4][2]);
        p2 = project(vertices[5][0], vertices[5][1], vertices[5][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        p1 = project(vertices[5][0], vertices[5][1], vertices[5][2]);
        p2 = project(vertices[6][0], vertices[6][1], vertices[6][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        p1 = project(vertices[6][0], vertices[6][1], vertices[6][2]);
        p2 = project(vertices[7][0], vertices[7][1], vertices[7][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        p1 = project(vertices[7][0], vertices[7][1], vertices[7][2]);
        p2 = project(vertices[4][0], vertices[4][1], vertices[4][2]);
        g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);

        // Draw connecting lines between front and back faces
        for (int i = 0; i < 4; i++) {
            p1 = project(vertices[i][0], vertices[i][1], vertices[i][2]);
            p2 = project(vertices[i + 4][0], vertices[i + 4][1], vertices[i + 4][2]);
            g.drawLine(p1[0] + 400, 400 - p1[1], p2[0] + 400, 400 - p2[1]);
        }
    }

    @Override
    public void paint(Graphics g) {
        // Draw coordinate axes
        g.setColor(Color.GRAY);
        g.drawLine(0, 400, 800, 400);  // X-axis
        g.drawLine(400, 0, 400, 800);  // Y-axis

        // Draw original cuboid
        g.setColor(Color.BLACK);
        drawCuboid(g, originalVertices);
        g.drawString("Original Cuboid", 400 + project(originalVertices[0][0], originalVertices[0][1], originalVertices[0][2])[0],
                400 - project(originalVertices[0][0], originalVertices[0][1], originalVertices[0][2])[1] - 10);

        // Draw reflected cuboid
        g.setColor(Color.RED);
        drawCuboid(g, reflectedVertices);
        g.drawString("Reflected Cuboid", 400 + project(reflectedVertices[0][0], reflectedVertices[0][1], reflectedVertices[0][2])[0],
                400 - project(reflectedVertices[0][0], reflectedVertices[0][1], reflectedVertices[0][2])[1] - 10);
    }

    public static void main(String[] args) {
        new Reflection3D();
    }
}
