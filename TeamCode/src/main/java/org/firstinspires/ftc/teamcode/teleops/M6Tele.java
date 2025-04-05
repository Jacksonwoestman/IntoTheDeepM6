package org.firstinspires.ftc.teamcode.teleops;

import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Arms;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;

@TeleOp(name = "M2 Teleop")
public class M6Tele extends LinearOpMode {

    Drive robot;
    boolean isResetting = false;
    double resetTime = 0;
    int specimen = 0;
    int observe = 0;
    VectorBuilder vB = null;
    boolean isRunningVB = false;

    private ColorSensor colorSensor;
    private final float[][] COLOR_RANGES = {
            {8, 30, 0.53f, 1f, 11f, 40f},   // Red
            {60, 90, 0.5f, 1.1f, 30f, 70f},    // Yellow
            {210, 240, 0.5f, 1.2f, 11f, 40f}     // Blue
    };
    private final String[] COLORS = {"Red", "Yellow", "Blue"};
    int color = 2; // Default to Yellow

    @Override
    public void runOpMode() throws InterruptedException {

        colorSensor = hardwareMap.get(ColorSensor.class, "cS");
        float[] hsvValues = new float[3];
        robot = new Drive(hardwareMap, Points.initPoseRight);

        boolean isGrabbing = false;
        boolean isSpecimening = false;
        double specimenTime = 0;
        boolean isLaunching = false;
        double launchTime = 0;
        boolean hasSeenBlock = false;

        int colorMatchFrames = 0;
        final double REQUIRED_MATCH_FRAMES = 0.005;

        telemetry.addData("Waiting for start", "...");
        telemetry.update();

        waitForStart();
        if (opModeIsActive()) {
            robot.onStart();
            while (opModeIsActive()) {

                Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
                String detectedColor = detectColor(hsvValues);
                String targetColorString = COLORS[color - 1];

                telemetry.addData("Detected Color", detectedColor);
                telemetry.addData("Target Color", targetColorString);
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("Saturation", hsvValues[1]);
                telemetry.addData("Value", hsvValues[2]);

                boolean isColorMatch = detectedColor.equals(targetColorString);
                if (isColorMatch) {
                    colorMatchFrames++;
                } else {
                    colorMatchFrames = 0;
                }

                double Speed = 0.7;
                if (gamepad1.left_bumper) Speed = 0.4;
                else if (gamepad1.right_bumper) Speed = 0.7;
                else Speed = 1;

                double Speed_Rot = -gamepad1.right_stick_x;
                double Speed_X = -gamepad1.left_stick_x;
                double Speed_Y = -gamepad1.left_stick_y;

                double fR = Math.min(Math.max(Speed_Y + Speed_X + Speed_Rot, -Speed), Speed);
                double bR = Math.min(Math.max((Speed_Y - Speed_X) + Speed_Rot, -Speed), Speed);
                double fL = Math.min(Math.max((Speed_Y - Speed_X) - Speed_Rot, -Speed), Speed);
                double bL = Math.min(Math.max((Speed_Y + Speed_X) - Speed_Rot, -Speed), Speed);

                if (isRunningVB && !vB.isAtEnd(robot.odometry.currentPos)) {
                    robot.updateTelemetry();
                    robot.vecAndStick(vB.getVector(robot.odometry.currentPos), fR, bR, fL, bL);
                    if (vB.isAtEnd(robot.odometry.currentPos)) isRunningVB = false;
                    robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos);
                } else {
                    robot.setMotorVel(fR, bR, fL, bL);
                }

                // Auto-reset after color match
                if (colorMatchFrames >= REQUIRED_MATCH_FRAMES) {
                    robot.intake.setPower(0);
                    isGrabbing = false;
                    if (!hasSeenBlock) {
                        isResetting = true;
                        resetTime = getRuntime();
                    }
                    hasSeenBlock = true;
                    isResetting = true;
                    resetTime = getRuntime();
                }

                if (gamepad1.b || gamepad2.b) {
                    isResetting = true;
                    resetTime = getRuntime();
                }

                if (gamepad2.a && !gamepad2.right_bumper) robot.specimenGrab();
                if (gamepad2.y) robot.bucketReady();
                if (gamepad2.x && !gamepad2.right_bumper) {
                    isSpecimening = true;
                    specimenTime = getRuntime();
                }

                if (gamepad2.b && gamepad2.right_trigger > 0.01) color = 1;
                if (gamepad2.x && gamepad2.right_trigger > 0.01) color = 2;
                if (gamepad2.a && gamepad2.right_trigger > 0.01) color = 3;

                if (gamepad1.x) color = 1;
                if (gamepad1.y) color = 2;
                if (gamepad1.a) color = 3;

                if (gamepad2.right_trigger > 0.01) {
                    robot.grab(0.85);
                    isGrabbing = true;
                } else {
                    isGrabbing = false;
                }
                if (gamepad2.left_trigger > 0.1) {
                    robot.intake.setPower(-1);
                }


                if (gamepad2.left_bumper) robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
                if (gamepad2.right_bumper) robot.grab(0.65);

                if (gamepad2.dpad_up) {
                    robot.intakeArm.setPosition(Arms.intakeArmLaunch);
                    robot.lHorz.setPosition(Arms.lHorzHalf);
                    robot.rHorz.setPosition(Arms.rHorzHalf);
                }

                if (gamepad2.dpad_left) robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
                if (gamepad2.dpad_right) robot.outtakeWrist.setPosition(Arms.outtakeWristInit);
                if (gamepad2.dpad_down) robot.outtakeWrist.setPosition(Arms.outtakeWristHalf);

                if (gamepad2.right_stick_y != 0) robot.vertSlideStick(gamepad2.right_stick_y);
                if (gamepad2.left_stick_y != 0) robot.horzSlideStick(gamepad2.left_stick_y);
                if (gamepad2.left_stick_button) robot.grabReady();
                if (gamepad2.right_stick_button) robot.vertSlide(Arms.vertBottom);

                if (isResetting) {

                    // Check if we still detect the block
                    boolean stillDetectsBlock = detectColor(hsvValues).equals(targetColorString);
                    //robot.resetStuff();
                    robot.intakeArm.setPosition(Arms.intakeArmInit);
                    if (stillDetectsBlock) {
                        robot.intake.setPower(0.6);
                    } else {
                        double elapsed = getRuntime() - resetTime;

                        if (elapsed > 0.9) {
                            robot.intakeArm.setPosition(Arms.intakeArmLaunch);
                            robot.vertSlide(Arms.vertAfterReset);
                            isResetting = false;
                            robot.intake.setPower(0);
                        } else if (elapsed > 0.5) {
                            robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
                        } else if (elapsed > 0.1) {
                            robot.vertSlide(Arms.vertInit);
                        } else if (elapsed > 0.00001) {
                            robot.resetStuff();
                        }
                    }
                }

                if (isLaunching) {
                    if (getRuntime() > 1.6 + launchTime) {
                        robot.intake.setPower(0);
                        isLaunching = false;
                    } else if (getRuntime() > 0.8 + launchTime) {
                        robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
                        robot.intake.setPower(1);
                    } else if (getRuntime() > launchTime) {
                        robot.resetStuff();
                        robot.intakeArm.setPosition(Arms.intakeArmLaunch);
                    }
                }

                if (isSpecimening) {
                    if (getRuntime() > 0.2 + specimenTime) {
                        robot.specimenPlace();
                        isSpecimening = false;
                    } else if (getRuntime() > specimenTime) {
                        robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
                    }
                }

                telemetry.addData("Target Color Number", color);
                telemetry.addData("Vertical Slide Pos", robot.vertSlidePos());
                telemetry.addData("Current Pos", robot.odometry.currentPos.toString());
                telemetry.addData("Color Match Frames", colorMatchFrames);
                telemetry.addData("Is Color Match", isColorMatch);
                telemetry.addData("Is Grabbing", isGrabbing);
                telemetry.update();

                robot.updateTelemetry();
            }
        }
    }

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
