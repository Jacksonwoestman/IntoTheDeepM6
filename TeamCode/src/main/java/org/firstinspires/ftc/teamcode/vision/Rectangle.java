package org.firstinspires.ftc.teamcode.vision;

import org.opencv.core.Point;
import org.opencv.core.Size;

// Custom Rectangle class to hold rectangle properties
public class Rectangle {
    public Point center;
    public Size size;
    public double angle;

    public Rectangle(Point center, Size size, double angle) {
        this.center = center;
        this.size = size;
        this.angle = angle;
    }
}
