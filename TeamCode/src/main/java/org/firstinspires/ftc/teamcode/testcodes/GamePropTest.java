package org.firstinspires.ftc.teamcode.testcodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name = "GameProp", group = "1")
@Disabled
public class GamePropTest extends LinearOpMode {

    OpenCvCamera webcam;
    private ElapsedTime runtime = new ElapsedTime();
    enum  GamePropLocation  {LEFT, MIDDLE, RIGHT};

    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize Camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "FrontCam"), cameraMonitorViewId);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });

        webcam.setPipeline(new SamplePipeline_Hue());

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            telemetry.update();
            sleep(100);

            telemetry.addData("Frame Count", webcam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
            telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
            telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
            telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
            telemetry.update();

        }
    }

        //HUE Based Pipeline
        class SamplePipeline_Hue extends OpenCvPipeline
        {

            Scalar RED = new Scalar(255.0, 0.0, 0.0);
            Scalar BLUE = new Scalar(0.0, 0.0, 255.0);
            Scalar Green = new Scalar(0.0, 255.0, 0.0);

            Point Left_pointA = new Point(400, 400.0);
            Point Left_pointB = new Point(500, 500);

            Point Mid_pointA = new Point(800, 350);
            Point Mid_pointB = new Point(900, 450);

            Point Right_pointA =  new Point(1180, 400);
            Point Right_pointB =  new Point(1280, 500);

            Mat left_Cb = null;
            Mat mid_Cb = null;
            Mat right_Cb = null;
            Mat HSV = new Mat();
            Mat hue = new Mat();
            int avg1, avg2, avg3;

            boolean viewportPaused;

            public void inputToHue(Mat input) {
                Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV);
                Core.extractChannel(HSV, hue, 0); //Gets the HUE value out
            }

            @Override
            public void init(Mat firstFrame)
            {
                inputToHue(firstFrame);

                left_Cb = hue.submat(new Rect(Left_pointA, Left_pointB));
                mid_Cb = hue.submat(new Rect(Mid_pointA, Mid_pointB));
                right_Cb = hue.submat(new Rect(Right_pointA, Right_pointB));

            }

            @Override
            public Mat processFrame(Mat input)
            {
                inputToHue(input);

                avg1 = (int)Core.mean(left_Cb).val[0];
                avg2 = (int)Core.mean(mid_Cb).val[0];
                avg3 = (int)Core.mean(right_Cb).val[0];

                double maxOneTwo = Math.max(avg1, avg2);
                double max = Math.max(maxOneTwo, avg3);

                Imgproc.rectangle(
                        input,
                        Left_pointA,
                        Left_pointB,
                        RED, 4);

                Imgproc.rectangle(
                        input,
                        Mid_pointA,
                        Mid_pointB,
                        RED, 4);
                Imgproc.rectangle(
                        input,
                        Right_pointA,
                        Right_pointB,
                        RED, 4);

                if(max == avg1) {
                    Imgproc.rectangle(
                            input,
                            Left_pointA,
                            Left_pointB,
                            Green);
                    telemetry.addData("Left", avg1);
                } else if (max == avg2) {
                    Imgproc.rectangle(
                            input,
                            Mid_pointA,
                            Mid_pointB,
                            Green);
                    telemetry.addData("Mid", avg2);
                } else {
                    Imgproc.rectangle(
                            input,
                            Right_pointA,
                            Right_pointB,
                            Green);
                    telemetry.addData("Right", avg3);
                }



                telemetry.addData("[avg1]", avg1);
                telemetry.addData("[avg2]", avg2);
                telemetry.addData("[avg3]", avg3);

                telemetry.update();
                return input;
            }

            @Override
            public void onViewportTapped()
            {
                viewportPaused = !viewportPaused;

                if(viewportPaused)
                {
                    webcam.pauseViewport();
                }
                else
                {
                    webcam.resumeViewport();
                }
            }
        }


    }








