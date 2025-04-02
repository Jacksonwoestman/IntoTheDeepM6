package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;


@Autonomous(name = "PIDTester")
@Disabled

public class PIDTester extends LinearOpMode {
    Drive robot;



    @Override
    public void runOpMode() throws InterruptedException {



        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();


        robot = new Drive(hardwareMap, new Pos2D(0,0,Math.PI));
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        //telemetry.addData("x", field1.toString());
        //telemetry.update();
        //Thread.sleep(4000);

        //telemetry.addData("x", field1.b.getPos(0.5).toString());
        // telemetry.addData(">", field1.fieldToString());



        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.update();
        }

        if (opModeIsActive() && !isStopRequested()) {

            Vector v = new Vector(new Pos2D(0,0,Math.PI));

            while(opModeIsActive()) {
                robot.runVec(v);
                robot.updateTelemetry();
                telemetry.addData("currentPos", robot.odometry.currentPos.toString());
                telemetry.addData("vector", v.toString());
                //telemetry.addData("robotpower", v.toRobotPower(robot.odometry.currentPos.theta).toString());
                telemetry.addData("vels", robot.getVel().toString());
                Pos2D error = robot.pidController.calculate(v.targetPos, robot.odometry.currentPos);

                telemetry.addData("terror", error.theta);
                telemetry.addData("xerror", error.x);
                telemetry.addData("yerror", error.y);
                telemetry.update();
                if(robot.odometry.currentPos.x + robot.odometry.currentPos.y > 30) {
                    break;
                }

            }

        }
    }

    public void runAutonoumousMode() throws InterruptedException {



    }
}

