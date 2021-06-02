package eu.cifpfbmoll.graphic.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class CustomPanel extends JPanel implements ActionListener {
    // CONS

    // VARS
    protected MainScreen mainScreen;
    protected int paddingHeight = 0, paddingWidth = 0;

    public CustomPanel(MainScreen mainScreen){
        this.mainScreen = mainScreen;
    }

    public CustomPanel(MainScreen mainScreen, LayoutManager mainLayout){
        this.mainScreen = mainScreen;
        this.setLayout(mainLayout);
    }

    public CustomPanel(MainScreen mainScreen, int paddingHeight, int paddingWidth){
        this.mainScreen = mainScreen;
        this.paddingHeight = paddingHeight;
        this.paddingWidth = paddingWidth;
    }

    public CustomPanel(MainScreen mainScreen, LayoutManager mainLayout, int paddingHeight, int paddingWidth){
        this.mainScreen = mainScreen;
        this.setLayout(mainLayout);
        this.paddingHeight = paddingHeight;
        this.paddingWidth = paddingWidth;
    }

    //<editor-fold desc="GETTERS">
    public MainScreen getMainScreen() {
        return mainScreen;
    }

    public int getPaddingHeight() {
        return paddingHeight;
    }

    public int getPaddingWidth() {
        return paddingWidth;
    }

    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    protected void setPaddingSize(int paddingHeight, int paddingWidth){
        this.paddingHeight = paddingHeight;
        this.paddingWidth = paddingWidth;
        this.setBorder(new EmptyBorder(paddingHeight, paddingWidth, paddingHeight, paddingWidth));
    }

    public void setPaddingHeight(int paddingHeight) {
        this.paddingHeight = paddingHeight;
    }

    public void setPaddingWidth(int paddingWidth) {
        this.paddingWidth = paddingWidth;
    }
    //</editor-fold>

    /**
     * Initializes the panel and its content.
     */
    protected abstract void initPanel();

    /**
     *  Adds the top elements to the panel.
     */
    protected abstract void addMainElements();

    @Override
    public void actionPerformed(ActionEvent e) {
        // Nothing by default
    }
}
