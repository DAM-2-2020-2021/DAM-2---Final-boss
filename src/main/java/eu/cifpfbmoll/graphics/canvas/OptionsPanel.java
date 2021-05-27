package eu.cifpfbmoll.graphics.canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel implements ActionListener {
    // CONS

    // VARS
    private MainScreen mainScreen;
    private JPanel leftPanel, rightPanel;

    // TEST
    private int meteorCount;
    private JLabel meteorCountLabel;
    private JButton previousMeteor;
    private JButton nextMeteor;
    private JButton saveChanges;

    public OptionsPanel(MainScreen mainScreen){
        this.mainScreen = mainScreen;
        setLayoutMainPanel(1, 3);
        addLeftPanel();
    }

    private void addLeftPanel(){
        leftPanel = new JPanel(new GridLayout(3,1));
        //
        JPanel leftTopPanel = new JPanel(new BorderLayout());
        JPanel leftMidPanel = new JPanel(new BorderLayout());
        JPanel leftBotPanel = new JPanel(new BorderLayout());
        //
        JLabel meteorLabel = new JLabel("METEORITOS");
        JLabel blackHoleLabel = new JLabel("AGUJEROS NEGROS");
        JLabel objectLabel = new JLabel("GENERACIÓN DE OBJETOS");
        meteorLabel.setFont(new Font("Verdana", Font.PLAIN, 34));
        blackHoleLabel.setFont(new Font("Verdana", Font.PLAIN, 34));
        objectLabel.setFont(new Font("Verdana", Font.PLAIN, 34));
        //
        leftTopPanel.add(meteorLabel, BorderLayout.CENTER);
        leftMidPanel.add(blackHoleLabel);
        leftBotPanel.add(objectLabel);
        leftTopPanel.setBackground(Color.MAGENTA);
        leftBotPanel.setBackground(Color.ORANGE);
        //
        JPanel meteorSelector = new JPanel(new FlowLayout());

        previousMeteor = new JButton("-1");
        meteorCountLabel = new JLabel("0");
        nextMeteor = new JButton("+1");
        previousMeteor.addActionListener(this);
        nextMeteor.addActionListener(this);

        meteorSelector.add(previousMeteor);
        meteorSelector.add(meteorCountLabel);
        meteorSelector.add(nextMeteor);
        leftTopPanel.add(meteorSelector, BorderLayout.SOUTH);
        //
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

    private void setLayoutMainPanel(int rows, int cols){
        this.setLayout(new GridLayout(rows, cols));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sourceButton = e.getSource();
        //
        if (sourceButton.equals(previousMeteor)){
            meteorCount--;
            meteorCountLabel.setText(String.valueOf(meteorCount));
        }else if(sourceButton.equals(nextMeteor)){
            meteorCount++;
            meteorCountLabel.setText(String.valueOf(meteorCount));
        }
        if(sourceButton.equals(saveChanges)){
            //mainScreen.theaterMode.configurations.setMeteoriteQuantity(meteorCount);
            mainScreen.changeScreen("ADMIN_PANEL");
        }
    }
}
