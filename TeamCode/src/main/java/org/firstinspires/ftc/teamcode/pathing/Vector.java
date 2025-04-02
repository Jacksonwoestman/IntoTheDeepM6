package org.firstinspires.ftc.teamcode.pathing;

public class Vector {

    double direction;
    double magnitude;
    public Pos2D targetPos, currentPos;

    public Vector(Pos2D currentPos, Pos2D targetPos, double speed) {
        direction = currentPos.angleRad(targetPos);
        if(currentPos.distance(targetPos) < speed) {
            magnitude = currentPos.distance(targetPos);
        } else {
            magnitude = speed;
        }
        this.targetPos = targetPos;
        this.currentPos = currentPos;
    }
    public Vector(Pos2D currentPos) {
        direction = 0;
        magnitude = 0;
        targetPos = currentPos;
        this.currentPos = currentPos;
    }

    public Vector(double d, double m) {
        direction = d;
        magnitude = m;

    }


    public boolean isAtEnd(Pos2D currentPos) {
        return currentPos.isEqualIshTo(targetPos);
    }



    public String toString() {
        return "(" + ((double)((int)(direction * 100)))/100 + " " + ((double)((int)(magnitude * 100)))/100 + ") " + "tp" + targetPos.toString() + "cp" + currentPos.toString();
    }


    public Pos2D toRobotPower(double heading, Pos2D error) {
        // Convert heading to radians (assuming heading is in degrees)
        double dx = magnitude * Math.cos(direction) + error.x;
        double dy = magnitude * Math.sin(direction) + error.y;

        double df = dx * Math.cos(heading) + dy * Math.sin(heading);
        double ds = -dx * Math.sin(heading) + dy * Math.cos(heading);
        // Return the robot-relative coordinates (x', y')
        return new Pos2D(df, ds, error.theta);
    }

    public Pos2D toComponents() {
        double x = magnitude * Math.cos(direction);
        double y = magnitude * Math.sin(direction);
        return new Pos2D(x, y, 0);

    }






}