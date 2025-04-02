package org.firstinspires.ftc.teamcode.testcodes;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pathing.Bezier;
import org.firstinspires.ftc.teamcode.pathing.DriveConstants;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.VectorBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


@Autonomous(name = "BezierTest")
@Disabled

public class BezierTest extends LinearOpMode {
    Bezier b;



    @Override
    public void runOpMode() throws InterruptedException {



        //telemetry.addData("Start OpMode", "Auto1");
        //telemetry.update();
        Pos2D initPose = new Pos2D(0, 0, 0);
        Pos2D parkingPose = new Pos2D(20, 0, 0);
        ArrayList<Pos2D> bList1 = new ArrayList<>(Arrays.asList(initPose, parkingPose));


        b = new Bezier(bList1);
        for(int i = 0; i <= 20; i++) {
            telemetry.addData("wassup", b.getPosAtTime(i/20.0));
        }
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();

        //telemetry.addData("x", field1.toString());
        //telemetry.update();
        //Thread.sleep(4000);

        //telemetry.addData("x", field1.b.getPos(0.5).toString());
        // telemetry.addData(">", field1.fieldToString());



        while (!isStopRequested() && !opModeIsActive()) {

            VectorBuilder vb = new VectorBuilder(bList1);
            telemetry.addData("blength", vb.bezier.length);
            int i;
            for(i = 0; i < vb.numPts; i++) {
                telemetry.addData("", vb.points.get(i));
            }
            telemetry.update();
        }

        if (opModeIsActive() && !isStopRequested()) {


        }
    }

    public void runAutonoumousMode() throws InterruptedException {



    }
}

