package org.firstinspires.ftc.teamcode.vision;/*package org.firstinspires.ftc.teamcode.vision;

import org.firstinspires.ftc.teamcode.pathing.Arms;
import org.firstinspires.ftc.teamcode.pathing.Pos2D;
import org.firstinspires.ftc.teamcode.pathing.Vector;

public class ColorSensorMovement {
    double lHorzPos;
    double lHorzLastPos;
    double swingPos;
    double swingLastPos;
    double wristPos;
    Pos2D robotPos;
    Pos2D lastRobotPos;

    public ColorSensorMovement(double lhp, double sp, double wp, Pos2D rp) {
        lHorzPos = lhp;
        lHorzLastPos = lhp;

        swingPos = sp;
        swingLastPos = sp;
        wristPos = wp;
        robotPos = rp;
        lastRobotPos = rp;
    }

    public void update(double lhp, double sp, double wp, Pos2D rp) {
        lHorzLastPos = lHorzPos;
        swingLastPos = swingPos;
        lastRobotPos = robotPos;

        lHorzPos = lhp;
        swingPos = sp;
        wristPos = wp;
        robotPos = rp;
    }

    /*public Pos2D getMovement() {
        double heading = getVector();
        double dx = Math.cos(heading);
        double dy = Math.sin(heading);
        //double lHorz = Arms.getLHorzAngle(dx);
        double rHorz = Arms.rHorzInit + (Arms.lHorzInit - lHorz);
        double swing = swingPos + dy * (-Arms.intakeSwingLeftBound + Arms.intakeSwingRightBound)/20;
        return new Pos2D(lHorz, rHorz, swing);
    }

    private double getVector() {
        Pos2D Arms = armsVector().toComponents();
        Pos2D Robot = robotVector().toComponents();
        double x = Arms.x + Robot.x;
        double y = Arms.y + Robot.y;
        double heading;
        if(x == 0) {
            if(y>0) {heading = Math.PI/2;} else {heading = 3 * Math.PI/2;}
        } else {
            heading = Math.atan(y/x);
            if(x < 0) {
                heading = 180 - heading;
            }
            while(heading < 0) {
                heading += 2 *Math.PI;
            }
        }
        return heading;
    }

    private Vector armsVector() {
        double x = Arms.getLHorzExtention(lHorzPos);
        double xl = Arms.getLHorzExtention(lHorzLastPos);
        double dx = x - xl;
        double dy = swingPos - swingLastPos;
        double armDirection;
        double armMagnitude;
        if(dx == 0) {
            armMagnitude = dy;
            if(dy>0) {armDirection = Math.PI/2;} else {armDirection = 3 * Math.PI/2;}
        } else {
            armDirection = Math.atan(dy/dx);
            armMagnitude = Math.hypot(dx, dy);
            if(dx < 0) {
                armDirection = 180 - armDirection;
            }
            while(armDirection < 0) {
                armDirection += 2 *Math.PI;
            }
        }
        return new Vector(armDirection, armMagnitude);
    }

    private Vector robotVector() {
        double dx = robotPos.x - lastRobotPos.x;
        double dy = robotPos.y - lastRobotPos.y;
        double df = dx * Math.cos(robotPos.theta) + dy * Math.sin(robotPos.theta);
        double ds = -dx * Math.sin(robotPos.theta) + dy * Math.cos(robotPos.theta);
        double robotDirection;
        double robotMagnitude;
        if(df == 0) {
            robotMagnitude = ds;
            if(ds>0) {robotDirection = Math.PI/2;} else {robotDirection = 3 * Math.PI/2;}
        } else {
            robotDirection = Math.atan(ds/df);
            robotMagnitude = Math.hypot(df, ds);
            if(df < 0) {
                robotDirection = 180 - robotDirection;
            }
            while(robotDirection < 0) {
                robotDirection += 2 *Math.PI;
            }
        }
        return new Vector(robotDirection, robotMagnitude);
    }
}
*/