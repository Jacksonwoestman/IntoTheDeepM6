package org.firstinspires.ftc.teamcode.pathing;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Points {
    public final static Pos2D initPosLeft = new Pos2D(20, 257, -Math.PI/2);
    public final static Pos2D initPoseRight = new Pos2D(21, 135.5, 2*(Math.PI));

    public final static Pos2D RNeutral = new Pos2D(52.5,320,-0.24);
    public final static Pos2D MNeutral = new Pos2D(48,317,0.18);
    public final static Pos2D LNeutral = new Pos2D(52,316,0.6);

    public final static Pos2D BasketDeliver = new Pos2D(42,314.5,-Math.PI/4);
    public final static Pos2D LeftParkStart = new Pos2D(180,300,-(Math.PI*3)/2);
    public final static Pos2D LeftParkEnd = new Pos2D(150,238,-(Math.PI*3)/2);
    public final static Pos2D LeftGrabStart = new Pos2D(180,320,-(Math.PI*3)/2);
    public final static Pos2D LeftGrabEnd = new Pos2D(150,237,-(Math.PI)/2);

    public final static Pos2D WeirdAutoGrab = new Pos2D(68.2,111,3.7579);
    public final static Pos2D LeftSpecimenGrab = new Pos2D(140,243,-(Math.PI)/2);
    public final static Pos2D LeftSpecimenGrabControl = new Pos2D(140,290,-(Math.PI)/2);
    public final static Pos2D observeGrab = new Pos2D(22.25, 75, 2*Math.PI);
    public final static Pos2D PreobserveGrab = new Pos2D(29, 75, 2*Math.PI);
    public final static Pos2D observeGrabTele = new Pos2D(46, 75, Math.PI);

    public final static Pos2D specimenSubPickup = new Pos2D(150, 115, Math.PI/2);
    public final static Pos2D observeDeliver = new Pos2D(100, 70, Math.PI);
    public final static Pos2D sSPcontrol1 = new Pos2D(170, 70, Math.PI);


    public final static Pos2D RSpecimenPreDeliver = new Pos2D(45,170, 2*(Math.PI));
    public final static Pos2D RSpecimenDeliver = new Pos2D(97,190, 2*(Math.PI));
    public final static Pos2D AfterRSpecimenDeliver = new Pos2D(75,150,Math.PI);


    public final static Pos2D SpecimenPreDeliverTele = new Pos2D(65,170, Math.PI);

    public final static Pos2D LColor1 = new Pos2D(50,90,(2*(Math.PI)));
    public final static Pos2D LColor2 = new Pos2D(LColor1.x + 202, LColor1.y + 31, LColor1.theta);
    public final static Pos2D LColor3 = new Pos2D(LColor1.x + 170, LColor1.y - 35, LColor1.theta);
    public final static Pos2D LColor4 = new Pos2D(LColor1.x + 202, LColor1.y - 31 - 26, LColor1.theta);
    public final static Pos2D LColor5 = new Pos2D(LColor1.x , LColor1.y - 38, LColor1.theta);

    //public final static Pos2D LColor2 = new Pos2D(140,70,(2*(Math.PI)));
    //public final static Pos2D LColor3 = new Pos2D(50,55,(2*(Math.PI)));

    public final static Pos2D MColor1 = new Pos2D(50,70, (2*(Math.PI)));
    public final static Pos2D MColor2 = new Pos2D(MColor1.x + 202, MColor1.y + 31, MColor1.theta);
    public final static Pos2D MColor3 = new Pos2D(MColor1.x + 170, MColor1.y - 34, MColor1.theta);
    public final static Pos2D MColor4 = new Pos2D(MColor1.x + 202, MColor1.y - 31 - 26, MColor1.theta);
    public final static Pos2D MColor5 = new Pos2D(MColor1.x , MColor1.y - 34, MColor1.theta);
    //public final static Pos2D MColor2 = new Pos2D(50,30, (2*(Math.PI)));

    public final static Pos2D RColor1 = new Pos2D(50,42, (2*(Math.PI)));
    public final static Pos2D RColor2 = new Pos2D(RColor1.x + 202, RColor1.y + 31, RColor1.theta);
    public final static Pos2D RColor3 = new Pos2D(RColor1.x + 170, RColor1.y - 22, RColor1.theta);
    public final static Pos2D RColor4 = new Pos2D(RColor1.x + 202, RColor1.y - 31 - 26, RColor1.theta);
    public final static Pos2D RColor5 = new Pos2D(RColor1.x , RColor1.y - 22, RColor1.theta);
    //public final static Pos2D RColor2 = new Pos2D(50,25,(2*(Math.PI)));

    public final static Pos2D LColorAtG = new Pos2D(72,95,5.44809);
    public final static Pos2D LColorObserveG = new Pos2D(65,80,3.6);
    //7/4 = grab
    public final static Pos2D MColorAtG = new Pos2D(67,72, 5.5562);
    public final static Pos2D MColorObserveG = new Pos2D(53,79, 4.086);

    public final static Pos2D RColorAtG = new Pos2D(80,58, 5.308);
    public final static Pos2D RColorObserveG = new Pos2D(53,70, 3.8);


    public final static Pos2D RColor = new Pos2D(93,30, 3*Math.PI);
    public final static Pos2D RColorDeliver = new Pos2D(80,50, Math.PI);
    public final static Pos2D PreObservePickup = new Pos2D(55,82,2*(Math.PI));
    public final static Pos2D ObservePickup = new Pos2D(35,82,2*(Math.PI));
    public final static Pos2D RightPark = new Pos2D(37,50,0);

    public final static Pos2D leftPickup = new Pos2D(157,262,-Math.PI/2);
    public final static Pos2D rightPickup = new Pos2D();

    public final static Pos2D leftStaging = new Pos2D(157, 360, -Math.PI/2);

    public static Pos2D endPos = initPoseRight;


//Testing Values
public final static Pos2D RSpecimenDeliverTester = new Pos2D(50,158, Math.PI);

    public Points() {}


}
