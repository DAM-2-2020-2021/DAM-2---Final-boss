package eu.cifpfbmoll.graphics.customComponent;

import eu.cifpfbmoll.graphics.panels.GraphicStyle;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScreenComponent extends JLabel implements MouseListener {
    // CONST
    private final Color COLOR_SELECTED = GraphicStyle.HELPER_COLOR;
    private final Color COLOR_NOT_SELECTED = GraphicStyle.PRIMARY_COLOR;
    private final Color COLOR_HOVER_SELECTED = GraphicStyle.DANGER_COLOR;
    private final Color COLOR_HOVER_NOT_SELECTED = GraphicStyle.SECONDARY_COLOR;
    private final Color COLOR_BORDER = GraphicStyle.WHITE_COLOR;

    private final String TEXT_NOT_SELECTED = "Esta pantalla no está seleccionada";
    private final String TEXT_UNSELECT = "Quitar esta pantalla";

    // VARS
    private boolean selected;

    public ScreenComponent(){
        // Set the properties
        setDefaultProperties();
        // Add the mouse listeners
        addMouseListener(this);
    }

    public ScreenComponent(int id, String ip){
        // Set the properties
        setDefaultProperties();
        // Add the mouse listeners
        addMouseListener(this);
        // Add the text to the label
        showInfo(id, ip);
    }

    private void select(){
        if (selected){
            this.setSelected(false);
            this.setBackground(COLOR_NOT_SELECTED);
            deleteInfo();
        }else{
            this.setSelected(true);
            this.setBackground(COLOR_SELECTED);
            showInfo(0, "192.168.1.1");
        }
    }

    private void showInfo(int id, String ip){
        // The info must be wrapped in html in order to get a line break
        this.setText("<html><body>Screen: " + id + "<br>IP: " + ip + "</body></html>");
    }

    private void showInfo(String message){
        this.setText(message);
    }

    private void deleteInfo(){
        this.setText(null);
    }

    private void setDefaultProperties(){
        // Set a border
        this.setBorder(new LineBorder(COLOR_BORDER, 5, false));
        // Set visible background
        this.setOpaque(true);
        // By default the component is not selected and therefore gray colored
        this.setBackground(COLOR_NOT_SELECTED);
        this.setSelected(false);
        // Set font style and color
        this.setFont(GraphicStyle.ANY_LOG_FONT);
        this.setForeground(Color.white);
        // Set the text horizontally and vertically centered
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
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
        //
        if (selected){
            this.setBackground(COLOR_HOVER_SELECTED);
            showInfo(TEXT_UNSELECT);
        }else{
            this.setBackground(COLOR_HOVER_NOT_SELECTED);
            showInfo(TEXT_NOT_SELECTED);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
        if (selected){
            this.setBackground(COLOR_SELECTED);
            showInfo(0, "192.168.1.1");
        }else{
            this.setBackground(COLOR_NOT_SELECTED);
            deleteInfo();
        }
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS">
    public boolean isSelected(){
        return this.selected;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setSelected(boolean selected){
        this.selected = selected;
    }
    //</editor-fold>
}
