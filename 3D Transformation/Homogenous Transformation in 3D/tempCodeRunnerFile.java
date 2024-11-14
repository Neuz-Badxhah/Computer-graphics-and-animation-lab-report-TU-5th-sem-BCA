
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

class Shearing3D extends Frame {

    int x, y, z, width, height, depth;
    double shxy, shxz, shyx, shyz, shzx, shzy;
    double[][] originalVertices;
    double[][] shearedVertices;

    public Shearing3D() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter front-top-left x-coordinate of the original 3D cuboid:");
            x = sc.nextInt();
            System.out.println("Enter front-top-left y-coordinate of the original 3D cuboid:");
            y = sc.nextInt();
            System.out.println("Enter front-top-left z-coordinate of the original 3D cuboid:");
            z = sc.nextInt();
            System.out.println("Enter width (x-direction) of the original 3D cuboid:");
            width = sc.nextInt();
            System.out.println("Enter height (y-direction) of the original 3D cuboid:");
            height = sc.nextInt();
            System.out.println("Enter depth (z-direction) of the original 3D cuboid:");
            depth = sc.nextInt();

            // Shearing factors
            System.out.println("Enter shearing factor shxy (shear x relative to y):");
            shxy = sc.nextDouble();
            System.out.println("Enter shearing factor shxz (shear x relative to z):");
            shxz = sc.nextDouble();
            System.out.println("Enter shearing factor shyx (shear y relative to x):");
            shyx = sc.nextDouble();
            System.out.println("Enter shearing factor shyz (shear y relative to z):");
            shyz = sc.nextDouble();
            System.out.println("Enter shearing factor shzx (shear z relative to x):");
            shzx = sc.nextDouble();
            System.out.println("Enter shearing factor shzy (shear z relative to y):");
            shzy = sc.nextDouble();
        }

        this.setTitle("3D Shearing");
        this.setLayout(null);
        this.setBounds(100, 100, 800, 800);
        this.setVisible(true);

        // Calculate the vertices of the original cuboid
        originalVertices = new double[][]{
            {x, y, z}, // front-top-left
            {x + width, y, z}, // front-top-right
            {x + width, y + height, z}, // front-bottom-right
            {x, y + height, z}, // front-bottom-left
            {x, y, z + depth}, // back-top-left
            {x + width, y, z + depth}, // back-top-right
            {x + width, y + height, z + depth}, // back-bottom-right
            {x, y + height, z + depth} // back-bottom-left
        };

        // Apply shearing transformation
        shearedVertices = new double[8][3];
        for (int i = 0; i < 8; i++) {
            double xOrig = originalVertices[i][0];
            double yOrig = originalVertices[i][1];
            double zOrig = originalVertices[i][2];

            // Apply 3D shearing transformation
            shearedVertices[i][0] = xOrig + shxy * yOrig + shxz * zOrig;
            shearedVertices[i][1] = shyx * xOrig + yOrig + shyz * zOrig;
            shearedVertices[i][2] = shzx * xOrig + shzy * yOrig + zOrig;
        }

        // Add a window listener to handle window closing
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    // Helper function to project 3D point onto 2D plane
    private int[] project(double x, double y, double z) {
        int[] point = new int[2];
        point[0] = (int) (x + z * 0.5);
        point[1] = (int) (y + z * 0.5);
        return point;
    }

    // Helper function to draw a cuboid
    private void drawCuboid(Graphics g, double[][] vertices) {
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

        // Draw sheared cuboid
        g.setColor(Color.RED);
        drawCuboid(g, shearedVertices);
        g.drawString("Sheared Cuboid", 400 + project(shearedVertices[0][0], shearedVertices[0][1], shearedVertices[0][2])[0],
                400 - project(shearedVertices[0][0], shearedVertices[0][1], shearedVertices[0][2])[1] - 10);
    }

    public static void main(String[] args) {
        new Shearing3D();
    }
}
