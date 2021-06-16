package eu.cifpfbmoll.visualObjects;


import eu.cifpfbmoll.graphic.canvas.Assets;
import eu.cifpfbmoll.graphic.objects.Spacecraft;
import eu.cifpfbmoll.logic.Beans.BulletPacket;
import eu.cifpfbmoll.logic.Beans.MeteroritePacket;
import eu.cifpfbmoll.logic.Beans.ShipPacket;
import eu.cifpfbmoll.logic.Constants;
import eu.cifpfbmoll.logic.LogicController;
import eu.cifpfbmoll.logic.Message;
import eu.cifpfbmoll.logic.Size;
import eu.cifpfbmoll.math.Vector2D;
import eu.cifpfbmoll.sound.Sound;

import java.awt.image.BufferedImage;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Utilities {

    public static Ship createShip(LogicController controller, int x, int y, int type,String equipo, int idMando,
                                  String username, double angle) {
        BufferedImage img;
        double maxVel;
        switch (type) {
            case 0:
                if (equipo.equals(Ship.team1)){
                    img = Assets.shipRed;
                }else{
                    img = Assets.shipBlue;
                }
                maxVel = 10;
                break;
            case 1:
                if (equipo.equals(Ship.team1)){
                    img = Assets.ship2Red;
                }else{
                    img = Assets.ship2Blue;
                }
                maxVel = 20;
                break;
            case 2:
                if (equipo.equals(Ship.team1)){
                    img = Assets.ship3Red;
                }else{
                    img = Assets.ship3Blue;
                }
                maxVel = 30;
                break;
            default:
                img = Assets.shipBlue;
                maxVel = 10;
        }
        Vector2D velocity = new Vector2D(0, 0);
        Vector2D position = new Vector2D(x, y);
        Ship ship = new Ship(controller, velocity, position, maxVel, img,equipo, idMando, username,angle);
        return ship;
    }

    public static Ship createShip(LogicController logicController, ShipPacket packet){
        BufferedImage texture = Assets.ship2Blue;
        Vector2D v = getCorrectPosition(new Vector2D(packet.getPositionX(),packet.getPositionY()),logicController,
                packet.getFrom(),texture);
        Ship ship = createShip(logicController,(int) v.getX(),(int) v.getY(),packet.getType(),
                packet.getTeam(),packet.getIdMando(),packet.getUserName(),packet.getAngle());
        return ship;
    }


    public static PowerUp createPowerUp(LogicController logicController) {
        BufferedImage img = Assets.powerUp;
        int type = ThreadLocalRandom.current().nextInt(0, 2 + 1);

        if (type == 0) {
            img = Assets.powerUp;
        } else if (type == 1) {
            img = Assets.powerUp2;
        } else if (type == 2) {
            img = Assets.powerUp3;
        }

        boolean done = false;
        PowerUp powerUp = null;
        while (!done) {
            int xMaxPosition = logicController.getViewer().getWidth() - img.getWidth();
            int yMaxPosition = logicController.getViewer().getHeight() - img.getHeight();
            int x = ThreadLocalRandom.current().nextInt(0, xMaxPosition + 1);
            int y = ThreadLocalRandom.current().nextInt(0, yMaxPosition + 1);

            powerUp = new PowerUp(new Vector2D(x, y), img, type, logicController);
            boolean colision = false;
            for (int i = 0; i < logicController.getStaticVisualObjects().size(); i++) {

                StaticVisualObject m = logicController.getStaticVisualObjects().get(i);

                if (m.getArea().intersects(powerUp.getArea())){
                    colision=true;
                }
            }
            if (colision == false) {
                done = true;
            }
        }
        return powerUp;
    }

    public static BlackHole createBlackHole(LogicController logicController) {
        BufferedImage img = Assets.blackHole;
        boolean done = false;
        BlackHole blackHole = null;
        while (!done) {
            int xMaxPosition = logicController.getViewer().getWidth() - img.getWidth();
            int yMaxPosition = logicController.getViewer().getHeight() - img.getHeight();
            int x = ThreadLocalRandom.current().nextInt(0, xMaxPosition + 1);
            int y = ThreadLocalRandom.current().nextInt(0, yMaxPosition + 1);

            blackHole = new BlackHole(new Vector2D(x, y), img, logicController);
            boolean colision = false;
            for (int i = 0; i < logicController.getStaticVisualObjects().size(); i++) {

                StaticVisualObject m = logicController.getStaticVisualObjects().get(i);

                if (m.getArea().intersects(blackHole.getArea())){
                    colision=true;
                }
            }
            if (colision == false) {
                done = true;
            }else{
            }
        }
        return blackHole;
    }

    public static ArrayList<Meteorite> createMeteorite(Meteorite meteorite, LogicController logicController) {
        Vector2D position = meteorite.getPosition();
        Size size = meteorite.getSize();

        BufferedImage[] textures = size.textures;
        int index = 0;
        Size newSize = null;
        ArrayList<Meteorite> meteorites = new ArrayList<>();
        BufferedImage texture = null;
        switch (size) {
            case BIG:
                newSize = Size.MED;
                index = Utilities.getRandom(0,Assets.bigMeteorSplit.length);
                texture = Assets.bigMeteorSplit[index];
                break;
            case MED:
                newSize = Size.SMALL;
                index = Utilities.getRandom(0,Assets.midMeteorSplit.length);
                texture = Assets.midMeteorSplit[index];
                break;
            default:
                return meteorites;
        }


        for (int i = 0; i < 2; i++) {
            Vector2D pos = new Vector2D();
            pos.setX((double) getRandom(100,200));
            pos.setY((double) getRandom(100,200));
            Vector2D directiom= new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2);
            meteorites.add(new Meteorite(logicController, position, directiom, Constants.METEOR_VEL, texture, newSize));
        }
        return meteorites;
    }


    public static ShipPacket createShipPacket(Ship ship, String from, boolean respawn){
        ShipPacket s = new ShipPacket();
        s.setAngle(ship.getAngle());
        s.setFrom(from);
        s.setIdMando(ship.getIdMando());
        s.setTeam(ship.getEquipo());
        s.setPositionX(ship.getPosition().getX());
        s.setPositionY(ship.getPosition().getY());
        s.setRespawn(respawn);
        s.setUserName(ship.getUserName());
        return s;
    }

    public static MeteroritePacket createMeteoritePacket(Meteorite meteorite, String from){
        MeteroritePacket meteroritePacket = new MeteroritePacket();
        int typeMeteorite=0;
        switch (meteorite.getSize()){
            case BIG:
                typeMeteorite = Constants.BIG_M;
                break;
            case MED:
                typeMeteorite = Constants.MEDIUM_M;
                break;
            case SMALL:
                typeMeteorite = Constants.SMALL_M;
                break;
        }
        meteroritePacket.setAngle(meteroritePacket.getAngle());
        meteroritePacket.setDeltaX(meteorite.getDelta().getX());
        meteroritePacket.setDeltaY(meteorite.getDelta().getY());
        meteroritePacket.setPositionX(meteorite.getPosition().getX());
        meteroritePacket.setPositionY(meteorite.getPosition().getY());
        meteroritePacket.setFrom(from);
        meteroritePacket.setType(typeMeteorite);
        return meteroritePacket;
    }

    public static Meteorite createMeteorite(LogicController logicController, MeteroritePacket meteroritePacket) {
        BufferedImage texture;
        Size size = null;
        int index =0;
        switch (meteroritePacket.getType()){
            case Constants.BIG_M:
                index = Utilities.getRandom(0,Assets.bigMeteor.length);
                texture=Assets.bigMeteor[index];
                size = Size.BIG;
                break;
            case Constants.MEDIUM_M:
                index = Utilities.getRandom(0,Assets.bigMeteorSplit.length);
                texture = Assets.bigMeteorSplit[index];
                size = Size.MED;
                break;
            case Constants.SMALL_M:
                index= Utilities.getRandom(0,Assets.midMeteorSplit.length);
                texture = Assets.midMeteorSplit[index];
                size = Size.SMALL;
                break;
            default:
                texture=Assets.bigMeteor[0];
                size = Size.BIG;
                break;
        }


        Vector2D delta = new Vector2D(meteroritePacket.getDeltaX(),meteroritePacket.getDeltaY());
        Vector2D position = getCorrectPosition(new Vector2D(meteroritePacket.getPositionX(),meteroritePacket.getPositionY()),
                logicController,meteroritePacket.getFrom(),texture);

        Meteorite m = new Meteorite(logicController,position,delta,Constants.METEOR_VEL,texture,size);
        return m;
    }

    public static Bullet createBullet(LogicController logicController, Vector2D delta,Vector2D position,int type,
                                      double angle, String team ){
        BufferedImage texture = null;
        double maxVel;
        if (type == Bullet.TYPE_POWERUP){
            Sound.shootPowerup();
            texture = Assets.bigShoot;
            maxVel = Constants.BULLET_VEL;
        }else{
            Sound.shooting();
            texture = Assets.greenShoot;
            maxVel = Constants.BULLET_VEL;
        }
        Bullet bullet = new Bullet(logicController,delta,position,maxVel,texture,angle,team,type);
        return bullet;
    }

    public static Bullet createBullet(BulletPacket bulletPacket, LogicController logicController){
        Vector2D position = new Vector2D(bulletPacket.getPositionX(),bulletPacket.getPositionY());
        BufferedImage texture = null;
        double maxVel;
        int type = bulletPacket.getType();
        if (type == Bullet.TYPE_POWERUP){
            Sound.shootPowerup();
            texture = Assets.bigShoot;
            maxVel = Constants.BULLET_VEL;
        }else{
            Sound.shooting();
            texture = Assets.greenShoot;
            maxVel = Constants.BULLET_VEL;
        }

        Vector2D delta = new Vector2D(bulletPacket.getDeltaX(),bulletPacket.getDeltaY());
        position = getCorrectPosition(position,logicController,bulletPacket.getFrom(),texture);
        Bullet bullet = new Bullet(logicController,delta,position,maxVel,texture,bulletPacket.getAngle(),
                bulletPacket.getTeam(),type);
        return bullet;
    }

    public static Vector2D getCorrectPosition(Vector2D position, LogicController logicController, String from,
                                              BufferedImage texture){
        switch (from){
            case Constants.UP:
                position.setY(0);
                break;
            case Constants.RIGHT:
                System.out.println(logicController.getViewer().getWidth());
                position.setX((double) logicController.getViewer().getWidth()-texture.getWidth());
                break;
            case Constants.DOWN:
                position.setY(logicController.getViewer().getHeight()-texture.getHeight());
                break;
            case Constants.LEFT:
                position.setX(0);
                break;
        }
        if (from.equals(Constants.UP) || from.equals(Constants.DOWN)){
            if (position.getX()>logicController.getViewer().getWidth()){
                position.setX(logicController.getViewer().getWidth()-texture.getWidth());
            }
        }else{
            if (position.getY()>logicController.getViewer().getWidth()){
                position.setY(logicController.getViewer().getHeight()-texture.getHeight());
            }
        }
        return position;
    }

    public static BulletPacket createBulletPacket(Bullet bullet, String from){
        BulletPacket bulletPacket = new BulletPacket();
        bulletPacket.setDeltaX(bullet.getDelta().getX());
        bulletPacket.setDeltaY(bullet.getDelta().getY());
        bulletPacket.setPositionX(bullet.getPosition().getX());
        bulletPacket.setPositionY(bullet.getPosition().getY());
        bulletPacket.setAngle(bullet.getAngle());
        bulletPacket.setTeam(bullet.getEquipo());
        bulletPacket.setType(bullet.getType());
        bulletPacket.setFrom(from);
        return bulletPacket;
    }

    public static Message updateIdController(int newId){
        Message message = new Message();
        message.setMessageType("ADMIN");
        message.setMessage(Integer.toString(newId));
        return message;
    }

    public static int getRandom(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
