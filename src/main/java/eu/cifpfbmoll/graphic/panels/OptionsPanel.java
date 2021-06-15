package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.graphic.component.CustomButton;
import eu.cifpfbmoll.graphic.component.CustomIcon;
import eu.cifpfbmoll.graphic.sprite.Sprite;
import eu.cifpfbmoll.logic.Configuration;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private int[] optionCount = {0, 0, 0};
    private JLabel[] optionLabels = new JLabel[3];
    private CustomButton[][] optionButtons = new CustomButton[3][2];

    private JButton saveChanges;
    private JRadioButton lowGeneration;
    private JRadioButton mediumGeneration;
    private JRadioButton highGeneration;

    public OptionsPanel(MainScreen mainScreen, Configuration configuration){
        super(mainScreen);
        this.mainScreen = mainScreen;
        this.configuration = configuration;
//        this.meteorCount = configuration.getMeteorQuantity();
//        this.blackHoleCount = configuration.getBlackHoleQuantity();
        initPanel();
    }

    private void addPanels(){
        this.add(getLeftPanel());
        this.add(getRightPanel());
    }

    private JPanel getRightPanel(){
        // Main panel
        JPanel rightPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // First mode
        JPanel firstModePanel = new JPanel(new GridBagLayout());
        firstModePanel.setBackground(Color.BLACK);
        CustomIcon firstModeIcon = new CustomIcon(Sprite.getMothership(0,0), 400, 450 * 2, true);

        firstModePanel.add(firstModeIcon);

        // Second mode
        JPanel secondModePanel = new JPanel(new GridBagLayout());
        secondModePanel.setBackground(Color.RED);
        CustomIcon secondModeIcon = new CustomIcon(Sprite.getMothership(2,0), 400, 450 * 2, true);
        secondModePanel.add(secondModeIcon);

        rightPanel.add(firstModePanel);
        rightPanel.add(secondModePanel);

        return rightPanel;
    }

    private JPanel getLeftPanel(){
        //
        leftPanel = new JPanel(new GridLayout(3,1));
        leftPanel.setOpaque(false);
        String[] messages = {"METEORS", "BLACK HOLES", "ITEM"};

        int imagePos = 0;
        for (int i = 0; i < 3; i++) {
            // Main panel
            JPanel leftPosPanel = new JPanel(new GridLayout(1,2));
            leftPosPanel.setOpaque(false);

            // Icon and option label
            JPanel optionPanel = new JPanel(new GridBagLayout());
            optionPanel.setOpaque(false);
            CustomIcon ci = new CustomIcon(Sprite.getObjectIcons(imagePos, 0), 150, 150, false);
            JLabel optionLabel = new JLabel(messages[i]);
            optionLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 40));
            optionLabel.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);

            // Layout construction
            GridBagConstraints gbCons = new GridBagConstraints();
            gbCons.gridx = 0;
            gbCons.gridwidth = gbCons.gridheight = 1;
            gbCons.anchor = GridBagConstraints.CENTER;
            gbCons.weightx = 200;
            gbCons.weighty = 20;
            optionPanel.add(ci, gbCons);
            gbCons.gridx = 1;
            optionPanel.add(optionLabel, gbCons);

            leftPosPanel.add(optionPanel);

            // Selector - previous and next buttons, count label
            JPanel meteorSelector = new JPanel(new GridBagLayout());
            meteorSelector.setOpaque(false);
            meteorSelector.setBorder(BorderFactory.createEmptyBorder(130, 20, 130, 80));

            // Previous button
            CustomButton previousMeteorButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, "-1");
            previousMeteorButton.addActionListener(this);
            optionButtons[i][0] = previousMeteorButton;

            // Count label
            JLabel countLabel = new JLabel(String.valueOf(this.configuration.getMeteorQuantity()));
            countLabel.setText(String.valueOf(optionCount[i]));
            countLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 50));
            countLabel.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
            countLabel.setHorizontalAlignment(SwingConstants.CENTER);
            optionLabels[i] = countLabel;

            // Next button
            CustomButton nextMeteorButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, "+1");
            nextMeteorButton.addActionListener(this);
            optionButtons[i][1] = nextMeteorButton;

            // Layout construction
            gbCons = new GridBagConstraints();
            gbCons.gridx = 0;
            gbCons.gridwidth = gbCons.gridheight = 1;
            gbCons.fill = GridBagConstraints.BOTH;
            gbCons.anchor = GridBagConstraints.CENTER;
            gbCons.weightx = 700;
            gbCons.weighty = 20;
            meteorSelector.add(previousMeteorButton, gbCons);
            gbCons.gridx = 1;
            meteorSelector.add(countLabel, gbCons);
            gbCons.gridx = 2;
            meteorSelector.add(nextMeteorButton, gbCons);

            leftPosPanel.add(meteorSelector);
            leftPanel.add(leftPosPanel);
            imagePos += 1;
        }

        return leftPanel;
    }

    @Override
    protected void initPanel() {
        this.setBackground(GraphicStyle.PRIMARY_COLOR);
        this.setLayout(mainLayout);
        addMainElements();
    }

    @Override
    protected void addMainElements() {
        addPanels();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        Object sourceButton = e.getSource();

        // Get the button position
        boolean end = false;
        int i = 0;
        while(!end){
            for (int j = 0; j < 2; j++) {
                if (sourceButton.equals(optionButtons[i][j])){
                    if (j == 0){
                        // Previous button
                        if (optionCount[i] > 0){
                            optionCount[i] --;
                        }
                    }else{
                        // Next button
                        if (optionCount[i] < 5){
                            optionCount[i] ++;
                        }
                    }
                    optionLabels[i].setText(String.valueOf(optionCount[i]));
                    end = true;
                }
            }
            i ++;
        }
    }

}