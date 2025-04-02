package org.firstinspires.ftc.teamcode.vision;

import android.provider.ContactsContract;

import org.firstinspires.ftc.teamcode.pathing.DriveConstants;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class RectangleDetectionPipeline extends OpenCvPipeline {

    private final List<Rectangle> detectedRectangles = new ArrayList<>();

    @Override
    public Mat processFrame(Mat input) {

        // Set the zoom level (if supported)


        detectedRectangles.clear();

        Mat hsv = new Mat();
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

        // Define color ranges (adjust these values for your environment)
        Scalar[] lowerBounds = {
                new Scalar(CameraConstants.RedLow, 60, 35),   // Red lower bound
                new Scalar(CameraConstants.YellowLow, 70, 50), // Yellow lower bound
                new Scalar(CameraConstants.BlueLow, 100, 100)  // Blue lower bound
        };
        Scalar[] upperBounds = {
                new Scalar(CameraConstants.RedHigh, 255, 255),   // Red upper bound
                new Scalar(CameraConstants.YellowHigh, 255, 255),  // Yellow upper bound
                new Scalar(CameraConstants.BlueHigh, 255, 255)// Blue upper bound
        };



        // Process each color range
        for (int i = 0; i < lowerBounds.length; i++) {
            Mat mask = new Mat();
            Core.inRange(hsv, lowerBounds[i], upperBounds[i], mask);

            // Apply morphological operations to clean up the mask (optional)
            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
            Imgproc.dilate(mask, mask, kernel);
            //Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, kernel);

            // Find contours in the mask
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_TC89_L1);

            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > 10000) { // Minimum area threshold to ignore noise
                    // Get the bounding rectangle for the contour
                    RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(contour.toArray()));
                    // Add the rectangle to the list
                    detectedRectangles.add(new Rectangle(rotatedRect.center, rotatedRect.size, rotatedRect.angle));
                }
            }

            // Clean up
            mask.release();
            hierarchy.release();
        }

        // Draw the detected rectangles on the input frame
        for (Rectangle rect : detectedRectangles) {
            RotatedRect rotatedRect = new RotatedRect(rect.center, rect.size, rect.angle);
            Point[] points = new Point[4];
            rotatedRect.points(points);

            for (int j = 0; j < 4; j++) {
                Imgproc.line(input, points[j], points[(j + 1) % 4], new Scalar(0, 255, 0), 2);
            }

            // Draw center point
            Imgproc.circle(input, rect.center, 5, new Scalar(255, 0, 0), -1);
        }

        hsv.release();
        return input;
    }

    // Public method to get all detected rectangles
    public List<Rectangle> getRectangles() {
        return new ArrayList<>(detectedRectangles); // Return a copy of the list
    }

    // Public method to get the closest rectangle
    public Rectangle getClosestRectangle(Point frameCenter) {
        Rectangle closestRectangle = null;
        double minDistance = Double.MAX_VALUE;

        List<Rectangle> detectedRectanglesCopy = new ArrayList<>(detectedRectangles);
        if(detectedRectanglesCopy.isEmpty() || detectedRectanglesCopy.get(0) == null) {return null;}
        for (Rectangle rect : detectedRectanglesCopy) {
            double distance = Math.sqrt(Math.pow(rect.center.x - frameCenter.x, 2) + Math.pow(rect.center.y - frameCenter.y, 2));
            if (distance < minDistance && rect.size.height*rect.size.width > 10000) {
                minDistance = distance;
                closestRectangle = rect;
            }
        }

        return closestRectangle;
    }
}


