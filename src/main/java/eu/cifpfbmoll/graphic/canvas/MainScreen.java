package eu.cifpfbmoll.graphic.canvas;

import eu.cifpfbmoll.logic.Configuration;
import eu.cifpfbmoll.logic.TheaterMode;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame implements Runnable{
    // CONST
    private final int FRAME_MINIMUM_HEIGHT = 600, FRAME_MINIMUM_WIDTH = 600;

    //VARS
    private StartPanel startPanel;
    public AdminPanel adminPanel;
    private ClientPanel clientPanel;
    private OptionsPanel optionsPanel;
    private PlayersPanel playersPanel;
    public TheaterMode theaterMode;
    public Configuration configuration;

    public MainScreen(TheaterMode theaterMode, Configuration configuration){
        this.theaterMode = theaterMode;
        this.configuration = configuration;
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
                    //cleanScreen();
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
                case "PLAYERS_PANEL":
                    cleanScreen();
                    this.add(playersPanel, BorderLayout.CENTER);
                    break;

                default:
                    throw new Exception("There is no screen called " + newScreenName);   // The screen Name does not exist. Throws error
            }
        }catch(Exception e){
            System.out.println("e " + e.getMessage());
        }
    }

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

    private void cleanScreen(){
        this.getContentPane().removeAll();
    }

    public void showFrame(){
        this.setVisible(true);
    }

    public void hideFrame(){
        this.setVisible(false);
    }

    private void initComponents(){
        adminPanel = new AdminPanel(this,this.theaterMode.getNodes());
        playersPanel = new PlayersPanel(this, this.theaterMode.getBlueTeam(), this.theaterMode.getRedTeam());
        //gameViewer = new GameViewer(this);  // Default game viewer (pink background)
        startPanel = new StartPanel(this);
        clientPanel = new ClientPanel(this);
        optionsPanel = new OptionsPanel(this,this.configuration);
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
            this.repaint();
        }
    }
}