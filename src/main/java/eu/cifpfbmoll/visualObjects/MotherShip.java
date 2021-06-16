package eu.cifpfbmoll.visualObjects;

import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MotherShip extends StaticVisualObject{

    public MotherShip(Vector2D position, BufferedImage texture, LogicController logicController) {
        super(position, texture, logicController);
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(texture,(int)position.getX(),(int)position.getY(),null);
    }
}
