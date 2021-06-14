package eu.cifpfbmoll.graphic.component;

import eu.cifpfbmoll.graphic.panels.AdminPanel;
import eu.cifpfbmoll.graphic.panels.GraphicStyle;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ScreenComponent extends JLabel implements MouseListener {
    // CONST
    private final Color COLOR_SCREEN_SELECTED = GraphicStyle.HELPER_COLOR;
    private final Color COLOR_SCREEN_NOT_SELECTED = GraphicStyle.PRIMARY_COLOR;
    private final Color COLOR_HOVER_SELECTED = GraphicStyle.DANGER_COLOR;
    private final Color COLOR_HOVER_NOT_SELECTED = GraphicStyle.SECONDARY_COLOR;
    private final Color COLOR_BORDER = GraphicStyle.GLOW_BLUE_COLOR;
    private final Color COLOR_SCREEN_AND_CLIENT_SELECTED = Color.YELLOW;

    private final String SCREEN_SELECTED_HOVER_CLIENT_SELECTED = "Remove screen";
    private final String SCREEN_HOVER_CLIENT_SELECTED = "Elegir esta pantalla para el cliente";
    private final String SCREEN_SELECTED_HOVER_CLIENT_NOT_SELECTED = "Unselect this screen";
    private final String SCREEN_HOVER_CLIENT_NOT_SELECTED = "Select this screen";

    private final String SCREEN_SELECTED_CLIENT_UNSELECTED = "Selected screen";

    // VARS
    private AdminPanel adminPanel;
    private int type;   // 0 -> screen, 1-> client
    private int id;
    private String ip;
    private Color currentColor;
    private boolean selected;           // only for screens
    private boolean hasClient = false;  // only for screens
    private String info;

    /**
     * Constructor for a screen type.
     * @param adminPanel
     */
    public ScreenComponent(AdminPanel adminPanel){
        //this.mainScreen = mainScreen;
        this.adminPanel = adminPanel;
        // Set the properties
        setDefaultProperties();
        // Add the mouse listeners
        addMouseListener(this);
        //
        this.type = 0;
        //
        this.id = -1;
        this.ip = null;
    }

    /**
     * Constructor for a client type.
     * @param adminPanel
     * @param id
     * @param ip
     */
    public ScreenComponent(AdminPanel adminPanel, int id, String ip){
        this.adminPanel = adminPanel;
        // Set the properties
        setDefaultProperties();
        // Add the mouse listeners
        addMouseListener(this);
        this.id = id;
        this.ip = ip;
        //
        this.type = 1;
        //
        currentColor = COLOR_SCREEN_NOT_SELECTED;
        // Add the text to the label
        info = "<html><body>Node -> ID: " + id + "<br>IP: " + ip + "</body></html>";
        //
        updateClientState();
    }

    //<editor-fold desc="SCREEN">
    private void selectScreen(){
        if (selected){
            Sound.notice();
            disableScreen(this);
        }else{
            Sound.soundInteractueMenu();
            enableScreen(this);
        }
    }

    private void enableScreen(ScreenComponent currentScreen){
        // Check if there is another client component selected
        for (ScreenComponent screen: adminPanel.getScreenComponentList()){
            if (screen.selected && !screen.hasClient && screen != currentScreen){
                disableScreen(screen);
            }
        }
        currentScreen.setSelected(true);
        currentScreen.updateScreenState();
    }

    private void disableScreen(ScreenComponent currentScreen){
        // If it has a client delete it
        ScreenComponent screenComponent = new ScreenComponent(this.adminPanel, this.id, this.ip);
        if(this.id != -1){
            adminPanel.getClientPanel().add(screenComponent);
        }

        this.id = -1;
        this.ip = null;
        // Unselect
        currentScreen.setSelected(false);
        // Remove the client
        currentScreen.hasClient = false;
        // Update the screen


        currentScreen.updateScreenState();
    }

    private void hoverScreen(ScreenComponent currentScreen){
        String hoverInfo;
        if (selected){
            currentScreen.setBackground(COLOR_HOVER_SELECTED);
            if (currentScreen.hasClient){
                hoverInfo = SCREEN_SELECTED_HOVER_CLIENT_SELECTED;
            }else{
                hoverInfo = SCREEN_SELECTED_HOVER_CLIENT_NOT_SELECTED;
            }
        }else{
            currentScreen.setBackground(COLOR_HOVER_NOT_SELECTED);
            if (currentScreen.hasClient){
                hoverInfo = SCREEN_HOVER_CLIENT_SELECTED;
            }else{
                hoverInfo = SCREEN_HOVER_CLIENT_NOT_SELECTED;
            }
        }
        currentScreen.setText(hoverInfo);
    }

    /**
     * Updates the components (Color and font) of the screen.
     */
    private void updateScreenState(){
        this.setForeground(Color.WHITE);
        if (selected){
            if (hasClient){
                currentColor = COLOR_SCREEN_AND_CLIENT_SELECTED;
                this.setForeground(Color.BLACK);
                //info = "ID: " + id + "\n IP: " + ip;
                info = "<html><body>Node -> ID: " + id + "<br>IP: " + ip + "</body></html>";
            }else{
                currentColor = COLOR_SCREEN_SELECTED;
                info = SCREEN_SELECTED_CLIENT_UNSELECTED;
            }
        }else{
            currentColor = COLOR_SCREEN_NOT_SELECTED;
            info = null;
        }
        setBackground(currentColor);
        this.setText(info);
    }
    //</editor-fold>

    //<editor-fold desc="CLIENT">
    private void selectClient(){
        // Check if there is a screen selected
        boolean found = false;
        int i = 0;
        while(!found){
            ScreenComponent screen = adminPanel.getScreenComponentList().get(i);
            if (screen.selected && !screen.hasClient){
                Sound.soundInteractueMenu();
                // Then the client moves to the screen
                screen.setId(this.id);
                screen.setIp(this.ip);
                // The client is deleted from the list
                adminPanel.getClientComponentList().remove(this);
                adminPanel.getClientPanel().remove(this);
                screen.hasClient = true;
                // Set the data
                found = true;
            }else if (i == adminPanel.getScreenComponentList().size() - 1){
                Sound.soundInteractueMenu();
                found = true;
            }
            i++;
            // Update the screen
            screen.updateScreenState();
        }
    }

    private void hoverClient(){
        currentColor = COLOR_HOVER_NOT_SELECTED;
        //info = "Seleccionar este cliente";
        updateClientState();
    }

    private void unhoverClient(){
        currentColor = COLOR_SCREEN_NOT_SELECTED;
        info = "<html><body>Node -> ID: " + id + "<br>IP: " + ip + "</body></html>";
        updateClientState();
    }

    private void updateClientState(){
        setBackground(currentColor);
        this.setText(info);
    }
    //</editor-fold>

    private void setDefaultProperties(){
        // Set a border
        this.setBorder(new LineBorder(COLOR_BORDER, 5, false));
        // Set visible background
        this.setOpaque(true);
        // By default the component is not selected and therefore gray colored
        this.setBackground(COLOR_SCREEN_NOT_SELECTED);
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
        switch (type){
            case 0:
                selectScreen();
                break;
            case 1:
                selectClient();
                break;
        }
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
        switch(type){
            case 0:
                hoverScreen(this);
                break;
            case 1:
                hoverClient();
                break;
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
        switch(type){
            case 0:
                updateScreenState();
                break;
            case 1:
                unhoverClient();
                break;
        }
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS">
    public boolean isSelected(){
        return this.selected;
    }

    public int getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public int getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public boolean hasClient() {
        return hasClient;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    //</editor-fold>
}