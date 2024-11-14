
import java.awt.*;
import java.util.Scanner;
import javax.swing.JFrame;

class Translation_2D extends Canvas {

    int x1, y1, x2, y2, tx, ty;
    int px1, px2, py1, py2;

    public Translation_2D() {
        // Input values for the line and translation
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter x1:");
            x1 = sc.nextInt();
            System.out.println("Enter y1:");
            y1 = sc.nextInt();
            System.out.println("Enter x2:");
            x2 = sc.nextInt();
            System.out.println("Enter y2:");
            y2 = sc.nextInt();
            System.out.println("Enter translation along x-axis (tx):");
            tx = sc.nextInt();
            System.out.println("Enter translation along y-axis (ty):");
            ty = sc.nextInt();
        }

        // Compute translated points
        px1 = x1 + tx;
        px2 = x2 + tx;
        py1 = y1 + ty;
        py2 = y2 + ty;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Define midpoints for X and Y axes (center of the canvas)
        int midX = getWidth() / 2;
        int midY = getHeight() / 2;
        int shortLength = 100; // Length for the short negative axes

        // Draw X and Y axes (positive and negative)
        g2d.setColor(Color.GRAY);

        // Draw positive X-axis (long) and negative X' (short)
        g2d.drawLine(midX, midY, getWidth(), midY); // X-axis (positive long)
        g2d.drawLine(midX, midY, midX - shortLength, midY); // X' (negative short)

        // Draw positive Y-axis (long) and negative Y' (short)
        g2d.drawLine(midX, midY, midX, 0); // Y-axis (positive long)
        g2d.drawLine(midX, midY, midX, midY + shortLength); // Y' (negative short)

        // Label X and Y axes
        g2d.setColor(Color.BLACK);
        g2d.drawString("X", getWidth() - 20, midY - 10); // Label for positive X
        g2d.drawString("X'", midX - shortLength - 20, midY - 10); // Label for negative X (X')
        g2d.drawString("Y", midX + 10, 20); // Label for positive Y
        g2d.drawString("Y'", midX + 10, midY + shortLength + 20); // Label for negative Y (Y')

        // Draw the original line before translation
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x1 + midX, midY - y1, x2 + midX, midY - y2); // Adjust to fit graph area
        g2d.drawString("Before translation", x1 + midX + 10, midY - y1 - 10);

        // Draw the translated line
        g2d.setColor(Color.RED);
        g2d.drawLine(px1 + midX, midY - py1, px2 + midX, midY - py2); // Adjust for translation and graph
        g2d.drawString("After translation", px1 + midX + 10, midY - py1 - 10);

        // Display input values outside the graph
        g2d.setColor(Color.BLUE);
        g2d.drawString("Input values:", 50, 50);
        g2d.drawString("x1: " + x1, 50, 70);
        g2d.drawString("y1: " + y1, 50, 90);
        g2d.drawString("x2: " + x2, 50, 110);
        g2d.drawString("y2: " + y2, 50, 130);
        g2d.drawString("tx: " + tx, 50, 150);
        g2d.drawString("ty: " + ty, 50, 170);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Line Translation in 2D");
        Translation_2D canvas = new Translation_2D();
        canvas.setSize(800, 800);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
