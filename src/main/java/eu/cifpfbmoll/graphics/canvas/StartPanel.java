package eu.cifpfbmoll.graphics.canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel implements ActionListener {
    // CONS
    private String ADMIN_TEXT = "Admin screen", CLIENT_TEXT = "Client screen";
    private String MAIN_PANEL_TEXT = "Main Panel";
    private String CHECKBOX_ADMIN_TEXT = "Admin";

    // VARS
    private MainScreen mainScreen;
    private JButton adminButton, clientButton;
    private  JCheckBox checkBoxAdmin;

    public StartPanel(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        setLayoutMainPanel();
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

    private void setLayoutMainPanel(){
        //this.setLayout(new GridLayout(rows, cols));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(adminButton)){
            if (checkBoxAdmin.isSelected()){
                mainScreen.changeScreen("ADMIN_PANEL");
            }else{
                mainScreen.changeScreen("CLIENT_PANEL");
            }
        }
    }
}

