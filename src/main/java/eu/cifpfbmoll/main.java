package eu.cifpfbmoll;

import eu.cifpfbmoll.graphic.sprite.Animation;
import eu.cifpfbmoll.graphic.sprite.Sprite;

import javax.swing.*;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class main {

    static BufferedImage image;

    private static final BufferedImage[] idle = {Sprite.getAsteroidSmallest(0,0)};
    private static final BufferedImage[] changeColor = {Sprite.getShip1(1,0), Sprite.getShip1(2,0)};
    private static Animation shipAnimation;


    private static final BufferedImage[] bulletExplosion = {
            Sprite.getExplosion(0,0), Sprite.getExplosion(1,0),
            Sprite.getExplosion(2,0), Sprite.getExplosion(3,0),
            Sprite.getExplosion(4,0), Sprite.getExplosion(5,0), Sprite.getExplosion(6,0) };

    private static final Animation bulletAnim = new Animation(bulletExplosion, 10);

    private static final Animation idleAnim = new Animation(idle, 10);
    private static final Animation changeColorAnim = new Animation(changeColor, 1);


    public static void main(String args[]){

        shipAnimation = idleAnim;

        image = Sprite.getBullet(3,0);

        Frame frame = new Frame();
        frame.add(new CustomPaintComponent());
        int frameWidth = 300;
        int frameHeight = 300;
        frame.setSize(frameWidth, frameHeight);
        frame.setVisible(true);

    }

    static class CustomPaintComponent extends Component {

        Boolean done = false;

        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(shipAnimation.getSprite(), 50, 50, this);
            if (!done) {
                timedChanges();
                done = true;
            }
        }

        public void timedChanges(){
            Timer timer;
            timer = new Timer();
            TimerTask task = new TimerTask() {
                int cont = 0;
                @Override
                public void run()
                {
                    if(cont == 10){
                        shipAnimation = bulletAnim;
                        shipAnimation.start();
                    }
                    shipAnimation.update();
                    cont++;
                    repaint();
                }
            };
            timer.schedule(task, 0, 100);
        }

    }
}
