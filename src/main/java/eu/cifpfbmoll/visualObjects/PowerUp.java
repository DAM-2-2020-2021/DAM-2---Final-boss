package eu.cifpfbmoll.visualObjects;



import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.math.Vector2D;
import eu.cifpfbmoll.sound.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PowerUp extends StaticVisualObject implements Runnable{
    public static final int TYPE_SPEED =2;
    public static final int TYPE_SHIELD =1;
    public static final int TYPE_SHOOT =0;
    public static final int NO_POWER_UP=-1;
    private final int TYPE;

    public PowerUp(Vector2D position, BufferedImage texture, int type, LogicController logicController) {
        super(position, texture, logicController);
        this.TYPE= type;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawImage(texture,(int)position.getX(),(int)position.getY(),null);
    }

    public synchronized void addPowerUpToShip(Ship ship){
        if (ship.getPowerUp()==PowerUp.NO_POWER_UP){
            ship.setPowerUp(this.TYPE);
            switch (this.TYPE){
                case PowerUp.TYPE_SHOOT:
                    Sound.getWeaponPowerUp();
                    break;
                case PowerUp.TYPE_SHIELD:
                    Sound.shield();
                    break;
                case PowerUp.TYPE_SPEED:
                    Sound.fast();
                    break;
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        ship.setPowerUp(PowerUp.NO_POWER_UP);
                        System.out.println("te he quitado el power up puto");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            this.logicController.getStaticVisualObjects().remove(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
