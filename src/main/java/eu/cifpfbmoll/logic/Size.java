package eu.cifpfbmoll.logic;




import eu.cifpfbmoll.graphic.canvas.Assets;

import java.awt.image.BufferedImage;

public enum Size {

    BIG(2, Assets.bigMeteorSplit), MED(2, Assets.midMeteorSplit), SMALL(2, Assets.smallMeteorSplit);

    public int quantity;

    public BufferedImage[] textures;

    private Size(int quantity, BufferedImage[] textures) {
        this.quantity = quantity;
        this.textures = textures;
    }

}
