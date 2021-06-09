package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.graphic.objects.Spacecraft;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientPanel extends CustomPanel implements Runnable{
    // CONS
    private final LayoutManager MAIN_LAYOUT = new GridLayout(1, 3);
    private final int SUBPANEL_PADDING_HEIGHT = 20, SUBPANEL_PADDING_WIDTH = 20;
    private final String TEAM_RED_TEXT = "RED TEAM", TEAM_BLUE_TEXT = "BLUE TEAM", LOG_TEXT = "Historial del log";
    private final int UPDATE_TIME_MILIS = 1500;

    // VARS
    private JScrollPane logPanel, logRedTeamPanel, logBlueTeamPanel;
    private JTextArea textLog, textLogRedTeam, textLogBlueTeam;
    private JPanel redTeamPanel, blueTeamPanel;

    public ClientPanel(MainScreen mainScreen){
        super(mainScreen);
        initPanel();
    }

    private void addPanels(){
        this.add(getRedTeamPanel());
        this.add(getBlueTeamPanel());
        this.add(getLogPanel());
    }

    private JPanel getLogPanel(){
        textLog = new JTextArea (10,30);
        textLog.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
        logPanel = new JScrollPane(textLog);
        //logPanel.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));

        // Set the font, background and font color
        logPanel.getViewport().getView().setFont(GraphicStyle.ANY_LOG_FONT);
        logPanel.getViewport().getView().setBackground(Color.BLACK);
        logPanel.getViewport().getView().setForeground(GraphicStyle.TEXT_YELLOW_COLOR);

        //JPanel
        JPanel parent = new JPanel(new GridLayout(1, 1));
        parent.add(logPanel);
        parent.setBorder(new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 4, false));
        parent.setBackground(Color.BLACK);

        return parent;
    }

    private JPanel getRedTeamPanel(){
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

        return extPanelRed;
    }

    private JPanel getBlueTeamPanel(){
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

        return extPanelBlue;
    }


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


    public void addMessagesToGeneralLog(String... newMessages){
        for (String newMessage : newMessages) {
            textLog.append(newMessage + "\n");
        }
    }


    // INHERIT METHODS
    @Override
    protected void initPanel() {
        this.setLayout(MAIN_LAYOUT);    // Set the main layout
        this.setPaddingSize(40, 40);
        this.setBackground(GraphicStyle.SECONDARY_COLOR);
        addMainElements();      // Add the top elements
        new Thread(this).start();   // Start a thread to fetch and update client list values

    }

    @Override
    protected void addMainElements() {
        addPanels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {
        boolean end = false;
        while(!end){
            updateTeamsLogs(this.mainScreen.theaterMode.getRedTeam(), this.mainScreen.theaterMode.getBlueTeam());
            try {
                Thread.sleep(UPDATE_TIME_MILIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
