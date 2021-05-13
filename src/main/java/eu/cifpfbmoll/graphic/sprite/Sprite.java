package eu.netlib.graphic.sprite;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private static String spriteSheetFile;
    private static int imgWidth;
    private static int imgHeight;
    private static BufferedImage spriteSheet;

    public Sprite(String spriteSheetFile, int imgWidth, int imgHeight){
        this.spriteSheetFile = spriteSheetFile;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
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
    public static BufferedImage getSprite(int xGrid, int yGrid) {

        if (spriteSheet == null) {
            spriteSheet = loadSprite(spriteSheetFile);
        }

        return spriteSheet.getSubimage(xGrid * imgWidth, yGrid * imgHeight, imgWidth, imgHeight);
    }

}
