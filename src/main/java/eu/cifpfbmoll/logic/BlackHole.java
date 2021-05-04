package eu.cifpfbmoll.logic;

import eu.cifpfbmoll.netlib.node.NodeServer;

import java.awt.*;

public class BlackHole extends StaticVisualObject{
    boolean occupied;

    //<editor-fold desc="Getters y setters">
    public synchronized boolean isOccupied() {
        return occupied;
    }

    public synchronized void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    //</editor-fold>

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void aparecer() {
        super.aparecer();
    }

    @Override
    public void desaparecer() {
        super.desaparecer();
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
