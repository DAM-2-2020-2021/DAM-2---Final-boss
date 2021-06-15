package eu.cifpfbmoll.visualObjects;

import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;


import java.awt.*;
import java.awt.image.BufferedImage;

public class MannedVisualObject extends DynamicVisualObject {

    public MannedVisualObject(LogicController logicController, Vector2D velocity, Vector2D position, double maxVel,
                              BufferedImage texture) {
        super(logicController,velocity,position,maxVel,texture);
    }


    @Override
    public void run() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
