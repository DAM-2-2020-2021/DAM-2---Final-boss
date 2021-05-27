package eu.cifpfbmoll.graphics.canvas;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame implements Runnable{
    // CONST

    //VARS
    private StartPanel startPanel;
    private AdminPanel adminPanel;
    private ClientPanel clientPanel;
    private OptionsPanel optionsPanel;

    public MainScreen(){
        initComponents();       // First initialize the components (JPanels)
        setFrame();     // Setup and show the frame
        addInitComponents();    // Add the initial component (adminPanel)
        new Thread(this).start();   // Start the thread
    }

    /**
     * Changes the screen JPanel to the one inserted.
     * The param screenName is temporary. It will be a convenient interface class.
     * @param newScreenName
     */
    public void changeScreen(String newScreenName){
        try{
            switch(newScreenName){
                case "GAME":
                    cleanScreen();
                    //this.add(gameViewer, BorderLayout.CENTER);
                    break;
                case "CLIENT_PANEL":
                    cleanScreen();
                    this.add(clientPanel, BorderLayout.CENTER);
                    break;
                case "ADMIN_PANEL":
                    cleanScreen();
                    this.add(adminPanel, BorderLayout.CENTER);
                    break;
                case "OPTIONS_PANEL":
                    cleanScreen();
                    this.add(optionsPanel, BorderLayout.CENTER);
                    break;
                default:
                    throw new Exception("There is no screen called " + newScreenName);   // The screen Name does not exist. Throws error
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void cleanScreen(){
        this.remove(adminPanel);
        //this.remove(gameViewer);
        this.remove(clientPanel);
        this.remove(startPanel);
        this.remove(optionsPanel);
    }

    public void showFrame(){
        this.setVisible(true);
    }

    public void hideFrame(){
        this.setVisible(false);
    }

    private void initComponents(){
        adminPanel = new AdminPanel(this);
        //gameViewer = new GameViewer(this);  // Default game viewer (pink background)
        startPanel = new StartPanel(this);
        clientPanel = new ClientPanel(this);
        optionsPanel = new OptionsPanel(this);
    }

    private void setFrame() {
        // Add frame config and show
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        revalidate();
    }

    private void addInitComponents(){
        // Add components
        this.add(startPanel, BorderLayout.CENTER);
    }

    @Override
    public void run() {
        while(true){
            this.validate();
            this.repaint();     // CHANGED
        }
    }
}