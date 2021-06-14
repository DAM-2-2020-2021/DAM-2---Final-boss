package eu.cifpfbmoll.graphic.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class focused on storing all the paths of the images and methods to simplify the use of sprites for the game
 * also minimizing the number of files added to the game.
 */
public class Sprite {

    /**
     * Returns a sprite sheet as a buffered image to cut it later.
     * @param file
     * @return
     */
    private static BufferedImage getSheet(String file){
        file = file.replace("%20", " "); //Blank space treatment
        String url = Sprite.class.getClassLoader().getResource(file).getPath();
        BufferedImage spriteSheet;
        try {
            spriteSheet = ImageIO.read(new File(url));
            return spriteSheet;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the sub image of a spriteSheet, also known as an individual Sprite
     * @param spriteSheetFile
     * @param imgWidth
     * @param imgHeight
     * @param xGrid
     * @param yGrid
     * @return
     */
    private static BufferedImage getSprite(BufferedImage spriteSheetFile,int imgWidth, int imgHeight, int xGrid, int yGrid) {
        try {
            return spriteSheetFile.getSubimage(xGrid * imgWidth, yGrid * imgHeight, imgWidth, imgHeight);
        }catch(Exception e){
                System.out.println(e+" Coord invalid: "+xGrid+ " " + yGrid);
        }
        return null;
    }

    /**
     * This one includes X values, beware.
     * @param x
     * @param y
     * @return
     */
    public static BufferedImage getBg(int x, int y){
        BufferedImage spriteSheet = getSheet("img/background/backgrounds1920x1080.png");
        return getSprite(spriteSheet, 1920, 1080, x, y);
    }

    public static BufferedImage getMenuBg(){
        return getBg(0,0);
    }

    public static BufferedImage[] getAllBg(){
        BufferedImage backgrounds[] = {
                getBg(0,0),
                getBg(1,0),
                getBg(2,0),
                getBg(3,0),
                getBg(4,0),
                getBg(5,0),
                getBg(6,0),
                getBg(0,1),
                getBg(1,1),
                getBg(2,1),
                getBg(3,1),
        };
        return backgrounds;
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

    public static BufferedImage getBigBullet(int x, int y){
        BufferedImage spriteSheet = getSheet("img/bullet/bulletbigsheet70x70.png");
        return getSprite(spriteSheet, 70, 70, x, y);
    }

    public static BufferedImage getExplosion(int x, int y){
        BufferedImage spriteSheet = getSheet("img/sfx/explosionbulletsheet100x100.png");
        return getSprite(spriteSheet, 100, 100, x, y);
    }

    public static BufferedImage getPowerup(int x, int y){
        BufferedImage spriteSheet = getSheet("img/powerups/powerupsheet30x45.png");
        return getSprite(spriteSheet, 30, 45, x, y);
    }

    public static BufferedImage getShield(int x, int y){
        BufferedImage spriteSheet = getSheet("img/powerups/shieldsheet60x60.png");
        return getSprite(spriteSheet, 60, 60, x, y);
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
        BufferedImage spriteSheet = getSheet("img/asteroid/asteroidsheet40x40.png");
        return getSprite(spriteSheet, 40, 40, x, y);
    }

    public static BufferedImage getAsteroidSmallest(int x, int y){
        BufferedImage spriteSheet = getSheet("img/asteroid/asteroidsheet20x20.png");
        return getSprite(spriteSheet, 20, 20, x, y);
    }

    public static BufferedImage getObjectIcons(int x, int y){
        BufferedImage spriteSheet = getSheet("img/icons/objecticonsheet20x20.png");
        return getSprite(spriteSheet, 20, 20, x, y);
    }
}
