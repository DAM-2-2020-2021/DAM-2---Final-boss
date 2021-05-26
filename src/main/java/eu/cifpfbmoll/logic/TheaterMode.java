package eu.cifpfbmoll.logic;

import eu.cifpfbmoll.graphic.canvas.*;
import eu.cifpfbmoll.graphic.objects.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class TheaterMode extends JFrame {


    //Attributes
    private ControlPanel controlPanel;
    private ArrayList<Spacecraft> redTeam = new ArrayList<>();
    private ArrayList<Spacecraft> blueTeam = new ArrayList<>();
    private boolean isAdmin;
    public Configuration configurations;


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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

   /* //Constructor
    public TheaterMode(){
        this.controlPanel = new ControlPanel(this);

        this.createFrame();
    }*/


    //Methods
    public static void main(String[] args) {
        TheaterMode theaterMode = new TheaterMode();
        theaterMode.configurations = new Configuration();
        MainScreen mainScreen = new MainScreen(theaterMode);
        mainScreen.showFrame();




        /*theaterMode.setVisible(true);

        for (int i = 0; i < 10; i++) {
            theaterMode.addSpacecraft(theaterMode ,i);
        }

        theaterMode.showSpacecrafts();

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
                    for (int i = 0; i < theaterMode.redTeam.size(); i++) {
                        if(theaterMode.redTeam.get(i).getIP().equals(action)){
                            theaterMode.addBlueTeam(theaterMode.getRedTeam().get(i));
                        }
                    }
                    theaterMode.showSpacecrafts();
                    break;
                case "addred":
                    System.out.print("Insert IP to change: ");
                    action = input.nextLine();
                    for (int i = 0; i < theaterMode.blueTeam.size(); i++) {
                        if(theaterMode.blueTeam.get(i).getIP() == action){
                            theaterMode.addRedTeam(theaterMode.getBlueTeam().get(i));
                        }
                    }
                    theaterMode.showSpacecrafts();
                    break;
                case "allready":
                    for (int i = 0; i < theaterMode.redTeam.size(); i++) {
                        theaterMode.redTeam.get(i).setReady(true);
                    }

                    for (int i = 0; i < theaterMode.blueTeam.size(); i++) {
                        theaterMode.blueTeam.get(i).setReady(true);
                    }
                    theaterMode.showSpacecrafts();
                    break;

                case "allfalse":
                    for (int i = 0; i < theaterMode.redTeam.size(); i++) {
                        theaterMode.redTeam.get(i).setReady(false);
                    }

                    for (int i = 0; i < theaterMode.blueTeam.size(); i++) {
                        theaterMode.blueTeam.get(i).setReady(false);
                    }
                    theaterMode.showSpacecrafts();
                    break;
            }
            if(theaterMode.allReady()){
                theaterMode.controlPanel.addPlayButton();
            }else {
                theaterMode.controlPanel.hidePlayButton();
            }
        }*/
    }

    /*private void addControlPanelToPane(Container panel){
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.gridheight = 2;
        panel.add(this.controlPanel, gbc);
    }



    private void createFrame() {
        Container panel;
        Dimension screeSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.setSize(screeSize.width, screeSize.height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        panel = this.getContentPane();


        this.addControlPanelToPane(panel);

        this.setVisible(true);
    }*/

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
        //call GameMode de Juan
        System.out.println("Let the games begin");
    }


}

