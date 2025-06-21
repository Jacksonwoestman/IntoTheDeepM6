package org.firstinspires.ftc.teamcode.autos;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;

@TeleOp(name = "Color Sensor Test", group = "Testing")
public class CameraTest extends LinearOpMode {

    Drive drive;

    @Override
    public void runOpMode() {
        // Initialize drive and pass in starting position (could be anything for testing)
        drive = new Drive(hardwareMap, new Pos2D(0, 0, 0));

        telemetry.addLine("Color sensor test initialized.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            int detectedColor = drive.color();

            String colorName;
            switch (detectedColor) {
                case 1:
                    colorName = "Red";
                    break;
                case 2:
                    colorName = "Green";
                    break;
                case 3:
                    colorName = "Blue";
                    break;
                default:
                    colorName = "Unknown";
                    break;
            }

            // Get raw RGB as well
            int red = drive.colorSensor.red();
            int green = drive.colorSensor.green();
            int blue = drive.colorSensor.blue();

            telemetry.addData("Detected Color", colorName);
            telemetry.addData("Raw Red", red);
            telemetry.addData("Raw Green", green);
            telemetry.addData("Raw Blue", blue);
            telemetry.update();
        }
    }
}
