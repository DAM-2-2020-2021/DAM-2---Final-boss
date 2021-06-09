package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.graphic.component.CustomButton;
import eu.cifpfbmoll.graphic.sprite.Sprite;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class StartPanel extends CustomPanel {
    // CONS
    private final LayoutManager mainLayout = new GridBagLayout();
    private final String ADMIN_TEXT = "Admin screen", CLIENT_TEXT = "Client screen";
    private final String MAIN_PANEL_TEXT = "START";
    private final String CHECKBOX_ADMIN_TEXT = "Are you admin?";
    private final String TITLE = "D A M N";

    // VARS
    private JButton adminButton, clientButton;
    private JCheckBox checkBoxAdmin;
    private BufferedImage backgroundImage;
    private GridBagConstraints gbCons;

    public StartPanel(MainScreen mainScreen){
        super(mainScreen);
        initPanel();
    }

    private void addMainPanel(){
        // Label
        JLabel titleLabel = new JLabel(TITLE);
        //titleLabel.setBorder(new LineBorder(GraphicStyle.TEXT_YELLOW_COLOR, 4, true));
        titleLabel.setFont(GraphicStyle.TITLE_GAME_FONT);
        titleLabel.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);

        // Buttons Panel
        adminButton = new CustomButton(CustomButton.CustomButtonType.PRIMARY, MAIN_PANEL_TEXT);
        adminButton.setPreferredSize(new Dimension(500, 200));
        // Add action listener to the button
        adminButton.addActionListener(this);

        // Checkbox
        checkBoxAdmin = new JCheckBox(CHECKBOX_ADMIN_TEXT);
        checkBoxAdmin.addActionListener(this);

        gbCons = new GridBagConstraints();
        gbCons.weightx = 1;
        gbCons.weighty = 1;
        gbCons.gridx = 0;
        gbCons.gridy = 0;
        this.add(titleLabel, gbCons);
        gbCons.gridy = 1;
        this.add(adminButton, gbCons);
        gbCons.gridy = 2;
        this.add(checkBoxAdmin, gbCons);
    }

    public void hideCheckbox(){
        this.checkBoxAdmin.setSelected(false);
        this.checkBoxAdmin.setVisible(false);
    }

    @Override
    protected void initPanel() {
        this.setLayout(mainLayout);
        this.setBackgroundImage(Sprite.getMenuBg());
        addMainElements();
    }

    @Override
    protected void addMainElements() {
        addMainPanel();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(backgroundImage, 0, 0, null);
        final int R = 149;
        final int G = 205;
        final int B = 191;
        Paint p =
                new GradientPaint(0.0f, (getHeight() - 100), new Color(R, G, B, 0),
                        0.0f, getHeight(), new Color(R, G, B, 165), false);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(p);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(adminButton)){
            if (checkBoxAdmin.isSelected()){
                mainScreen.changeScreen(mainScreen.getAdminPanel());
            }else{
                mainScreen.changeScreen(mainScreen.getClientPanel());
            }
        }else if(sourceButton.equals(checkBoxAdmin)){
            if (checkBoxAdmin.isSelected()){
                this.getMainScreen().theaterMode.setImAdmin(true);
            }else{
                this.getMainScreen().theaterMode.setImAdmin(false);
            }
        }
    }

    public void setBackgroundImage(BufferedImage backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
