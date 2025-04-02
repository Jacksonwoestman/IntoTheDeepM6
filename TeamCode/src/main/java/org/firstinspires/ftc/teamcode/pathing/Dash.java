/*package org.firstinspires.ftc.teamcode.pathing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import java.util.ArrayList;

public class Dash {
    private final FtcDashboard dashboard;
    private final ArrayList<Vector> vectorList;

    public Dash() {
        dashboard = FtcDashboard.getInstance();
        vectorList = new ArrayList<>();
    }

    public void update(Pos2D pos, Pos2D targetPos) {
        TelemetryPacket packet = new TelemetryPacket();
        Canvas fieldOverlay = packet.fieldOverlay();
        int radius = 9;

        // Convert to inches (assuming metric input)
        double posX = pos.x / 2.54;
        double posY = pos.y / 2.54;

        fieldOverlay.setStroke("blue");
        fieldOverlay.strokeCircle(posX, posY, radius);

        double endX = posX + radius * Math.cos(pos.theta);
        double endY = posY + radius * Math.sin(pos.theta);
        fieldOverlay.strokeLine(posX, posY, endX, endY);

        for (Vector vector : vectorList) {
            Pos2D cPos = vector.currentPos;
            double x2 = cPos.x / 2.54 + vector.magnitude * 2 * Math.cos(vector.direction);
            double y2 = cPos.y / 2.54 + vector.magnitude * 2 * Math.sin(vector.direction);

            fieldOverlay.fillCircle(cPos.x / 2.54, cPos.y / 2.54, 0.5);
        }

        packet.put("X Current", posX);
        packet.put("X Target", targetPos.x / 2.54);
        packet.put("Y Current", posY);
        packet.put("Y Target", targetPos.y / 2.54);
        packet.put("IsEqualIsh", pos.isEqualIshTo(targetPos));

        dashboard.sendTelemetryPacket(packet);
    }

    public void update(Pos2D pos, Pos2D targetPos, Pos2D finalPos) {
        TelemetryPacket packet = new TelemetryPacket();
        Canvas fieldOverlay = packet.fieldOverlay();
        int radius = 9;

        double posX = pos.x / 2.54;
        double posY = pos.y / 2.54;

        fieldOverlay.setStroke("blue");
        fieldOverlay.strokeCircle(posX, posY, radius);

        double endX = posX + radius * Math.cos(pos.theta);
        double endY = posY + radius * Math.sin(pos.theta);
        fieldOverlay.strokeLine(posX, posY, endX, endY);

        for (Vector vector : vectorList) {
            Pos2D cPos = vector.currentPos;
            double x2 = cPos.x / 2.54 + vector.magnitude * 2 * Math.cos(vector.direction);
            double y2 = cPos.y / 2.54 + vector.magnitude * 2 * Math.sin(vector.direction);
            fieldOverlay.fillCircle(cPos.x / 2.54, cPos.y / 2.54, 0.5);
        }

        packet.put("X Current", posX);
        packet.put("X Target", targetPos.x / 2.54);
        packet.put("Y Current", posY);
        packet.put("Y Target", targetPos.y / 2.54);
        packet.put("IsEqualIsh", pos.isEqualIshTo(targetPos));
        packet.put("X Error", 2.54 * (finalPos.x - pos.x));
        packet.put("Y Error", 2.54 * (finalPos.y - pos.y));
        packet.put("Theta Error", 2.54 * (finalPos.theta - pos.theta));

        dashboard.sendTelemetryPacket(packet);
    }

    public void addVectors(ArrayList<Vector> vList) {
        vectorList.addAll(vList);
    }
}
*/