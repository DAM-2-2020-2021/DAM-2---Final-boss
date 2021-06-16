package eu.cifpfbmoll.graphic.canvas;



import eu.cifpfbmoll.visualObjects.DynamicVisualObject;
import eu.cifpfbmoll.visualObjects.StaticVisualObject;
import eu.cifpfbmoll.visualObjects.Utilities;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Viewer extends Canvas implements Runnable{
    private boolean motherShip=false;

    private Thread thread;
    private BufferStrategy bs;
    int indexBackgroud = Utilities.getRandom(0,5);
    private ArrayList<StaticVisualObject> staticVisualObjects;
    private ArrayList<DynamicVisualObject> dynamicVisualObjects;
    public Viewer(ArrayList<StaticVisualObject> staticVisualObjects, ArrayList<DynamicVisualObject> dynamicVisualObjects){
        this.thread = new Thread(this);
        this.staticVisualObjects = staticVisualObjects;
        this.dynamicVisualObjects = dynamicVisualObjects;
    }

    //<editor-fold desc="Getters and Setters">
    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public BufferStrategy getBs() {
        return bs;
    }

    public void setBs(BufferStrategy bs) {
        this.bs = bs;
    }

    public boolean isMotherShip() {
        return motherShip;
    }

    public void setMotherShip(boolean motherShip) {
        this.motherShip = motherShip;
    }

    //</editor-fold>


    public void paint()  {
        createBufferStrategy(2);
        BufferStrategy bs;
        BufferedImage bufferedImage = Assets.backgrounds[indexBackgroud];


        do {
            bs = getBufferStrategy();
            Graphics g = bs.getDrawGraphics();
            g.clearRect(0, 0, getWidth(), getHeight());
            g.drawImage(bufferedImage,0,0,null);


            for (int i = 0; i < this.staticVisualObjects.size(); i++) {
                this.staticVisualObjects.get(i).draw(g);
            }

            for (int i = 0; i < this.dynamicVisualObjects.size(); i++) {
                this.dynamicVisualObjects.get(i).draw(g);
            }


            g.dispose();
            bs.show();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

    @Override
    public void run() {
        paint();
    }
}
