import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// Cohen-Sutherland Line Clipping Algorithm Constants
class CohenSutherland {
    public static final int INSIDE = 0; // 0000
    public static final int LEFT = 1;   // 0001
    public static final int RIGHT = 2;  // 0010
    public static final int BOTTOM = 4; // 0100
    public static final int TOP = 8;    // 1000

    private int x_min, y_min, x_max, y_max;

    public CohenSutherland(int x_min, int y_min, int x_max, int y_max) {
        this.x_min = x_min;
        this.y_min = y_min;
        this.x_max = x_max;
        this.y_max = y_max;
    }

    private int computeOutCode(double x, double y) {
        int code = INSIDE;

        if (x < x_min)
            code |= LEFT;
        else if (x > x_max)
            code |= RIGHT;
        if (y < y_min)
            code |= BOTTOM;
        else if (y > y_max)
            code |= TOP;

        return code;
    }

    public boolean clipLine(double[] p1, double[] p2) {
        double x1 = p1[0], y1 = p1[1];
        double x2 = p2[0], y2 = p2[1];

        int outcode1 = computeOutCode(x1, y1);
        int outcode2 = computeOutCode(x2, y2);
        boolean accept = false;

        while (true) {
            if ((outcode1 | outcode2) == 0) {
                // Both endpoints are inside the clipping region
                accept = true;
                break;
            } else if ((outcode1 & outcode2) != 0) {
                // Both endpoints share an outside region
                break;
            } else {
                // Some segment of the line lies inside the clipping rectangle
                double x, y;
                int outcodeOut = (outcode1 != 0) ? outcode1 : outcode2;

                if ((outcodeOut & TOP) != 0) {
                    x = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1);
                    y = y_max;
                } else if ((outcodeOut & BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1);
                    y = y_min;
                } else if ((outcodeOut & RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1);
                    x = x_max;
                } else {
                    y = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1);
                    x = x_min;
                }

                if (outcodeOut == outcode1) {
                    x1 = x;
                    y1 = y;
                    outcode1 = computeOutCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    outcode2 = computeOutCode(x2, y2);
                }
            }
        }

        if (accept) {
            p1[0] = x1;
            p1[1] = y1;
            p2[0] = x2;
            p2[1] = y2;
        }

        return accept;
    }
}

// Line class to store line coordinates
class Line {
    double x1, y1, x2, y2;

    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
}

// JPanel for drawing lines
class LineClippingPanel extends JPanel {
    private List<Line> lines;      // Lines before clipping
    private List<Line> clippedLines; // Clipped lines
    private CohenSutherland clipper;
    private String title;

    public LineClippingPanel(List<Line> lines, int x_min, int y_min, int x_max, int y_max, String title) {
        this.lines = lines;
        this.clippedLines = new ArrayList<>();
        this.clipper = new CohenSutherland(x_min, y_min, x_max, y_max);
        this.title = title;

        // Clip each line and store the result
        for (Line line : lines) {
            double[] p1 = {line.x1, line.y1};
            double[] p2 = {line.x2, line.y2};
            if (clipper.clipLine(p1, p2)) {
                clippedLines.add(new Line(p1[0], p1[1], p2[0], p2[1]));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Define clipping rectangle (window)
        int x_min = 100, y_min = 100, x_max = 400, y_max = 400;
        g.setColor(Color.BLACK);
        g.drawRect(x_min, y_min, x_max - x_min, y_max - y_min); // Draw the clipping window

        // Draw lines based on the title
        if (title.equals("Before Clipping")) {
            // Draw lines before clipping in red
            g.setColor(Color.RED);
            for (Line line : lines) {
                g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
            }
        } else {
            // Draw lines after clipping in green
            g.setColor(Color.GREEN);
            for (Line line : clippedLines) {
                g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
            }
        }

        // Label the stage
        g.setColor(Color.BLACK);
        g.drawString(title, 20, 20);
    }
}

// Main class for the JFrame and panel
public class LineClipping extends JFrame {
    public LineClipping() {
        setTitle("Line Clipping Before and After");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Define lines for clipping
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(50, 50, 200, 300));   // Outside
        lines.add(new Line(150, 150, 350, 350)); // Completely inside
        lines.add(new Line(350, 50, 500, 350));  // Partial clipping

        // Clipping window coordinates (x_min, y_min, x_max, y_max)
        int x_min = 100, y_min = 100, x_max = 400, y_max = 400;

        // Create two panels for before and after clipping
        LineClippingPanel beforePanel = new LineClippingPanel(lines, x_min, y_min, x_max, y_max, "Before Clipping");
        LineClippingPanel afterPanel = new LineClippingPanel(lines, x_min, y_min, x_max, y_max, "After Clipping");

        // Set layout to display panels side by side
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(beforePanel);
        mainPanel.add(afterPanel);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LineClipping frame = new LineClipping();
            frame.setVisible(true);
        });
    }
}
