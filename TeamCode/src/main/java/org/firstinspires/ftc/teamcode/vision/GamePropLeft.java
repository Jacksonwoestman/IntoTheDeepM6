package org.firstinspires.ftc.teamcode.vision;

import static java.lang.Math.abs;

import com.qualcomm.hardware.dfrobot.HuskyLens;

import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class GamePropLeft extends OpenCvPipeline {

    enum gamePropPosition {
        LEFT, CENTER, RIGHT
    }

    TelemetryImpl telemetry;

    //RYAN: Change the co-ordinates for LEFT Point
    Point Mid_pointA = new Point(750, 500);
    Point Mid_pointB = new Point(800,600);

    Point Left_pointA =  new Point(350, 525);
    Point Left_pointB =  new Point(450, 625);

    Point Right_pointA = new Point(875, 325);
    Point Right_pointB = new Point(975,425);


    public static gamePropPosition position = gamePropPosition.LEFT; //Default Position

    Mat mid_Hue = null;
    Mat left_Hue = null;
    Mat right_Hue = null;
    Mat HSV = new Mat();
    Mat hue = new Mat();
    int avg1, avg2, avg3;
    Scalar RED = new Scalar(255.0, 0.0, 0.0);
    Scalar BLUE = new Scalar(0.0, 0.0, 255.0);

    public void inputToHue(Mat input) {
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
        Core.extractChannel(HSV, hue, 1); //Gets the HUE value out
    }

    @Override
    public void init(Mat firstFrame) {
        inputToHue(firstFrame);


        left_Hue = hue.submat(new Rect(Left_pointA, Left_pointB));
        mid_Hue = hue.submat(new Rect(Mid_pointA, Mid_pointB));
        right_Hue = hue.submat(new Rect(Right_pointA, Right_pointB));
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToHue(input);

        avg1 = (int) Core.mean(left_Hue).val[0];
        avg2 = (int) Core.mean(mid_Hue).val[0];
        avg3 = abs(avg1 - avg2);

        int max = Math.max(avg1, avg2);

        Imgproc.rectangle(
                input,
                Left_pointA,
                Left_pointB,
                RED, 4);
        Imgproc.rectangle(
                input,
                Mid_pointA,
                Mid_pointB,
                RED, 4);


        if (avg3 < 50) {
            position = gamePropPosition.RIGHT;
        } else if (max == avg2) {
            position = gamePropPosition.CENTER;
        } else {
            position = gamePropPosition.LEFT;
        }
        //telemetry.addData("[avg1]", avg1);
        // telemetry.addData("[avg2]", avg2);
        //telemetry.addData("[avg3]", avg3);
        //telemetry.addData("Position", position);

        // telemetry.update();
        return input;
    }
}

/*
  /*public String huskyLens() {
    huskyTelemetry = "";
    try {
      // Attempt to get the detected blocks (array, not List)
      myHuskyLensBlocks = huskylens.blocks();

      // Display block count and check if blocks are detected
      huskyTelemetry += "Block count, " + myHuskyLensBlocks.length;

      if (myHuskyLensBlocks.length == 0) {
        huskyTelemetry += "No blocks detected";
      }

      // Process detected blocks
      for (HuskyLens.Block myHuskyLensBlock_item : myHuskyLensBlocks) {
        myHuskyLensBlock = myHuskyLensBlock_item;

        // Display telemetry for each detected block
        huskyTelemetry += "Block, ID=" + myHuskyLensBlock.id + " Size: " + myHuskyLensBlock.width + "x" + myHuskyLensBlock.height + " Position: " + myHuskyLensBlock.x + "," + myHuskyLensBlock.y;

        // Check if width and height are valid (positive values)
        if (myHuskyLensBlock.width > 0 && myHuskyLensBlock.height > 0) {
          tangechange = Math.abs(((Math.atan((double) myHuskyLensBlock.height / myHuskyLensBlock.width) / Math.PI * 180 - 22.5) / (67.5 - 22.5)) * (Math.PI / 2));

          // Adjust servo based on tangechange
          //if (tangechange > Math.PI / 4) {
            //grabberRot.setPosition(Points.grabberRotInit);
         // } else {
          //  grabberRot.setPosition(Points.grabberRotHorz);
         // }
        }
      }
    } catch (Exception e) {
      huskyTelemetry += "Error Failed to read blocks: " + e.getMessage();
    }
    return huskyTelemetry;
  }
public Pos2D getRobotPos() {
    getBlock();

    if (myHuskyLensBlock == null) {
        return null;
    } else {
        double wh = (double) myHuskyLensBlock.width / myHuskyLensBlock.height;

        wh = (wh - 0.68)/1.11;
        HuskyLens.Block block = myHuskyLensBlock;
        double dx = 0.00135786 * Math.pow(block.y, 2) - 0.584028 * block.y + 84.6657 - 50 + wh * Points.whmult + Points.xoffset; // x
        double dy = -0.0967931 * block.x + 15.69984 + Points.yoffset; // y


        double robotx = dx * Math.cos(odometry.currentPos.theta) - dy * Math.sin(odometry.currentPos.theta);
        double roboty = dx * Math.sin(odometry.currentPos.theta) + dy * Math.cos(odometry.currentPos.theta);

        if (wh > 0.5) {
            outtakeHuskyPos = Points.grabberRotHorz;
        } else {
            outtakeHuskyPos = Points.grabberRotInit;
        }
//1.79-0.68
        return new Pos2D(robotx, roboty, 0);
    }
}

public String getBlock() {
    // Get blocks detected by HuskyLens
    myHuskyLensBlocks = huskylens.blocks();

    // If already locked onto a block, check if it is still visible
    if (myHuskyLensBlock != null) {
        for (HuskyLens.Block block : myHuskyLensBlocks) {
            if (block.x == myHuskyLensBlock.x && block.y == myHuskyLensBlock.y) {
                // Keep the lock if the block is still visible
                return myHuskyLensBlock.x + ", " + myHuskyLensBlock.y;
            }
        }
    }

    // If not locked or the block is lost, find the closest block to the center
    double minDistance = Double.MAX_VALUE;
    double centerX = 160; // Center of the screen

    for (HuskyLens.Block block : myHuskyLensBlocks) {
        double distance = Math.sqrt(Math.pow(block.x - centerX, 2));

        if (distance < minDistance) {
            minDistance = distance;
            myHuskyLensBlock = block;
        }
    }

    // Return the position of the newly selected block, if any
    if (myHuskyLensBlock != null) {
        return myHuskyLensBlock.x + ", " + myHuskyLensBlock.y;
    }

    // Return null if no block is found
    return null;
}

}
*/