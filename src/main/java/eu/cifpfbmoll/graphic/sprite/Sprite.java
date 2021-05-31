package eu.cifpfbmoll.graphic.sprite;

import eu.cifpfbmoll.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    public Sprite(){
    }

    /**
     * Returns a bufferedIMage from the string file, also works for the getSprite method.
     * @param file
     * @return
     */
    public static BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    /**
     * Returns a single sprite of a spritesheet as a buffered image,
     * @param xGrid
     * @param yGrid
     * @return
     */
    public static BufferedImage getSprite(BufferedImage spriteSheetFile,int imgWidth, int imgHeight, int xGrid, int yGrid) {
        try {
            return spriteSheetFile.getSubimage(xGrid * imgWidth, yGrid * imgHeight, imgWidth, imgHeight);
        }catch(Exception e){
                System.out.println(e+" Coord invalid: "+xGrid+ " " + yGrid);
        }
        return null;
    }

    private static BufferedImage getSheet(String file){
        String url = Sprite.class.getClassLoader().getResource(file).getPath();
        BufferedImage spriteSheet = loadSprite(url);
        return spriteSheet;
    }

    public static BufferedImage getMenuBg(){
        BufferedImage spriteSheet = getSheet("img/background/menu.png");
        return spriteSheet;
    }

    public static BufferedImage getShip1(int x, int y){
        BufferedImage spriteSheet = getSheet("img/ship/shipsheet50x50.png");
        return getSprite(spriteSheet, 50, 50, x, y);
    }

    public static BufferedImage getShip2(int x, int y){
        BufferedImage spriteSheet = getSheet("img/ship/shipsheetswift50x50.png");
        return getSprite(spriteSheet, 50, 50, x, y);
    }

    public static BufferedImage getShip3(int x, int y){
        BufferedImage spriteSheet = getSheet("img/ship/shipsheetbig50x50.png");
        return getSprite(spriteSheet, 50, 50, x, y);
    }
    
    public static BufferedImage getBullet(int x, int y){
        BufferedImage spriteSheet = getSheet("img/bullet/bulletsheet10x35.png");
        return getSprite(spriteSheet, 10, 35, x, y);
    }

    public static BufferedImage getExplosion(int x, int y){
        BufferedImage spriteSheet = getSheet("img/sfx/explosionsheet100x100.png");
        return getSprite(spriteSheet, 100, 100, x, y);
    }

    public static BufferedImage getPowerup(int x, int y){
        BufferedImage spriteSheet = getSheet("img/powerups/powerupsheet30x45.png");
        return getSprite(spriteSheet, 30, 45, x, y);
    }

    public static BufferedImage getMothership(int x, int y){
        BufferedImage spriteSheet = getSheet("img/mothership/mothershipsheet300x800.png");
        return getSprite(spriteSheet, 300, 800, x, y);
    }

    public static BufferedImage getBlackhole(int x, int y){
        BufferedImage spriteSheet = getSheet("img/blackhole/blackholesheet100x100.png");
        return getSprite(spriteSheet, 100, 100, x, y);
    }

    public static BufferedImage getAsteroid(int x, int y){
        BufferedImage spriteSheet = getSheet("img/asteroid/asteroidsheet70x70.png");
        return getSprite(spriteSheet, 70, 70, x, y);
    }

    public static BufferedImage getAsteroidSmall(int x, int y){
        BufferedImage spriteSheet = getSheet("img/asteroid/asteroidsmallsheet40x40.png");
        return getSprite(spriteSheet, 40, 40, x, y);
    }

    public static BufferedImage getAsteroidSmallest(int x, int y){
        BufferedImage spriteSheet = getSheet("img/asteroid/asteroidsmallestsheet20x20.png");
        return getSprite(spriteSheet, 20, 20, x, y);
    }
}
