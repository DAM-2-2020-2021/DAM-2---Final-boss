package eu.cifpfbmoll.visualObjects;

import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BlackHole extends StaticVisualObject {

    //<editor-fold desc="Getters y setters">
    public synchronized boolean isOccupied() {
        return occupied;
    }

    public synchronized void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    //</editor-fold>


    public BlackHole(Vector2D position, BufferedImage texture, LogicController logicController) {
        super(position, texture, logicController);
        this.occupied = false;
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(texture,(int)position.getX(),(int)position.getY(),null);
    }


    public synchronized void putNave(Ship ship){

    }

    public synchronized void outNave(){

    }

    public synchronized void putBullet(Bullet bullet){

    }

    public synchronized void outBullet(){

    }

    public synchronized void putMeteorite(Meteorite meteorite){

    }

    public synchronized void outMeteorite(){

    }

}
