/*package org.firstinspires.ftc.teamcode.autos;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Arms;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;


@Autonomous(name = "RightAutoTestingSystem")
@Disabled

public class RightAutoTester extends LinearOpMode {
    Drive robot;
    int numTimesSpecimen = 0;







    ArrayList<Pos2D> StartDeliverSpecimen = new ArrayList<>(Arrays.asList(Points.initPoseRight, Points.RSpecimenDeliverTester));

    ArrayList<Pos2D> GoLColor1 = new ArrayList<>(Arrays.asList(Points.RSpecimenDeliver, Points.LColorAt));
    ArrayList<Pos2D> GoLColor2 = new ArrayList<>(Arrays.asList(Points.LColorAt, Points.LColorObserve));

    ArrayList<Pos2D> GoMColor1 = new ArrayList<>(Arrays.asList(Points.LColorObserve, Points.MColorAt));
    ArrayList<Pos2D> GoMColor2 = new ArrayList<>(Arrays.asList(Points.MColorAt, Points.MColorObserve));

    ArrayList<Pos2D> PreSpecimenPickupfromM = new ArrayList<>(Arrays.asList(Points.MColorObserve, Points.WeirdAutoGrab));
    ArrayList<Pos2D> PreSpecimenDeliver= new ArrayList<>(Arrays.asList(Points.WeirdAutoGrab, Points.RSpecimenPreDeliver));
    ArrayList<Pos2D> AfterSpecimenDeliver= new ArrayList<>(Arrays.asList(Points.RSpecimenPreDeliver, Points.AfterRSpecimenDeliver));
    ArrayList<Pos2D> PreSpecimenPickup = new ArrayList<>(Arrays.asList(Points.AfterRSpecimenDeliver, Points.WeirdAutoGrab));

    boolean isSpecimenPlacing = false;

    double specimenPlaceTime = 0;

    int specimen = 0;




    @Override
        public void runOpMode() throws InterruptedException {





            //telemetry.addData("Start OpMode", "Auto1");
            //telemetry.update();

            robot = new Drive(hardwareMap, Points.initPoseRight);
        telemetry.addData(">", "Touch Play to start OpMode");
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


       robot.lVert.setTargetPosition(Arms.VertSpecimenReady);
        robot.rVert.setTargetPosition(Arms.VertSpecimenReady);
        runList(StartDeliverSpecimen);
        Thread.sleep(5000);
        robot.outtakeArm.setPosition(Arms.outtakeArmInit);
        robot.intakeGrabReady();


        //Left Color Stuff
        runList(GoLColor1);
        robot.vertSlide(Arms.VertBottom);
        intake1();
        Thread.sleep(5000);

        runListNoWait(GoLColor2);


        runList(GoMColor1);
        intake1();
        Thread.sleep(5000);
        runListNoWait(GoMColor2);
        Thread.sleep(160);

        robot.resetStuff();
        robot.intakeGrab.setPosition(Arms.intakeGrabRelease);
        robot.weirdAutoGrab();
        robot.lHorz.setPosition(Arms.lHorzOutAuto);
        robot.rHorz.setPosition(Arms.rHorzOutAuto);


        //2nd Deliver

        runListNoWait(PreSpecimenPickupfromM);
        observeReadyCycle();
        observeReadyCycle();
        observeReadyCycle();
        robot.weirdAutoGrab();


        }

        private void observeReadyCycle() throws InterruptedException {
            robot.weirdAutoGrab();
            robot.lHorz.setPosition(Arms.lHorzSGrab);
            robot.rHorz.setPosition(Arms.rHorzSGrab);
            runList(PreSpecimenPickup);
            robot.lHorz.setPosition(Arms.lHorzOut);
            robot.rHorz.setPosition(Arms.rHorzOut);

            Thread.sleep(350);
            robot.intakeGrab.setPosition(Arms.intakeGrabGrab);
            Thread.sleep(80);
            robot.resetStuff();
            robot.specimenTransfer();

            Thread.sleep(320);
            robot.outtakeGrab.setPosition(Arms.outtakeGrabGrab);
            Thread.sleep(340);
            robot.intakeGrab.setPosition(Arms.intakeGrabRelease);
            Thread.sleep(50);

            specimenReady();
            runListNoWait(PreSpecimenDeliver);

            runListNoWait(specimen());

            robot.outtakeGrab.setPosition(Arms.outtakeGrabRelease);
            robot.resetOuttakeStuff();
            runListNotToEnd(AfterSpecimenDeliver);

        }


    private ArrayList<Pos2D> specimen() throws InterruptedException {
        specimen -= 6;
        Pos2D targetPos = new Pos2D(Points.RSpecimenDeliver.x, Points.RSpecimenDeliver.y + specimen, Points.RSpecimenDeliver.theta);

        ArrayList<Pos2D> observeToSpecimen = new ArrayList<>(Arrays.asList(Points.RSpecimenPreDeliver, targetPos));

        return observeToSpecimen;
    }

    private void specimenReady() throws InterruptedException {
        robot.specimenPlace();
        specimenPlaceTime = getRuntime();
        isSpecimenPlacing = true;
    }
    private void specimenReadyFirstPlace() throws InterruptedException {
        robot.specimenPlaceFirstAuto();
        specimenPlaceTime = getRuntime();
        isSpecimenPlacing = true;
    }
    private void intake2() throws InterruptedException {
        robot.intakeSwing.setPosition(Arms.intakeSwingLeftBound);
        //Thread.sleep(100);
        //robot.intakeWrist.setPosition(Arms.intakeWristRightAuto);
        //Thread.sleep(250);
        robot.intakeArm1.setPosition(0.18);
        //Thread.sleep(270);
        //robot.intakeGrab.setPosition(Arms.intakeGrabGrab);
        Thread.sleep(300);
        //robot.intakeArm1.setPosition(Arms.intakeArm1GrabReady);

    }

    private void intake1() throws InterruptedException {

        //Thread.sleep(100);
        robot.rHorz.setPosition(Arms.rHorzOutAuto);
        robot.lHorz.setPosition(Arms.lHorzOutAuto);
        robot.intakeWrist.setPosition(Arms.intakeWristRightAuto);
        Thread.sleep(320);
        robot.intakeArm1.setPosition(Arms.intakeArm1Grab);
        Thread.sleep(180);
        robot.intakeGrab.setPosition(Arms.intakeGrabGrab);
        Thread.sleep(180);
        robot.intakeArm1.setPosition(Arms.intakeArm1GrabReady);

    }





    private void runListNotToEnd(ArrayList<Pos2D> runlist) {
        VectorBuilder vB = new VectorBuilder(runlist);
        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEndAuto(robot.odometry.currentPos)) {
            robot.updateTelemetry();
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            if (vB.isAtEndAuto(robot.odometry.currentPos)) {
                break;
            }
            if (isSpecimenPlacing && getRuntime() > 1 + specimenPlaceTime) {
                isSpecimenPlacing = false;
            }
            telemetry.addData("", new Vector(runlist.get(runlist.size() - 1)).toString());
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, runlist.get(runlist.size() - 1));
        }

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
            if (isSpecimenPlacing && getRuntime() > 1 + specimenPlaceTime) {
                isSpecimenPlacing = false;
            }
            telemetry.addData("", new Vector(runlist.get(runlist.size() - 1)).toString());
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, runlist.get(runlist.size() - 1));
        }
        wait(0.35, runlist.get(runlist.size() - 1));

    }

    private void runList(ArrayList<Pos2D> runlist, double waittime) {
        VectorBuilder vB = new VectorBuilder(runlist);
        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEnd(robot.odometry.currentPos)) {
            robot.updateTelemetry();
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            if (vB.isAtEnd(robot.odometry.currentPos)) {
                break;
            }

            telemetry.addData("", new Vector(runlist.get(runlist.size() - 1)).toString());
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, runlist.get(runlist.size() - 1));
        }
        wait(waittime, runlist.get(runlist.size() - 1));

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

*/
