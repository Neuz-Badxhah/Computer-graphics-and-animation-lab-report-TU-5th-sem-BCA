import java.awt.*;
import javax.swing.*;
import java.util.Scanner;

public class TopBottomBorderCircleDrawing extends JPanel {

    private final int x_center, y_center, radius;

    public TopBottomBorderCircleDrawing(int x_center, int y_center, int radius) {
        this.x_center = x_center;
        this.y_center = y_center;
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the panel's width and height
        int width = getWidth();
        int height = getHeight();

        // Find the center of the panel, which represents the origin (0, 0)
        int origin_x = width / 2;
        int origin_y = height / 2;

        // Draw the x and y axes (the borders of the four quadrants)
        drawAxes(g, origin_x, origin_y, width, height);

        // Draw the circle at the user-defined center coordinates relative to the origin
        drawPartialCircleWithBorders(g, origin_x + x_center, origin_y - y_center, radius);

        // Optionally, display the center and radius information
        displayInfo(g, x_center, y_center, radius, origin_x, origin_y);
    }

    private void drawAxes(Graphics g, int origin_x, int origin_y, int width, int height) {
        g.setColor(Color.BLACK);
        // Draw the x-axis (horizontal line)
        g.drawLine(0, origin_y, width, origin_y);
        // Draw the y-axis (vertical line)
        g.drawLine(origin_x, 0, origin_x, height);

        // Mark the origin (0, 0)
        g.setColor(Color.RED);
        g.fillOval(origin_x - 3, origin_y - 3, 6, 6); // Small circle for the origin point
        g.drawString("(0,0)", origin_x + 5, origin_y - 5);
    }

    private void drawPartialCircleWithBorders(Graphics g, int xc, int yc, int r) {
        g.setColor(Color.BLUE);

        // Draw only the top and bottom arcs (border lines for the circle)
        // Top border arc
        g.drawArc(xc - r, yc - r, r * 2, r * 2, 0, 180); // Top half
        // Bottom border arc
        g.drawArc(xc - r, yc - r, r * 2, r * 2, 180, 180); // Bottom half

        // Draw the radius lines connecting the center to the circle's edge
        g.drawLine(xc, yc, xc, yc - r); // Vertical radius to top border
        g.drawLine(xc, yc, xc, yc + r); // Vertical radius to bottom border
    }

    private void displayInfo(Graphics g, int xc, int yc, int r, int origin_x, int origin_y) {
        // Display info about the circle's center and radius relative to the origin
        String info = String.format("Circle Center: (%d, %d) | Radius: %d", xc - origin_x, origin_y - yc, r);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        int padding = 10;
        g.drawString(info, padding, padding + g.getFontMetrics().getAscent());
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter circle x_center relative to origin: ");
            int x_center = sc.nextInt();
            System.out.print("Enter circle y_center relative to origin: ");
            int y_center = sc.nextInt();
            System.out.print("Enter radius: ");
            int radius = sc.nextInt();

            JFrame frame = new JFrame("Top and Bottom Border Circle Drawing");
            TopBottomBorderCircleDrawing panel = new TopBottomBorderCircleDrawing(x_center, y_center, radius);
            frame.add(panel);
            frame.setSize(800, 800);  // Set the size of the window
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
