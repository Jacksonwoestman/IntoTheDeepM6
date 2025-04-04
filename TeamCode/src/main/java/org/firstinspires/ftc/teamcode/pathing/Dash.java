package org.firstinspires.ftc.teamcode.pathing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import java.util.ArrayList;

public class Dash {
    private FtcDashboard dashboard;
    private ArrayList<Vector> vectorList;

    public Dash() {
        dashboard = FtcDashboard.getInstance();
        vectorList = new ArrayList<>();
    }

    public void update(Pos2D pos, Pos2D targetPos) {
        // Create a new telemetry packet
        TelemetryPacket packet = new TelemetryPacket();
        Canvas fieldOverlay = packet.fieldOverlay();
        int radius = 9;
        // Draw the robot as a stroke circle
        pos.x /= 2.54;
        pos.y /= 2.54;
        fieldOverlay.setStroke("blue");
        fieldOverlay.strokeCircle(pos.x, pos.y, radius);

        // Draw the robot heading as an arrow or line
        double endX = pos.x + radius * Math.cos(pos.theta);
        double endY = pos.y + radius * Math.sin(pos.theta);
        fieldOverlay.strokeLine(pos.x, pos.y, endX, endY);

        for(int i = 0; i < vectorList.size(); i++) {
            Pos2D cPos = vectorList.get(i).currentPos;
            double magnitude = vectorList.get(i).magnitude;
            double direction = vectorList.get(i).direction;

            double x2 = cPos.x/2.54 + magnitude * 2 * Math.cos(direction);
            double y2 = cPos.y/2.54 + magnitude * 2 * Math.sin(direction);

            fieldOverlay.fillCircle(cPos.x/2.54, cPos.y/2.54, 0.5);
        }
        packet.put("X currentval", pos.x);
        packet.put("X targetval", targetPos.x/2.54);
        packet.put("Y currentval", pos.y);
        packet.put("Y targetval", targetPos.y/2.54);
        packet.put("Isequalish", pos.isEqualIshTo(targetPos));


        // Send the telemetry packet to the dashboard
        dashboard.sendTelemetryPacket(packet);

    }
    public void update(Pos2D pos, Pos2D targetPos, Pos2D finalPos) {
        // Create a new telemetry packet
        TelemetryPacket packet = new TelemetryPacket();
        Canvas fieldOverlay = packet.fieldOverlay();
        int radius = 9;
        // Draw the robot as a stroke circle
        pos.x /= 2.54;
        pos.y /= 2.54;
        fieldOverlay.setStroke("blue");
        fieldOverlay.strokeCircle(pos.x, pos.y, radius);

        // Draw the robot heading as an arrow or line
        double endX = pos.x + radius * Math.cos(pos.theta);
        double endY = pos.y + radius * Math.sin(pos.theta);
        fieldOverlay.strokeLine(pos.x, pos.y, endX, endY);

        for(int i = 0; i < vectorList.size(); i++) {
            Pos2D cPos = vectorList.get(i).currentPos;
            double magnitude = vectorList.get(i).magnitude;
            double direction = vectorList.get(i).direction;

            double x2 = cPos.x/2.54 + magnitude * 2 * Math.cos(direction);
            double y2 = cPos.y/2.54 + magnitude * 2 * Math.sin(direction);

            fieldOverlay.fillCircle(cPos.x/2.54, cPos.y/2.54, 0.5);
        }
        packet.put("X currentval", pos.x);
        packet.put("X targetval", targetPos.x/2.54);
        packet.put("Y currentval", pos.y);
        packet.put("Y targetval", targetPos.y/2.54);
        packet.put("Isequalish", pos.isEqualIshTo(targetPos));
        packet.put("xerror", 2.54*(finalPos.x - pos.x));
        packet.put("yerror", 2.54*(finalPos.y - pos.y));
        packet.put("terror", 2.54*(finalPos.theta - pos.theta));



        // Send the telemetry packet to the dashboard
        dashboard.sendTelemetryPacket(packet);

    }



    public void addVectors(ArrayList<Vector> vList) {
        for(int i = 0; i < vList.size(); i++) {
            vectorList.add(vList.get(i));
        }
    }
}
