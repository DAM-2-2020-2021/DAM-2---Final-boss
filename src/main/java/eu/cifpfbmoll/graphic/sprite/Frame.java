package eu.cifpfbmoll.graphic.sprite;

import java.awt.image.BufferedImage;

/**
 * An animation is formed by these, the frames, they are just buffered images
 */
public class Frame {

    private BufferedImage frame;

    public Frame(BufferedImage frame) {
        this.frame = frame;
    }

    public BufferedImage getFrame() {
        return frame;
    }

}