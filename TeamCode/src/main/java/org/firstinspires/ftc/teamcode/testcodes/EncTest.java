package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.Dash;
import org.firstinspires.ftc.teamcode.pathing.Odometry;
import org.firstinspires.ftc.teamcode.pathing.Points;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;


@TeleOp(name = "Localization Test")

public class EncTest extends LinearOpMode {
    Drive robot = null;

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
            robot = new Drive(hardwareMap, new Pos2D(0,0,0));
            while(opModeIsActive()) {
                runAutonoumousMode();
            }
        }
    }

    public void runAutonoumousMode() throws InterruptedException {
        robot.updateTelemetry();
        Pos2D currentPos = robot.odometry.currentPos;
        telemetry.addData("x", currentPos.x);
        telemetry.addData("y", currentPos.y);
        telemetry.addData("theta", currentPos.theta);
        telemetry.addData("", 0);
        telemetry.addData("l", robot.lEncoder.getCurrentPosition());
        telemetry.addData("r", robot.rEncoder.getCurrentPosition());
        telemetry.addData("b", robot.bEncoder.getCurrentPosition());





        telemetry.update();
        robot.dashboard.update(robot.odometry.currentPos, new Pos2D());

    }


}