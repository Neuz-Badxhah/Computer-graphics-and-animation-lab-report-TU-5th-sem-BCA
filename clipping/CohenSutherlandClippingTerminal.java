import java.util.Scanner;

public class CohenSutherlandClippingTerminal {

    // Define the clipping window boundaries
    int left, right, top, bottom;
    double x1, y1, x2, y2;

    public CohenSutherlandClippingTerminal() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for clipping window and line segment coordinates
        try {
            System.out.print("Enter left boundary of clipping window: ");
            left = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter right boundary of clipping window: ");
            right = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter top boundary of clipping window: ");
            top = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter bottom boundary of clipping window: ");
            bottom = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter x1 of the line segment: ");
            x1 = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter y1 of the line segment: ");
            y1 = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter x2 of the line segment: ");
            x2 = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter y2 of the line segment: ");
            y2 = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default values.");
            // Default values if input is invalid
            left = 50;
            right = 250;
            top = 50;
            bottom = 150;
            x1 = 30;
            y1 = 30;
            x2 = 300;
            y2 = 200;
        }

        // Perform Cohen-Sutherland clipping
        clipLine(x1, y1, x2, y2);
    }

    private int computeOutcode(double x, double y) {
        int outcode = 0;

        if (x < left) {
            outcode |= 1; // Left
        }
        if (x > right) {
            outcode |= 2; // Right
        }
        if (y < top) {
            outcode |= 4; // Top
        }
        if (y > bottom) {
            outcode |= 8; // Bottom
        }
        return outcode;
    }

    private void clipLine(double x1, double y1, double x2, double y2) {
        int outcode1 = computeOutcode(x1, y1);
        int outcode2 = computeOutcode(x2, y2);

        boolean accept = false;

        while (true) {
            if ((outcode1 | outcode2) == 0) {
                // Both points are inside the clipping window
                accept = true;
                break;
            } else if ((outcode1 & outcode2) != 0) {
                // Both points are outside the same boundary
                break;
            } else {
                // Line segment is partially inside
                int outcodeOut = outcode1 != 0 ? outcode1 : outcode2;
                double x = 0, y = 0;

                // Calculate intersection point
                if ((outcodeOut & 8) != 0) { // Bottom
                    x = x1 + (x2 - x1) * (bottom - y1) / (y2 - y1);
                    y = bottom;
                } else if ((outcodeOut & 4) != 0) { // Top
                    x = x1 + (x2 - x1) * (top - y1) / (y2 - y1);
                    y = top;
                } else if ((outcodeOut & 2) != 0) { // Right
                    y = y1 + (y2 - y1) * (right - x1) / (x2 - x1);
                    x = right;
                } else if ((outcodeOut & 1) != 0) { // Left
                    y = y1 + (y2 - y1) * (left - x1) / (x2 - x1);
                    x = left;
                }

                // Update the endpoint outside the clipping window
                if (outcodeOut == outcode1) {
                    x1 = x;
                    y1 = y;
                    outcode1 = computeOutcode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    outcode2 = computeOutcode(x2, y2);
                }
            }
        }

        // Output the result
        if (accept) {
            System.out.println("Clipped Line Segment:");
            System.out.println("New coordinates: (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
        } else {
            System.out.println("Line is completely outside the clipping window.");
        }
    }

    public static void main(String[] args) {
        new CohenSutherlandClippingTerminal();
    }
}
