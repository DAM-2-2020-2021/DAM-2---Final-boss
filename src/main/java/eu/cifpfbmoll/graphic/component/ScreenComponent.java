package eu.cifpfbmoll.graphic.component;

import eu.cifpfbmoll.graphic.canvas.AdminPanel;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScreenComponent extends JComponent implements MouseListener {
    // CONST
    private Color COLOR_SELECTED = Color.RED;
    private Color COLOR_NOT_SELECTED = Color.GRAY;
    // VARS
    private Color backgroundColor;
    private Color borderColor;
    private boolean selected;
    private String myNode = "";

    public ScreenComponent(){
        // Set a border
        this.setBorder(new LineBorder(Color.ORANGE, 5, true));
        // By default the component is not selected and therefore gray colored
        this.setColor(COLOR_NOT_SELECTED);
        this.setSelected(false);
        // Add the mouse listeners
        addMouseListener(this);

    }

    private void select(){
        if (selected){
            Sound.soundInteractueMenu();
            this.setSelected(false);
            this.setColor(COLOR_NOT_SELECTED);
            AdminPanel.getNodesToString().add(this.myNode);
            this.myNode = "";
            AdminPanel.setSelectedNode("Select a screen");
            AdminPanel.updateDropDown();
        }else{
            if(!AdminPanel.getSelectedNode().equals("Select a screen")){
                Sound.soundInteractueMenu();
                this.setSelected(true);
                this.setColor(COLOR_SELECTED);
                this.myNode = AdminPanel.getSelectedNode();
                deleteSelectedNode();
                AdminPanel.setSelectedNode("Select a screen");
                AdminPanel.updateDropDown();
            } else {
                //sound.error()
            }
        }
        this.updateComponent();
    }

    //<editor-fold desc="MOUSE EVENTS">
    @Override
    public void mouseClicked(MouseEvent e) {
        // Select the component
        select();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //</editor-fold>

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.WHITE);
        g.drawString(myNode, 25, 25);
    }

    private void updateComponent(){
        this.repaint();
    }

    private void deleteSelectedNode(){
        for (int i = 0; i < AdminPanel.getNodesToString().size(); i++) {
            if(AdminPanel.getNodesToString().get(i).equals(this.myNode)){
                AdminPanel.getNodesToString().remove(i);
            }
        }
    }

    //<editor-fold desc="GETTERS">
    public boolean isSelected(){
        return this.selected;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public void setColor(Color color){
        this.backgroundColor = color;
    }

    public void setBorderColor(Color color){
        this.borderColor = color;
    }
//</editor-fold>
}