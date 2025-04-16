package org.firstinspires.ftc.teamcode.autos;


import android.widget.GridLayout;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Arms;

import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;


@Autonomous(name = "RightAuto")
public class RightAutoGrab extends LinearOpMode {
    Drive robot;
    int numTimesSpecimen = 0;







    ArrayList<Pos2D> StartDeliverSpecimen = new ArrayList<>(Arrays.asList(Points.initPoseRight, Points.RSpecimenDeliver));

    ArrayList<Pos2D> GoLColor1 = new ArrayList<>(Arrays.asList(Points.RSpecimenDeliver, Points.RSpecimenPreDeliver));
    ArrayList<Pos2D> GoLColor2 = new ArrayList<>(Arrays.asList(Points.RSpecimenPreDeliver, Points.LColorAtG));
    ArrayList<Pos2D> GoLColor3 = new ArrayList<>(Arrays.asList(Points.LColorAtG, Points.LColorObserveG));

    ArrayList<Pos2D> GoMColor1 = new ArrayList<>(Arrays.asList(Points.LColorObserveG, Points.MColorAtG));
    ArrayList<Pos2D> GoMColor2 = new ArrayList<>(Arrays.asList(Points.MColorAtG, Points.MColorObserveG));

    ArrayList<Pos2D> GoRColor1 = new ArrayList<>(Arrays.asList(Points.MColorObserveG, Points.RColorAtG));
    ArrayList<Pos2D> GoRColor2 = new ArrayList<>(Arrays.asList(Points.RColorAtG, Points.RColorObserveG));

    ArrayList<Pos2D> PreSpecimenPickupfromR = new ArrayList<>(Arrays.asList(Points.RColorObserveG, Points.PreobserveGrab));
    ArrayList<Pos2D> SpecimenPickupfromR = new ArrayList<>(Arrays.asList(Points.PreobserveGrab, Points.observeGrab));
    ArrayList<Pos2D> SpecimenDeliver= new ArrayList<>(Arrays.asList(Points.observeGrab, Points.RSpecimenPreDeliver));
    ArrayList<Pos2D> SpecimenPickup = new ArrayList<>(Arrays.asList(Points.RSpecimenDeliver, Points.observeGrab));

    boolean isSpecimenPlacing = false;

    double specimenPlaceTime = 0;

    int specimen = 0;
    boolean isSpecimenTransferring = false;
    double specimenTransferTime = 0;




    @Override
    public void runOpMode() throws InterruptedException {





        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();

        robot = new Drive(hardwareMap, Points.initPoseRight);

        telemetry.addData(">", "Touch Play to start OpMode");
        robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
        robot.outtakeWrist.setPosition(Arms.outtakeWristHalf);
        //robot.outtakeArm(Arms.outtakeArmSpecimenPlace);
        telemetry.update();



        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }

        if (opModeIsActive() && !isStopRequested()) {
            runAutonoumousMode();
        }
    }

    public void runAutonoumousMode() throws InterruptedException {
        //1st Deliver
        specimenReadyFirstPlace();
        Thread.sleep(250);


        runListNoWait(StartDeliverSpecimen);
        robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);

        //Left Color Stuff

        runList(GoLColor1);
        robot.horzSlideFraction(0.7);
        robot.intake.setPower(0.5);
        runList(GoLColor2);
        robot.resetOuttakeStuffSpecimen();
        intake();

        runList(GoLColor3);
        outtake();
        robot.intake.setPower(0.5);
        runList(GoMColor1);
        intake();

        runList(GoMColor2);
        outtake();
        robot.intake.setPower(0.5);
        runList(GoRColor1);
        intake();
        runList(GoRColor2);
        outtake();


        robot.horzSlide(Arms.lHorzInit, Arms.rHorzInit);
        robot.intakeArm.setPosition(Arms.intakeArmInit);
        Thread.sleep(300);
        runList(PreSpecimenPickupfromR);
        Thread.sleep(100);
        runList(SpecimenPickupfromR);


        observeReadyCycle();
        observeReadyCycle();
        observeReadyCycle();
        observeReadyCycle();



        //2nd Deliver





        runList(SpecimenPickup);

    }

    private void observeReadyCycle() throws InterruptedException {
        robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
        Thread.sleep(300);
       specimenReady();

        runListNoWait(specimen());

       robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
       robot.specimenGrab();

        Thread.sleep(100);
        ArrayList<Pos2D> pickup = new ArrayList<>(Arrays.asList(robot.odometry.currentPos, Points.observeGrab));
        runList(pickup);
    }


    private ArrayList<Pos2D> specimen() throws InterruptedException {
        specimen += 5;
        Pos2D targetPos = new Pos2D(Points.RSpecimenDeliver.x+2, Points.RSpecimenDeliver.y -10, Points.RSpecimenDeliver.theta);

        ArrayList<Pos2D> observeToSpecimen = new ArrayList<>(Arrays.asList(Points.observeGrab, Points.RSpecimenPreDeliver, targetPos));

        return observeToSpecimen;
    }

    private void specimenReady() {
        robot.specimenPlace();
        specimenPlaceTime = getRuntime();
        isSpecimenPlacing = true;
    }
    private void specimenReadyFirstPlace() {
        robot.specimenPlace();
        specimenPlaceTime = getRuntime();
        isSpecimenPlacing = true;
    }



    private void intake() throws InterruptedException {
        while(robot.color() == 0) {
            robot.horzSlide(Arms.lHorzOut, Arms.rHorzOut);


            robot.intake.setPower(0.9);
            robot.intakeArm.setPosition(Arms.intakeArmGrab);
        }
        robot.intake.setPower(0);
        robot.horzSlideFraction(0.8);
        robot.intakeArm.setPosition(Arms.intakeArmLaunch);

    }

    private void outtake() throws InterruptedException {
        robot.intake.setPower(-1);
        Thread.sleep(300);
        robot.intake.setPower(0);
        robot.intakeArm.setPosition(Arms.intakeArmGrab);
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
            if(robot.color() != 0) {robot.intake.setPower(0);}
            if (isSpecimenPlacing && getRuntime() > 1 + specimenPlaceTime) {
                isSpecimenPlacing = false;
            }
            telemetry.addData("", new Vector(runlist.get(runlist.size() - 1)).toString());
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, runlist.get(runlist.size() - 1));
        }
        wait(0.2, robot.odometry.currentPos);
    }





    private void runListNoWait(ArrayList<Pos2D> runlist) {
        VectorBuilder vB = new VectorBuilder(runlist);
        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEnd(robot.odometry.currentPos)) {
            robot.updateTelemetry();
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            if (vB.isAtEnd(robot.odometry.currentPos)) {
                break;
            }
            if(robot.color() != 0) {robot.intake.setPower(0);}


            telemetry.addData("", new Vector(runlist.get(runlist.size() - 1)).toString());
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, runlist.get(runlist.size() - 1));
        }


    }



    private void wait(double timeS, Pos2D currentPos) {
        double start = getRuntime();
        int num = 0;
        while (getRuntime() < start + 0.5) {
            robot.runVec(new Vector(currentPos));
            robot.updateTelemetry();
            telemetry.addData("waiting", getRuntime());

            telemetry.update();

        }
        telemetry.update();
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

