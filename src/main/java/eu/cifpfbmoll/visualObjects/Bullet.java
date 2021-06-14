package eu.cifpfbmoll.visualObjects;

import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Bullet extends IaVisualObject {
    //types of bullets
    public static final int TYPE_NORMAL =0;
    public static final int TYPE_POWERUP=1;


    private boolean alive;
    private String equipo;
    private int type;


    public Bullet(LogicController logicController, Vector2D delta, Vector2D position, double maxVel,
                  BufferedImage texture, double angle,String equipo,int type) {
        super(logicController, delta, position, maxVel,texture);
        this.setAngle(angle);
        this.setDelta(delta.scale(maxVel));
        this.alive=true;
        this.thread = new Thread(this);
        this.colisionable=true;
        this.equipo = equipo;
        this.type = type;
        this.thread.start();
    }


    //<editor-fold desc="getters and setters">
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //</editor-fold>


    @Override
    public void update() {
        position = position.add(delta);
        if (position.getX() < 0 || position.getX() > this.logicController.getViewer().getWidth() ||
                position.getY() < 0 || position.getY() > this.logicController.getViewer().getHeight()) {
            logicController.destroy(this);
        }

        if (this.colisionable){
            logicController.collidesWith(this);
        }


    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX() - width / 2, position.getY());

        at.rotate(angle, width / 2, 0);

        g2d.drawImage(texture, at, null);

    }

    @Override
    public Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + width / 2);
    }

    @Override
    public void destroy(){
        this.alive =false;
    }

    @Override
    public void run() {
        super.run();
        while (alive){
            update();
            logicController.outOfBounds(this);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
