package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.logic.Configuration;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OptionsPanel extends CustomPanel {
    // CONS
    private final int PANEL_ROWS = 1, PANEL_COLUMNS = 3;
    private final LayoutManager mainLayout = new GridLayout(PANEL_ROWS, PANEL_COLUMNS);
    // VARS
    private MainScreen mainScreen;
    private JPanel leftPanel, rightPanel;
    private Configuration configuration;

    // TEST
    private int meteorCount;
    private JLabel meteorCountLabel;
    private JButton previousMeteor;
    private JButton nextMeteor;

    private int blackHoleCount;
    private JLabel blackHoleCountLabel;
    private JButton previousBlackHole;
    private JButton nextBlackHole;

    private JButton saveChanges;
    private JRadioButton lowGeneration;
    private JRadioButton mediumGeneration;
    private JRadioButton highGeneration;

    public OptionsPanel(MainScreen mainScreen, Configuration configuration){
        super(mainScreen);
        this.mainScreen = mainScreen;
        this.configuration = configuration;
        this.meteorCount = configuration.getMeteorQuantity();
        this.blackHoleCount = configuration.getBlackHoleQuantity();
        initPanel();
    }

    @Override
    protected void initPanel() {
        this.setLayout(mainLayout);
        addMainElements();
    }

    @Override
    protected void addMainElements() {
        addLeftPanel();
    }

    private void addLeftPanel(){
        leftPanel = new JPanel(new GridLayout(3,1));
        //
        JPanel leftTopPanel = new JPanel(new BorderLayout());
        JPanel leftMidPanel = new JPanel(new BorderLayout());
        JPanel leftBotPanel = new JPanel(new BorderLayout());
        //
        JLabel meteorLabel = new JLabel("METEORS");
        JLabel blackHoleLabel = new JLabel("BLACK HOLES");
        JLabel objectLabel = new JLabel("OBJECT GENERATION");

        meteorLabel.setFont(new Font("Verdana", Font.PLAIN, 34));
        blackHoleLabel.setFont(new Font("Verdana", Font.PLAIN, 34));
        objectLabel.setFont(new Font("Verdana", Font.PLAIN, 34));
        //
        leftTopPanel.add(meteorLabel, BorderLayout.CENTER);
        leftMidPanel.add(blackHoleLabel);
        leftBotPanel.add(objectLabel);
        leftMidPanel.setBackground(Color.CYAN);
        leftTopPanel.setBackground(Color.MAGENTA);
        leftBotPanel.setBackground(Color.ORANGE);
        //
        JPanel meteorSelector = new JPanel(new FlowLayout());

        previousMeteor = new JButton("-1");
        meteorCountLabel = new JLabel(String.valueOf(this.configuration.getMeteorQuantity()));
        nextMeteor = new JButton("+1");
        previousMeteor.addActionListener(this);
        nextMeteor.addActionListener(this);

        meteorSelector.add(previousMeteor);
        meteorSelector.add(meteorCountLabel);
        meteorSelector.add(nextMeteor);
        leftTopPanel.add(meteorSelector, BorderLayout.SOUTH);
        //
        JPanel blackHoleSelector = new JPanel(new FlowLayout());

        previousBlackHole = new JButton("-1");
        blackHoleCountLabel = new JLabel(String.valueOf(this.configuration.getBlackHoleQuantity()));
        nextBlackHole = new JButton("+1");
        previousBlackHole.addActionListener(this);
        nextBlackHole.addActionListener(this);

        blackHoleSelector.add(previousBlackHole);
        blackHoleSelector.add(blackHoleCountLabel);
        blackHoleSelector.add(nextBlackHole);
        leftMidPanel.add(blackHoleSelector, BorderLayout.SOUTH);


        JPanel objectGenerationSelector = new JPanel(new FlowLayout());
        ButtonGroup buttonGroup = new ButtonGroup();

        switch (this.configuration.getObjectGeneration().toLowerCase()) {
            case "low":
                lowGeneration = new JRadioButton("LOW",true);
                mediumGeneration = new JRadioButton("MEDIUM");
                highGeneration = new JRadioButton("HIGH");
                break;
            case "medium":
                lowGeneration = new JRadioButton("LOW");
                mediumGeneration = new JRadioButton("MEDIUM",true);
                highGeneration = new JRadioButton("HIGH");
                break;
            case "high":
                lowGeneration = new JRadioButton("LOW");
                mediumGeneration = new JRadioButton("MEDIUM");
                highGeneration = new JRadioButton("HIGH",true);
                break;
        }
        lowGeneration.addActionListener(this);
        mediumGeneration.addActionListener(this);
        highGeneration.addActionListener(this);
        buttonGroup.add(lowGeneration);
        buttonGroup.add(mediumGeneration);
        buttonGroup.add(highGeneration);

        objectGenerationSelector.add(lowGeneration);
        objectGenerationSelector.add(mediumGeneration);
        objectGenerationSelector.add(highGeneration);

        leftBotPanel.add(objectGenerationSelector, BorderLayout.SOUTH);


        leftPanel.add(leftTopPanel);
        leftPanel.add(leftMidPanel);
        leftPanel.add(leftBotPanel);

        this.add(leftPanel);
        rightPanel = new JPanel();


        saveChanges = new JButton("SAVE CHANGES");
        saveChanges.addActionListener(this);
        rightPanel.add(saveChanges);
        this.add(rightPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(previousMeteor)){
            meteorCount--;
            if(meteorCount < 0){
                meteorCount++;
            }
            meteorCountLabel.setText(String.valueOf(meteorCount));
        }else if(sourceButton.equals(nextMeteor)){
            meteorCount++;
            meteorCountLabel.setText(String.valueOf(meteorCount));
        }

        if (sourceButton.equals(previousBlackHole)){
            blackHoleCount--;
            if(blackHoleCount < 0){
                blackHoleCount++;
            }
            blackHoleCountLabel.setText(String.valueOf(blackHoleCount));
        }else if(sourceButton.equals(nextBlackHole)){
            blackHoleCount++;
            blackHoleCountLabel.setText(String.valueOf(blackHoleCount));
        }

        if(sourceButton.equals(lowGeneration)){
           this.configuration.setObjectGeneration("LOW");
        }else if(sourceButton.equals(mediumGeneration)){
            this.configuration.setObjectGeneration("MEDIUM");
        }else if(sourceButton.equals(highGeneration)){
            this.configuration.setObjectGeneration("HIGH");
        }
        if(sourceButton.equals(saveChanges)){
            this.configuration.setMeteorQuantity(meteorCount);
            this.configuration.setBlackHoleQuantity(blackHoleCount);
            mainScreen.changeScreen(mainScreen.getAdminPanel());
        }
    }
}