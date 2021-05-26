package eu.cifpfbmoll.graphic.canvas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientPanel extends JPanel implements ActionListener {
    // CONS
    private final int panelPadding = 30;
    // VARS
    private MainScreen mainScreen;
    private int screenNumber;
    private JScrollPane logPanel;
    private JPanel redTeamPanel, blueTeamPanel;

    public ClientPanel(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        setLayoutMainPanel(1, 3);   // Set the main layout
        addElementsToMain();
    }

    private void addElementsToMain(){
        addReadTeamPanel();
        addBlueTeamPanel();
        addLogPanel();
    }

    private void addReadTeamPanel(){
        redTeamPanel = new JPanel();
        redTeamPanel.setBackground(Color.RED);
        redTeamPanel.setBorder(new EmptyBorder(panelPadding, panelPadding, panelPadding, panelPadding));
        this.add(redTeamPanel);
    }

    private void addBlueTeamPanel(){
        blueTeamPanel = new JPanel();
        blueTeamPanel.setBackground(Color.BLUE);
        blueTeamPanel.setBorder(new EmptyBorder(panelPadding, panelPadding, panelPadding, panelPadding));
        this.add(blueTeamPanel);
    }

    private void addLogPanel(){
        Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);    // Font to be used
        JTextArea textArea = new JTextArea (10,30);
        logPanel = new JScrollPane(textArea);
        logPanel.setBorder(new EmptyBorder(panelPadding, panelPadding, panelPadding, panelPadding));

        // Set the font, background and font color
        logPanel.getViewport().getView().setFont(font);
        logPanel.getViewport().getView().setBackground(Color.black);
        logPanel.getViewport().getView().setForeground(Color.white);

        this.add(logPanel);
    }

    private void setLayoutMainPanel(int rows, int cols){
        this.setLayout(new GridLayout(rows, cols));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
