package eu.cifpfbmoll.graphics.customComponent;

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
            this.setSelected(false);
            this.setColor(COLOR_NOT_SELECTED);
        }else{
            this.setSelected(true);
            this.setColor(COLOR_SELECTED);
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
    }

    private void updateComponent(){
        this.repaint();
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
