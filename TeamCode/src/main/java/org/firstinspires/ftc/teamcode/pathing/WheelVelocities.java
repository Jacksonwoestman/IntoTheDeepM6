package org.firstinspires.ftc.teamcode.pathing;

public class WheelVelocities {
  double fL;
  double fR;
  double bL;
  double bR;
  
  public WheelVelocities(double frL, double frB, double baL, double baR) {
    fL = frL;
    fR = frB;
    bL = baL;
    bR = baR;
  }

  public String toString() {
    return "(" + fL + " " + fR + " " + bL + " " + bR + ")";
  }
}