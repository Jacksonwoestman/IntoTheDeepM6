package org.firstinspires.ftc.teamcode.autos;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Bezier;
import org.firstinspires.ftc.teamcode.pathing.PIDController;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


@Autonomous(name = "Auto1")
@Disabled
public class Auto1 extends LinearOpMode {
    Drive robot;


    Pos2D init = new Pos2D(0, 0,Math.PI);
    Pos2D specimendeliver = new Pos2D(50, 0, Math.PI);


    ArrayList<Pos2D> initToSpecimen = new ArrayList<>(Arrays.asList(init, specimendeliver));




    @Override
    public void runOpMode() throws InterruptedException {



        robot = new Drive(hardwareMap, init);
        VectorBuilder vB = new VectorBuilder(initToSpecimen);
            telemetry.addData("wassup", vB.pToString());
            telemetry.addData("wassup", vB.vToString());

        //telemetry.addData("Start OpMode", "Auto1");
        telemetry.update();






        while (!isStopRequested() && !opModeIsActive()) {
        }

        if (opModeIsActive() && !isStopRequested()) {
            runAutonoumousMode();
        }
    }

    public void runAutonoumousMode() throws InterruptedException {
        runList(initToSpecimen);


    }

    private void runList(ArrayList<Pos2D> runlist) throws InterruptedException {
        VectorBuilder vB = new VectorBuilder(runlist);

        robot.dashboard.addVectors(vB.vectors);
        while (!vB.isAtEnd(robot.odometry.currentPos)) {
            robot.updateTelemetry();
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            PIDController pid = new PIDController();
            Pos2D error = pid.calculate(vB.getVector(robot.odometry.currentPos).targetPos, robot.odometry.currentPos);
            telemetry.addData("vector", vB.getVector(robot.odometry.currentPos).toString());
            telemetry.addData("pos", robot.odometry.currentPos.toString());
            telemetry.addData("x", error.x);
            telemetry.addData("y", error.y);
            telemetry.addData("t", error.theta);
            telemetry.update();
            robot.dashboard.update(robot.odometry.currentPos, vB.getVector(robot.odometry.currentPos).targetPos);

        }
        wait(1, runlist.get(runlist.size() - 1));

    }

private void wait(double timeS, Pos2D currentPos) {
    double start = getRuntime();
    while (getRuntime() < start + timeS) {
       // robot.stayAt(currentPos);
        robot.updateTelemetry();


    }
}
}