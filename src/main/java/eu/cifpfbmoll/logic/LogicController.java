package eu.cifpfbmoll.logic;


import eu.cifpfbmoll.graphic.canvas.Assets;
import eu.cifpfbmoll.graphic.canvas.Viewer;
import eu.cifpfbmoll.logic.Beans.BulletPacket;
import eu.cifpfbmoll.logic.Beans.MeteroritePacket;
import eu.cifpfbmoll.logic.Beans.ShipPacket;
import eu.cifpfbmoll.math.Vector2D;
import eu.cifpfbmoll.netlib.node.NodeManager;
import eu.cifpfbmoll.sound.Sound;
import eu.cifpfbmoll.visualObjects.*;

import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LogicController implements Runnable {

    private ArrayList<DynamicVisualObject> dynamicVisualObjects;
    private ArrayList<StaticVisualObject> staticVisualObjects;
    private ArrayList<Ship> ships;
    private int[] network = new int[4]; // order is: up, right, down, left
    private Viewer viewer;
    private MotherShip motherShipRed;
    private MotherShip motherShipBlue;
    private int puntosEquipoRojo = 0;
    private int puntosEquipoAzul = 0;
    private NodeManager nodeManager;
    private int adminId;//id de la mother screen
    private int myId;
    private boolean imAdmin;

    public LogicController(ArrayList<StaticVisualObject> staticVisualObjects, ArrayList<DynamicVisualObject> dynamicVisualObjects,
                           Viewer viewer, NodeManager nodeManager, Configuration configuration,
                           ArrayList<Ship> ships, int adminId, int myId, boolean imAdmin) {
        this.dynamicVisualObjects = dynamicVisualObjects;
        this.staticVisualObjects = staticVisualObjects;
        this.ships = ships;
        this.viewer = viewer;
        this.adminId = adminId;
        this.myId = myId;
        this.imAdmin = imAdmin;
        if (this.imAdmin){
            Vector2D po = new Vector2D(0,100);
            Vector2D po2 = new Vector2D(this.getViewer().getWidth()-Assets.motherShip.getWidth(),100);
            motherShipRed = new MotherShip(po,Assets.motherShip2,this);
            motherShipBlue = new MotherShip(po2,Assets.motherShip,this);
            this.staticVisualObjects.add(motherShipRed);
            this.staticVisualObjects.add(motherShipBlue);
        }
        colocarNaves();
        this.nodeManager = nodeManager;
        this.network[0] =configuration.getUp();
        this.network[1] =configuration.getRight();
        this.network[2] =configuration.getDown();
        this.network[3] =configuration.getLeft();
        generateBlackHoles(configuration.getBlackHoleQuantity());
        startWave(configuration.getMeteorQuantity());
        int timeSleepPowerUp =4000;
        if (configuration.getObjectGeneration().equals("LOW")){
            timeSleepPowerUp=7000;
        }else if (configuration.getObjectGeneration().equals("HIGH")){
            timeSleepPowerUp=3000;
        }
        addThreadPowerUpGenerator(this,timeSleepPowerUp);
        System.out.println("size de ships" + ships.size());
        addListenerMessage();
        this.initTheard();
    }

    //<editor-fold desc="getters and setters">
    public ArrayList<DynamicVisualObject> getDynamicVisualObjects() {
        return dynamicVisualObjects;
    }

    public void setDynamicVisualObjects(ArrayList<DynamicVisualObject> dynamicVisualObjects) {
        this.dynamicVisualObjects = dynamicVisualObjects;
    }

    public ArrayList<StaticVisualObject> getStaticVisualObjects() {
        return staticVisualObjects;
    }

    public void setStaticVisualObjects(ArrayList<StaticVisualObject> staticVisualObjects) {
        this.staticVisualObjects = staticVisualObjects;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
        this.colocarNaves();
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public boolean isImAdmin() {
        return imAdmin;
    }

    public void setImAdmin(boolean imAdmin) {
        this.imAdmin = imAdmin;
    }

    //</editor-fold>

    public void initTheard(){
        Thread thread = new Thread(this);
        thread.start();
    }

    public void sendMessage(Message m, int id){
        this.nodeManager.send(id,m);
    }

    public void addShips(){
        for (int i = 0; i < 1; i++) {
            Ship ship = Utilities.createShip(this,100,0,Ship.TYPE_O,Ship.team1,10,"juan",
                    Math.toRadians(90));
            this.ships.add(ship);
            this.dynamicVisualObjects.add(ship);
        }
        for (int i = 0; i < 1; i++) {
            Ship ship = Utilities.createShip(this,100,0,Ship.TYPE_O,Ship.team2,10,"juan",
                    Math.toRadians(270));
            ship.setPowerUp(PowerUp.TYPE_SHIELD);
            this.ships.add(ship);
            this.dynamicVisualObjects.add(ship);
        }
    }

    public void colocarNaves(){
        ArrayList<Ship> redTeam = new ArrayList<>();
        ArrayList<Ship> blueTeam = new ArrayList<>();
        for (int i = 0; i < this.ships.size(); i++) {
            if (this.ships.get(i).getEquipo().equals(Ship.team1)){
                redTeam.add(this.ships.get(i));
            }else{
                blueTeam.add(this.ships.get(i));
            }
        }
        Double positionY = motherShipRed.getPosition().getY()+100;
        Double positionX = motherShipRed.getCenter().getX();
        for (int i = 0; i < redTeam.size(); i++) {
            positionY = positionY + redTeam.get(i).getTexture().getHeight()+20;
            if (positionY+redTeam.get(i).getHeight()>this.motherShipRed.getPosition().getY()+motherShipRed.getHeight()){
                positionY= motherShipRed.getPosition().getY()+100 +redTeam.get(i).getTexture().getHeight()+20;
                positionX += redTeam.get(i).getTexture().getWidth()+10;
            }
            redTeam.get(i).setPosition(new Vector2D(positionX,positionY));
        }
        positionY = motherShipBlue.getPosition().getY()+100;
        positionX = motherShipBlue.getCenter().getX();
        for (int i = 0; i < blueTeam.size(); i++) {
            positionY = positionY + blueTeam.get(i).getTexture().getHeight()+20;
            if (positionY+blueTeam.get(i).getHeight()>this.motherShipBlue.getPosition().getY()+motherShipBlue.getHeight()){
                positionY= motherShipBlue.getPosition().getY()+100 +blueTeam.get(i).getTexture().getHeight()+20;
                positionX -= blueTeam.get(i).getTexture().getWidth()+10;
            }
            blueTeam.get(i).setPosition(new Vector2D(positionX,positionY));
        }
    }

    public void addListenerMessage(){
        this.nodeManager.unregister("INFO");
        this.nodeManager.register(Message.class, (id, serverMessage) -> {
            Ship ship = getShipToPerformAction(id);
            switch (serverMessage.getMessage()){
                case Ship.LEFT:
                    ship.rotate("de");
                    break;
                case Ship.RIGHT:
                    ship.rotate("iz");
                    break;
                case Ship.MOVE:
                    ship.acceleration();
                    break;
                case Ship.SHOOT:
                    ship.shoot();
                    break;
                case "SCORE":
                    if (serverMessage.getMessage().equals(Ship.team1)){
                        puntosEquipoRojo++;
                        sendScore(false, false);
                    }
                    break;
            }
        });

        this.nodeManager.register(BulletPacket.class, (id, bulletPacket) -> {
            System.out.println("new bullet");
            Bullet bullet = Utilities.createBullet(bulletPacket,this);
            dynamicVisualObjects.add(bullet);
        });

        this.nodeManager.register(MeteroritePacket.class, (id, m) -> {
            System.out.println("new meteorite");
            Meteorite meteorite = Utilities.createMeteorite(this,m);
            dynamicVisualObjects.add(meteorite);
        });

        this.nodeManager.register(ShipPacket.class, (id, m) -> {
            System.out.println("new ship");
            Ship ship = Utilities.createShip(this,m);
            this.ships.add(ship);
            this.dynamicVisualObjects.add(ship);
            if (m.isRespawn()){
                ship.respawn();
            }
        });


    }

    public Ship getShipToPerformAction(int id){
        for (int i = 0; i < this.ships.size(); i++) {
            if (this.ships.get(i).getIdMando() == id){
                return this.ships.get(i);
            }
        }
        return null;
    }

    public void sendScore(Boolean red, Boolean sendToAdmin){
        String score = Integer.toString(puntosEquipoRojo)+":"+Integer.toString(puntosEquipoAzul);
        System.out.println(score);
        Message message = new Message();
        message.setMessageType("SCORE");
        message.setMessage(score);
        if (sendToAdmin){
            Message messageScore = new Message();
            messageScore.setMessageType("SCORE");
            if (red){
                messageScore.setMessage(Ship.team1);
            }else{
                messageScore.setMessage(Ship.team2);
            }
            nodeManager.send(this.adminId,messageScore);
        }
        for (int i = 0; i < this.dynamicVisualObjects.size(); i++) {
            if (this.dynamicVisualObjects.get(i) instanceof Ship){
                Ship ship = (Ship) dynamicVisualObjects.get(i);
                System.out.println("se ha enviado" + ship.getIdMando());
                nodeManager.send(ship.getIdMando(),message);
            }
        }
    }

    public void generateBlackHoles(int numBlackHoles) {
       for (int i = 0; i < numBlackHoles; i++) {
            this.staticVisualObjects.add(Utilities.createBlackHole(this));
        }
    }

    public void addThreadPowerUpGenerator(LogicController logicController,int time) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    PowerUp powerUp = Utilities.createPowerUp(logicController);
                    logicController.staticVisualObjects.add(powerUp);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    public void collidesWith(DynamicVisualObject object) {
        for (int i = 0; i < dynamicVisualObjects.size(); i++) {
            DynamicVisualObject m = dynamicVisualObjects.get(i);

            if (m.equals(object))
                continue;

            double distance = m.getCenter().subtract(object.getCenter()).getMagnitude();
            if (distance < m.getWidth() / 2 + object.getWidth() / 2) {
                objectCollision(object, m);
            }
        }

        for (int i = 0; i < staticVisualObjects.size(); i++) {

            StaticVisualObject m = staticVisualObjects.get(i);
            if (m.equals(object))
                continue;

            double distance = m.getCenter().subtract(object.getCenter()).getMagnitude();

            if (distance < m.getWidth() / 2 + object.getWidth() / 2) {
                objectCollision(object, m);
            }
        }
    }

    public void outOfBounds(DynamicVisualObject dynamicVisualObject) {
        int yPosition = ((int) dynamicVisualObject.getPosition().getY());
        int xPosition = ((int) dynamicVisualObject.getPosition().getX());
        int idToSend = 0;
        String from=Constants.DOWN;
        boolean outOfBounds = false;
        if ((yPosition < 0 - dynamicVisualObject.getHeight() / 2)) {
            outOfBounds = true;
            idToSend = this.network[0];
            from = Constants.DOWN;
            System.out.println("up");
        } else if (yPosition + dynamicVisualObject.getHeight() / 2 > this.getViewer().getHeight()) {
            outOfBounds = true;
            idToSend = this.network[2];
            from =Constants.UP;
            System.out.println("down");
        } else if ((xPosition < 0 - dynamicVisualObject.getWidth() / 2)) {
            outOfBounds = true;
            idToSend = this.network[1];
            from = Constants.RIGHT;
            System.out.println("left");
        } else if (xPosition + dynamicVisualObject.getWidth() / 2 > this.getViewer().getWidth()) {
            outOfBounds = true;
            idToSend = this.network[3];
            from = Constants.LEFT;
            System.out.println("right");
        }
        if (outOfBounds && idToSend!=0) {
            if (dynamicVisualObject instanceof Bullet){
                Bullet bullet = (Bullet) dynamicVisualObject;
                BulletPacket b = Utilities.createBulletPacket(bullet, from);
                nodeManager.send(idToSend,b);
                dynamicVisualObject.destroy();
                dynamicVisualObjects.remove(bullet);
            }else if (dynamicVisualObject instanceof Ship){
                Ship ship = (Ship) dynamicVisualObject;
                ShipPacket shipPacket = Utilities.createShipPacket(ship,from,false);
                ship.destroy();
                ships.remove(ship);
                dynamicVisualObjects.remove(ship);
                nodeManager.send(idToSend,shipPacket);
                nodeManager.send(ship.getIdMando(),Utilities.updateIdController(idToSend));
            }else if (dynamicVisualObject instanceof Meteorite){
                Meteorite meteorite = (Meteorite) dynamicVisualObject;
                MeteroritePacket meteoritePacket = Utilities.createMeteoritePacket(meteorite,from);
                meteorite.destroy(false);
                dynamicVisualObjects.remove(meteorite);
                nodeManager.send(idToSend,meteoritePacket);
            }
        } else if (outOfBounds && idToSend==0) {
            System.out.println("no send");
            if (dynamicVisualObject instanceof Meteorite){
                ((Meteorite) dynamicVisualObject).destroy(false);
            }else {
                dynamicVisualObject.destroy();
            }
            if (dynamicVisualObject instanceof Ship) {
                Ship ship = (Ship) dynamicVisualObject;
                respawnShip(ship);
            } else {
                this.dynamicVisualObjects.remove(dynamicVisualObject);
            }
        }
    }

    public void respawnShip(Ship ship){
        if (imAdmin==false){
            //TODO crear mensaje tipo nave y enviar
            ShipPacket ship1 = Utilities.createShipPacket(ship,Constants.RIGHT,true);
            Message message = new Message();
            message.setMessageType("ADMIN");
            message.setMessage(Integer.toString(this.adminId));
            nodeManager.send(this.adminId,ship1);
            nodeManager.send(ship.getIdMando(),message);
        }else{
            if (ship.getEquipo().equals(Ship.team1)){
                ship.setPosition(motherShipRed.getCenter());
                ship.setAngle(Math.toRadians(90));
            }else if (ship.getEquipo().equals(Ship.team2)){
                ship.setPosition(motherShipBlue.getCenter());
                ship.setAngle(Math.toRadians(270));
            }
        }
    }

    private synchronized void objectCollision(DynamicVisualObject a, VisualObject b) {
        if (!(a instanceof Meteorite && b instanceof Meteorite)) {
            if (a instanceof Ship && b instanceof Ship) {
                if (a.isColisionable() && ((Ship) b).isColisionable()){
                    if (!((Ship) a).getEquipo().equals(((Ship) b).getEquipo())){
                        Sound.shipCollision();
                        ((Ship) a).destroy();
                        ((Ship) b).destroy();
                    }
                }
            } else if (a instanceof Bullet && b instanceof Ship) {
                if (((Ship) b).isColisionable()){
                    ((Bullet) a).destroy();
                    this.dynamicVisualObjects.remove(a);
                    if (!((Bullet) a).getEquipo().equals(((Ship) b).getEquipo())) {
                        //chequeo del power up escudo de la nave
                        if (((Ship) b).getPowerUp()!=PowerUp.TYPE_SHIELD){
                            ((Ship) b).destroy();
                            if (((Bullet) a).getEquipo().equals(Ship.team1)) {
                                this.puntosEquipoRojo++;
                                sendScore(true,false);
                            } else {
                                this.puntosEquipoAzul++;
                                sendScore(false,false);
                            }
                        }else{
                            ((Ship) b).setPowerUp(PowerUp.NO_POWER_UP);
                        }
                        showPuntuacion();
                    }
                }
            } else if (a instanceof Bullet && b instanceof Meteorite) {
                if (((Bullet) a).isAlive()) {
                    Sound.destroyMeteorite();
                    this.dynamicVisualObjects.remove(a);
                    this.dynamicVisualObjects.remove(b);
                    ((Bullet) a).setAlive(false);
                    ((Meteorite) b).setAlive(false);
                    ((Meteorite) b).destroy(true);
                }
            } else if (a instanceof Ship && b instanceof PowerUp) {
                ((PowerUp) b).addPowerUpToShip((Ship) a);
                this.staticVisualObjects.remove(b);
            } else if (a instanceof DynamicVisualObject && b instanceof BlackHole) {
                Sound.blackHole();
                Vector2D aux = new Vector2D(100, 200);
                addElementFromBlackHole(a);
            } else if (a instanceof Ship && b instanceof Meteorite) {
                if (a.isColisionable()){
                    Sound.shipCollision();
                    a.destroy();
                    this.dynamicVisualObjects.remove(b);
                    ((Meteorite) b).divideMeteor((Meteorite) b);
                }
            }
        }
    }

    public void showPuntuacion() {
        System.out.println("puntos equipo rojo:" + this.puntosEquipoRojo);
        System.out.println("puntos equipo azul:" + this.puntosEquipoAzul);
    }

    public void addElementFromBlackHole(DynamicVisualObject object) {
        ArrayList<BlackHole> blackHoles = new ArrayList<>();
        for (int i = 0; i < this.staticVisualObjects.size(); i++) {
            if (this.getStaticVisualObjects().get(i) instanceof BlackHole) {
                blackHoles.add((BlackHole) getStaticVisualObjects().get(i));
            }
        }
        int aux = Utilities.getRandom(0, blackHoles.size()-1);
        object.setPosition(blackHoles.get(aux).getCenter());
        object.setColisionable(false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (object instanceof Meteorite){
                        Thread.sleep(3000);
                    }else{
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                object.setColisionable(true);
            }
        });
        thread.start();
    }

    public void destroy(DynamicVisualObject object) {
        getMovingObjects().remove(object);
        if (object instanceof Meteorite) {
            Meteorite meteorite = (Meteorite) object;
            meteorite.destroy(true);
        }
    }

    private void startWave(int meteoritePerMinute) {
        int timeToSleep = 60000/meteoritePerMinute;
        LogicController controller = this;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    double x, y;
                    for (int i = 0; i < 1; i++) {
                        x = i % 2 == 0 ? Math.random() * Constants.WIDTH : 0;
                        y = i % 2 == 0 ? 0 : Math.random() * Constants.HEIGHT;
                        BufferedImage texture = Assets.bigMeteor[0];
                        dynamicVisualObjects.add(new Meteorite(
                                controller, new Vector2D(x, y), new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                                Constants.METEOR_VEL * Math.random() + 1, texture, Size.BIG
                        ));
                        try {
                            Thread.sleep(timeToSleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        t.start();
    }

    public ArrayList<DynamicVisualObject> getMovingObjects() {
        return dynamicVisualObjects;
    }

    @Override
    public void run() {
        long timepassed = 0;
        Clip musica = Sound.clipSoundGame(15);
        boolean alive = true;
        while (alive){
            if (puntosEquipoRojo>=70 || puntosEquipoAzul>=70){
                alive=false;
            }
            if (timepassed<10){

            }else if (timepassed==240){
                musica.close();
                musica = Sound.clipSoundLastMinute(15);
            }else if (timepassed<240){

            }else if (timepassed==300){
                alive=false;
            }
            try {
                Thread.sleep(1000);
                timepassed++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message message = new Message();
        message.setMessageType("FINISH");
        message.setMessage("");
        for (int i = 0; i < ships.size(); i++) {
            int aux =  ships.get(i).getIdMando();
            for (int j = 0; j < 5 ; j++) {
                nodeManager.send(aux,message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.exit(0);
    }
}
