package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.vision.AprilTagDetectionPipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;
import java.util.Arrays;


@Autonomous(name = "AprilTagFollower")
@Disabled
public class AprilTagFollower extends LinearOpMode {
    Drive robot;
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    Pos2D init = new Pos2D(0, 0, 0);
    Pos2D p2 = new Pos2D(300, 0, 0);
    Pos2D p3 = new Pos2D(100,-50,0);
    Pos2D p4 = new Pos2D(0,-50,0);

    ArrayList<Pos2D> list1 = new ArrayList<>(Arrays.asList(init, p2));
    ArrayList<Pos2D> list2 = new ArrayList<>(Arrays.asList(p2,p3,p4,init));

    double speed1 = 0.8;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int BLUE_LEFT_TAG = 0;
    int BLUE_RIGHT_TAG = 0;
    int BLUE_MID_TAG =0 ;
    int RED_LEFT_TAG = 0;
    int RED_RIGHT_TAG = 0;
    int RED_MID_TAG =0 ;
    int ID_TAG_OF_INTEREST = BLUE_LEFT_TAG; // Tag ID 18 from the 36h11 family

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "FrontCam"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
        robot = new Drive(hardwareMap, new Pos2D());
        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
        telemetry.setMsTransmissionInterval(50);


        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();

        robot = new Drive(hardwareMap, init);

        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();


        while (!isStopRequested() && !opModeIsActive()) {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if (currentDetections.size() != 0) {
                telemetry.addLine("Tag of interest is in sight!");
                runToTag(currentDetections.get(0));
            } else {
                telemetry.addLine("Don't see tag of interest :(");
                robot.runVec(new Vector(robot.odometry.currentPos));
            }

            telemetry.update();
            sleep(20);
        }

        if (opModeIsActive() && !isStopRequested()) {
            while (opModeIsActive()) {

            }
        }

    }
    private void runToTag(AprilTagDetection aprilTag) {
        Orientation rot = Orientation.getOrientation(aprilTag.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.RADIANS);
        Pos2D dRobot = new Pos2D(aprilTag.pose.z*30.48, aprilTag.pose.x*-30.48, -rot.firstAngle);
        Pos2D dField = dRobotToDFieldPos(dRobot);
        Pos2D targetPos = dField.add(robot.odometry.currentPos);
        Vector v = new Vector(targetPos);
        robot.runVec(v);
    }

    private Pos2D dRobotToDFieldPos(Pos2D dRobot) {
        double dtheta = dRobot.theta;
        double dx = dRobot.x*Math.cos(robot.odometry.currentPos.theta) - dRobot.y*Math.sin(robot.odometry.currentPos.theta);
        double dy = dRobot.x*Math.sin(robot.odometry.currentPos.theta) + dRobot.y*Math.cos(robot.odometry.currentPos.theta);

        return new Pos2D(dx,dy,dtheta);
    }
}


    /*void tagToTelemetry(AprilTagDetection detection)
    {
        Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", rot.firstAngle));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", rot.secondAngle));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", rot.thirdAngle));
    }*/