package eu.cifpfbmoll.logic;

import java.awt.*;

public class StaticVisualObject extends VisualObject{

    public boolean occupied=false;

    //<editor-fold desc="getters and setters">
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    //</editor-fold>

    //to avoid change xPositon
    @Override
    public void setxPosition(int xPosition) {

    }

    //to avoid change yPositon
    @Override
    public void setyPosition(int yPosition) {

    }

}
