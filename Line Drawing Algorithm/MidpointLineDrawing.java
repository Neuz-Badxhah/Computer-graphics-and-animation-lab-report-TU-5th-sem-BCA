import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MidpointLineDrawing extends JFrame {

    private DrawPanel drawPanel;

    public MidpointLineDrawing() {
        setTitle("Midpoint Line Drawing Algorithm");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Drawing panel
        drawPanel = new DrawPanel();
        add(drawPanel, BorderLayout.CENTER);
        
        // Initially set the frame to be invisible
        setVisible(false);
    }

    private class DrawPanel extends JPanel {
        private List<Point> points = new ArrayList<>();

        public void clear() {
            points.clear();
            repaint();
        }

        public void drawLine(int x0, int y0, int x1, int y1) {
            int dx = x1 - x0;
            int dy = y1 - y0;
            int stepX = (dx > 0) ? 1 : -1;
            int stepY = (dy > 0) ? 1 : -1;
            dx = Math.abs(dx);
            dy = Math.abs(dy);

            int d;
            if (dx > dy) {
                d = 2 * dy - dx;
                while (x0 != x1) {
                    points.add(new Point(x0, y0));
                    if (d >= 0) {
                        y0 += stepY;
                        d -= 2 * dx;
                    }
                    x0 += stepX;
                    d += 2 * dy;
                }
            } else {
                d = 2 * dx - dy;
                while (y0 != y1) {
                    points.add(new Point(x0, y0));
                    if (d >= 0) {
                        x0 += stepX;
                        d -= 2 * dy;
                    }
                    y0 += stepY;
                    d += 2 * dx;
                }
            }
            points.add(new Point(x1, y1)); // Add the final point
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.LIGHT_GRAY);
            // Draw grid
            for (int i = 0; i <= getWidth(); i += 20) {
                g.drawLine(i, 0, i, getHeight());
                g.drawLine(0, i, getWidth(), i);
            }

            // Draw points and lines
            g.setColor(Color.RED);
            for (Point p : points) {
                g.fillOval(p.x - 2, p.y - 2, 4, 4); // Draw points
            }

            g.setColor(Color.BLUE);
            if (!points.isEmpty()) {
                Point prev = points.get(0);
                for (Point p : points) {
                    g.drawLine(prev.x, prev.y, p.x, p.y); // Draw line segments
                    prev = p;
                }
            }

            // Display a message if no lines are drawn
            if (points.isEmpty()) {
                g.setColor(Color.BLACK);
                g.drawString("Enter coordinates in the terminal to draw a line.", 150, 300);
            }
        }
    }

    public static void main(String[] args) {
        MidpointLineDrawing frame = new MidpointLineDrawing();

        // Taking input from the terminal
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int x0, y0, x1, y1;

        while (true) {
            try {
                System.out.print("Enter x0: ");
                x0 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter y0: ");
                y0 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter x1: ");
                x1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter y1: ");
                y1 = Integer.parseInt(scanner.nextLine());

                // Show the frame after first valid input
                frame.setVisible(true);
                
                // Draw the line with the given coordinates
                frame.drawPanel.drawLine(x0, y0, x1, y1);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter valid integer coordinates.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
