package eu.cifpfbmoll.graphic.canvas;

import eu.cifpfbmoll.graphic.sprite.Sprite;

import java.awt.image.BufferedImage;

public class Assets {

    // Ship
    public static BufferedImage shipBlue;
    public static BufferedImage shipRed;
    public static BufferedImage ship2Blue;
    public static BufferedImage ship2Red;
    public static BufferedImage ship3Blue;
    public static BufferedImage ship3Red;
    public static BufferedImage motherShip;
    public static BufferedImage motherShip2;
    // Effects
    public static BufferedImage speed;
    public static BufferedImage explosion;
    public static BufferedImage shield;
    // BlackHole
    public static BufferedImage blackHole;

    // PowerUp
    public static BufferedImage powerUp;
    public static BufferedImage powerUp2;
    public static BufferedImage powerUp3;

    // Shoots
    public static BufferedImage greenShoot;
    public static BufferedImage bigShoot;

    // Meteors
    public static BufferedImage[] bigMeteor = new BufferedImage[2];
    public static BufferedImage[] bigMeteorSplit = new BufferedImage[2];
    public static BufferedImage[] midMeteorSplit = new BufferedImage[2];
    public static BufferedImage[] smallMeteorSplit = new BufferedImage[2];

    //
    public static BufferedImage[] backgrounds;


    public static void init() {
        shipBlue = Sprite.getShip1(1,0);
        shipRed = Sprite.getShip1(2,0);
        ship2Blue = Sprite.getShip2(1,0);
        ship2Red = Sprite.getShip2(2,0);
        ship3Blue = Sprite.getShip3(1,0);
        ship3Red = Sprite.getShip3(2,0);
        motherShip = Sprite.getMothership(1,0);
        motherShip2 = Sprite.getMothership(2,0);
        explosion = Sprite.getExplosion(0,0);
        speed = null;
        shield = Sprite.getShield(0,0);
        powerUp = Sprite.getPowerup(0,0);
        powerUp2 = Sprite.getPowerup(1,0);
        powerUp3 = Sprite.getPowerup(2,0);
        greenShoot = Sprite.getBullet(2,0);
        blackHole = Sprite.getBlackhole(0,0);
        bigShoot = Sprite.getBigBullet(0,0);

        for (int i = 0; i < bigMeteor.length; i++)
            bigMeteor[i] = Sprite.getAsteroid(i, 0);

        for (int i = 0; i < bigMeteorSplit.length; i++)
            bigMeteorSplit[i] = Sprite.getAsteroidSmall(i, 0);

        for (int i = 0; i < midMeteorSplit.length; i++)
            midMeteorSplit[i] = Sprite.getAsteroidSmallest(i, 0);

        for (int i = 0; i < smallMeteorSplit.length; i++)
            smallMeteorSplit[i] = Sprite.getAsteroidSmallest(i, 0);

        backgrounds = Sprite.getAllBg();
    }

}
