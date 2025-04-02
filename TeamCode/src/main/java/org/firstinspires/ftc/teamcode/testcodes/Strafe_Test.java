package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;


@Autonomous(name = "Strafe Test")
@Disabled

public class Strafe_Test extends LinearOpMode {
    Drive robot;


    @Override
    public void runOpMode() throws InterruptedException {



        telemetry.addData("Start OpMode", "Auto1");
        telemetry.update();
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
        robot = new Drive(hardwareMap, new Pos2D());
        Pos2D initPose = new Pos2D(0, 0, 0);
        Pos2D parkingPose = new Pos2D(100, 0, 0);
        ArrayList<Pos2D> List1 = new ArrayList<>(Arrays.asList(initPose, parkingPose));
        VectorBuilder vB = new VectorBuilder(List1);
        while(!vB.isAtEnd(robot.odometry.currentPos)) {
            robot.runVec(vB.getVector(robot.odometry.currentPos));
            telemetry.addData("loc", robot.odometry.currentPos.toString());
            telemetry.update();
            robot.updateTelemetry();
        }
    }
}