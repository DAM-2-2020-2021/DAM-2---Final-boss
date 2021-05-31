package eu.cifpfbmoll.graphics.panels;


import eu.cifpfbmoll.graphics.customComponent.ScreenComponent;
import eu.cifpfbmoll.graphics.window.MainScreen;
import eu.cifpfbmoll.utils.Spacecraft;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminPanel extends CustomPanel implements Runnable{
    // CONST
    private final int PANEL_ROWS = 2, PANEL_COLUMN = 1;
    private final LayoutManager mainLayout = new GridLayout(PANEL_ROWS, PANEL_COLUMN);
    private final String PLAY_TEXT = "JUGAR", OPTIONS_TEXT = "OPCIONES", PLAYERS_TEXT = "JUGADORES",
            ADD_ROW_TEXT = "AÑADIR FILA", RMV_ROW_TEXT = "ELIMINAR FILA",
            ADD_COL_TEXT = "AÑADIR COLUMNA", RMV_COL_TEXT = "ELIMINAR COLUMNA",
            SCREEN_TEXT = "SELECCIONAR PANTALLAS";
    private final int SCREEN_ROWS_DEFAULT = 3, SCREEN_COLUMNS_DEFAULT = 3;
    private final int UPDATE_TEAMS_LOGS_TIME_MILIS = 3000;

    // VARS
    //Components
    private JButton buttonPlay;
    private JButton buttonOptions;
    private JButton buttonPlayers;
    // Panels
    private JPanel buttonsPanel;
    private JPanel bottomPanel;
    private JPanel screenSelectionPanel;
    private JPanel screenControlPanel;
    private JPanel screenPanel;
    private JScrollPane logPanel;

    // Test
    private int screenSelectionRows = SCREEN_ROWS_DEFAULT;
    private int screenSelectionColumns = SCREEN_COLUMNS_DEFAULT;
    private JScrollPane logRedTeamPanel, logBlueTeamPanel;
    private JTextArea textLog, textLogRedTeam, textLogBlueTeam;
    private JPanel redTeamPanel, blueTeamPanel;
    private final int SUBPANEL_PADDING_HEIGHT = 20, SUBPANEL_PADDING_WIDTH = 20;
    private final String TEAM_RED_TEXT = "Equipo Rojo", TEAM_BLUE_TEXT = "Equipo azul", LOG_TEXT = "Historial del log";
    private ArrayList<Spacecraft> redTeamPlayers = new ArrayList<>(), blueTeamPlayers = new ArrayList<>();  // temporary
    private Map<Integer, String> clientMap = new HashMap<>();

    public AdminPanel(MainScreen mainScreen){
        super(mainScreen);
        initPanel();    // Init the panel
    }

    @Override
    protected void initPanel() {
        this.setLayout(mainLayout);
        addMainElements();
        this.setBackground(GraphicStyle.SECONDARY_COLOR);

        // TEST
        new Thread(this).start();
    }

    @Override
    protected void addMainElements() {
        // Add both left and right panels
        addTopPanel();
        addBottomPanel();

        // TEST
        testAddSpacecrafts();
        testAddClients();
        testAddClientMessages();
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
        JButton buttonPlay = new JButton(PLAY_TEXT);
        JButton buttonOptions = new JButton(OPTIONS_TEXT);
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
        textLogRedTeam.setBackground(GraphicStyle.TEAM_RED_TEXTPANEL_COLOR);
        textLogRedTeam.setForeground(Color.WHITE);
        // ScrollPanel
        logRedTeamPanel = new JScrollPane(textLogRedTeam);
        logRedTeamPanel.setOpaque(false);
        logRedTeamPanel.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));

        redTeamPanel.add(logRedTeamPanel);
        //parentPanel.add(redTeamPanel);
        JPanel extPanelRed = new JPanel(new GridLayout(1, 1));
        extPanelRed.setOpaque(false);
        extPanelRed.setBorder(new LineBorder(GraphicStyle.WHITE_COLOR, 5, false));
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
        textLogBlueTeam.setBackground(GraphicStyle.TEAM_BLUE_TEXTPANEL_COLOR);
        textLogBlueTeam.setForeground(Color.WHITE);
        // ScrollPanel
        logBlueTeamPanel = new JScrollPane(textLogBlueTeam);
        logBlueTeamPanel.setOpaque(false);
        logBlueTeamPanel.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));

        blueTeamPanel.add(logBlueTeamPanel);
        blueTeamPanel.setBorder(new EmptyBorder(0, 50, 0, 50));

        JPanel extPanelBlue = new JPanel(new GridLayout(1, 1));
        extPanelBlue.setOpaque(false);
        extPanelBlue.add(blueTeamPanel);
        extPanelBlue.setBorder(new LineBorder(GraphicStyle.WHITE_COLOR, 5, false));
        parentPanel.add(extPanelBlue);
        //</editor-fold>

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
                ScreenComponent screenComponent = new ScreenComponent();
                screenSelectionPanel.add(screenComponent);
            }
        }

        screenPanel.add(screenSelectionPanel);

        return screenPanel;
    }

    private JTabbedPane getScreenDetailsPanel(){

        // Config tab
        JTabbedPane configTab = new JTabbedPane();
        JPanel configPanel = new JPanel();
        configPanel.setBackground(GraphicStyle.DANGER_COLOR);
        configPanel.add(getScreenControlPanel());
        configTab.addTab("Configuration", getScreenControlPanel());

        // Client list tab
        JTabbedPane clientListTab = new JTabbedPane();
        JPanel clientListPanel = new JPanel();
        clientListPanel.setBackground(GraphicStyle.HELPER_COLOR);
        configTab.addTab("Client list", clientListPanel);

        return configTab;
    }

    private JPanel getScreenControlPanel(){
        screenControlPanel = new JPanel();
        screenControlPanel.setOpaque(false);
        screenControlPanel.setLayout(new GridLayout(5, 1));
        int padding = 20;
        //screenControlPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        // Right panel buttons
        JButton screenControlAddRowButton = new JButton(ADD_ROW_TEXT);
        JButton screenControlRemoveRowButton = new JButton(RMV_ROW_TEXT);
        JButton screenControlAddColumnButton = new JButton(ADD_COL_TEXT);
        JButton screenControlRemoveColumnButton = new JButton(RMV_COL_TEXT);
        JButton screenButton = new JButton(SCREEN_TEXT);
        screenControlRemoveRowButton.addActionListener(this);
        screenControlAddColumnButton.addActionListener(this);
        screenControlAddRowButton.addActionListener(this);
        screenControlRemoveColumnButton.addActionListener(this);
        screenButton.addActionListener(this);
        screenControlPanel.add(screenControlAddRowButton);
        screenControlPanel.add(screenControlRemoveRowButton);
        screenControlPanel.add(screenControlAddColumnButton);
        screenControlPanel.add(screenControlRemoveColumnButton);
        screenControlPanel.add(screenButton);

        // Finally add both panels to the main one
//        screenOptionsPanel.add(screenControlPanel, BorderLayout.PAGE_START);
        return screenControlPanel;
    }

    private JPanel getLogPanel(){
        int padding = 20;
        // Parent panel
        JPanel parentPanel = new JPanel(new GridLayout(1, 1));
        parentPanel.setOpaque(false);
        parentPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);    // Font to be used

        // Textlog and logpanel
        textLog = new JTextArea ();
        textLog.setBorder(new EmptyBorder(padding, padding, padding, padding));
        logPanel = new JScrollPane(textLog);
        logPanel.setBorder(new LineBorder(GraphicStyle.WHITE_COLOR, 5, false));
        logPanel.setOpaque(false);

        // Set the font, background and font color
        logPanel.getViewport().getView().setFont(font);
        logPanel.getViewport().getView().setBackground(Color.black);
        logPanel.getViewport().getView().setForeground(Color.white);

        parentPanel.add(logPanel);

        return parentPanel;
    }

    private JPanel getClientListPanel(){
        // TEST
        for (Map.Entry<Integer, String> client : clientMap.entrySet()) {
            textLog.append("Client " + client.getKey() + ": " + "192.168.1." + client.getValue() + "joined the game.");
        }

        //
        JScrollPane clientPanel = new JScrollPane();
        return null;
    }

    private void changeScreenSelectionPanel(){
        screenPanel.remove(screenSelectionPanel);
        screenPanel.add(getScreenSelectionPanel(screenSelectionRows, screenSelectionColumns));
    }
    //</editor-fold>

    /**
     * Adds from 0 to N messages to the general log.
     * @param newMessages
     */
    private void addMessagesToGeneralLog(String... newMessages){
        for (String newMessage : newMessages) {
            textLog.append(newMessage + "\n");
        }
    }

    /**
     * Updates the log of both teams.
     * @param redTeamPlayers
     * @param blueTeamPlayers
     */
    private void updateTeamsLogs(ArrayList<Spacecraft> redTeamPlayers, ArrayList<Spacecraft> blueTeamPlayers){
        for (int team = 0; team < 2; team++) {
            ArrayList<Spacecraft> currentMap;
            JTextArea currentLog;
            if (team == 0){
                currentMap = redTeamPlayers;
                currentLog = textLogRedTeam;
            }else{
                currentMap = blueTeamPlayers;
                currentLog = textLogBlueTeam;
            }
            currentLog.setText(""); // Clear the text
            for (Spacecraft spacecraft: currentMap){
                currentLog.append(spacecraft.getNickname() + "\t" + spacecraft.getIP()
                        + "\t" + spacecraft.getReady() + "\n");    // Concat player + id
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case PLAY_TEXT: {
                // Todo on click "JUGAR"
//                mainScreen.changeScreen(mainScreen.getGameViewer());
                break;
            }
            case OPTIONS_TEXT: {
                // Todo on click "OPCIONES"
                mainScreen.changeScreen(mainScreen.getOptionsPanel());
                break;
            }
            case SCREEN_TEXT:
                // Todo on click "SELECCIONAR PANTALLAS"
                break;
            case ADD_ROW_TEXT:
                screenSelectionRows++;
                changeScreenSelectionPanel();
                break;
            case RMV_ROW_TEXT:
                screenSelectionRows--;
                changeScreenSelectionPanel();
                break;
            case ADD_COL_TEXT:
                screenSelectionColumns++;
                changeScreenSelectionPanel();
                break;
            case RMV_COL_TEXT:
                screenSelectionColumns--;
                changeScreenSelectionPanel();
                break;
        }
    }

    @Override
    public void run() {
        boolean end = false;
        while(!end){
            // TEST
            updateTeamsLogs(redTeamPlayers, blueTeamPlayers);
            try {
                Thread.sleep(UPDATE_TEAMS_LOGS_TIME_MILIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // TEST
    private void testAddSpacecrafts(){
        for (int i = 0; i < 10; i++) {
            Spacecraft spacecraft = new Spacecraft();
            spacecraft.setIP("192.168.1" + i);
            spacecraft.setReady(true);

            spacecraft.setNickname("PlayerRed" + i);
            redTeamPlayers.add(spacecraft);

            spacecraft.setNickname("PlayerBlue" + i);
            blueTeamPlayers.add(spacecraft);
        }
    }

    private void testAddClients(){
        for (int i = 0; i < 5; i++) {
            clientMap.put(i, "192.168.2." + i);
        }
    }

    private void testAddClientMessages(){
        String[] messages = new String[clientMap.size()];
        int i = 0;
        for (Map.Entry<Integer, String> client : clientMap.entrySet()) {
            //textLog.append("Client " + client.getKey() + ": " + "192.168.1." + client.getValue() + " joined the game.\n");
            messages[i] = "Client " + client.getKey() + ": " + "192.168.1." + client.getValue() + " joined the game.\n";
            i++;
        }

        addMessagesToGeneralLog(messages);
    }
}
