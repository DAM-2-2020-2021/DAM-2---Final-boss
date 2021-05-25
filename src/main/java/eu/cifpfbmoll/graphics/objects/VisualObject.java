package eu.cifpfbmoll.graphics.objects;

import eu.cifpfbmoll.logic.TheaterMode;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VisualObject {

    int xPosition;
    int yPosition;
    private TheaterMode theaterMode;
    BufferedImage img;
    //<editor-fold desc="Getteres y setters">
    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public TheaterMode getTheaterMode() {
        return theaterMode;
    }

    public void setTheaterMode(TheaterMode theaterMode) {
        this.theaterMode = theaterMode;
    }

    //</editor-fold>


    public VisualObject(TheaterMode theaterMode) {
        this.theaterMode = theaterMode;
    }

    public void paint(Graphics g){

    }

    public void appear(){

    }

    public void disappear(){

    }
}