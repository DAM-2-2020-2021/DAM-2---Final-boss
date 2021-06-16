package eu.cifpfbmoll.logic;


import eu.cifpfbmoll.netlib.annotation.PacketAttribute;
import eu.cifpfbmoll.netlib.annotation.PacketType;

@PacketType("CONF")
public class Configuration {

    @PacketAttribute
    private int meteorQuantity = 5;
    @PacketAttribute
    private int blackHoleQuantity = 2;
    @PacketAttribute
    private String objectGeneration = "MEDIUM";
    @PacketAttribute
    private String mode = "Equipos";
    @PacketAttribute
    private int up = 0;
    @PacketAttribute
    private int down = 0;
    @PacketAttribute
    private int right = 0;
    @PacketAttribute
    private int left = 0;

    private int screenID;

    public int getScreenID() {
        return screenID;
    }

    public void setScreenID(int screenID) {
        this.screenID = screenID;
    }

    public int getMeteorQuantity() {
        return meteorQuantity;
    }

    public void setMeteorQuantity(int meteorQuantity) {
        this.meteorQuantity = meteorQuantity;
    }

    public int getBlackHoleQuantity() {
        return blackHoleQuantity;
    }

    public void setBlackHoleQuantity(int blackHoleQuantity) {
        this.blackHoleQuantity = blackHoleQuantity;
    }

    public String getObjectGeneration() {
        return objectGeneration;
    }

    public void setObjectGeneration(String objectGeneration) {
        this.objectGeneration = objectGeneration;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }
}
