package eu.cifpfbmoll.logic;

public class Configuration {

    private int meteoriteQuantity = 5;

    private int blackHoleQuantity = 2;

    private String objectGeneration = "Medium";

    private String mode = "Equipos";

    private int up;

    private int down;

    private int right;

    private int left;

    public int getMeteoriteQuantity() {
        return meteoriteQuantity;
    }

    public void setMeteoriteQuantity(int meteoriteQuantity) {
        this.meteoriteQuantity = meteoriteQuantity;
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
