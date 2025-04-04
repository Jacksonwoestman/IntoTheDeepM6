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
    ArrayList<Pos2D> BasketToMN = new ArrayList<>(Arrays.asList(Points.RNeutral, Points.MNeutral));
    ArrayList<Pos2D> BasketToLN = new ArrayList<>(Arrays.asList(Points.MNeutral, Points.LNeutral));
    ArrayList<Pos2D> LNToBasket = new ArrayList<>(Arrays.asList(Points.LNeutral, Points.BasketDeliver));
    ArrayList<Pos2D> BasketToGrab = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.LeftGrabStart, Points.LeftGrabEnd));
    ArrayList<Pos2D> BasketToPark = new ArrayList<>(Arrays.asList(Points.BasketDeliver, Points.LeftParkStart, Points.LeftParkEnd));

    ArrayList<Pos2D> SubmersibletoPlace = new ArrayList<>(Arrays.asList(Points.LeftGrabEnd, Points.LeftGrabStart, Points.BasketDeliver));


    boolean isResetting = false;
    double resetTime = 0;



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
        //robot.intakeArm1.setPosition(Arms/*.intakeArm1SpecimenTransfer);
        //robot.grabSpecimen(Points.SpecimenGrab);
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
        robot.vertSlide(Arms.vertBucket);


        runList(initToBasket);

        bucketStart();
        runList(BasketToRN);
        robot.intake.setPower(0.5);
        intake(0);


        rightbucket();

        runList(BasketToMN);
        robot.intake.setPower(0.45);
        intake(0);

        robot.vertSlide(Arms.vertBucket);
        middlebucket();
        runList(BasketToLN);
        robot.intake.setPower(0.5);
        intake(0);
        robot.vertSlide(Arms.vertBucket);
        runList(LNToBasket);

        leftbucket();
        robot.resetStuff();
        Thread.sleep(80);

        runList(BasketToGrab);
        intake(350);



        robot.vertSlide(Arms.vertBucket);
        runList(SubmersibletoPlace);
        leftbucket();

        robot.resetStuff();
        Thread.sleep(80);
        runList(BasketToGrab);
        intake(350);



        robot.vertSlide(Arms.vertBucket);
        runList(SubmersibletoPlace);
        leftbucket();
        robot.outtakeArm(Arms.outtakeArmBucket);

    }





    private void intake(int time) throws InterruptedException {
        robot.grabReady();

        Thread.sleep(time);

        robot.grab(0.8);

        while(robot.color() == 0) {

        }

        // robot.intake.setPower(0.05);
        robot.intakeArm.setPosition(Arms.intakeArmInit);
        robot.intake.setPower(0.4);
        robot.resetStuffAuto();
        Thread.sleep(160);
        robot.intake.setPower(0.7);
        Thread.sleep(200);
        robot.intake.setPower(0.4);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
        Thread.sleep(300);
        robot.intake.setPower(-0.4);
        robot.intakeArm.setPosition(Arms.intakeArmUp);

    }

    private void bucketStart() throws InterruptedException {
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        robot.outtakeArm(Arms.outtakeArmBucket);

        Thread.sleep(440);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        Thread.sleep(150);
        robot.resetOuttakeExtHorz();

        Thread.sleep(100);
    }
    private void rightbucket() throws InterruptedException {
        robot.vertSlide(Arms.vertBucket);
        Thread.sleep(190);
        //robot.grabReady();
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        Thread.sleep(190);
        robot.outtakeArm(Arms.outtakeArmBucket);

        Thread.sleep(440);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        Thread.sleep(140);
        robot.resetOuttakeExtHorz();
        Thread.sleep(140);
    }
    private void middlebucket() throws InterruptedException {
        robot.vertSlide(Arms.vertBucket);
        Thread.sleep(190);
        //robot.grabReady();
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        Thread.sleep(190);
        robot.outtakeArm(Arms.outtakeArmBucket);

        Thread.sleep(440);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        Thread.sleep(130);
        robot.resetOuttakeExtHorz();
        Thread.sleep(130);
    }


    private void leftbucket() throws InterruptedException {
        robot.outtakeWrist.setPosition(Arms.outtakeWrist180);
        robot.outtakeArm(Arms.outtakeArmBucket);

        Thread.sleep(480);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
        Thread.sleep(130);
        robot.resetStuff();
        Thread.sleep(130);

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
    private void runListForTime(ArrayList<Pos2D> runlist) {
        double starttime = getRuntime();
        VectorBuilder vB = new VectorBuilder(runlist);
        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEnd(robot.odometry.currentPos) && getRuntime() < starttime + 1.5) {
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