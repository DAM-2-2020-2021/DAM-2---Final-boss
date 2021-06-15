package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.graphic.component.AnimatedComponent;
import eu.cifpfbmoll.logic.Configuration;
import eu.cifpfbmoll.logic.TheaterMode;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame implements Runnable{
    // CONST

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
     * @param newPanel
     */
    public void changeScreen(PanelType oldPanel, PanelType newPanel){
        try{
            HudPanel.HudType hudType;
            if (newPanel == PanelType.GAME){
                hudType = HudPanel.HudType.INGAME;
            }else{
                hudType = HudPanel.HudType.OUTGAME;
            }
            // Add the provided panel
            this.add(getPanelWithHud(getNewPanel(newPanel), hudType), BorderLayout.CENTER);
            // Null the old panel
            nullPanel(oldPanel);
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
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        int taskBarHeight = scrnSize.height - winSize.height - 1;

        //
        HudPanel hudPanel = new HudPanel(this, hudType, "23", "192.168.1.32");
        //
        newPanel.setBounds(0, 0, this.getWidth() , this.getHeight() - taskBarHeight);
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

    }

    private void addInitComponents(){
        // Add components
        cleanScreen();
//        this.add(startPanel, BorderLayout.CENTER);
        changeScreen(null, PanelType.START);
    }
    //</editor-fold>

    //<editor-fold desc="FRAME">
    private void initFrame() {
        // Add frame config and show
        this.setName("DAMN: The Official Game");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setExtendedState( this.getExtendedState()|JFrame.MAXIMIZED_BOTH );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        //this.setResizable(false);
        this.revalidate();
        this.pack();
    }

    public void showFrame(){
        this.setVisible(true);
    }

    public void hideFrame(){
        this.setVisible(false);
    }
    //</editor-fold>

    private CustomPanel getNewPanel(PanelType newPanel){
        CustomPanel panel = null;
        // Clean the screen of any panel
        cleanScreen();
        // Init the panel
        if (newPanel == PanelType.START){
            panel = new StartPanel(this);
        }else if (newPanel == PanelType.ADMIN){
            panel = new AdminPanel(this, this.theaterMode.getNodes());
        }else if (newPanel == PanelType.CLIENT){
            panel = new ClientPanel(this);
        }else if (newPanel == PanelType.OPTIONS){
            panel = new OptionsPanel(this, this.configuration);
        }else if (newPanel == PanelType.GAME){
            panel = new GameViewer(this);
        }

        return panel;
    }

    private void nullPanel(PanelType oldPanel){
        // Init the panel
        if (oldPanel == PanelType.START){
            startPanel = null;
        }else if (oldPanel == PanelType.ADMIN){
            adminPanel = null;
        }else if (oldPanel == PanelType.CLIENT){
            clientPanel = null;
        }else if (oldPanel == PanelType.OPTIONS){
            optionsPanel = null;
        }else if (oldPanel == PanelType.GAME){
            gameViewer = null;
        }
    }

    private void cleanScreen(){
        this.getContentPane().removeAll();
    }

    private void initScreen(){
        initComponents();       // First initialize the components (JPanels)
        initFrame();     // Setup and show the frame
        this.showFrame();
        addInitComponents();    // Add the initial component (adminPanel)
        new Thread(this).start();   // Start the thread
    }

    //<editor-fold desc="GETTERS">
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

    public enum PanelType{
        START,
        ADMIN,
        CLIENT,
        OPTIONS,
        GAME;
    }
}