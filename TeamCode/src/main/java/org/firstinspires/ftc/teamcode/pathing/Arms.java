package org.firstinspires.ftc.teamcode.pathing;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Arms {


        public static int vertBucket = 2080;
        public static int vertSpecimenPlace = 480;
        public static int vertAfterReset = 460;
        public static int vertInit = 52;
        public static int vertInitAuto = 40;

        public static int vertBottom = 0;
        public static int vertHang = 2025;



        public static double lHorzOut = 0.64;
        public static double rHorzOut = 0.64;
        public static double lHorzInit = 0.87;
        public static double rHorzInit = 0.87;

        public static double lHorzHalf = (lHorzInit - lHorzOut)/2 + lHorzOut;
        public static double rHorzHalf = (rHorzInit - rHorzOut)/2 + rHorzOut;



        public static double intakeArmInit = 0.76;
        public static double intakeArmGrab = 0.32;
        public static double intakeArmUp = 0.78;
        public static double intakeArmLaunch = 0.64;

        public static double intVel1 = 0.80;
        public static double intVel2 = -0.3;
        public static double intVel3 = 0.5;
        public static double intVel4 = 0.5;

        public static double intakeTime1 = 0.1;




        public static double outtakeGrabRelease = 0.65;
        public static double outtakeGrabReady = 0.48;
        public static double outtakeGrabStart = 0.32;
        public static double outtakeGrabGrab = 0.26;

        public static double outtakeWristInit = 0.85;
        public static double outtakeWrist180 = 0.192;
        public static double outtakeWristHalf = 0.5;

        public static double outtakeArmStart = 0.25-0.095;

        public static double outtakeArmInit = 0.020;

        public static double outtakeArmSpecimenPlace = 0.255;

        public static double outtakeArmBucket = 0.68;

        public static double outtakeArmSpecimenGrab = 1-0.06;

        public static double outtakeArmPark = 0.74-0.095+0.04;



    }













