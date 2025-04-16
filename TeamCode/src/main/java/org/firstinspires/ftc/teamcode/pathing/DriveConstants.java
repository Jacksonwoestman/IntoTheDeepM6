package org.firstinspires.ftc.teamcode.pathing;

import com.acmerobotics.dashboard.config.Config;

@Config
public class DriveConstants {


  public static double xWheelDistance = 31.36;
  public static double yWheelOffset = 3.75;
  public static double forwardMultiplier = 0.99752;
  public static double lateralMultiplier = 0.99723;
  public static final double TICKS_PER_REV = 2000;//tuned
  public static final double MAX_RPM = 435;//tuned
  public static double WHEEL_RADIUS = 4.8 ; // cm//tuned
  public static double ENC_WHEEL_RADIUS = 1.6 ; // cm//tuned

  public static double GEAR_RATIO = 1; // output (wheel) speed / input (motor) speed
  public static double wheelTrackOffset = xWheelDistance/2; // mech wheel to middle



  public static double dRobotAllowance = 0.05;
  public static double waitTime = 0.35;


  public static double kP = 0.26;
  public static double kI = 0.000001;//0.0000015;
  public static double kD = 0.61;//0.01;
  public static double tKP = 0.26;
  public static double tKI = 0.000001;
  public static double tKD = 0.58;//0.1;

  public static double posPrecision = 1;

  public static final int width = 1920;
  public static final int height = 1080;

  public static double blur = 7;

  public static double powerBegin = 0.85;
  public static double powerMax = 1;
  public static double powerEnd = -0.15;
}


/*
 * Constants shared between multiple drive types.
 *
 * Constants generated by LearnRoadRunner.com/drive-constants
 *
 * TODO: Tune or adjust the following constants to fit your robot. Note that the non-final
 * fields may also be edited through the dashboard (connect to the robot's WiFi network and
 * navigate to https://192.168.49.1:8080/dash). Make sure to save the values here after you
 * adjust them in the dashboard; **config variable changes don't persist between app restarts**.
 *
 * These are not the only parameters; some are located in the localizer classes, drive base classes,
 * and op modes themselves.
 */





  /*
   * These are the feedforward parameters used to model the drive motor behavior. If you are using
   * the built-in velocity PID, *these values are fine as is*. However, if you do not have drive
   * motor encoders or have elected not to use them for velocity control, these values should be
   * empirically tuned.
   */








