package eu.cifpfbmoll.visualObjects;



import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class VisualObject {

    protected Vector2D position;
    protected BufferedImage texture;
    protected LogicController logicController;
    protected int width;
    protected int height;

    public VisualObject(Vector2D position, BufferedImage texture, LogicController logicController) {
        this.position = position;
        this.texture = texture;
        this.logicController = logicController;
        width = texture.getWidth();
        height = texture.getHeight();
    }

    //<editor-fold desc="getters and setters">
    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public LogicController getLogicController() {
        return logicController;
    }

    public void setLogicController(LogicController logicController) {
        this.logicController = logicController;
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

    //</editor-fold>

    public abstract void update();

    public abstract void draw(Graphics g);

    public Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }

}
