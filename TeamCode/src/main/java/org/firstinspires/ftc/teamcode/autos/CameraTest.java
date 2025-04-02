package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Husky.Java")
@Disabled

public class CameraTest extends LinearOpMode {

    private HuskyLens huskylens;
    private Servo rotate;

    @Override
    public void runOpMode() {
        double tangechange = 0.0;
        HuskyLens.Block myHuskyLensBlock;
        ElapsedTime myElapsedTime;
        HuskyLens.Block[] myHuskyLensBlocks;

        huskylens = hardwareMap.get(HuskyLens.class, "huskylens");
        rotate = hardwareMap.get(Servo.class, "GrabberRot");

        // Initialization
        telemetry.addData(">>", huskylens.knock() ? "Touch start to continue" : "Problem communicating with HuskyLens");
        telemetry.update();

        if (!huskylens.knock()) {
            telemetry.addData("Error", "HuskyLens not responding!");
            telemetry.update();
            return;  // Exit if HuskyLens is not responding
        }

        // Select the algorithm (e.g., Object recognition)
        huskylens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION); // Specify the algorithm to use
        telemetry.addData("HuskyLens", "Algorithm selected: COLOR_RECOGNITION");
        telemetry.update();

        myElapsedTime = new ElapsedTime();
        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (myElapsedTime.seconds() >= 1) {
                    myElapsedTime.reset();

                    try {
                        // Attempt to get the detected blocks (array, not List)
                        myHuskyLensBlocks = huskylens.blocks();

                        // Display block count and check if blocks are detected
                        telemetry.addData("Block count", myHuskyLensBlocks.length);

                        if (myHuskyLensBlocks.length == 0) {
                            telemetry.addData("No blocks detected", "");
                        }

                        // Process detected blocks
                        for (HuskyLens.Block myHuskyLensBlock_item : myHuskyLensBlocks) {
                            myHuskyLensBlock = myHuskyLensBlock_item;

                            // Display telemetry for each detected block
                            telemetry.addData("Block", "ID=" + myHuskyLensBlock.id + " Size: " + myHuskyLensBlock.width + "x" + myHuskyLensBlock.height + " Position: " + myHuskyLensBlock.x + "," + myHuskyLensBlock.y);

                            // Check if width and height are valid (positive values)
                            if (myHuskyLensBlock.width > 0 && myHuskyLensBlock.height > 0) {
                                tangechange = Math.abs(((Math.atan(myHuskyLensBlock.height / myHuskyLensBlock.width) / Math.PI * 180 - 22.5) / (67.5 - 22.5)) * (Math.PI / 2));

                                // Adjust servo based on tangechange
                                if (tangechange > Math.PI / 4) {
                                    rotate.setPosition(0.29);
                                } else {
                                    rotate.setPosition(0.68);
                                }
                            }
                        }
                    } catch (Exception e) {
                        telemetry.addData("Error", "Failed to read blocks: " + e.getMessage());
                    }

                    // Display the tangechange value
                    telemetry.addData("TanChange", tangechange);
                    telemetry.update();
                }
            }
        }
    }
}
