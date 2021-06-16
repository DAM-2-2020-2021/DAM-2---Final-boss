package eu.cifpfbmoll.visualObjects;

import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public abstract class DynamicVisualObject extends VisualObject implements Runnable{

    protected Vector2D delta;
    protected float vel;
    protected AffineTransform at;
    protected double angle;
    protected double maxVel;
    protected Thread thread;
    protected boolean colisionable;

        public DynamicVisualObject(LogicController logicController, Vector2D velocity, Vector2D position, double maxVel,
                                   BufferedImage texture) {
        super(position,texture,logicController);
        this.delta = velocity;
        this.maxVel = maxVel;
        this.colisionable=true;
    }

    //<editor-fold desc="getters and setters">
    public Vector2D getDelta() {
        return delta;
    }

    public void setDelta(Vector2D delta) {
        this.delta = delta;
    }

    public float getVel() {
        return vel;
    }

    public void setVel(float vel) {
        this.vel = vel;
    }

    public AffineTransform getAt() {
        return at;
    }

    public void setAt(AffineTransform at) {
        this.at = at;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getMaxVel() {
        return maxVel;
    }

    public void setMaxVel(double maxVel) {
        this.maxVel = maxVel;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isColisionable() {
        return colisionable;
    }

    public void setColisionable(boolean colisionable) {
        this.colisionable = colisionable;
    }

    //</editor-fold>


    public void destroy(){

    }
}
