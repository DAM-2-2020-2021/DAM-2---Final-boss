package eu.cifpfbmoll.graphic.objects;

import eu.cifpfbmoll.logic.TheaterMode;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spacecraft extends MannedVisualObject{

    private String nickname;
    private int ID;
    private String team = "RED";
    private Boolean ready = false;
    private int spacecraftType = 0;
    public final int TYPE_O = 0;
    public final int TYPE_1 = 1;
    public final int TYPE_2 = 2;
    private BufferedImage img;
    private BufferedImage imm2;

    //

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public Spacecraft(TheaterMode theaterMode) {
        super(theaterMode);
    }

    public int getSpacecraftType() {
        return spacecraftType;
    }

    public void setSpacecraftType(int spacecraftType) {
        this.spacecraftType = spacecraftType;
    }

    public void paint(Graphics g){

    }
}
