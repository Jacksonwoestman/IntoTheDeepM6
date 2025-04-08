package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.pathing.Dash;
import org.firstinspires.ftc.teamcode.pathing.DriveConstants;
import org.firstinspires.ftc.teamcode.pathing.Odometry;
import org.firstinspires.ftc.teamcode.pathing.PIDController;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Arms;

import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.pathing.WheelVelocities;
import org.firstinspires.ftc.teamcode.vision.CameraConstants;
import org.firstinspires.ftc.teamcode.vision.Rectangle;
import org.firstinspires.ftc.teamcode.vision.RectangleDetectionPipeline;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.openftc.easyopencv.OpenCvCamera;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;



import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.List;


public class Drive {
  HardwareMap hardwareMap;
  public Odometry odometry;
  public ColorSensor colorSensor;

  public DcMotorEx bR, fR, fL, bL, lEncoder, rEncoder, bEncoder, lVert, rVert, mVert, intake;

  public Servo lHorz, rHorz, intakeArm, outtakeGrab, outtakeWrist, lOuttakeArm, rOuttakeArm;
  //public CRServo outtakeArm;
  public PIDController pidController;
  public Dash dashboard;
  public double outtakeArmTarget;

  public boolean outtakeArmIsUp = true;
  public double outtakeArmLastPos = 0;



  public void resetStuff() {
    horzSlide(Arms.lHorzInit, Arms.rHorzInit);
    intakeArm.setPosition(Arms.intakeArmInit);
    outtakeArm(Arms.outtakeArmInit);
    outtakeWrist.setPosition(Arms.outtakeWristInit);
    outtakeGrab.setPosition(Arms.outtakeGrabRelease);
    vertSlide(Arms.vertAfterReset);

  }
  public void resetOuttakeExtHorz() {
    horzSlide(Arms.lHorzOut, Arms.rHorzOut);
    intakeArm.setPosition(Arms.intakeArmLaunch);
    outtakeArm(Arms.outtakeArmInit);

    outtakeWrist.setPosition(Arms.outtakeWristInit);
    outtakeGrab.setPosition(Arms.outtakeGrabRelease);
    vertSlide(Arms.vertAfterReset);

  }
  public void resetStuffAuto() {
    horzSlide(Arms.lHorzInit, Arms.rHorzInit);
    intakeArm.setPosition(Arms.intakeArmInit);
    outtakeArm(Arms.outtakeArmInit);

    outtakeWrist.setPosition(Arms.outtakeWristInit);
    outtakeGrab.setPosition(Arms.outtakeGrabRelease);
    vertSlide(Arms.vertInit);

  }
  public void resetOuttakeStuff() {
    outtakeArm(Arms.outtakeArmSpecimenGrab);

    outtakeWrist.setPosition(Arms.outtakeWristInit);
    outtakeGrab.setPosition(Arms.outtakeGrabRelease);
    vertSlide(Arms.vertBottom);

  }

  public void onStart() {
    vertSlide(Arms.vertAfterReset);
    outtakeArm(Arms.outtakeArmInit);

  }

  public void grabReady() {
    horzSlide(Arms.lHorzOut, Arms.rHorzOut);
    intake.setPower(0);
  }

  public void grabReadyHalf() {
    horzSlide(Arms.lHorzHalf, Arms.rHorzHalf);
    intake.setPower(0);
  }

  public void grab(double power) {
    intake.setPower(power);
    intakeArm.setPosition(Arms.intakeArmGrab);
  }


  public void specimenGrab() {
    vertSlide(Arms.vertBottom);
    outtakeArm(Arms.outtakeArmSpecimenGrab);

    outtakeWrist.setPosition(Arms.outtakeWristInit);
  }

  public void specimenPlace() {
    vertSlide(Arms.vertSpecimenPlace);
    outtakeArm(Arms.outtakeArmSpecimenPlace);

    outtakeWrist.setPosition(Arms.outtakeWrist180);
  }

  public void bucketReady() {
    vertSlide(Arms.vertBucket);
    outtakeArm(Arms.outtakeArmBucket);

    outtakeWrist.setPosition(Arms.outtakeWrist180);
  }


  public void vertSlide(int targetPos) {
    lVert.setTargetPosition(targetPos);
    mVert.setTargetPosition(targetPos);
    rVert.setTargetPosition(targetPos);
  }

  public void outtakeArm(double targetPos) {
    lOuttakeArm.setPosition(targetPos);
    rOuttakeArm.setPosition(targetPos);
  }


  public int vertSlidePos() {
    return (int) ((lVert.getCurrentPosition() + rVert.getCurrentPosition()) / 2);
  }



  public void horzSlide(double lPos, double rPos) {
    if(lPos > Arms.lHorzInit) {lPos = Arms.lHorzInit; rPos = Arms.rHorzInit;}
    if(lPos < Arms.lHorzOut) {lPos = Arms.lHorzOut; rPos = Arms.rHorzOut;}
    lHorz.setPosition(lPos);
    rHorz.setPosition(rPos);
  }

  public void horzSlideStick(double stickPos) {
    double lpos = lHorz.getPosition() + 0.0125*stickPos;
    double rpos = rHorz.getPosition() + 0.0125*stickPos;
    horzSlide(lpos, rpos);
  }

  public void vertSlideStick(double stickPos) {
    vertSlide(vertSlidePos() - (int)(200*stickPos));
  }


  public void runVec(Vector v) {

    Pos2D error = pidController.calculate(v.targetPos, odometry.currentPos);
    Pos2D vels = v.toRobotPower(odometry.currentPos.theta, error);
    double fV = vels.x;
    double sV = vels.y;
    double tV = vels.theta;

    double R = DriveConstants.wheelTrackOffset;

    fR.setPower((fV) + (sV) + 2 * R * (tV));
    bR.setPower((fV) - (sV) + 2 * R * (tV));
    fL.setPower((fV) - (sV) - 2 * R * (tV));
    bL.setPower((fV) + (sV) - 2 * R * (tV));

  }

  public void vecAndStick(Vector v, double fRp, double bRp, double fLp, double bLp) {

    Pos2D error = pidController.calculate(v.targetPos, odometry.currentPos);
    Pos2D vels = v.toRobotPower(odometry.currentPos.theta, error);
    double fV = vels.x;
    double sV = vels.y;
    double tV = vels.theta;

    double R = DriveConstants.wheelTrackOffset;

    fR.setPower((fV) + (sV) + 2 * R * (tV) + fRp);
    bR.setPower((fV) - (sV) + 2 * R * (tV) + bRp);
    fL.setPower((fV) - (sV) - 2 * R * (tV) + fLp);
    bL.setPower((fV) + (sV) - 2 * R * (tV) + bLp);

  }

  public void setMotorVel(double fRp, double bRp, double fLp, double bLp) {
    fR.setPower(fRp);
    bR.setPower(bRp);
    fL.setPower(fLp);
    bL.setPower(bLp);
  }

  public void updateTelemetry() {
    Pos2D curEncPos = new Pos2D(-lEncoder.getCurrentPosition(), rEncoder.getCurrentPosition(), bEncoder.getCurrentPosition());
    odometry.updateTelemetry(curEncPos);



  }

  public WheelVelocities getVel() {
    double fLpower = fL.getPower();
    double fRpower = fR.getPower();
    double bLpower = bL.getPower();
    double bRpower = bR.getPower();
    return new WheelVelocities(fLpower, fRpower, bLpower, bRpower);
  }

  public int color() {
    int color;

    // Get the RGB values from the color sensor
    int red = colorSensor.red();
    int green = colorSensor.green();
    int blue = colorSensor.blue();

    if (red > 100 && red > green + 50 && red > blue + 50) {
      color = 1;  // Red
    } else if (green > 110 && green > red + 55 && green > blue + 55) {
      color = 2;  // Green
    } else if (green < 100 && green > red + 20 && green > blue + 110) {
      color = 3;  // Blue
    } else {
      color = 0;  // Unknown color
    }

    return color;
  }



  public Drive(HardwareMap hardwareMap, Pos2D startPos) {



    Pos2D initPos = new Pos2D();
    initPos.x = startPos.x;
    initPos.y = startPos.y;
    initPos.theta = startPos.theta;


    this.hardwareMap = hardwareMap;
    bR = hardwareMap.get(DcMotorEx.class, "BR");
    fR = hardwareMap.get(DcMotorEx.class, "FR");
    fL = hardwareMap.get(DcMotorEx.class, "FL");
    bL = hardwareMap.get(DcMotorEx.class, "BL");
    bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    bR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    fR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    fL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    bL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    bR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    fR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    fL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    bL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    lEncoder = hardwareMap.get(DcMotorEx.class, "BR");
    rEncoder = hardwareMap.get(DcMotorEx.class, "FL");
    bEncoder = hardwareMap.get(DcMotorEx.class, "BL");
    lEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    bEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    rEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    bEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    bL.setDirection(DcMotorSimple.Direction.REVERSE);
    fL.setDirection(DcMotorSimple.Direction.REVERSE);

    lVert = hardwareMap.get(DcMotorEx.class, "LVert");
    mVert = hardwareMap.get(DcMotorEx.class, "MVert");
    rVert = hardwareMap.get(DcMotorEx.class, "RVert");
    lVert.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rVert.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    mVert.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    mVert.setTargetPosition(0);
    lVert.setTargetPosition(0);
    rVert.setTargetPosition(0);
    mVert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lVert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    rVert.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    lVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    rVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    mVert.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    lVert.setDirection(DcMotorSimple.Direction.REVERSE);
    mVert.setDirection(DcMotorSimple.Direction.REVERSE);
    rVert.setDirection(DcMotorSimple.Direction.REVERSE);

    mVert.setPower(1);
    lVert.setPower(1);
    rVert.setPower(1);


    intake = hardwareMap.get(DcMotorEx.class, "Intake");
    intake.setDirection(DcMotorSimple.Direction.REVERSE);


    lHorz = hardwareMap.get(Servo.class, "LHorz");
    rHorz = hardwareMap.get(Servo.class, "RHorz");


    intakeArm = hardwareMap.get(Servo.class, "IntakeArm");

    lOuttakeArm = hardwareMap.get(Servo.class, "ArmLeft");
    rOuttakeArm = hardwareMap.get(Servo.class, "ArmRight");
    outtakeWrist = hardwareMap.get(Servo.class, "OuttakeWrist");
    outtakeGrab = hardwareMap.get(Servo.class, "OuttakeGrabber");


    lHorz.setPosition(Arms.lHorzInit);
    rHorz.setPosition(Arms.rHorzInit);

    intakeArm.setPosition(Arms.intakeArmInit);

    //outtakeArm.setPower(0);
    outtakeWrist.setPosition(Arms.outtakeWristInit);
    outtakeGrab.setPosition(Arms.outtakeGrabRelease);
    outtakeArm(Arms.outtakeArmStart);




    odometry = new Odometry(initPos);
    pidController = new PIDController();
    dashboard = new Dash();

    colorSensor = hardwareMap.get(ColorSensor.class, "cS");
  }
}