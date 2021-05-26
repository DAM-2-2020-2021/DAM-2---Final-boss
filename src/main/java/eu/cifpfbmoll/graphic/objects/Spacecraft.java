package eu.cifpfbmoll.graphic.objects;

import eu.cifpfbmoll.logic.TheaterMode;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spacecraft extends MannedVisualObject{

    private String nickname;
    private String IP;
    private String team;
    private Boolean ready;
    private int spacecraftType;
    public final int TYPE_O = 0;
    public final int TYPE_1 = 1;
    public final int TYPE_2 = 2;
    private BufferedImage img;
    private BufferedImage imm2;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public Spacecraft(TheaterMode theaterMode) {
        super(theaterMode);
    }



    public void paint(Graphics g){

    }
}
