package eu.cifpfbmoll.logic;

import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import eu.cifpfbmoll.graphic.panels.*;
import eu.cifpfbmoll.graphic.objects.*;
import eu.cifpfbmoll.netlib.node.NodeManager;
import eu.cifpfbmoll.sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;


public class TheaterMode extends JFrame implements Runnable{


    //Attributes
    private ArrayList<Spacecraft> redTeam = new ArrayList<>();
    private ArrayList<Spacecraft> blueTeam = new ArrayList<>();
    private ArrayList<Spacecraft> allSpacecrafts = new ArrayList<>();
    public Configuration configuration;
    private MainScreen mainScreen;
    private Thread logicThread;
    private Clip sound;
    private Map<Integer, String> nodes;
    private Map<Integer, String> pcList = new HashMap();
    public NodeManager nodeManager;
    private int myID;
    private int myAdminIs = 0;
    private String IP;
    private boolean imAdmin = false;
    private boolean theaterActive = true;

    private final String NICKNAME = "NICKNAME",TEAM = "TEAM", READY = "READY", SPACECRAFT_TYPE = "SPACECRAFT TYPE",
            ADMIN = "ADMIN", BLUE = "BLUE", RED = "RED", TRUE = "TRUE", FALSE = "FALSE", DISCOVER = "DISCOVER", START = "START",
            NICKNAMEACK = "NICKNAMEACK";


    //Getters & Setters
    public ArrayList<Spacecraft> getRedTeam() {
        return redTeam;
    }

    public void setRedTeam(ArrayList<Spacecraft> redTeam) {
        this.redTeam = redTeam;
    }

    public ArrayList<Spacecraft> getBlueTeam() {
        return blueTeam;
    }

    public void setBlueTeam(ArrayList<Spacecraft> blueTeam) {
        this.blueTeam = blueTeam;
    }

    public Map<Integer, String> getPcList() {
        return pcList;
    }

    public void setPcList(Map<Integer, String> pcList) {
        this.pcList = pcList;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Map<Integer, String> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, String> nodes) {
        this.nodes = nodes;
    }

    public boolean isImAdmin() {
        return imAdmin;
    }

    public void setImAdmin(boolean imAdmin) {
        this.imAdmin = imAdmin;
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }


    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public TheaterMode(){
        this.configuration = new Configuration();
        this.sound = Sound.clipSoundMenu();
        this.sound.start();
        //Para ver las interfaces
        /*try {
            for (NetworkInterface netint : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                System.out.println(netint.getName());
                System.out.println(netint.getDisplayName());
                for (InetAddress addr : Collections.list(netint.getInetAddresses())) {
                    System.out.println(addr);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }*/
        NetworkInterface networkInterface = NodeManager.getInterfaceByName("eth6");
        String ip = NodeManager.getIPForInterface(networkInterface);
        this.myID = NodeManager.getIdForIp(ip);
        this.IP = ip;




        nodeManager = new NodeManager(ip);
        String subnet = nodeManager.getSubnet(ip);
        List<String> ips = nodeManager.getIpsForSubnet(subnet);
        nodeManager.startScan(ips);

        this.configuration.setScreenID(myID);

        nodeManager.register(Message.class, (id, message) ->{
            switch (message.getMessageType()){
                case NICKNAME:
                    Spacecraft spacecraft = new Spacecraft(this);
                    spacecraft.setID(id);
                    spacecraft.setNickname(message.getMessage());
                    this.allSpacecrafts.add(spacecraft);
                    addNewMessage(id, spacecraft.getNickname(), "joined the game");
                    Message messageACK = new Message();
                    messageACK.setMessageType(NICKNAMEACK);
                    messageACK.setMessage("");
                    nodeManager.send(id,messageACK);
                    //??¿?¿?¿?¿?¿check if nickname exists
                    break;
                case TEAM:
                    for (int i = 0; i < allSpacecrafts.size(); i++) {
                        if(allSpacecrafts.get(i).getID() == id){
                            allSpacecrafts.get(i).setTeam(message.getMessage());
                            if(message.getMessage().equals(RED)){
                                addRedTeam(allSpacecrafts.get(i));
                            }else if(message.getMessage().equals(BLUE)){
                                addBlueTeam(allSpacecrafts.get(i));
                            }
                        }
                    }
                    break;
                case READY:
                    for (int i = 0; i < allSpacecrafts.size(); i++) {
                        if(allSpacecrafts.get(i).getID() == id){
                            boolean ready = false;
                            if(message.getMessage().equals(TRUE)){
                                ready = true;
                            }else if(message.getMessage().equals(FALSE)){
                                ready = false;
                            }
                            allSpacecrafts.get(i).setReady(ready);
                            String readyString;
                            if(ready){
                                readyString = "is ready";
                            }else{
                                readyString = "is not ready";
                            }
                            addNewMessage(allSpacecrafts.get(i).getID(), allSpacecrafts.get(i).getNickname(),readyString);
                        }
                    }
                    break;
                case SPACECRAFT_TYPE:
                    for (int i = 0; i < allSpacecrafts.size(); i++) {
                        if(allSpacecrafts.get(i).getID() == id){
                            allSpacecrafts.get(i).setSpacecraftType(Integer.parseInt(message.getMessage()));
                        }
                    }
                    break;
                case DISCOVER:
                    if(pcList.get(id) == null){
                        pcList.put(id, message.getMessage());
                    }
                    break;
                case START:
                    startGame();
                    break;
                case ADMIN:
                    if(!imAdmin){
                        this.myAdminIs = Integer.parseInt(message.getMessage());
                        this.mainScreen.getStartPanel().hideCheckbox();
                        Message message1 = new Message();
                        message1.setMessageType(DISCOVER);
                        message1.setMessage(ip);
                        nodeManager.send(this.myAdminIs, message1);
                    }
                    break;
            }
        });

        nodeManager.register(Configuration.class, (identifier, configuration) ->{
            this.configuration = configuration;
        });

        /*for (int i = 0; i < 15; i++) {
            nodeManager.addNode(i,"192.168.1."+i);
        }*/


        this.pcList.put(myID, ip);
        this.mainScreen = new MainScreen(this, this.configuration);
        mainScreen.showFrame();
        logicThread = new Thread(this);
        logicThread.start();
    }

    //Methods
    public static void main(String[] args) {
        TheaterMode theaterMode = new TheaterMode();
    }

    public void addSpacecraft(TheaterMode theaterMode, int ID){

        Spacecraft spacecraft = new Spacecraft(theaterMode);
        spacecraft.setNickname("Nick_"+ID);
        spacecraft.setID(ID);
        Random rd = new Random();
        spacecraft.setReady(true);
        if(rd.nextBoolean()){
            spacecraft.setTeam("Red");
            addRedTeam(spacecraft);
        }else{
            spacecraft.setTeam("Blue");
            addBlueTeam(spacecraft);
        }
    }

    public void addRedTeam(Spacecraft spacecraft){
        boolean newSpacecraft = true;
        for (int i = 0; i < blueTeam.size(); i++) {
            if(blueTeam.get(i).getID() == spacecraft.getID()){
                newSpacecraft = false;
                addNewMessage(blueTeam.get(i).getID(), blueTeam.get(i).getNickname(),"switched to RED TEAM");
                blueTeam.remove(i);
            }
        }
        boolean exist = false;
        for (int i = 0; i < redTeam.size(); i++) {
            if(redTeam.get(i).getID() == spacecraft.getID()){
                exist = true;
            }
        }
        if(newSpacecraft && !exist){
            addNewMessage(spacecraft.getID(), spacecraft.getNickname(),"added to RED TEAM");
        }
        if(!exist){
            spacecraft.setTeam(RED);
            redTeam.add(spacecraft);
        }

    }

    public void addBlueTeam(Spacecraft spacecraft){
        boolean newSpacecraft = true;
        for (int i = 0; i < redTeam.size(); i++) {
            if(redTeam.get(i).getID() == spacecraft.getID()){
                newSpacecraft = false;
                addNewMessage(redTeam.get(i).getID(), redTeam.get(i).getNickname(),"switched to BLUE TEAM");
                redTeam.remove(i);
            }
        }
        boolean exist = false;
        for (int i = 0; i < blueTeam.size(); i++) {
            if(blueTeam.get(i).getID() == spacecraft.getID()){
                exist = true;
            }
        }
        if (newSpacecraft && !exist) {
            addNewMessage(spacecraft.getID(), spacecraft.getNickname(),"added to BLUE TEAM");
        }
        if(!exist){
            spacecraft.setTeam(BLUE);
            blueTeam.add(spacecraft);
        }

    }

    public void addNewMessage(int ID, String nickname, String messageInfo){
        String[] message = new String[1];
        message[0] = "Player -> ID: " + ID + ", Nick: " + nickname
                + " " +messageInfo+ "\n";

            this.mainScreen.adminPanel.addMessagesToGeneralLog(message);

            this.mainScreen.getClientPanel().addMessagesToGeneralLog(message);

    }

    public boolean allReady(){
        boolean allReady = true;
        for (int i = 0; i < redTeam.size(); i++) {
            if(!redTeam.get(i).getReady()){
                allReady = false;
            }
        }

        for (int i = 0; i < blueTeam.size(); i++) {
            if(!blueTeam.get(i).getReady()){
                allReady = false;
            }
        }
        return allReady;
    }


    public void startGame(){
        theaterActive = false;
        nodeManager.stopScan();
        //GameMode gameMode = new GameMode(nodeManager, redTeam, blueTeam,configuration);
        sound.stop();
        System.out.println("Let the games begin");
    }


    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {
            addSpacecraft(this, i);
        }
        while (theaterActive){
            try{
                if(imAdmin){
                    Message message = new Message();
                    message.setMessageType(ADMIN);
                    message.setMessage(Integer.toString(myID));
                    nodeManager.broadcast(message);

                    if(allReady()){
                        this.mainScreen.adminPanel.getButtonPlay().setEnabled(true);
                    }else{
                        this.mainScreen.adminPanel.getButtonPlay().setEnabled(false);
                    }
                }
                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println("Exception in theater mode run: " + e);
            }

        }
    }
}

