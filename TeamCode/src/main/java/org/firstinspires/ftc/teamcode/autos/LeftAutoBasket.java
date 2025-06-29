package org.firstinspires.ftc.teamcode.autos;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Arms;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.RunList;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;
import org.firstinspires.ftc.teamcode.vision.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;


@Autonomous(name = "LeftAutoBucket")
public class LeftAutoBasket extends LinearOpMode {
    Drive robot;


    ArrayList<Pos2D> initToBasket = new ArrayList<>(Arrays.asList(Points.initPosLeft, Points.BasketDeliver));

    ArrayList<Pos2D> BasketToRN = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.RNeutral));
    ArrayList<Pos2D> RNToBasket = new ArrayList<>(Arrays.asList(Points.RNeutral, Points.BasketDeliver));
    ArrayList<Pos2D> BasketToMN = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.MNeutral));
    ArrayList<Pos2D> MNToBasket = new ArrayList<>(Arrays.asList(Points.MNeutral, Points.BasketDeliver));
    ArrayList<Pos2D> BasketToLN = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.LNeutral));
    ArrayList<Pos2D> LNToBasket = new ArrayList<>(Arrays.asList(Points.LNeutral, Points.BasketDeliver));
    ArrayList<Pos2D> BasketToGrab = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.LeftGrabStart, Points.LeftGrabEnd));
    ArrayList<Pos2D> BasketToPark = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.LeftParkStart, Points.LeftParkEnd));

    ArrayList<Pos2D> SubmersibletoPlace = new ArrayList<>(Arrays.asList(Points.LeftGrabEnd, Points.LeftGrabStart, Points.BasketDeliver));


    boolean isResetting = false;
    double resetTime = 0;
    boolean sad = false;



    @Override
    public void runOpMode() throws InterruptedException {




        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();

        robot = new Drive(hardwareMap, Points.initPosLeft);

        robot.lEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.bEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.lEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.bEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);

        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.addData("", robot.odometry.currentPos.toString());
        telemetry.addData("", Points.initPosLeft.toString());
        telemetry.update();


        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }

        if (opModeIsActive() && !isStopRequested()) {
            runAutonoumousMode();
        }
    }

    public void runAutonoumousMode() throws InterruptedException {
        robot.bucketReady();
        robot.outtakeArm(0.48);

        runList(initToBasket);

        bucketStart();
//
        runList(BasketToRN);

        intake();
        robot.horzSlideFraction(0.6);

        robot.bucketReady();
        runList(RNToBasket);
        bucketStart();
//
        runList(BasketToMN);

        intake();
        robot.horzSlideFraction(0.3);

        robot.vertSlide(Arms.vertBucket);
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        robot.outtakeArm(0.32);

        runList(MNToBasket);
        leftbucket();
//
        runList(BasketToLN);

        intake();
        robot.horzSlideFraction(0.6);
        robot.vertSlide(Arms.vertBucket);
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        robot.outtakeArm(0.45);
        runList(LNToBasket);

        leftbucket();
//
        robot.resetStuff();
        Thread.sleep(80);

        robot.horzSlideFraction(0.3);
        runList(BasketToGrab);
        intake();
        robot.horzSlideFraction(0.3);



        robot.bucketReady();
        runList(SubmersibletoPlace);
        subToBucket();

        robot.resetStuff();
        Thread.sleep(80);
        robot.horzSlideFraction(0.6);
        runList(BasketToGrab);
        intake();



        if(!sad) {

        robot.bucketReady();
        runList(SubmersibletoPlace);
        subToBucket();
        robot.outtakeArm(Arms.outtakeArmPark);
        robot.horzSlide(Arms.lHorzInit,Arms.rHorzInit);
        runList(BasketToPark);} else {
            runList(new ArrayList<>(Arrays.asList(robot.odometry.currentPos, Points.LeftParkEnd)));
        }

    }





    private void intake() throws InterruptedException {
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        robot.outtakeArm(Arms.outtakeArmInit);


        robot.grab(0.65);
        Thread.sleep(100);
        robot.horzSlide(Arms.lHorzOut, Arms.rHorzOut);


        //Thread.sleep(time);

        resetTime = getRuntime();
        sad = false;
        robot.outtakeArm(Arms.outtakeArmInit);

        while(robot.color() != 2 && !sad) {
            if(getRuntime() > resetTime + 4) {
                sad = true;
            } else if(getRuntime() > resetTime + 2) {
                robot.setMotorVel(-0.15, -0.15, 0.15, 0.15);
            }
        }
        if(!sad) {
            robot.intake.setPower(0.3);
            resetTime = getRuntime();
            robot.resetStuff();
            Thread.sleep(100);
            robot.intake.setPower(0.55);
            robot.intakeArm.setPosition(Arms.intakeArmInit);


            while (robot.color() == 2) {
                if(getRuntime() > resetTime + 2.5) {
                    robot.intake.setPower(-1);
                    sad = true;
                } else if(getRuntime() > resetTime + 1) {
                    robot.intake.setPower(1);
                }
                if(getRuntime() > resetTime + 4) sad = true;
            }

            if(!sad) {
            Thread.sleep(100);

            robot.intake.setPower(0.50);
            robot.vertSlide(Arms.vertInitAuto);

            Thread.sleep(100);
            robot.intake.setPower(0.2);
            robot.outtakeGrab.setPosition(Arms.outtakeGrabReady);

            Thread.sleep(150);
            robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);

            Thread.sleep(150);
            robot.intakeArm.setPosition(Arms.intakeArmLaunch);
            robot.vertSlide(Arms.vertAfterReset);
            robot.intake.setPower(-1);
            Thread.sleep(100);
            robot.intake.setPower(0);}
            else {
                robot.intake.setPower(-1);
                robot.resetStuff();}
        } else {
            robot.intake.setPower(-1);
            robot.resetStuff();}


    }




    private void bucketStart() throws InterruptedException {
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        robot.outtakeArm(Arms.outtakeArmBucket);
        robot.horzSlideFraction(0.6);
        Thread.sleep(240);
        robot.intakeArm.setPosition(Arms.intakeArmGrab);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        Thread.sleep(150);
        robot.outtakeArm(Arms.outtakeArmStart);

        robot.outtakeWrist.setPosition(Arms.outtakeWristInit);
        robot.vertSlide(Arms.vertBottom);
         Thread.sleep(175);
    }


    private void leftbucket() throws InterruptedException {

        robot.vertSlide(Arms.vertBucket);
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        robot.outtakeArm(Arms.outtakeArmBucket);
        robot.horzSlideFraction(0.60);
        Thread.sleep(350);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        robot.intakeArm.setPosition(Arms.intakeArmGrab);
        Thread.sleep(120);
        robot.resetOuttakeStuffBasket();

    }

    private void subToBucket() throws InterruptedException {
        Thread.sleep(75);
        robot.intakeArm.setPosition(Arms.intakeArmGrab);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        Thread.sleep(150);
        robot.resetOuttakeStuffBasket();
        Thread.sleep(100);
    }
    private void runList(ArrayList<Pos2D> runlist) {
        VectorBuilder vB = new VectorBuilder(runlist);
        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEnd(robot.odometry.currentPos)) {
            robot.updateTelemetry();
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            if (vB.isAtEnd(robot.odometry.currentPos)) {
                break;
            }
            /*if(isResetting) {
                if(getRuntime() > resetTime + Arms.reset1 + 0.3) {
                    robot.intakeGrab.setPosition(Arms.intakeGrabRelease);
                    isResetting = false;
                } else if (getRuntime() > resetTime + Arms.reset1) {
                    robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
                }
            }*/
            telemetry.addData("", new Vector(runlist.get(runlist.size() - 1)).toString());
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, runlist.get(runlist.size() - 1));
        }
        wait(0.2, runlist.get(runlist.size() - 1));
    }


    private void wait(double timeS, Pos2D currentPos) {
        double start = getRuntime();
        while (getRuntime() < start + timeS) {
            robot.runVec(new Vector(currentPos));
            robot.updateTelemetry();
            telemetry.addData("waiting", 0);
            telemetry.update();
        }
        robot.setMotorVel(0,0,0,0);
    }
    private void telemetry(VectorBuilder vB) {
        telemetry.addData("currentPos", robot.odometry.currentPos.toString());
        telemetry.addData("currentVec", vB.getVector(robot.odometry.currentPos).toString());
        telemetry.addData("isatend", robot.odometry.currentPos.isEqualIshTo(vB.initPoints.get(vB.initPoints.size()-1)));
        telemetry.addData("vbisatend", vB.isAtEnd(robot.odometry.currentPos));
        telemetry.addData("initpointslast", vB.initPoints.get(vB.initPoints.size()-1));
        telemetry.update();
    }
}