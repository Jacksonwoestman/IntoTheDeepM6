package org.firstinspires.ftc.teamcode.pathing;

public class PIDController {

    private double kP;
    private double kI;
    private double kD;
    private double tKP;
    private double tKI;
    private double tKD;


    private double xpreviousError;
    private double xintegral;
    private double ypreviousError;
    private double yintegral;
    private double tpreviousError;
    private double tintegral;

    public PIDController() {
        kP = DriveConstants.kP;
        kI = DriveConstants.kI;
        kD = DriveConstants.kD;
        tKP = DriveConstants.tKP;
        tKI = DriveConstants.tKI;
        tKD = DriveConstants.tKD;
    }

    public void setI(double kI, double tKI){
        this.kI = kI; this.tKI = tKI;
    }

    public Pos2D calculate(Pos2D target, Pos2D current) {

        double xerror = target.x - current.x;
        double yerror = target.y - current.y;
        double terror = target.theta - current.theta;

        // Proportional term
        double xproportional = kP * xerror;
        double yproportional = kP * yerror;
        double tproportional = tKP * terror;

        // Integral term
        xintegral += xerror;
        yintegral += yerror;
        tintegral += terror;

        double xintegralTerm = kI * xintegral;
        double yintegralTerm = kI * yintegral;
        double tintegralTerm = tKI * tintegral;

        // Derivative term
        double xderivative = kD * (xerror - xpreviousError);
        double yderivative = kD * (yerror - ypreviousError);
        double tderivative = tKD * (terror - tpreviousError);

        // Update the previous error
        xpreviousError = xerror;
        ypreviousError = yerror;
        tpreviousError = terror;

        // Calculate the output
        double xoutput = xproportional + xintegralTerm + xderivative;
        double youtput = yproportional + yintegralTerm + yderivative;
        double toutput = tproportional + tintegralTerm + tderivative;

        return new Pos2D(xoutput, youtput, toutput);
    }

    public void reset() {
        xpreviousError = 0;
        xintegral = 0;
        ypreviousError = 0;
        yintegral = 0;
        tpreviousError = 0;
        tintegral = 0;

    }

}
