package eu.cifpfbmoll.logic;


import eu.cifpfbmoll.graphic.canvas.Viewer;
import eu.cifpfbmoll.graphic.objects.Spacecraft;
import eu.cifpfbmoll.netlib.node.NodeManager;
import eu.cifpfbmoll.visualObjects.DynamicVisualObject;
import eu.cifpfbmoll.visualObjects.Ship;
import eu.cifpfbmoll.visualObjects.StaticVisualObject;
import eu.cifpfbmoll.visualObjects.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameMode extends JFrame implements KeyListener {
    private Viewer viewer;
    private LogicController logicController;
    private ArrayList<StaticVisualObject> staticVisualObjects = new ArrayList<>();
    private ArrayList<DynamicVisualObject> dynamicVisualObjects = new ArrayList<DynamicVisualObject>();
    private ArrayList<Ship> ships = new ArrayList<>();
    //cosas del pana cristian
    private NodeManager nodeManager;
    private ArrayList<Spacecraft> redTeam;
    private ArrayList<Spacecraft> blueTeam;
    private Configuration configuration;
    //TODO AÑADIR AL CONTRUCTOR LOS ATRIBUTOS DE CRISTIAN

    public GameMode(NodeManager nodeManager, ArrayList<Spacecraft> redTeam, ArrayList<Spacecraft> blueTeam,
                    Configuration configuration, TheaterMode theaterMode){
        this.nodeManager = nodeManager;
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        this.configuration = configuration;
        this.setLayout(new GridBagLayout());
        this.addKeyListener(this);
        this.setSize(1920,1080);
        addViewer();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.viewer.getThread().start();
        int myId = theaterMode.getMyID();
        int adminId = theaterMode.getMyAdminIs();
        addLogicController(myId,adminId,theaterMode.isImAdmin());
        addShips();
        logicController.setShips(ships);
    }

    //<editor-fold desc="getter and setters">

    public ArrayList<StaticVisualObject> getStaticVisualObjects() {
        return staticVisualObjects;
    }

    public void setStaticVisualObjects(ArrayList<StaticVisualObject> staticVisualObjects) {
        this.staticVisualObjects = staticVisualObjects;
    }

    public ArrayList<DynamicVisualObject> getDynamicVisualObjects() {
        return dynamicVisualObjects;
    }

    public void setDynamicVisualObjects(ArrayList<DynamicVisualObject> dynamicVisualObjects) {
        this.dynamicVisualObjects = dynamicVisualObjects;
    }

    //</editor-fold>

    public void addShips(){
        for (int i = 0; i < this.redTeam.size(); i++) {
            Spacecraft spacecraft = this.redTeam.get(i);
            String team = spacecraft.getTeam();
            int id = spacecraft.getID();
            String username = spacecraft.getNickname();
            int type = spacecraft.getSpacecraftType();
            Ship ship= Utilities.createShip(this.logicController,0,0,type,team,id,username,Math.toRadians(90));
            this.dynamicVisualObjects.add(ship);
            this.ships.add(ship);
        }
        for (int i = 0; i < this.blueTeam.size(); i++) {
            Spacecraft spacecraft = this.blueTeam.get(i);
            String team = spacecraft.getTeam();
            int id = spacecraft.getID();
            String username = spacecraft.getNickname();
            int type = spacecraft.getSpacecraftType();
            Ship ship= Utilities.createShip(this.logicController,0,0,type,team,id,username,Math.toRadians(270));
            this.dynamicVisualObjects.add(ship);
            this.ships.add(ship);
        }
    }

    private void addViewer() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx =0.9;
        gbc.weighty = 0.9;
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.viewer = new Viewer(staticVisualObjects, dynamicVisualObjects);
        this.viewer.setBackground(Color.BLACK);
        this.add(viewer, gbc);
    }

    private void addLogicController(int myId, int myAdmin,boolean imAdmin){
        //TODO mother screen
        this.logicController = new LogicController(staticVisualObjects, dynamicVisualObjects,this.viewer,
                nodeManager,configuration,ships,myAdmin,myId,imAdmin);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Ship ship = (Ship) this.dynamicVisualObjects.get(0);
        switch (keyEvent.getKeyChar()){
            case 'd':
                ship.rotate("iz");
                break;
            case 'a':
                ship.rotate("de");
                break;
            case 'e':
                ship.shoot();
                break;
            default:
                ship.acceleration();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
