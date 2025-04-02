package org.firstinspires.ftc.teamcode.pathing;

public class Odometry {
  public Pos2D currentPos, lastPos;
  public Pos2D lastEncVal = new Pos2D();
  public Pos2D curEncVal = new Pos2D();
  public Pos2D dField = new Pos2D();
  public Pos2D dRobot = new Pos2D();
  public double dL = 0;
  public double dR = 0;
  public double dB = 0;


  public Odometry(Pos2D startPos) {
    currentPos = startPos;
    lastPos = startPos;
  }
  public Odometry() {
    currentPos = new Pos2D(0,0,0);
    lastPos = new Pos2D();
    lastPos.setEqualTo(currentPos);
  }

  public void resetPosObserve() {
    Pos2D initPos = new Pos2D();
    initPos.x = Points.observeGrabTele.x;
    initPos.y = Points.observeGrabTele.y;
    initPos.theta = Points.observeGrabTele.theta;
    currentPos = initPos;
    lastPos = initPos;
  }

  public Pos2D updateTelemetry(Pos2D curEnc) {
    curEncVal.x = -encTicksToCm(curEnc.x);
    curEncVal.y = -encTicksToCm(curEnc.y);
    curEncVal.theta = -encTicksToCm(curEnc.theta);

    dL = curEncVal.x - lastEncVal.x;
    dR = curEncVal.y - lastEncVal.y;
    dB = curEncVal.theta - lastEncVal.theta;
    lastEncVal.setEqualTo(curEncVal);

    dRobot = dRobotPos(dL, dR, dB);
    dField = dRobotToDFieldPos(dRobot);
    currentPos = lastPos.add(dField);
    lastPos.setEqualTo(currentPos);
    return currentPos;
  }
  public static double encTicksToCm(double ticks) {
    return DriveConstants.ENC_WHEEL_RADIUS * 2 * Math.PI * ticks / DriveConstants.TICKS_PER_REV;
  }
  public static Pos2D dRobotPos(double dL, double dR, double dB) {
    double L,F,dtheta,dx,dy;
    L = DriveConstants.xWheelDistance;
    F = DriveConstants.yWheelOffset;
    dtheta = (dR-dL)/L;
    dx = (dL+dR)/2;
    dy = dB-(F*dtheta);
    return new Pos2D(dx,dy,dtheta);
  }

  public Pos2D dRobotToDFieldPos(Pos2D dRobot) {
    double dtheta = dRobot.theta;
    double dx = dRobot.x*Math.cos(currentPos.theta) - dRobot.y*Math.sin(currentPos.theta);
    double dy = dRobot.x*Math.sin(currentPos.theta) + dRobot.y*Math.cos(currentPos.theta);

    return new Pos2D(dx*DriveConstants.forwardMultiplier,dy*DriveConstants.lateralMultiplier,dtheta);
  }
}