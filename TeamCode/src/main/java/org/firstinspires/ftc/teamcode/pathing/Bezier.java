package org.firstinspires.ftc.teamcode.pathing;

import java.util.ArrayList;

public class Bezier {
  private ArrayList<Pos2D> points;
  public double length;

  public Bezier(ArrayList<Pos2D> points) {
    this.points = points;
    length = getCurveLength(20);
  }

  public Pos2D getPosAtTime(double t) {
    return calculateBezierPoint(t, points);
  }

  public double getCurveLength(int numSamples) {
    double length = 0;
    Pos2D previousPoint = getPosAtTime(0);

    for (int i = 1; i <= numSamples; i++) {
      double t = (double) i / numSamples;
      Pos2D currentPoint = getPosAtTime(t);
      length += distance(previousPoint, currentPoint);
      previousPoint = currentPoint;
    }

    return length;
  }

  private Pos2D calculateBezierPoint(double t, ArrayList<Pos2D> controlPoints) {
    int n = controlPoints.size() - 1;
    double theta = controlPoints.get(0).theta + t * (controlPoints.get(controlPoints.size() - 1).theta - controlPoints.get(0).theta);
    Pos2D point = new Pos2D(0, 0, theta);

    for (int i = 0; i <= n; i++) {
      double bernsteinPolynomial = bernstein(i, n, t);
      point.x += bernsteinPolynomial * controlPoints.get(i).x;
      point.y += bernsteinPolynomial * controlPoints.get(i).y;
    }

    return point;
  }

  private double bernstein(int i, int n, double t) {
    return binomialCoefficient(n, i) * Math.pow(t, i) * Math.pow(1 - t, n - i);
  }

  private int binomialCoefficient(int n, int k) {
    int result = 1;
    for (int i = 1; i <= k; i++) {
      result = result * (n - (k - i)) / i;
    }
    return result;
  }

  private double distance(Pos2D p1, Pos2D p2) {
    return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
  }
}





/*package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Bezier extends Actions {
  private List<Pos2D> pts;

  private int n;
  double length;

  public Bezier(List<Pos2D> points) {
    pts = points;
    n = pts.size();
    length = (1/8)*(fOfX(0) + 3*fOfX(1/3) + 3*fOfX(2/3) + fOfX(1));
    //length = Math.abs(Math.hypot((pts.get(n-1).x - pts.get(0).x), (pts.get(n-1).y - pts.get(0).y)));
    //length = 20;
  }


  private Pos2D deriv(double t) {
    int n = pts.size() - 1;
    ArrayList<Pos2D> derivativePoints = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      double x = n * (pts.get(i + 1).x - pts.get(i).x) * Math.pow(1 - t, n - 1);
      double y = n * (pts.get(i + 1).y - pts.get(i).y) * Math.pow(1 - t, n - 1);
      derivativePoints.add(new Pos2D(x, y));
    }
    t*=(derivativePoints.size()-1);
    return derivativePoints.get((int)t);
  }

  private double fOfX(double t) {
    t = (int)(t*(pts.size()-1));
    double dy = Math.pow(deriv(t).y, 2);
    double dx = Math.pow(deriv(t).x, 2);
    return Math.sqrt(dy+dx);
  }
  
  public Pos2D getPos(double t){
    double x =0.0;
    double y =0.0;
    for(int j = 0; j<n; j++){
      x+= Math.pow(t, j) * cjx(j);
      y+= Math.pow(t, j) * cjy(j);
    }
    return new Pos2D(x,y);
  }

  private double cjx(int j) {
    double sum = 0;
    for(int i = 0; i < j; i++) {
      sum += ((Math.pow(-1, i + j)) * (pts.get(i).x)) / ((factorial(i)) * (factorial(j - i)));
    }
    return sum * (factorial(n)/factorial(n-j));
  }
  private double cjy(int j) {
    double sum = 0;
    for(int i = 0; i < j; i++) {
      sum += ((Math.pow(-1, i + j)) * (pts.get(i).y)) / ((factorial(i)) * (factorial(j - i)));
    }
    return sum * (factorial(n)/factorial(n-j));
  }

  private int factorial(int n) {
    if (n == 1 || n == 0) {
      return 1;
    } else {
      return n * factorial (n-1);
    }
  }
  
}*/