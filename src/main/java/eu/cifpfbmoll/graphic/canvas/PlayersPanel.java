package eu.cifpfbmoll.graphic.canvas;

import eu.cifpfbmoll.graphic.objects.Spacecraft;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayersPanel extends CustomPanel implements Runnable{
    // CONS
    private final LayoutManager MAIN_LAYOUT = new GridLayout(1, 3);
    private final int SUBPANEL_PADDING_HEIGHT = 20, SUBPANEL_PADDING_WIDTH = 20;
    private final String TEAM_RED_TEXT = "Equipo Rojo", TEAM_BLUE_TEXT = "Equipo azul", LOG_TEXT = "Historial del log";
    private final Color TEAM_RED_PANEL_COLOR = new Color (207, 27, 27), TEAM_RED_TEXTPANEL_COLOR = Color.RED,
            TEAM_BLUE_PANEL_COLOR = new Color (27, 92, 207), TEAM_BLUE_TEXTPANEL_COLOR = Color.BLUE;
    private final Font TITLE_FONT = new Font("Verdana", Font.PLAIN, 34);
    private final Font ANY_LOG_FONT = new Font("Verdana", Font.PLAIN, 20);
    private final int UPDATE_TIME_MILIS = 1500;

    // VARS
    private Map<String, String> redTeamPlayers = new HashMap<>(), blueTeamPlayers = new HashMap<>();
    private ArrayList<Spacecraft> redTeam,blueTeam;
    private int screenNumber;
    private JScrollPane logPanel, logRedTeamPanel, logBlueTeamPanel;
    private JTextArea textLog, textLogRedTeam, textLogBlueTeam;
    private JPanel redTeamPanel, blueTeamPanel;
    private JButton goBack;

    public PlayersPanel(MainScreen mainScreen, ArrayList<Spacecraft> blueTeam, ArrayList<Spacecraft> redTeam){
        super(mainScreen);
        this.redTeam = redTeam;
        this.blueTeam = blueTeam;
        initPanel();
    }

    //<editor-fold desc="ADDERS">
    private void addReadTeamPanel(){
        // Panel
        redTeamPanel = new JPanel();
        redTeamPanel.setLayout(new BoxLayout(redTeamPanel, BoxLayout.Y_AXIS));
        redTeamPanel.setBackground(TEAM_RED_PANEL_COLOR);

        // Label
        JLabel redTeamLabel = new JLabel(TEAM_RED_TEXT);
        redTeamLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        redTeamLabel.setFont(TITLE_FONT);
        redTeamLabel.setForeground(Color.WHITE);
        redTeamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        redTeamPanel.add(redTeamLabel);

        // TextPanel
        JPanel textPanelParent = new JPanel();
        textPanelParent.setOpaque(false);
        textPanelParent.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));
        textLogRedTeam = new JTextArea (25,20);
        textLogRedTeam.setFont(ANY_LOG_FONT);
        textLogRedTeam.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
        textLogRedTeam.setBackground(TEAM_RED_TEXTPANEL_COLOR);
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


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        goBack = new JButton("GO BACK");
        goBack.addActionListener(this);
        buttonPanel.add(goBack);
        this.add(buttonPanel,BorderLayout.SOUTH);


    }

    private void addBlueTeamPanel(){
        // Panel
        blueTeamPanel = new JPanel();
        blueTeamPanel.setLayout(new BoxLayout(blueTeamPanel, BoxLayout.Y_AXIS));
        blueTeamPanel.setBackground(TEAM_BLUE_PANEL_COLOR);

        // Label
        JLabel blueTeamLabel = new JLabel(TEAM_BLUE_TEXT);
        blueTeamLabel.setBorder(new EmptyBorder(30, 0, 0, 0));
        blueTeamLabel.setFont(TITLE_FONT);
        blueTeamLabel.setForeground(Color.WHITE);
        blueTeamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blueTeamPanel.add(blueTeamLabel);

        // TextPanel
        JPanel textPanelParent = new JPanel();
        textPanelParent.setOpaque(false);
        textPanelParent.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, 40, SUBPANEL_PADDING_HEIGHT, 40));
        textLogBlueTeam = new JTextArea (25,20);
        textLogBlueTeam.setFont(ANY_LOG_FONT);
        textLogBlueTeam.setBorder(new EmptyBorder(SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH, SUBPANEL_PADDING_HEIGHT, SUBPANEL_PADDING_WIDTH));
        textLogBlueTeam.setBackground(TEAM_BLUE_TEXTPANEL_COLOR);
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
        logPanel.getViewport().getView().setFont(ANY_LOG_FONT);
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
            ArrayList<Spacecraft> currrentArray;
            if (currentLog.equals(textLogRedTeam)){
                currrentArray = redTeam;
            }else{
                currrentArray = blueTeam;
            }
            currentLog.setText(""); // Clear the text

            for(Spacecraft spacecraft : currrentArray){
                String isReady = "";
                if(spacecraft.getReady()){
                    isReady = "Ready";
                }
                currentLog.append(spacecraft.getIP()+ "\t" + spacecraft.getTeam() + "\t" + isReady + "\n");    // Concat player + id

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


    }

    @Override
    protected void addMainElements() {
        addReadTeamPanel();
        addBlueTeamPanel();
        addLogPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        Object sourceButton = e.getSource();
        if(sourceButton.equals(goBack)){
            mainScreen.changeScreen("ADMIN_PANEL");
        }
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



}