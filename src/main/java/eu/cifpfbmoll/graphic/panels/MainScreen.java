package eu.cifpfbmoll.graphic.panels;

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
     * @param panel
     */
    public void changeScreen(CustomPanel panel){
        try{
            /*if (gameViewer.equals(panel)) {
                cleanScreen();
                this.add(gameViewer, BorderLayout.CENTER);
            } else */
            if (clientPanel.equals(panel)) {
                cleanScreen();
                this.add(clientPanel, BorderLayout.CENTER);
            } else if (adminPanel.equals(panel)) {
                cleanScreen();
                this.add(adminPanel, BorderLayout.CENTER);
            } else if (optionsPanel.equals(panel)) {
                cleanScreen();
                this.add(optionsPanel, BorderLayout.CENTER);
            } else {
                throw new Exception("There is no screen panel " + panel);   // The screen panel does not exist. Throws error
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
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
        adminPanel = new AdminPanel(this,this.theaterMode.getPcList());
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

    //<editor-fold desc="GETTERS">
    public int getFRAME_MINIMUM_HEIGHT() {
        return FRAME_MINIMUM_HEIGHT;
    }

    public int getFRAME_MINIMUM_WIDTH() {
        return FRAME_MINIMUM_WIDTH;
    }

    public StartPanel getStartPanel() {
        return startPanel;
    }

    public AdminPanel getAdminPanel() {
        return adminPanel;
    }

    public ClientPanel getClientPanel() {
        return clientPanel;
    }

    public OptionsPanel getOptionsPanel() {
        return optionsPanel;
    }


    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setStartPanel(StartPanel startPanel) {
        this.startPanel = startPanel;
    }

    public void setAdminPanel(AdminPanel adminPanel) {
        this.adminPanel = adminPanel;
    }

    public void setClientPanel(ClientPanel clientPanel) {
        this.clientPanel = clientPanel;
    }

    public void setOptionsPanel(OptionsPanel optionsPanel) {
        this.optionsPanel = optionsPanel;
    }


    @Override
    public void run() {
        while(true){
            this.validate();
            this.repaint();
        }
    }
}