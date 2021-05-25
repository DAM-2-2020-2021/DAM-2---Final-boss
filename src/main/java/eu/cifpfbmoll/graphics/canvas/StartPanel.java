package eu.cifpfbmoll.graphics.canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel implements ActionListener {
    // CONS
    private String ADMIN_TEXT = "Admin screen", CLIENT_TEXT = "Client screen";
    // VARS
    private MainScreen mainScreen;
    private JButton adminButton, clientButton;

    public StartPanel(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        setLayoutMainPanel(1, 2);
        addButtonsPanel();
    }

    private void addButtonsPanel(){
        adminButton = new JButton(ADMIN_TEXT);
        clientButton = new JButton(CLIENT_TEXT);
        // Add action listeners to the buttons
        adminButton.addActionListener(this);
        clientButton.addActionListener(this);
        this.add(adminButton);
        this.add(clientButton);
    }

    private void setLayoutMainPanel(int rows, int cols){
        this.setLayout(new GridLayout(rows, cols));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(adminButton)){
            mainScreen.changeScreen("ADMIN_PANEL");
        }else if(sourceButton.equals(clientButton)){
            mainScreen.changeScreen("CLIENT_PANEL");
        }
    }
}
