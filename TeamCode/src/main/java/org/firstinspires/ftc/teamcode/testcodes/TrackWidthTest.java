package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Drive;
import org.firstinspires.ftc.teamcode.pathing.DriveConstants;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;


@TeleOp(name = "TrackWidth Test")
@Disabled

public class TrackWidthTest extends LinearOpMode {
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
            robot = new Drive(hardwareMap, new Pos2D());
            while(opModeIsActive()) {
            while(!gamepad1.x) {
                if (gamepad1.a) {
                    robot.setMotorVel(0.25,0.25,-0.25,-0.25);
                } else {
                    robot.setMotorVel(0,0,0,0);
                }
                robot.updateTelemetry();
                telemetry.addData("pos", robot.odometry.currentPos.toString());
                telemetry.update();
            }

            double theta = robot.odometry.currentPos.theta;
            double multiplier = (5 * Math.PI) / theta;
            double xwheeldistance = multiplier * DriveConstants.xWheelDistance;
            telemetry.addData("xwd", xwheeldistance);
            telemetry.update();
            Thread.sleep(10000);
        }}
    }
}