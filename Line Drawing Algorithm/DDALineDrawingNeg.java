import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class DDALineDrawingNeg extends JPanel {

    private int x0, y0, x1, y1;

    public DDALineDrawingNeg(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g); // Draw axes
        drawDDALine(g, x0, y0, x1, y1); // Draw DDA line
    }

    // Method to draw the axes
    private void drawAxes(Graphics g) {
        // Draw x-axis
        g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        g.drawString("X", getWidth() - 20, getHeight() / 2 - 5);

        // Draw y-axis
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        g.drawString("Y", getWidth() / 2 + 5, 20);
        
        // Draw origin point
        g.drawString("O", getWidth() / 2 + 5, getHeight() / 2 + 15);
    }

    private void drawDDALine(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        // Determine the steps required for the line drawing
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        // Calculate the increment values for each step
        float xInc = (float) dx / steps;
        float yInc = (float) dy / steps;

        float x = x0;
        float y = y0;

        // Draw the starting point
        drawPoint(g, x0, y0, Color.GREEN, "starting Point");

        for (int i = 0; i <= steps; i++) {
            g.fillRect(Math.round(x) + getWidth() / 2, getHeight() / 2 - Math.round(y), 1, 1); // Draw the line
            x += xInc;
            y += yInc;
        }

        // Draw the ending point
        drawPoint(g, x1, y1, Color.RED, "Ending Point");
    }

    private void drawPoint(Graphics g, int x, int y, Color color, String label) {
        g.setColor(color);
        g.fillOval(x + getWidth() / 2 - 3, getHeight() / 2 - y - 3, 6, 6); // Adjust position to center on the point
        g.drawString("(" + x + ", " + y + ")", x + getWidth() / 2 + 5, getHeight() / 2 - y - 5); // Label the point
        g.setColor(Color.BLACK); // Reset color to black for drawing the line
    }

    public static void main(String[] args) {
        // Take user input for the start and end points of the line
        try (Scanner scanner = new Scanner(System.in)) {
            // Take user input for the start and end points of the line
            System.out.print("Enter x0 (start x): ");
            int x0 = scanner.nextInt();
            System.out.print("Enter y0 (start y): ");
            int y0 = scanner.nextInt();
            System.out.print("Enter x1 (end x): ");
            int x1 = scanner.nextInt();
            System.out.print("Enter y1 (end y): ");
            int y1 = scanner.nextInt();
            
            // Create a window and add the DDA line drawing panel
            JFrame frame = new JFrame("DDA Line Drawing Algorithm with Steep and Negative Slopes");
            DDALineDrawingNeg panel = new DDALineDrawingNeg(x0, y0, x1, y1);
            frame.add(panel);
            frame.setSize(800, 400); // Adjust the size to fit the graph
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
