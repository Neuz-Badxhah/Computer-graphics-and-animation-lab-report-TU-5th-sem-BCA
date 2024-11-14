
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class WindowToViewportTransformer extends JPanel {

    // Define window space boundaries
    private final double windowX, windowY;
    private final double[] viewportCoordinates;
    private final double windowMinX, windowMinY, windowMaxX, windowMaxY;
    private final double viewportMinX, viewportMinY, viewportMaxX, viewportMaxY;

    public WindowToViewportTransformer(double windowX, double windowY,
            double windowMinX, double windowMinY,
            double windowMaxX, double windowMaxY,
            double viewportMinX, double viewportMinY,
            double viewportMaxX, double viewportMaxY) {
        this.windowX = windowX;
        this.windowY = windowY;
        this.windowMinX = windowMinX;
        this.windowMinY = windowMinY;
        this.windowMaxX = windowMaxX;
        this.windowMaxY = windowMaxY;
        this.viewportMinX = viewportMinX;
        this.viewportMinY = viewportMinY;
        this.viewportMaxX = viewportMaxX;
        this.viewportMaxY = viewportMaxY;

        this.viewportCoordinates = transformToViewport(windowX, windowY,
                windowMinX, windowMinY,
                windowMaxX, windowMaxY,
                viewportMinX, viewportMinY,
                viewportMaxX, viewportMaxY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        // Calculate dimensions
        int windowWidth = (int) (windowMaxX - windowMinX);
        int windowHeight = (int) (windowMaxY - windowMinY);
        int viewportWidth = (int) (viewportMaxX - viewportMinX);
        int viewportHeight = (int) (viewportMaxY - viewportMinY);

        // Draw Window
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(50, 50, windowWidth, windowHeight);
        graphics2D.drawString("Window", 50 + windowWidth / 2 - 20, 40);

        // Draw and label Window Point
        graphics2D.setColor(Color.RED);
        int windowPointX = 50 + (int) ((windowX - windowMinX) / (windowMaxX - windowMinX) * windowWidth);
        int windowPointY = 50 + windowHeight - (int) ((windowY - windowMinY) / (windowMaxY - windowMinY) * windowHeight);
        graphics2D.fillOval(windowPointX - 5, windowPointY - 5, 10, 10);
        graphics2D.drawString("Window Point", windowPointX + 10, windowPointY);

        // Draw Viewport
        graphics2D.setColor(Color.GREEN);
        graphics2D.drawRect(400, 50, viewportWidth, viewportHeight);
        graphics2D.drawString("Viewport", 400 + viewportWidth / 2 - 20, 40);

        // Draw and label Viewport Point
        graphics2D.setColor(Color.BLUE);
        int viewportPointX = 400 + (int) ((viewportCoordinates[0] - viewportMinX) / (viewportMaxX - viewportMinX) * viewportWidth);
        int viewportPointY = 50 + viewportHeight - (int) ((viewportCoordinates[1] - viewportMinY) / (viewportMaxY - viewportMinY) * viewportHeight);
        graphics2D.fillOval(viewportPointX - 5, viewportPointY - 5, 10, 10);
        graphics2D.drawString("Viewport Point", viewportPointX + 10, viewportPointY);
    }

    /**
     * Transforms window coordinates to viewport coordinates.
     *
     * @param wx X-coordinate in window space
     * @param wy Y-coordinate in window space
     * @param windowMinX Minimum X of window
     * @param windowMinY Minimum Y of window
     * @param windowMaxX Maximum X of window
     * @param windowMaxY Maximum Y of window
     * @param viewportMinX Minimum X of viewport
     * @param viewportMinY Minimum Y of viewport
     * @param viewportMaxX Maximum X of viewport
     * @param viewportMaxY Maximum Y of viewport
     * @return Transformed coordinates in viewport space
     */
    public static double[] transformToViewport(double wx, double wy,
            double windowMinX, double windowMinY,
            double windowMaxX, double windowMaxY,
            double viewportMinX, double viewportMinY,
            double viewportMaxX, double viewportMaxY) {
        double xViewport = viewportMinX
                + ((wx - windowMinX) / (windowMaxX - windowMinX)) * (viewportMaxX - viewportMinX);
        double yViewport = viewportMinY
                + ((wy - windowMinY) / (windowMaxY - windowMinY)) * (viewportMaxY - viewportMinY);
        return new double[]{xViewport, yViewport};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input Window Coordinates
        System.out.println("Enter the minimum x coordinate for the window: ");
        double winMinX = scanner.nextDouble();
        System.out.println("Enter the minimum y coordinate for the window: ");
        double winMinY = scanner.nextDouble();
        System.out.println("Enter the maximum x coordinate for the window: ");
        double winMaxX = scanner.nextDouble();
        System.out.println("Enter the maximum y coordinate for the window: ");
        double winMaxY = scanner.nextDouble();

        // Input Viewport Coordinates
        System.out.println("Enter the minimum x coordinate for the viewport: ");
        double vpMinX = scanner.nextDouble();
        System.out.println("Enter the minimum y coordinate for the viewport: ");
        double vpMinY = scanner.nextDouble();
        System.out.println("Enter the maximum x coordinate for the viewport: ");
        double vpMaxX = scanner.nextDouble();
        System.out.println("Enter the maximum y coordinate for the viewport: ");
        double vpMaxY = scanner.nextDouble();

        // Input Point in Window Space
        System.out.println("Enter the x coordinate of the point in window space: ");
        double pointWinX = scanner.nextDouble();
        System.out.println("Enter the y coordinate of the point in window space: ");
        double pointWinY = scanner.nextDouble();

        // Create JFrame and add the panel
        JFrame frame = new JFrame("Window-to-Viewport Transformation");
        WindowToViewportTransformer panel = new WindowToViewportTransformer(
                pointWinX, pointWinY,
                winMinX, winMinY,
                winMaxX, winMaxY,
                vpMinX, vpMinY,
                vpMaxX, vpMaxY
        );
        frame.add(panel);
        frame.setSize(800, 600); // Set size for the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
