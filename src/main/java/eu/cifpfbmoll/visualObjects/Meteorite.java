package eu.cifpfbmoll.visualObjects;



import eu.cifpfbmoll.logic.Constants;
import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.logic.Size;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Meteorite extends IaVisualObject{

    private Size size;
    private boolean alive=true;
    private boolean occupied= false;
    public Meteorite(LogicController logicController, Vector2D position, Vector2D delta, double maxVel, BufferedImage
                     texture, Size size) {
        super(logicController,delta,position,maxVel,texture);
        this.size = size;
        this.delta = delta.scale(maxVel);
        this.thread = new Thread(this);
        this.thread.start();
    }

    //<editor-fold desc="getters y setters">
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    //</editor-fold>

    @Override
    public void update() {
        position = position.add(delta);

        angle += Constants.DELTAANGLE / 2;

        if (this.colisionable){
            this.logicController.collidesWith(this);
        }
    }

    public synchronized void destroy(Boolean destroy) {
        super.destroy();
        this.alive = false;
        if (destroy){
            this.divideMeteor(this);
        }

    }


    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(texture, at, null);
    }

    public void divideMeteor(Meteorite meteor) {
        ArrayList<Meteorite> aux = Utilities.createMeteorite(meteor,this.logicController);
        for (int i = 0; i < aux.size(); i++) {
            this.logicController.getDynamicVisualObjects().add(aux.get(i));
        }
        System.out.println("divideo meteor");
        int count =0;
        for (int i = 0; i < this.logicController.getDynamicVisualObjects().size(); i++) {
            if (this.logicController.getDynamicVisualObjects().get(i) instanceof Meteorite){
                count++;
            }
        }
        System.out.println("meteorites :"+ count);
    }


    public Size getSize() {
        return size;
    }

    @Override
    public void run() {
        super.run();
        while (alive){
            update();
            this.logicController.outOfBounds(this);
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
