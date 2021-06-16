package eu.cifpfbmoll.visualObjects;


import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class StaticVisualObject extends VisualObject{

    protected boolean occupied=false;
    protected Rectangle area;

    public StaticVisualObject(Vector2D position, BufferedImage texture, LogicController logicController) {
        super(position, texture, logicController);
        this.area = new Rectangle((int)position.getX(),(int)position.getY(),texture.getWidth(),texture.getHeight());
    }

    //<editor-fold desc="Getters and setters">
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    //</editor-fold>

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
