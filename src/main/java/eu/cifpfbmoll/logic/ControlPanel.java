package eu.cifpfbmoll.logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private JButton play,play2;
    private JCheckBox checkBox;
    private GridBagConstraints gbc = new GridBagConstraints();

    public ControlPanel(TheaterMode theaterMode){
        setLayout(new GridBagLayout());

        gbc.weightx = 1;
        gbc.weighty = 1;

        this.play = new JButton("PLAY");
        this.play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(theaterMode.isAdmin()){
                    System.out.println("Im Admin");
                    //start admin window
                }else{
                    System.out.println("Im Waiting");
                    //start waiting room
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(play, gbc);



        this.checkBox = new JCheckBox("ADMINISTRADOR");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox chBox = (JCheckBox) e.getSource();
                if(chBox.isSelected()){
                    System.out.println("Is selected");
                    theaterMode.setAdmin(true);
                    System.out.println(theaterMode.isAdmin());
                }else {
                    System.out.println("No selected");
                    theaterMode.setAdmin(false);
                    System.out.println(theaterMode.isAdmin());
                }
            }
        });
        add(checkBox, gbc);

        this.play2 = new JButton("BEGIN");
        this.play2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theaterMode.startGame();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        this.play2.setVisible(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(play2, gbc);

    }

    public void addPlayButton(){
        play2.setVisible(true);
    }

    public void hidePlayButton(){
        play2.setVisible(false);
    }
}
