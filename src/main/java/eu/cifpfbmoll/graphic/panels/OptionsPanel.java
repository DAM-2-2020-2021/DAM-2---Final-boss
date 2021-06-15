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
    private int[] optionCount;
    private String[] optionText = {"LOW","MEDIUM","HIGH"};
    private JLabel[] optionLabels = new JLabel[3];
    private CustomButton[][] optionButtons = new CustomButton[3][2];

    private CustomButton firstModePanel;
    private CustomButton secondModePanel;
    private CustomButton returnButton;

    public OptionsPanel(MainScreen mainScreen, Configuration configuration){
        super(mainScreen);
        this.mainScreen = mainScreen;
        this.configuration = configuration;
        this.optionCount = new int[]{configuration.getMeteorQuantity(), configuration.getBlackHoleQuantity(), 1};
        initPanel();
    }

    private void addPanels(){
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        int taskBarHeight = scrnSize.height - winSize.height + 10;

        JPanel mainPanel = new JPanel(new GridLayout(PANEL_ROWS, 2));
        mainPanel.setOpaque(false);
        mainPanel.add(getLeftPanel());
        mainPanel.add(getRightPanel());
        mainPanel.setBounds(0, 0, 1920 , 1080 - taskBarHeight);

        JPanel returnPanel = getReturnPanel();
        returnPanel.setBounds(0, 0, 1920 , 1080 - taskBarHeight);

        JLayeredPane layeredPanel = new JLayeredPane();
        layeredPanel.add(mainPanel, new Integer(1));
        layeredPanel.add(returnPanel, new Integer(2));

        this.add(layeredPanel, BorderLayout.CENTER);
    }

    private JPanel getRightPanel(){
        // Main panel
        JPanel rightPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // First mode
        firstModePanel = new CustomButton(CustomButton.CustomButtonType.PRIMARY, null, true);
        firstModePanel.setLayout(new GridBagLayout());
        firstModePanel.addActionListener(this);
        firstModePanel.setBackground(Color.BLACK);
        JLabel j = new JLabel();
        j.setPreferredSize(new Dimension(400, 450 * 2));
        CustomIcon firstModeIcon = new CustomIcon(Sprite.getMothership(0,0), 400, 450 * 2, true);
        j.add(firstModeIcon);
        firstModePanel.add(j);

        JLabel firstTitle = new JLabel("MODE 1");
        firstTitle.setFont(GraphicStyle.TITLE_FONT);
        firstTitle.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
        firstTitle.setPreferredSize(new Dimension(400, 50));
        firstTitle.setHorizontalAlignment(SwingConstants.CENTER);
        // Layout construction
        GridBagConstraints gbCons = new GridBagConstraints();
        gbCons.gridx = 0;
        gbCons.gridwidth = gbCons.gridheight = 1;
        gbCons.anchor = GridBagConstraints.NORTH;
        gbCons.weightx = 200;
        gbCons.weighty = 20;
        firstModePanel.add(firstTitle, gbCons);

        // Second mode
        secondModePanel = new CustomButton(CustomButton.CustomButtonType.PRIMARY, null, true);
        secondModePanel.setLayout(new GridBagLayout());
        secondModePanel.addActionListener(this);
        secondModePanel.setBackground(Color.RED);
        j = new JLabel();
        j.setPreferredSize(new Dimension(400, 450 * 2));
        CustomIcon secondModeIcon = new CustomIcon(Sprite.getMothership(2,0), 400, 450 * 2, true);
        j.add(secondModeIcon);
        secondModePanel.add(j);

        JLabel secondTitle = new JLabel("MODE 2");
        secondTitle.setFont(GraphicStyle.TITLE_FONT);
        secondTitle.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
        secondTitle.setPreferredSize(new Dimension(400, 50));
        secondTitle.setHorizontalAlignment(SwingConstants.CENTER);
        // Layout construction
        gbCons = new GridBagConstraints();
        gbCons.gridx = 0;
        gbCons.gridwidth = gbCons.gridheight = 1;
        gbCons.anchor = GridBagConstraints.NORTH;
        gbCons.weightx = 200;
        gbCons.weighty = 20;
        secondModePanel.add(secondTitle, gbCons);

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
        String more = "+1";
        String less = "-1";

        for (int i = 0; i < 3; i++) {
            String value = Integer.toString(optionCount[i]);
            if(i==1){
                imagePos = 2;
            }
            if(i == 2){
                imagePos = 1;
                value = "MEDIUM";
                more = "+";
                less = "-";
            }
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
            CustomButton previousMeteorButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, less);
            previousMeteorButton.addActionListener(this);
            optionButtons[i][0] = previousMeteorButton;

            // Count label
            JLabel countLabel = new JLabel(value);
            countLabel.setText(value);
            countLabel.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 50));
            countLabel.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
            countLabel.setHorizontalAlignment(SwingConstants.CENTER);
            optionLabels[i] = countLabel;

            // Next button
            CustomButton nextMeteorButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, more);
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

    private JPanel getReturnPanel(){
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        returnButton = new CustomButton(CustomButton.CustomButtonType.TERTIARY, "Back to admin", true);
        returnButton.addActionListener(this);
        GridBagConstraints gbCons = new GridBagConstraints();
        gbCons.anchor = GridBagConstraints.SOUTHWEST;
        gbCons.weightx = 1;
        gbCons.weighty = 1;
        gbCons.ipadx = 50;
        gbCons.ipady = 30;
        gbCons.insets = new Insets(0, 20, 20, 0);
        mainPanel.add(returnButton, gbCons);
        return mainPanel;
    }

    @Override
    protected void initPanel() {
        this.setBackground(GraphicStyle.PRIMARY_COLOR);
        this.setLayout(new BorderLayout());
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

        if (sourceButton.equals(firstModePanel) || sourceButton.equals(secondModePanel)){
            // Todo when mode is clicked
        }else if (sourceButton.equals(returnButton)){
            // Return to admin
            this.configuration.setMeteorQuantity(optionCount[0]);
            this.configuration.setBlackHoleQuantity(optionCount[1]);
            this.configuration.setObjectGeneration(optionText[optionCount[2]]);
            mainScreen.changeScreen(mainScreen.getAdminPanel());
        }else{
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
                            optionCount[i] ++;
                        }
                        if(i == 2){
                            if(optionCount[i] > 2){
                                optionCount[i]--;
                            }
                            optionLabels[i].setText(optionText[optionCount[i]]);
                        }else{
                            optionLabels[i].setText(String.valueOf(optionCount[i]));
                        }

                        end = true;
                    }
                }
                i ++;
            }
        }
    }

}