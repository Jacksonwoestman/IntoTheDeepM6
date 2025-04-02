package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.DriveConstants;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;


@Autonomous(name = "Back y Forth")


public class BackAndForth extends LinearOpMode {
    Drive robot;





    final Pos2D init = new Pos2D(0,0,0);
    final Pos2D forth = new Pos2D(50, 0, Math.PI/2);
    final Pos2D rightward = new Pos2D(50,-50,Math.PI);
    final Pos2D back = new Pos2D(0, -50, 3 * Math.PI /2);

    ArrayList<Pos2D> Path1 = new ArrayList<>(Arrays.asList(init, forth));
    ArrayList<Pos2D> Path2 = new ArrayList<>(Arrays.asList(forth, rightward));
    ArrayList<Pos2D> Path3 = new ArrayList<>(Arrays.asList(rightward, back));

    ArrayList<Pos2D> Path4 = new ArrayList<>(Arrays.asList(back, init));

    VectorBuilder vB1;
    VectorBuilder vB2;
    VectorBuilder vB3;
    VectorBuilder vB4;










    @Override
    public void runOpMode() throws InterruptedException {





        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();

        robot = new Drive(hardwareMap, init);
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        vB1 = new VectorBuilder(Path1);
        vB2 = new VectorBuilder(Path2);
        vB3 = new VectorBuilder(Path3);
        vB4 = new VectorBuilder(Path4);


        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }

        if (opModeIsActive() && !isStopRequested()) {
            runAutonoumousMode();
        }
    }

    public void runAutonoumousMode() throws InterruptedException {
        runList(vB1);
        runList(vB2);
        runList(vB3);
        runList(vB4);


        runList(vB1);
        runList(vB2);
        runList(vB3);
        runList(vB4);


    }









    private void runList(VectorBuilder vB) {
        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEnd(robot.odometry.currentPos)) {
            robot.updateTelemetry();
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            if (vB.isAtEnd(robot.odometry.currentPos)) {
                break;
            }
            telemetry(vB);
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos, vB.initPoints.get(vB.initPoints.size() - 1));
        }
        wait(1, vB.initPoints.get(vB.initPoints.size() - 1), vB);
    }

    private void wait(double timeS, Pos2D currentPos, VectorBuilder vB) {
        double start = getRuntime();
        while (getRuntime() < start + timeS) {
            robot.runVec(new Vector(currentPos));
            robot.updateTelemetry();
            telemetry.addData("waiting", 0);
            telemetry(vB);
        }
        robot.runVec(new Vector(robot.odometry.currentPos));
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


