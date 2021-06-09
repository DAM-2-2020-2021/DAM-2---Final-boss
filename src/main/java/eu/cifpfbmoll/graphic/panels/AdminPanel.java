package eu.cifpfbmoll.graphic.panels;


import eu.cifpfbmoll.graphic.component.CustomButton;
import eu.cifpfbmoll.graphic.component.ScreenComponent;
import eu.cifpfbmoll.graphic.objects.Spacecraft;
import eu.cifpfbmoll.graphic.sprite.Sprite;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminPanel extends CustomPanel implements Runnable{
    // CONST
    private final int PANEL_ROWS = 2, PANEL_COLUMN = 1;
    private final LayoutManager mainLayout = new GridLayout(PANEL_ROWS, PANEL_COLUMN);
    private final String PLAY_TEXT = "PLAY", OPTIONS_TEXT = "OPTIONS", PLAYERS_TEXT = "PLAYERS",
            ADD_ROW_TEXT = "ADD ROW", RMV_ROW_TEXT = "DELETE ROW",
            ADD_COL_TEXT = "ADD COLUMN", RMV_COL_TEXT = "DELETE COLUMN";
    private final int SCREEN_ROWS_DEFAULT = 3, SCREEN_COLUMNS_DEFAULT = 3;
    private final int UPDATE_TEAMS_LOGS_TIME_MILIS = 1500;

    // VARS
    private List<ScreenComponent> clientComponentList = new ArrayList<>();  // Contains the clients components (ScreenComponent).
    private List<ScreenComponent> screenComponentList = new ArrayList<>();  // Contains the screens components (ScreenComponent). The selected and not selected.
    // Components
    // Buttons
    public CustomButton buttonPlay;
    private CustomButton buttonOptions;
    private CustomButton buttonPlayers;

    // Panels
    private JPanel screenPanel;
    private JPanel screenSelectionPanel;
    private JPanel clientPanel;
    private JTabbedPane screenDetailsPanel;
    private JPanel screenControlPanel, buttonsPanel, bottomPanel, redTeamPanel, blueTeamPanel;  // Can be converted to local
    private JScrollPane logPanel, logRedTeamPanel, logBlueTeamPanel;                            // Can be converted to local

    // Text area
    private JTextArea textLog, textLogRedTeam, textLogBlueTeam;

    // Test
    private int screenSelectionRows = SCREEN_ROWS_DEFAULT;
    private int screenSelectionColumns = SCREEN_COLUMNS_DEFAULT;
    private final int SUBPANEL_PADDING_HEIGHT = 20, SUBPANEL_PADDING_WIDTH = 20;
    private final String TEAM_RED_TEXT = "RED TEAM", TEAM_BLUE_TEXT = "BLUE TEAM", LOG_TEXT = "Historial del log";
    private Map<Integer, String> nodes;
    private BufferedImage backgroundImage = Sprite.getMenuBg();

    public AdminPanel(MainScreen mainScreen, Map<Integer, String> nodes){
        super(mainScreen);
        this.nodes = nodes;
        initPanel();    // Init the panel
    }

    //<editor-fold desc="GET AND ADD PANELS">
    private void addTopPanel(){
        // Create parent panel
        JPanel topPanel = new JPanel(new GridLayout(1,2));
        topPanel.setOpaque(false);

        // Add left panel - log panel
        topPanel.add(getLogPanel());

        // Add right panel - teams log panel
        topPanel.add(getTeamsLogPanel());

        this.add(topPanel);
    }

    private void addBottomPanel(){
        // Create parent panel
        bottomPanel = new JPanel(new GridLayout(1,2));
        bottomPanel.setOpaque(false);

        // Add left panel - screen panel
        bottomPanel.add(getScreenSelectionPanel(screenSelectionRows, screenSelectionColumns));
        // Add right panel - screen config and buttons
        JPanel rightPanel = new JPanel(new GridLayout(1, 2));
        rightPanel.setOpaque(false);
        rightPanel.add(getScreenDetailsPanel());
        rightPanel.add(getButtonsPanel());
        bottomPanel.add(rightPanel);

        this.add(bottomPanel);
    }

    private JPanel getButtonsPanel() {
        // Create a new nested panel
        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        int padding = 20;
        buttonsPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        buttonsPanel.setLayout(new GridLayout(3, 0));

        // Create the buttons
        buttonPlay = new CustomButton(CustomButton.CustomButtonType.PRIMARY, PLAY_TEXT);
        buttonOptions = new CustomButton(CustomButton.CustomButtonType.SECONDARY, OPTIONS_TEXT);
        // Add action listeners to the buttons
        buttonPlay.addActionListener(this);
        buttonOptions.addActionListener(this);
        // Add the buttons to the panel
        buttonsPanel.add(buttonPlay);
        buttonsPanel.add(buttonOptions);
        //buttonsPanel.setBackground(Color.black);

        return buttonsPanel;
    }

    private JPanel getTeamsLogPanel(){
        JPanel parentPanel = new JPanel(new GridLayout(1,2));
        int padding = 20;
        parentPanel.setBorder(new EmptyBorder(padding, 0, padding, 0));
        parentPanel.setOpaque(false);

        //<editor-fold desc="RED TEAM">
        // Panel
        redTeamPanel = new JPanel();
        redTeamPanel.setLayout(new BoxLayout(redTeamPanel, BoxLayout.Y_AXIS));
        redTeamPanel.setBackground(GraphicStyle.TEAM_RED_PANEL_COLOR);

        // Label
        JLabel redTeamLabel = new JLabel(TEAM_RED_TEXT);
        redTeamLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        redTeamLabel.setFont(GraphicStyle.TITLE_FONT);
        redTeamLabel.setForeground(Color.WHITE);
        redTeamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        redTeamPanel.add(redTeamLabel);

        // TextPanel
        textLogRedTeam = new JTextArea();
        textLogRedTeam.setFont(GraphicStyle.ANY_LOG_FONT);
        textLogRedTeam.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
        //textLogRedTeam.setBorder(new LineBorder(GraphicStyle.TEAM_RED_PANEL_COLOR, 5, false));
        textLogRedTeam.setBackground(Color.BLACK);
        textLogRedTeam.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
        // ScrollPanel
        logRedTeamPanel = new JScrollPane(textLogRedTeam);
        logRedTeamPanel.setOpaque(false);
        logRedTeamPanel.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));

        redTeamPanel.add(logRedTeamPanel);
        JPanel extPanelRed = new JPanel(new GridLayout(1, 1));
        extPanelRed.setOpaque(false);
        extPanelRed.setBorder(new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 5, false));
        extPanelRed.add(redTeamPanel);
        parentPanel.add(extPanelRed);
        //</editor-fold>

        //<editor-fold desc="BLUE TEAM">
        // Panel
        blueTeamPanel = new JPanel();
        blueTeamPanel.setLayout(new BoxLayout(blueTeamPanel, BoxLayout.Y_AXIS));
        blueTeamPanel.setBackground(GraphicStyle.TEAM_BLUE_PANEL_COLOR);

        // Label
        JLabel blueTeamLabel = new JLabel(TEAM_BLUE_TEXT);
        blueTeamLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        blueTeamLabel.setFont(GraphicStyle.TITLE_FONT);
        blueTeamLabel.setForeground(Color.WHITE);
        blueTeamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blueTeamPanel.add(blueTeamLabel);

        // TextPanel
        textLogBlueTeam = new JTextArea();
        textLogBlueTeam.setFont(GraphicStyle.ANY_LOG_FONT);
        textLogBlueTeam.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
//        textLogBlueTeam.setBackground(GraphicStyle.TEAM_BLUE_TEXTPANEL_COLOR);
//        textLogBlueTeam.setForeground(Color.WHITE);
        textLogBlueTeam.setBackground(Color.BLACK);
        textLogBlueTeam.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
        // ScrollPanel
        logBlueTeamPanel = new JScrollPane(textLogBlueTeam);
        logBlueTeamPanel.setOpaque(false);
        logBlueTeamPanel.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));

        blueTeamPanel.add(logBlueTeamPanel);
        //blueTeamPanel.setBorder(new EmptyBorder(0, 50, 0, 50));

        JPanel extPanelBlue = new JPanel(new GridLayout(1, 1));
        extPanelBlue.setOpaque(false);
        extPanelBlue.add(blueTeamPanel);
        extPanelBlue.setBorder(new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 5, false));
        parentPanel.add(extPanelBlue);
        //</editor-fold>

        return parentPanel;
    }

    private JPanel getLogPanel(){
        int padding = 20;
        // Parent panel
        JPanel parentPanel = new JPanel(new GridLayout(1, 1));
        parentPanel.setOpaque(false);
        parentPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        //Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);    // Font to be used

        // Textlog and logpanel
        textLog = new JTextArea ();
        textLog.setBorder(new EmptyBorder(padding, padding, padding, padding));
        logPanel = new JScrollPane(textLog);
        logPanel.setBorder(new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 5, false));
        logPanel.setOpaque(false);

        // Set the font, background and font color
        logPanel.getViewport().getView().setFont(GraphicStyle.ANY_LOG_FONT);
        logPanel.getViewport().getView().setBackground(Color.black);
        logPanel.getViewport().getView().setForeground(GraphicStyle.TEXT_YELLOW_COLOR);

        parentPanel.add(logPanel);

        return parentPanel;
    }

    private JPanel getScreenSelectionPanel(int rows, int columns){
        // Parent panel
        screenPanel = new JPanel(new BorderLayout());
        screenPanel.setOpaque(false);

        // Left panel - screen selection
        screenSelectionPanel = new JPanel();
        screenSelectionPanel.setOpaque(false);
        screenSelectionPanel.setLayout(new GridLayout(rows, columns));
        int padding = 20;
        screenSelectionPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                ScreenComponent screenComponent = new ScreenComponent(this);
                screenSelectionPanel.add(screenComponent);
                screenComponentList.add(screenComponent);
            }
        }

        screenPanel.add(screenSelectionPanel);

        return screenPanel;
    }

    private JTabbedPane getScreenDetailsPanel(){
        // Config tab
        screenDetailsPanel = new JTabbedPane();
        JPanel configPanel = new JPanel();
        configPanel.setBackground(GraphicStyle.DANGER_COLOR);
        configPanel.add(getScreenControlPanel());
        screenDetailsPanel.addTab("Configuration", getScreenControlPanel());

        // Client list tab
        JScrollPane clientListPanel = getClientListPanel();
        //clientListPanel.setBackground(GraphicStyle.HELPER_COLOR);
        screenDetailsPanel.addTab("Client list", clientListPanel);

        return screenDetailsPanel;
    }

    private JPanel getScreenControlPanel(){
        screenControlPanel = new JPanel();
        screenControlPanel.setOpaque(false);
        screenControlPanel.setLayout(new GridLayout(4, 1));
        int padding = 20;
        //screenControlPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        // Right panel buttons
        CustomButton screenControlAddRowButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, ADD_ROW_TEXT);
        CustomButton screenControlRemoveRowButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, RMV_ROW_TEXT);
        CustomButton screenControlAddColumnButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, ADD_COL_TEXT);
        CustomButton screenControlRemoveColumnButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, RMV_COL_TEXT);
//        CustomButton screenButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, SCREEN_TEXT);
        screenControlRemoveRowButton.addActionListener(this);
        screenControlAddColumnButton.addActionListener(this);
        screenControlAddRowButton.addActionListener(this);
        screenControlRemoveColumnButton.addActionListener(this);
//        screenButton.addActionListener(this);
        screenControlPanel.add(screenControlAddRowButton);
        screenControlPanel.add(screenControlRemoveRowButton);
        screenControlPanel.add(screenControlAddColumnButton);
        screenControlPanel.add(screenControlRemoveColumnButton);
//        screenControlPanel.add(screenButton);

        return screenControlPanel;
    }

    private JScrollPane getClientListPanel(){
        // Create a panel which will be inside the scroll panel
        clientPanel = new JPanel();
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
        // Create the scroll panel and add the client panel
        JScrollPane clientScrollPanel = new JScrollPane(clientPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Set the client panel scroll speed
        clientScrollPanel.getVerticalScrollBar().setUnitIncrement(7);
        // Create a label with its properties and add it to the client panel
        for (Map.Entry<Integer, String> client : nodes.entrySet()) {
            ScreenComponent clientLabel = new ScreenComponent(this, client.getKey(), client.getValue());
            // Set a border
            clientLabel.setBorder(new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 5, false));
            // Set visible background
            clientLabel.setOpaque(true);
            // By default the component is not selected and therefore gray colored
            //clientLabel.setBackground(GraphicStyle.TERTIARY_COLOR);
            // Set font style and color
            clientLabel.setFont(GraphicStyle.ANY_LOG_FONT);
            clientLabel.setForeground(Color.white);
            // Set the height (the width does not change)
            //clientLabel.setPreferredSize(new Dimension(300, 100));
            clientLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
            // Add the label to the panel
            clientPanel.add(clientLabel);
            // Add a separator
            clientPanel.add(new JSeparator());
            clientComponentList.add(clientLabel);
        }

        return clientScrollPanel;
    }

    private void changeScreenSelectionPanel(){
        screenPanel.remove(screenSelectionPanel);
        screenPanel.add(getScreenSelectionPanel(screenSelectionRows, screenSelectionColumns));
    }

    public void updatePCList(){
        screenDetailsPanel.remove(1);
        JScrollPane clientListPanel = getClientListPanel();
        //clientListPanel.setBackground(GraphicStyle.HELPER_COLOR);
        screenDetailsPanel.addTab("Client list", clientListPanel);
    }
    //</editor-fold>

    /**
     * Adds from 0 to N messages to the general log.
     * @param newMessages
     */
    public void addMessagesToGeneralLog(String... newMessages){
        for (String newMessage : newMessages) {
            textLog.append(newMessage + "\n");
        }
    }

    /**
     * Updates the log of both teams.
     * @param redTeam
     * @param blueTeam
     */
    private void updateTeamsLogs(ArrayList<Spacecraft> redTeam, ArrayList<Spacecraft> blueTeam){
        for (int team = 0; team < 2; team++) {
            ArrayList<Spacecraft> currentMap;
            JTextArea currentLog;
            if (team == 0){
                currentMap = redTeam;
                currentLog = textLogRedTeam;
            }else{
                currentMap = blueTeam;
                currentLog = textLogBlueTeam;
            }
            currentLog.setText(""); // Clear the text
            for (Spacecraft spacecraft: currentMap){
                String ready;
                if(spacecraft.getReady()){
                    ready = "Ready";
                }else{
                    ready = "";
                }
                currentLog.append( spacecraft.getID()+ "\t" + spacecraft.getNickname()
                        + "\t" + ready + "\n");    // Concat player + id
            }
        }
    }

    // INHERIT
    @Override
    protected void initPanel() {
        this.setLayout(mainLayout);
        addMainElements();
        this.setBackground(GraphicStyle.SECONDARY_COLOR);
        this.setBorder(new EmptyBorder(20, 30, 20, 40));
        // TEST
        new Thread(this).start();
    }

    @Override
    protected void addMainElements() {
        // Add both left and right panels
        addTopPanel();
        addBottomPanel();

        // TEST
        addInitialConnections();
    }

    /**
     * Manages the button and other components actions.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        final String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case PLAY_TEXT: {
                // Todo on click "JUGAR"
                mainScreen.changeScreen(mainScreen.getGameViewer());
                break;
            }
            case OPTIONS_TEXT: {
                mainScreen.changeScreen(mainScreen.getOptionsPanel());
                break;
            }
            case ADD_ROW_TEXT:
                screenSelectionRows++;

                changeScreenSelectionPanel();
                updatePCList();
                break;
            case RMV_ROW_TEXT:
                screenSelectionRows--;
                changeScreenSelectionPanel();
                updatePCList();
                break;
            case ADD_COL_TEXT:
                screenSelectionColumns++;

                changeScreenSelectionPanel();
                updatePCList();
                break;
            case RMV_COL_TEXT:
                screenSelectionColumns--;
                changeScreenSelectionPanel();
                updatePCList();
                break;
        }
    }

    //<editor-fold desc="GETTERS">
    public List<ScreenComponent> getClientComponentList() {
        return clientComponentList;
    }

    public JPanel getClientPanel() {
        return clientPanel;
    }

    public List<ScreenComponent> getScreenComponentList() {
        return screenComponentList;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    private void setBackgroundColor(Color backgroundColor){
        setBackground(backgroundColor);
    }
    //</editor-fold>

    //<editor-fold desc="TEST">
    private void addInitialConnections(){
        String[] messages = new String[nodes.size()];
        int i = 0;
        for (Map.Entry<Integer, String> client : nodes.entrySet()) {
            messages[i] = "Node -> ID: " + client.getKey() + ", IP: " + client.getValue() + " joined the game.\n";
            i++;
        }

        addMessagesToGeneralLog(messages);
    }

    private void testShowSelectedScreens(){
        int i = 0;
        for (ScreenComponent screen: screenComponentList){
            if (screen.hasClient()){
                System.out.println("Pos " + i + ". Client: " + screen.getId() + " Id: " + screen.getIp());
            }else{
                System.out.println("Pos " + i + ". Not a selected screen");
            }
            i++;
        }
    }

    /**
     * Checks the screenComponents grid and returns a list containing the position of the selected ones.
     */
    private List<Integer> getScreenComponentPositions(){
        // Todo return array containing screens positions on click "SELECCIONAR PANTALLAS"
        // Get all components from the screenControlPanel
        Component[] components = screenSelectionPanel.getComponents();
        List<Integer> componentPositions = new ArrayList();
        int j = 0;
        for (int componentIndex = 0; componentIndex < components.length; componentIndex++) {
            if (( (ScreenComponent) components[componentIndex]).isSelected()){
                componentPositions.add(componentIndex);
                j++;
            }
        }
        return componentPositions;
    }

    // Todo TEST - ideally it is not necessary
    @Override
    public void run() {
        boolean end = false;
        while(!end){
            // TEST
            updateTeamsLogs(this.mainScreen.theaterMode.getRedTeam(), this.mainScreen.theaterMode.getBlueTeam());
            try {
                Thread.sleep(UPDATE_TEAMS_LOGS_TIME_MILIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void paintComponent(Graphics g){
//        g.drawImage(backgroundImage, 0, 0, null);
//    }
    //</editor-fold>
}
