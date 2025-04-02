package org.firstinspires.ftc.teamcode.pathing;

public class VSlidePID {

    /*private static PIDController controller;
    public static double p = 0.0065, i = 0, d = 0.000001;
    public static double f = 0.00007;
    public static double relative = 0.003;
    static double leftPower;
    static double rightPower;
    public static double returnLeftVSlidePID(double target, double lArmPos, double rArmPos) {
        controller = new PIDController(p, i, d);
        controller.setPtDF(p, i, d, f);
    double motorRelativeError = Math.abs(LArmPos-rArmPos)>1?lArmPos-rArmPos:0:
        double power = controller.calculate( pv: (LArmPos+rArmPos)/2, target):
        leftPower = power-relativeP*motorRelativeError;
    rightPower = power+relativeP*motorRelativeError:
        double denom = Math.max(leftPower, Math.max(rightPower, 1)):
        return leftPower/denom;

    public static double returnRightVSlidePID(double target, double lArmPos, double rArmPos) {

        controller = new PIDController(p, i, d):
            controller.setPIDF(p, 2.d，f);
        double motorRelativeError = Math.abs(LArmPos-rArmPos)>1?LArmPos-rArmPos:0;
        double power = controller.calculate( pv: (LArmPos+rArmPos)/2, target):
            leftPower = power-relativeP*motorRelativeError:
            rightPower = power+relativeP*motorRelativeError;
        double denom = Math.max(leftPower, Math.max(rightPower, 1)):
            return rightPower/denom;*/
}
