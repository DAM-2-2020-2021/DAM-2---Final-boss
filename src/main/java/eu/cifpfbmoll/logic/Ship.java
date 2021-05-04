package eu.cifpfbmoll.logic;

import java.awt.*;

public class Ship extends MannedVisualObject{

    public final int TYPE_O = 0;
    public final int TYPE_1 = 1;
    public final int TYPE_2 = 2;


    public Ship(int type, int xPosition, int yPosition, String userName) {
        switch (type){
            case TYPE_O:
                break;
            case TYPE_1:
                break;
            case TYPE_2:
                break;
            default:
                // will construct the TYPE_0 ship
        }
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void aparecer() {
        super.aparecer();
    }

    @Override
    public void desaparecer() {
        super.desaparecer();
    }

    public void rotate(String direccion){

    }

    @Override
    public void move() {
        super.move();
    }

    public void explode(){
    }

    public void shoot(){

    }

    public void collide(){

    }

    public void recolectObject(){

    }

    public void acceleration(int accelerate){

    }
}
