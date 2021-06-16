package eu.cifpfbmoll.visualObjects;


import eu.cifpfbmoll.graphic.canvas.Assets;
import eu.cifpfbmoll.logic.Constants;
import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.logic.Message;
import eu.cifpfbmoll.math.Vector2D;
import eu.cifpfbmoll.sound.Sound;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Ship extends MannedVisualObject {

    public static final int TYPE_O = 0;
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;

    public static final String MOVEMENT = "MOVEMENT";
    public static final String MOVE = "MOVE";
    public static final String SHOOT = "SHOOT";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";

    public static final String team1= "RED";
    public static final String team2= "BLUE";

    private Vector2D heading;
    private Vector2D acceleration;
    private boolean accelerating = false;
    private int movementNoAcceleration = 0;
    private int powerUp;
    private int idMando;
    private String userName;
    private String equipo;
    private boolean colisionable = true;
    private long lastShoot;
    private long timeCoolDown =200;
    private Rectangle rectangle;

    public Ship(LogicController logicController, Vector2D velocity, Vector2D position, Double maxVel, BufferedImage texture,
                String equipo, int idMando, String userName, double angle) {
        super(logicController, velocity, position, maxVel, texture);
        heading = new Vector2D(0, 1);
        acceleration = new Vector2D();
        powerUp = PowerUp.NO_POWER_UP;
        lastShoot = System.currentTimeMillis();
        this.thread = new Thread(this);
        this.equipo = equipo;
        this.colisionable = false;
        this.angle=angle;
        this.userName = userName;
        this.idMando= idMando;
        this.rectangle = new Rectangle((int) position.getX(),(int) position.getY(),texture.getWidth(),texture.getHeight());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hilo");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("colisionable");
                colisionable=true;
            }
        });
        thread.start();
        this.thread.start();
    }


    //<editor-fold desc="getter and setters">
    public int getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(int powerUp) {
        this.powerUp = powerUp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public boolean isColisionable() {
        return colisionable;
    }

    public void setColisionable(boolean colisionable) {
        this.colisionable = colisionable;
    }

    public int getIdMando() {
        return idMando;
    }

    public void setIdMando(int idMando) {
        this.idMando = idMando;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    //</editor-fold>

    public void shoot() {
        if (System.currentTimeMillis()-lastShoot> timeCoolDown){
            heading = heading.setDirection(angle - Math.PI / 2);
            int type;
            int aux=0;
            if (this.powerUp==PowerUp.TYPE_SHOOT){
                Sound.shootPowerup();
                type = Bullet.TYPE_POWERUP;
                aux=50;
            }else{
                Sound.shooting();
                type = Bullet.TYPE_NORMAL;
            }
           /* logicController.getDynamicVisualObjects().add(new Bullet(
                    logicController,
                    heading,
                    getCenter().add(heading.scale(width)),
                    Constants.BULLET_VEL,
                    shoot,
                    angle, equipo
            ));*/

            Bullet bullet = Utilities.createBullet(logicController,heading,getCenter().add(heading.scale(width+aux)),
                    type,this.angle,this.equipo);
            this.logicController.getDynamicVisualObjects().add(bullet);
            lastShoot= System.currentTimeMillis();
        }else{
            System.out.println("cool down refachero");
        }
    }

    public void rotate(String direccion) {
        if (direccion.equals("iz")) {
            this.angle += 0.1;
        } else {
            this.angle -= 0.1;
        }
    }

    public void move() {
        Vector2D newPosition = new Vector2D();
        double newX = this.getPosition().getX();

        double newY = this.getPosition().getY();
        if (this.powerUp == PowerUp.TYPE_SPEED) {
            newX += this.getDelta().getX() * (this.vel * 2);
            newY += this.getDelta().getY() * (this.vel * 2);
        } else {
            newX += this.getDelta().getX() * (this.vel);
            newY += this.getDelta().getY() * (this.vel);
        }

        newPosition.setX(newX);
        newPosition.setY(newY);
        this.setPosition(newPosition);
        if (this.colisionable){
            logicController.collidesWith(this);
        }
        logicController.outOfBounds(this);
    }

    @Override
    public void destroy() {
        Sound.shipDeath();
        logicController.respawnShip(this);
        this.vel=0;
        this.colisionable=false;
        System.out.println(colisionable +"colisionable");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    colisionable=true;
                    System.out.println(colisionable+ "colisonable tiene que ser true");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        sendDeath();
    }

    public void sendDeath(){
        Message message = new Message();
        message.setMessageType("DEAD");
        message.setMessage("");
        this.logicController.sendMessage(message,this.getIdMando());
    }

    public void respawn(){

    }

    public void acceleration() {
        this.delta.setX(Math.cos(angle - Math.toRadians(90)));
        this.delta.setY(Math.sin(angle - Math.toRadians(90)));
        if (this.vel < maxVel) {
            this.vel += 0.4;
            this.accelerating = true;
            System.out.println("acelarando");
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform at1 = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5,
                position.getY() + height / 2 + 10);
        AffineTransform at2 = AffineTransform.getTranslateInstance(position.getX() + 5, position.getY() + height / 2 + 10);
        at1.rotate(angle, -5, -10);
        at2.rotate(angle, width / 2 - 5, -10);

        if (accelerating) {

        }

        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

        at.rotate(angle, width / 2, height / 2);

        g2d.drawImage(texture, at, null);
        if (this.getPowerUp()==PowerUp.TYPE_SHIELD){
            BufferedImage aux = Assets.shield;
            g2d.drawImage(aux,at,null);
        }
        if (this.equipo.equals(Ship.team1)){
            g.setColor(Color.red);
        }else {
            g.setColor(Color.blue);
        }
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.drawString(this.userName,(float) this.getPosition().getX(),(float) this.getPosition().getY()-10);
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            this.move();
            if (this.accelerating == false) {
                this.movementNoAcceleration++;
                if (movementNoAcceleration >= 20) {
                    if (this.vel - 0.02 >= 0) {
                        this.vel -= 0.02;
                    } else if (this.vel - 0.2 < 0) {
                        this.vel = 0;
                    }
                }
            }
            this.accelerating = false;
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
