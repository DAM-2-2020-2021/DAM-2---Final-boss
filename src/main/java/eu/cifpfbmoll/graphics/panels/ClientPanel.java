package eu.cifpfbmoll.graphics.panels;

import eu.cifpfbmoll.graphics.window.MainScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class ClientPanel extends CustomPanel implements Runnable{
    // CONS
    private final LayoutManager MAIN_LAYOUT = new GridLayout(1, 3);
    private final int SUBPANEL_PADDING_HEIGHT = 20, SUBPANEL_PADDING_WIDTH = 20;
    private final String TEAM_RED_TEXT = "Equipo Rojo", TEAM_BLUE_TEXT = "Equipo azul", LOG_TEXT = "Historial del log";
    private final int UPDATE_TIME_MILIS = 3000;

    // VARS
    private Map<String, String> redTeamPlayers = new HashMap<>(), blueTeamPlayers = new HashMap<>();
    private int screenNumber;
    private JScrollPane logPanel, logRedTeamPanel, logBlueTeamPanel;
    private JTextArea textLog, textLogRedTeam, textLogBlueTeam;
    private JPanel redTeamPanel, blueTeamPanel;

    public ClientPanel(MainScreen mainScreen){
        super(mainScreen);
        initPanel();
    }

    //<editor-fold desc="ADDERS">
    private void addReadTeamPanel(){
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
        JPanel textPanelParent = new JPanel();
        textPanelParent.setOpaque(false);
        textPanelParent.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));
        textLogRedTeam = new JTextArea ();
        textLogRedTeam.setFont(GraphicStyle.ANY_LOG_FONT);
        textLogRedTeam.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
        textLogRedTeam.setBackground(GraphicStyle.TEAM_RED_TEXTPANEL_COLOR);
        textLogRedTeam.setForeground(Color.WHITE);
        textLogRedTeam.append("hola\nwow");
        // ScrollPanel
        logRedTeamPanel = new JScrollPane(textLogRedTeam);
        textPanelParent.add(logRedTeamPanel);

        redTeamPanel.add(textPanelParent);

        JPanel extPanel = new JPanel();
        //extPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        extPanel.add(redTeamPanel);

        this.add(extPanel);
    }

    private void addBlueTeamPanel(){
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
        JPanel textPanelParent = new JPanel();
        textPanelParent.setOpaque(false);
        textPanelParent.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));
        textLogBlueTeam = new JTextArea ();
        textLogBlueTeam.setFont(GraphicStyle.ANY_LOG_FONT);
        textLogBlueTeam.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
        textLogBlueTeam.setBackground(GraphicStyle.TEAM_BLUE_TEXTPANEL_COLOR);
        textLogBlueTeam.setForeground(Color.WHITE);
        // ScrollPanel
        logBlueTeamPanel = new JScrollPane(textLogBlueTeam);
        textPanelParent.add(logBlueTeamPanel);

        blueTeamPanel.add(textPanelParent);

        JPanel extPanel = new JPanel();
        //extPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        extPanel.add(blueTeamPanel);

        this.add(extPanel);
    }

    private void addLogPanel(){
        textLog = new JTextArea (10,30);
        logPanel = new JScrollPane(textLog);
        logPanel.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));

        // Set the font, background and font color
        logPanel.getViewport().getView().setFont(GraphicStyle.ANY_LOG_FONT);
        logPanel.getViewport().getView().setBackground(Color.black);
        logPanel.getViewport().getView().setForeground(Color.white);

        this.add(logPanel);
    }
    //</editor-fold>

    private void updateLogs(){
        updateLog(textLogRedTeam);
        updateLog(textLogBlueTeam);
        updateLog(textLog);
    }

    private void updateLog(JTextArea currentLog){
        if (currentLog.equals(textLog)){
            // Todo update common log
        }else{
            Map<String, String> currentMap;
            if (currentLog.equals(textLogRedTeam)){
                currentMap = redTeamPlayers;
            }else{
                currentMap = blueTeamPlayers;
            }
            currentLog.setText(""); // Clear the text
            for (Map.Entry<String, String> client: currentMap.entrySet()){
                currentLog.append(client.getKey()+ "\t" + client.getValue() + "\n");    // Concat player + id
            }
        }
    }

    private void fetchClientList(){
        // Todo receive a client list
        // Update clientsDictionary
    }

    // INHERIT METHODS
    @Override
    protected void initPanel() {
        this.setLayout(MAIN_LAYOUT);    // Set the main layout
        this.setPaddingSize(40, 40);
        addMainElements();      // Add the top elements
        new Thread(this).start();   // Start a thread to fetch and update client list values

        // TEST
        testAddPlayers();
    }

    @Override
    protected void addMainElements() {
        addReadTeamPanel();
        addBlueTeamPanel();
        addLogPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
        boolean end = false;
        while(!end){
            fetchClientList();
            updateLogs();
            try {
                Thread.sleep(UPDATE_TIME_MILIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // TEST
    private void testAddPlayers(){
        // Red team
        redTeamPlayers.put("Pedro", "192.168.1.1");
        redTeamPlayers.put("Tomas", "192.169.1.0");

        // Blue team
        blueTeamPlayers.put("Toni", "192.321.1.0");
        blueTeamPlayers.put("Qliao", "192.200.1.0");
    }
}
