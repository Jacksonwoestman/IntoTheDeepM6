package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import android.graphics.Color;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "HSV Color Detection")
public class ColorSensorOpMode extends LinearOpMode {


    private ColorSensor colorSensor;


    // HSV ranges for Red, Yellow, and Blue
    private final float[][] COLOR_RANGES = {
            {18, 20, 0.73f, 0.8f, 21f, 30f},   // Red: Hue [0-10], Saturation [0.4-1.0], Value [0.4-1.0]
            {73, 83, 0.7f, 0.9f, 40f, 60f},  // Yellow: Hue [20-40], Saturation [0.4-1.0], Value [0.4-1.0]
            {220, 230, 0.7f, 1f, 21f, 30f} // Blue: Hue [200-250], Saturation [0.4-1.0], Value [0.4-1.0]
    };


    private final String[] COLORS = {"Red", "Yellow", "Blue"};


    @Override
    public void runOpMode() {
        colorSensor = hardwareMap.get(ColorSensor.class, "cS");


        waitForStart();


        while (opModeIsActive()) {
            // Get RGB values from the sensor
            float[] hsvValues = new float[3];
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);


            String detectedColor = detectColor(hsvValues);

            telemetry.addData("Color Detected", detectedColor);
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.addData("Saturation", hsvValues[1]);
            telemetry.addData("Value", hsvValues[2]);
            telemetry.update();
        }
    }


    /**
     * Detects the color by comparing HSV values against defined ranges.
     */
    private String detectColor(float[] hsv) {
        for (int i = 0; i < COLOR_RANGES.length; i++) {
            float[] range = COLOR_RANGES[i];


            boolean hueInRange = hsv[0] >= range[0] && hsv[0] <= range[1];
            boolean satInRange = hsv[1] >= range[2] && hsv[1] <= range[3];
            boolean valInRange = hsv[2] >= range[4] && hsv[2] <= range[5];


            if (hueInRange && satInRange && valInRange) {
                return COLORS[i];
            }
        }
        return "Unknown";
    }
}




