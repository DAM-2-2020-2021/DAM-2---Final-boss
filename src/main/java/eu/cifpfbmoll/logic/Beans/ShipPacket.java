package eu.cifpfbmoll.logic.Beans;

import eu.cifpfbmoll.netlib.annotation.PacketAttribute;
import eu.cifpfbmoll.netlib.annotation.PacketType;

@PacketType("SHIP")
public class ShipPacket {
    @PacketAttribute
    private int type;
    @PacketAttribute
    private int idMando;
    @PacketAttribute
    private double angle;
    @PacketAttribute
    private String userName;
    @PacketAttribute
    private String Team;
    @PacketAttribute
    private String from;//it tells us from which border of the screen the packet is send
    @PacketAttribute
    private boolean respawn=false;
    @PacketAttribute
    private double positionX;
    @PacketAttribute
    private double positionY;

    //<editor-fold desc="Getters and setters">
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIdMando() {
        return idMando;
    }

    public void setIdMando(int idMando) {
        this.idMando = idMando;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isRespawn() {
        return respawn;
    }

    public void setRespawn(boolean respawn) {
        this.respawn = respawn;
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
    //</editor-fold>
}
