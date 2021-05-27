package eu.cifpfbmoll.graphics.panels;

import eu.cifpfbmoll.graphics.window.MainScreen;

import javax.swing.*;
import java.awt.*;

public class GameViewer extends JPanel implements Runnable{
    // CONST
    // VARS
    private MainScreen mainScreen;
    private Color backgroundColor;

    /**
     * Default constructor. Background color MAGENTA
     * @param mainScreen
     */
    public GameViewer(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        this.backgroundColor = Color.MAGENTA;
        initViewer();
    }

    /**
     * Alternative constructor.
     * @param backgroundColor
     * @param mainScreen
     */
    public GameViewer(Color backgroundColor, MainScreen mainScreen){
        this.mainScreen = mainScreen;
        this.backgroundColor = backgroundColor;
        initViewer();
    }

    /**
     * All steps to init the viewer.
     */
    private void initViewer(){
        setBackground(backgroundColor); // set background color of JPanel
    }

    /**
     * Paints the JPanel.
     * @param g2
     */
    @Override
    public void paintComponent(Graphics g2){
        super.paintComponent(g2);
        Graphics2D g = (Graphics2D) g2;
    }

    /**
     * Paints a singular ship.
     * @param ship
     * @param g
     */
    //FIXME insert class Ship (Nave). Ship will contain a paint(Graphics2D) method.
    // public void paintShip(Ship ship, Graphics2D g){
    //    ship.paint(g);
    // }

    /**
     * Paints a collection of ships.
     * @param ships
     * @param g
     */
    //FIXME insert class Ship[ship1, ..., shipN] (Nave)
    // public void paintShips(Ship[] ships, Graphics2D g){
//        for (Ship ship : ships){
//            paintShip(ship, g);
//        }
//    }

    /**
     * Method for a thread. Paints repeatedly the panel.
     */
    @Override
    public void run(){
        while(true){
            this.repaint();
        }
    }
}