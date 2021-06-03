package eu.cifpfbmoll.logic;

import eu.cifpfbmoll.graphic.panels.*;
import eu.cifpfbmoll.graphic.objects.*;
import eu.cifpfbmoll.netlib.node.NodeManager;
import eu.cifpfbmoll.sound.Sound;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.net.NetworkInterface;
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
    private NodeManager nodeManager;
    private int myID;
    private int myAdminIs = 0;
    private boolean imAdmin = false;

    private final String NICKNAME = "NICKNAME",TEAM = "TEAM", READY = "READY", SPACECRAFT_TYPE = "SPACECRAFT TYPE",
            ADMIN = "ADMIN", BLUE = "BLUE", RED = "RED", TRUE = "TRUE", FALSE = "FALSE";


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

    public TheaterMode(){
        this.configuration = new Configuration();
        /*this.sound = Sound.clipSoundMenu();
        this.sound.start();*/

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
        NetworkInterface networkInterface = NodeManager.getInterfaceByName("eth0");
        String ip = NodeManager.getIPForInterface(networkInterface);
        this.myID = NodeManager.getIdForIp(ip);


        nodeManager = new NodeManager(ip);
        String subnet = nodeManager.getSubnet(ip);
        List<String> ips = nodeManager.getIpsForSubnet(subnet);
        nodeManager.startScan(ips);

        nodeManager.register(Message.class, (id, message) ->{
            switch (message.getMessageType()){
                case NICKNAME:
                    Spacecraft spacecraft = new Spacecraft(this);
                    spacecraft.setID(id);
                    spacecraft.setNickname(message.getMessage());
                    this.allSpacecrafts.add(spacecraft);
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
                case ADMIN:
                    this.myAdminIs = Integer.parseInt(message.getMessage());
                    this.mainScreen.getStartPanel().hideCheckbox();
                    break;
            }

            nodeManager.register(Configuration.class, (identifier, configuration) ->{
                this.configuration = configuration;
            });

            //code when recieve message with this packet
        });

        for (int i = 0; i < 15; i++) {
            nodeManager.addNode(i,"192.168.1."+i);
        }

        this.nodes = nodeManager.getNodes();
        this.mainScreen = new MainScreen(this, this.configuration);
        mainScreen.showFrame();
        logicThread = new Thread(this);
        logicThread.start();
    }

    //Methods
    public static void main(String[] args) {
        TheaterMode theaterMode = new TheaterMode();

        //List<String> ips = nodeManager.getIpsForSubnet("192.168.1");
        //nodeManager.startScan(ips);
        //nodeManager.stopScan();

        /* RECEIVE MESSAGE
        nodeManager.register(Configuration.class, (id, user) ->{
            //code when recieve message with this packet
        });

        //DEJAR DE ESCUCHAR
        nodeManager.unregister(Configuration.class);
        */

        //FOR TESTING
        //nodeManager.addNode(35,"192.168.1.35");

        //SEND MESSAGE
        /*
        nodeManager.send(ID,OBJECT WITH PACKET);
        nodeManager.send(35,theaterMode.configuration);
        */
    }

    public void addSpacecraft(TheaterMode theaterMode, int ID){
        Random rd = new Random();
        Spacecraft spacecraft = new Spacecraft(theaterMode);
        spacecraft.setNickname("Nick_"+ID);
        spacecraft.setID(ID);
        spacecraft.setReady(rd.nextBoolean());
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
                addNewMessage(blueTeam.get(i).getID(), blueTeam.get(i).getNickname(),"switched to BLUE TEAM");
                blueTeam.remove(i);
            }
        }
        if(newSpacecraft){
            addNewMessage(spacecraft.getID(), spacecraft.getNickname(),"added to RED TEAM");
        }
        spacecraft.setTeam(RED);
        redTeam.add(spacecraft);
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
        if (newSpacecraft) {
            addNewMessage(spacecraft.getID(), spacecraft.getNickname(),"added to BLUE TEAM");
        }
        spacecraft.setTeam(BLUE);
        blueTeam.add(spacecraft);
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
        //Todo see how to send to ech a diferent configuration
        //send to all the game start
        /*for (int i = 0; i < nodes.size(); i++) {
            Message message = new Message();
            message.setMessageType("START");
            message.setMessage("");
            nodeManager.send(Integer.valueOf(nodes.get(i)),message);
        }*/
        //pass viewer,arrays a juan
        //pass nodemanager
        //call GameMode de Juan
        //pass localhost IP
        sound.stop();
        System.out.println("Let the games begin");
    }


    @Override
    public void run() {
        /*
        this.nodes = nodeManager.getNodes();

        for (int i = 0; i < nodes.size(); i++) {
            Message message = new Message();
            message.setMessageType("ADMIN");
            message.setMessage(Integer.toString(myID));
            nodeManager.send(Integer.valueOf(nodes.get(i)),message);
        }*/

        for (int i = 0; i < 20; i++) {
            this.addSpacecraft(this ,i);
        }


        boolean exit = false;
        Scanner input = new Scanner(System.in);
        this.mainScreen.adminPanel.buttonPlay.setEnabled(false);
        while (!exit){
            System.out.print("Select Action: ");
            String action = input.nextLine();

            switch (action.toLowerCase()){
                case "exit":
                    exit = true;
                    break;
                case "addblue":
                    System.out.print("Insert IP to change: ");
                    action = input.nextLine();
                    for (int i = 0; i < this.redTeam.size(); i++) {
                        if(this.redTeam.get(i).getID() == Integer.parseInt(action)){
                            this.addBlueTeam(this.getRedTeam().get(i));
                        }
                    }
                    break;
                case "addred":
                    System.out.print("Insert IP to change: ");
                    action = input.nextLine();
                    for (int i = 0; i < this.blueTeam.size(); i++) {
                        if(this.blueTeam.get(i).getID() == Integer.parseInt(action)){
                            this.addRedTeam(this.getBlueTeam().get(i));
                        }
                    }
                    break;
                case "allready":
                    for (int i = 0; i < this.redTeam.size(); i++) {
                        this.redTeam.get(i).setReady(true);
                    }

                    for (int i = 0; i < this.blueTeam.size(); i++) {
                        this.blueTeam.get(i).setReady(true);
                    }
                    break;

                case "allfalse":
                    for (int i = 0; i < this.redTeam.size(); i++) {
                        this.redTeam.get(i).setReady(false);
                    }

                    for (int i = 0; i < this.blueTeam.size(); i++) {
                        this.blueTeam.get(i).setReady(false);
                    }
                    break;
                case "start":
                    this.sound.start();break;
                case "stop":
                    this.sound.stop();break;
            }
            if(this.allReady()){
                this.mainScreen.adminPanel.buttonPlay.setEnabled(true);
            }else {
                this.mainScreen.adminPanel.buttonPlay.setEnabled(false);
            }
        }
    }
}

