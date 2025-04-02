package org.firstinspires.ftc.teamcode.pathing;

import java.util.ArrayList;

public class VectorBuilder {
    double maxSpeed, minSpeed;
    public Bezier bezier;
    public ArrayList<Pos2D> points, initPoints;
    public ArrayList<Vector> vectors;
    double ptsPerCm;
    public int numPts;
    private boolean isNearEnd = false;
    private double voltage;
    double b, x, e;






    public VectorBuilder(ArrayList<Pos2D> bPoints) {
        b = DriveConstants.powerBegin;
        x = DriveConstants.powerMax;
        e = DriveConstants.powerEnd;
        initPoints = bPoints;
        bezier = new Bezier(bPoints);
        points = new ArrayList<Pos2D>();
        vectors = new ArrayList<Vector>();
        ptsPerCm = 1;
        numPts = (int)(bezier.length * ptsPerCm);
        if(numPts == 0) {numPts = 1;}
        initBezier();
        initVectors();
    }

    public VectorBuilder(ArrayList<Pos2D> bPoints, double b, double x, double e) {
        this.b = b;
        this.x = x;
        this.e = e;
        initPoints = bPoints;
        bezier = new Bezier(bPoints);
        points = new ArrayList<Pos2D>();
        vectors = new ArrayList<Vector>();
        ptsPerCm = 1;
        numPts = (int)(bezier.length * ptsPerCm);
        if(numPts == 0) {numPts = 1;}
        initBezier();
        initVectors();
    }

    private void initBezier() {
        for(int i = 0; i <= numPts - 1; i++) {points.add(bezier.getPosAtTime(i/(double)(numPts-1)));}
    }

    public boolean isAtEnd(Pos2D currentPos) {
        return currentPos.isEqualIshTo(initPoints.get(initPoints.size()-1));
    }

    public boolean isAtEndAuto(Pos2D currentPos) {
        return currentPos.isEqualIshTo(initPoints.get(initPoints.size()-1), 2.5);
    }
    public boolean isAlmostAtEnd(Pos2D currentPos, int numCmB4End) {
        return points.get(closestPoint(currentPos)).isEqualIshTo(points.get(points.size()-numCmB4End));
    }

    public Vector getVector(Pos2D currentPos) {
        if(currentPos.distance(initPoints.get(initPoints.size() - 1)) < 5) {isNearEnd = true;}
        if(isNearEnd) {return new Vector(initPoints.get(initPoints.size() -1));}
        return vectors.get(closestPoint(currentPos));
    }

    private void initVectors() {
        //I is going to point i + 1
        for (int i = 0; i <= numPts - 1; i++) {
            if (i == numPts - 1) {
                vectors.add(new Vector(points.get(i), points.get(i), getSpeed(i/(double)(numPts-1))));
            } else {
                Pos2D currentPos = points.get(i);
                Pos2D targetPos = points.get(i + 1);
                vectors.add(new Vector(currentPos, targetPos, getSpeed(i/(double)(numPts-1))));
            }
        }
    }
    private double getSpeed(double t) {
        double root = Math.pow((e - x)/(b - x),0.25);

        double power = (b - x) * Math.pow(root + 1, 4) * Math.pow((t-1/(root+1)), 4) + x;

        return power;
    }

    private int closestPoint(Pos2D currentLoc) {
        int shortestLoc = 0;
        double shortestLength = currentLoc.distance(points.get(0));
        for(int i = numPts - 1; i >= 0; i--) {
            double dis = currentLoc.distance(points.get(i));

            if(dis < shortestLength) {
                shortestLength = dis;
                shortestLoc = i;
            }
        }
        return shortestLoc;
    }

    public String pToString() {
        String s = "";
        for(int i = 0; i < points.size(); i++) {
            s += points.get(i).toString();
            s += "/n";
        }
        return s;
    }
    public String vToString() {
        String s = "";
        for(int i = 0; i < vectors.size(); i++) {
            s += vectors.get(i).toString();
        }
        return s;
    }


}
