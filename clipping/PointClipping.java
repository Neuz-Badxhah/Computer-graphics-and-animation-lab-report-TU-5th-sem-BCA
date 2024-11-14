import java.awt.*;
import javax.swing.*;

public class PointClipping extends JPanel {

    int rectX, rectY, rectWidth, rectHeight;
    int pointX, pointY;
    boolean pointBefore, pointAfter;

    public PointClipping(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY) {
        this.rectX = rectX;
        this.rectY = rectY;
        this.rectWidth = rectWidth;
        this.rectHeight = rectHeight;
        this.pointX = pointX;
        this.pointY = pointY;

        // Determine if the point is inside before clipping
        pointBefore = isPointInside();
        
        // Determine if the point is still inside after clipping (in this case it won't change)
        pointAfter = pointBefore; // Clipping does not change point in this case
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the rectangle
        g2d.drawRect(rectX, rectY, rectWidth, rectHeight);
        g2d.drawString("Rectangle", rectX + rectWidth + 10, rectY + rectHeight / 2);

        // Draw the point
        g2d.fillOval(pointX - 3, pointY - 3, 6, 6);
        g2d.drawString("Point (" + pointX + ", " + pointY + ")", pointX + 10, pointY);

        // Display the results
        g2d.drawString("Clipping: " + (pointBefore ? "Inside" : "Outside"), rectX, rectY - 10);
    }

    private boolean isPointInside() {
        return pointX >= rectX && pointX <= rectX + rectWidth && pointY >= rectY && pointY <= rectY + rectHeight;
    }

    public static void main(String[] args) {
        // Input values from the terminal
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter the x coordinate of the rectangle's top-left corner: ");
        int rectX = scanner.nextInt();
        System.out.print("Enter the y coordinate of the rectangle's top-left corner: ");
        int rectY = scanner.nextInt();
        System.out.print("Enter the width of the rectangle: ");
        int rectWidth = scanner.nextInt();
        System.out.print("Enter the height of the rectangle: ");
        int rectHeight = scanner.nextInt();

        System.out.print("Enter the x coordinate of the point: ");
        int pointX = scanner.nextInt();
        System.out.print("Enter the y coordinate of the point: ");
        int pointY = scanner.nextInt();

        // Create and display the frame
        JFrame frame = new JFrame("Point Clipping");
        PointClipping panel = new PointClipping(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Print results to the terminal
        System.out.println("Clipping: " + (panel.pointBefore ? "Inside" : "Outside"));
        // Close the scanner
        scanner.close();
    }
}
