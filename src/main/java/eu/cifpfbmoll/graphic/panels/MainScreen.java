package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.logic.Configuration;
import eu.cifpfbmoll.logic.TheaterMode;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame implements Runnable{
    // CONST
    private final int FRAME_MINIMUM_HEIGHT = 600, FRAME_MINIMUM_WIDTH = 600;

    //VARS
    private GameViewer gameViewer;
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
            if (gameViewer.equals(panel)) {
                cleanScreen();
                this.add(getPanelWithHud(gameViewer, HudPanel.HudType.INGAME), BorderLayout.CENTER);
            } else if (clientPanel.equals(panel)) {
                cleanScreen();
                this.add(clientPanel, BorderLayout.CENTER);
            } else if (adminPanel.equals(panel)) {
                cleanScreen();
//                this.add(adminPanel, BorderLayout.CENTER);
                this.add(getPanelWithHud(adminPanel, HudPanel.HudType.OUTGAME), BorderLayout.CENTER);
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

    /**
     * Returns a layered panel with the panel entered and the hud.
     * @param newPanel
     * @param hudType
     * @return
     */
    private JLayeredPane getPanelWithHud(CustomPanel newPanel, HudPanel.HudType hudType){
        //
        HudPanel hudPanel = new HudPanel(this, hudType);
        //
        newPanel.setBounds(0, 0, this.getWidth() - 15 , this.getHeight() - 35);
        hudPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        //
        JLayeredPane layeredPanel = new JLayeredPane();
        layeredPanel.add(newPanel, new Integer(1));
        layeredPanel.add(hudPanel, new Integer(2));
        return layeredPanel;
    }

    //<editor-fold desc="COMPONENTS">
    /**
     * All panels must be initialized here, otherwise the panel will throw error.
     */
    private void initComponents(){
        gameViewer = new GameViewer(this);
        adminPanel = new AdminPanel(this,this.theaterMode.getNodes());
        //gameViewer = new GameViewer(this);  // Default game viewer (pink background)
        startPanel = new StartPanel(this);
        clientPanel = new ClientPanel(this);
        optionsPanel = new OptionsPanel(this,this.configuration);
    }

    private void addInitComponents(){
        // Add components
        this.add(startPanel, BorderLayout.CENTER);
    }
    //</editor-fold>

    //<editor-fold desc="FRAME">
    private void initFrame() {
        // Add frame config and show
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(FRAME_MINIMUM_WIDTH, FRAME_MINIMUM_HEIGHT));
        this.revalidate();
    }

    public void showFrame(){
        this.setVisible(true);
    }

    private void setFrame() {
        // Add frame config and show
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        revalidate();
    }

    public void hideFrame(){
        this.setVisible(false);
    }
    //</editor-fold>

    private void cleanScreen(){
        this.getContentPane().removeAll();
    }

    private void initScreen(){
        initComponents();       // First initialize the components (JPanels)
        initFrame();     // Setup and show the frame
        addInitComponents();    // Add the initial component (adminPanel)
        new Thread(this).start();   // Start the thread
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

    public GameViewer getGameViewer() {
        return gameViewer;
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
    //</editor-fold>

    /**
     *
     */
    @Override
    public void run() {
        while(true){
            this.validate();
            this.repaint();
        }
    }
}