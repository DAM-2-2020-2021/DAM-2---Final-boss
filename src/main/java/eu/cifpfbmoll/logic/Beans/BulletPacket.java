package eu.cifpfbmoll.logic.Beans;

import eu.cifpfbmoll.netlib.annotation.PacketAttribute;
import eu.cifpfbmoll.netlib.annotation.PacketType;

@PacketType("SHOT")
public class BulletPacket {
    @PacketAttribute
    public double deltaX;
    @PacketAttribute
    private double deltaY;
    @PacketAttribute
    private double positionX;
    @PacketAttribute
    private double positionY;
    @PacketAttribute
    private double angle;
    @PacketAttribute
    private int type;
    @PacketAttribute
    private String team;
    @PacketAttribute
    private String from;

    public BulletPacket() {
    }

    //<editor-fold desc="getters and setters">

    public double getDeltaX() {
        return deltaX;
    }

    public void setDeltaX(double deltaX) {
        this.deltaX = deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    public void setDeltaY(double deltaY) {
        this.deltaY = deltaY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
    //</editor-fold>
}
