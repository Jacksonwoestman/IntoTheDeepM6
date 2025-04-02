package org.firstinspires.ftc.teamcode.pathing;

public class Pos2D {
  public double x;
  public double y;
  public double theta;
  public int vertPos = 0;
  public int horzPos = 0;

  public Pos2D(double xpos, double ypos, double t) {
    x = xpos;
    y = ypos;
    theta = t;
  }
  public Pos2D(double xpos, double ypos, double t, int ivertPos, int ihorzPos) {
    x = xpos;
    y = ypos;
    theta = t;
    vertPos = ivertPos;
    horzPos = ihorzPos;
  }

  public Pos2D() {
    x = 0;
    y = 0;
    theta = 0;
  }
  public Pos2D add(Pos2D pos2) {
    return new Pos2D(x + pos2.x,y + pos2.y,theta + pos2.theta);
  }
  public String toString() {
    String s = "(" + x + "," + y + "," + theta + ")";
    return s;
  }

  public double distance(Pos2D pos2) {
    return Math.sqrt(Math.pow((x - pos2.x),2) + Math.pow((y - pos2.y),2));
  }

  public double angleRad(Pos2D pos2) {
    double deltaY = pos2.y - this.y;
    double deltaX = pos2.x - this.x;
    return Math.atan2(deltaY, deltaX);
  }

  public boolean isEqualIshTo(Pos2D pos2) {
    double pP = DriveConstants.posPrecision;
    if (Math.abs(pos2.x - this.x) < pP && Math.abs(pos2.y - this.y) < pP ) {
      return true;
    }
    return false;
  }

  public boolean isEqualIshTo(Pos2D pos2, double precision) {
    double pP = precision;
    if (Math.abs(pos2.x - this.x) < pP && Math.abs(pos2.y - this.y) < pP ) {
      return true;
    }
    return false;
  }

  public void setEqualTo(Pos2D pos2) {
    x = pos2.x;
    y = pos2.y;
    theta = pos2.theta;
  }

}