package eu.cifpfbmoll.graphic.component;

import eu.cifpfbmoll.graphic.sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class CustomIcon extends JPanel implements MouseListener {
    // CONS

    // VARS
    private BufferedImage icon;
    private int width, height;
    private boolean hover;
    private String labelText;

    public CustomIcon(BufferedImage sprite){
        icon = sprite;
        this.width = 150;
        this.height = 150;
    }

    public CustomIcon(BufferedImage sprite, int width, int height, boolean hover){
        icon = sprite;
        this.width = width;
        this.height = height;
        this.setHover(hover);
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(icon, 0, 0, width, height, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    //<editor-fold desc="MOUSE METHODS">
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
//        width -= 100;
//        height -= 100;
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //</editor-fold>

    //<editor-fold desc="GETTERS">
    public BufferedImage getIcon() {
        return icon;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public boolean isHover() {
        return hover;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        if (hover){
            addMouseListener(this);
        }
    }
    //</editor-fold>
}