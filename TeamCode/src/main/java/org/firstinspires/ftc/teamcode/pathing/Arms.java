package org.firstinspires.ftc.teamcode.pathing;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Arms {

    public static int vertBucket = 2050;
    public static int vertSpecimenPlace = 400;
    public static int vertAfterReset = 400;
    public static int vertInit = 120;
    public static int vertBottom = 0;
    public static int vertHang = 2000;



    public static double lHorzOut = 0.485;
    public static double rHorzOut = 0.485;
    public static double lHorzInit = 0.84;
    public static double rHorzInit = 0.84;

    public static double lHorzHalf = (lHorzInit - lHorzOut)/2 + lHorzOut;
    public static double rHorzHalf = (rHorzInit - rHorzOut)/2 + rHorzOut;



    public static double intakeArmInit = 0.80;
    public static double intakeArmGrab = 0.40;
    public static double intakeArmUp = 0.78;
    public static double intakeArmLaunch = 0.66;




    public static double outtakeGrabRelease = 0.5;
    public static double outtakeGrabReady = 0.47;

    public static double outtakeGrabGrab = 0.4575;

    public static double outtakeWristInit = 0.192;
    public static double outtakeWrist180 = 0.854;
    public static double outtakeWristHalf = 0.5;

    public static double outtakeArmStart = 0.25-0.095;

    public static double outtakeArmInit = 0.029;

    public static double outtakeArmSpecimenPlace = 0.37-0.09;

    public static double outtakeArmBucket = 0.76-0.095;

    public static double outtakeArmSpecimenGrab = 1-0.075;




}
