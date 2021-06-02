package eu.cifpfbmoll.logic;

import eu.cifpfbmoll.graphic.canvas.*;
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
    public Configuration configuration;
    private MainScreen mainScreen;
    private Thread logicThread;
    private Clip sound;
    private Map<Integer, String> nodes;
    private String localhostIP;


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

    public TheaterMode(){
        this.configuration = new Configuration();
        this.sound = Sound.clipSoundMenu();
        this.sound.start();
   /*Para ver las interfaces
        try {
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
        NodeManager nodeManager = new NodeManager(ip);
        String subnet = nodeManager.getSubnet(ip);
        List<String> ips = nodeManager.getIpsForSubnet(subnet);
        nodeManager.startScan(ips);

        try{
            Thread.sleep(6000);
        }catch (Exception e){}

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

    public void addSpacecraft(TheaterMode theaterMode, int IP){
        Random rd = new Random();
        Spacecraft spacecraft = new Spacecraft(theaterMode);
        spacecraft.setIP(Integer.toString(IP));
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
        for (int i = 0; i < blueTeam.size(); i++) {
            if(blueTeam.get(i).getIP() == spacecraft.getIP()){
                blueTeam.remove(i);
            }
        }
        spacecraft.setTeam("Red");
        redTeam.add(spacecraft);
    }

    public void addBlueTeam(Spacecraft spacecraft){
        for (int i = 0; i < redTeam.size(); i++) {
            if(redTeam.get(i).getIP() == spacecraft.getIP()){
                redTeam.remove(i);
            }
        }
        spacecraft.setTeam("Blue");
        blueTeam.add(spacecraft);
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

    public void showSpacecrafts(){

        for (int i = 0; i < redTeam.size(); i++) {
            System.out.println(redTeam.get(i).getTeam());
            System.out.println(redTeam.get(i).getReady());
            System.out.println(redTeam.get(i).getIP());
            System.out.println();
        }

        System.out.println("//////////////////////////////////////");

        for (int i = 0; i < blueTeam.size(); i++) {
            System.out.println(blueTeam.get(i).getTeam());
            System.out.println(blueTeam.get(i).getReady());
            System.out.println(blueTeam.get(i).getIP());
            System.out.println();
        }
    }

    public void startGame(){
        //pass viewer,arrays a juan
        //pass nodemanager
        //call GameMode de Juan
        //pass localhost IP
        sound.stop();
        System.out.println("Let the games begin");
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            this.addSpacecraft(this ,i);
        }

        this.showSpacecrafts();

        boolean exit = false;
        Scanner input = new Scanner(System.in);
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
                        if(this.redTeam.get(i).getIP().equals(action)){
                            this.addBlueTeam(this.getRedTeam().get(i));
                        }
                    }
                    showSpacecrafts();
                    break;
                case "addred":
                    System.out.print("Insert IP to change: ");
                    action = input.nextLine();
                    for (int i = 0; i < this.blueTeam.size(); i++) {
                        if(this.blueTeam.get(i).getIP().equals(action)){
                            this.addRedTeam(this.getBlueTeam().get(i));
                        }
                    }
                    showSpacecrafts();
                    break;
                case "allready":
                    for (int i = 0; i < this.redTeam.size(); i++) {
                        this.redTeam.get(i).setReady(true);
                    }

                    for (int i = 0; i < this.blueTeam.size(); i++) {
                        this.blueTeam.get(i).setReady(true);
                    }
                    showSpacecrafts();
                    break;

                case "allfalse":
                    for (int i = 0; i < this.redTeam.size(); i++) {
                        this.redTeam.get(i).setReady(false);
                    }

                    for (int i = 0; i < this.blueTeam.size(); i++) {
                        this.blueTeam.get(i).setReady(false);
                    }
                    showSpacecrafts();
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

