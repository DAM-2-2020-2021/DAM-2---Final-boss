package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.logic.Configuration;
import eu.cifpfbmoll.logic.TheaterMode;
import eu.cifpfbmoll.graphic.component.AnimatedComponent;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame implements Runnable{
    // CONST

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
            HudPanel.HudType hudType;
            hudType = HudPanel.HudType.OUTGAME;
            // Clean the screen of any panel
            cleanScreen();
            // Add the provided panel
            this.add(getPanelWithHud(panel, hudType), BorderLayout.CENTER);
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
        HudPanel hudPanel = new HudPanel(this, hudType);
        //
        newPanel.setBounds(0, 0, this.getWidth() , this.getHeight() - taskBarHeight);
        hudPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        //
        JLayeredPane layeredPanel = new JLayeredPane();
        layeredPanel.add(newPanel, new Integer(1));
        layeredPanel.add(hudPanel, new Integer(2));
        return layeredPanel;
    }


    private void initComponents(){
        adminPanel = new AdminPanel(this,this.theaterMode.getPcList());
        startPanel = new StartPanel(this);
        clientPanel = new ClientPanel(this);
        optionsPanel = new OptionsPanel(this,this.configuration);
    }

    private void addInitComponents(){
        // Add components
        cleanScreen();
        this.add(getPanelWithHud(startPanel, HudPanel.HudType.OUTGAME), BorderLayout.CENTER);
        //this.add(startPanel, BorderLayout.CENTER);
    }

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

    private void cleanScreen(){
        this.getContentPane().removeAll();
    }

    public void showFrame(){
        this.setVisible(true);
    }

    public void hideFrame(){
        this.setVisible(false);
    }

    private void initScreen(){
        initComponents();       // First initialize the components (JPanels)
        initFrame();     // Setup and show the frame
        this.showFrame();
        addInitComponents();    // Add the initial component (adminPanel)
        new Thread(this).start();   // Start the thread
    }

    private void setFrame() {
        // Add frame config and show
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        revalidate();
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