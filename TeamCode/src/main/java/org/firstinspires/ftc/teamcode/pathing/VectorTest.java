package org.firstinspires.ftc.teamcode.pathing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Drive;


@Autonomous(name = "Vector Test")
@Disabled
public class VectorTest extends LinearOpMode {
    Drive robot;



    @Override
    public void runOpMode() throws InterruptedException {



        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();


        robot = new Drive(hardwareMap, new Pos2D());
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

            Vector v = new Vector(new Pos2D(0,20,0));

            while(opModeIsActive()) {
                robot.runVec(v);
                robot.updateTelemetry();
                telemetry.addData("currentPos", robot.odometry.currentPos.toString());
                telemetry.addData("vector", v.toString());
               // telemetry.addData("robotpower", v.toRobotPower(robot.odometry.currentPos.theta).toString());
                telemetry.addData("vels", robot.getVel().toString());
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
