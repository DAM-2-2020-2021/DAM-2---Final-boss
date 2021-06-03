package eu.cifpfbmoll.graphic.panels;

import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

    public void hideCheckbox(){
        this.checkBoxAdmin.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(adminButton)){
            if (checkBoxAdmin.isSelected()){
                //Todo parar scaner de IPS
                this.getMainScreen().theaterMode.setImAdmin(true);
                mainScreen.changeScreen(mainScreen.getAdminPanel());
            }else{
                mainScreen.changeScreen(mainScreen.getClientPanel());
            }
        }
    }
}
