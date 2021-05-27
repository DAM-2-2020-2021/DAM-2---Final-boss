package eu.cifpfbmoll.graphics.window;

import eu.cifpfbmoll.graphics.panels.*;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame implements Runnable{
    // CONST
    private final int FRAME_MINIMUM_HEIGHT = 600, FRAME_MINIMUM_WIDTH = 600;

    //VARS
    private StartPanel startPanel;
    private AdminPanel adminPanel;
    private ClientPanel clientPanel;
    private OptionsPanel optionsPanel;
    private GameViewer gameViewer;

    public MainScreen(){
        initScreen();
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
                    this.add(gameViewer, BorderLayout.CENTER);
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

    //<editor-fold desc="INITS">
    private void initScreen(){
        initComponents();       // First initialize the components (JPanels)
        initFrame();     // Setup and show the frame
        addInitComponents();    // Add the initial component (adminPanel)
        new Thread(this).start();   // Start the thread
    }

    private void initFrame() {
        // Add frame config and show
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
        this.revalidate();
    }

    private void initComponents(){
        adminPanel = new AdminPanel(this);
        gameViewer = new GameViewer(this);  // Default game viewer (pink background)
        startPanel = new StartPanel(this);
        clientPanel = new ClientPanel(this);
        optionsPanel = new OptionsPanel(this);
    }

    private void addInitComponents(){
        // Add initial and necessary components
        this.add(startPanel, BorderLayout.CENTER);
    }
    //</editor-fold>

    private void cleanScreen(){
        this.getContentPane().removeAll();
    }

    public void showFrame(){
        this.setVisible(true);
    }

    public void hideFrame(){
        this.setVisible(false);
    }

    @Override
    public void run() {
        while(true){
            this.validate();
            this.repaint();
        }
    }
}