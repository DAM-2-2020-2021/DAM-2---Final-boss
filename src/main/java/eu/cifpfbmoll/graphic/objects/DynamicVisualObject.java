package eu.cifpfbmoll.graphic.objects;


import eu.cifpfbmoll.logic.TheaterMode;

public class DynamicVisualObject extends VisualObject implements Runnable{

    int velocity;
    int xVelocity;
    int yVelocity;
    Thread thread;

    public DynamicVisualObject(TheaterMode theaterMode) {
        super(theaterMode);
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
    }




    public void move(){

    }

}