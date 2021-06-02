package eu.cifpfbmoll.graphic.canvas;

import eu.cifpfbmoll.graphic.canvas.MainScreen;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends CustomPanel {
    // CONS
    private LayoutManager mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
    private String ADMIN_TEXT = "Admin screen", CLIENT_TEXT = "Client screen";
    private String MAIN_PANEL_TEXT = "Main Panel";
    private String CHECKBOX_ADMIN_TEXT = "Admin";

    // VARS
    private JButton adminButton, clientButton;
    private JCheckBox checkBoxAdmin;

    public StartPanel(MainScreen mainScreen){
        super(mainScreen);
        initPanel();
    }

    @Override
    protected void initPanel() {
        this.setLayout(mainLayout);
        addMainElements();
    }

    @Override
    protected void addMainElements() {
        addMainPanel();
    }

    private void addMainPanel(){
        // Buttons Panel
        adminButton = new JButton(MAIN_PANEL_TEXT);
        // Add action listener to the button
        adminButton.addActionListener(this);
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Checkbox
        checkBoxAdmin = new JCheckBox(CHECKBOX_ADMIN_TEXT);
        checkBoxAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkBoxAdmin.addActionListener(this);

        this.add(adminButton);
        this.add(checkBoxAdmin);
    }

    private void addButtonsPanel(){
        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        adminButton = new JButton(MAIN_PANEL_TEXT);
        //clientButton = new JButton(CLIENT_TEXT);
        // Add action listeners to the buttons
        adminButton.addActionListener(this);
        //clientButton.addActionListener(this);
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(adminButton);
        //buttonsPanel.add(clientButton);
        //this.add(buttonsPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(adminButton)){
            if (checkBoxAdmin.isSelected()){
                //parar scaner de ips
                mainScreen.changeScreen("ADMIN_PANEL");
            }else{
                mainScreen.changeScreen("CLIENT_PANEL");
            }
        }
    }
}
