package eu.cifpfbmoll.graphic.canvas;

import eu.cifpfbmoll.graphic.component.ScreenComponent;
import eu.cifpfbmoll.sound.Sound;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel extends CustomPanel{
    // CONST
    private final int PANEL_ROWS = 0, PANEL_COLUMN = 2;
    private LayoutManager mainLayout = new GridLayout(PANEL_ROWS, PANEL_COLUMN);
    private final String PLAY_TEXT = "PLAY", OPTIONS_TEXT = "OPTIONS", PLAYERS_TEXT = "PLAYERS",
            ADD_ROW_TEXT = "ADD ROW", RMV_ROW_TEXT = "DELETE ROW",
            ADD_COL_TEXT = "ADD COLUMN", RMV_COL_TEXT = "DELETE COLUMN",
            SCREEN_TEXT = "SELECT SCREEN";
    private final int SCREEN_QUANTITY = 3;
    private final int SCREEN_ROWS = 3, SCREEN_COLUMNS = 2;

    // VARS
    //Components
    public JButton buttonPlay;
    private JButton buttonOptions;
    private JButton buttonPlayers;
    // Panels
    private JPanel buttonsPanel;
    private JPanel rightPanel;
    private JPanel screenSelectionPanel;
    private JPanel screenControlPanel;
    private JPanel screenOptionsPanel;
    private JScrollPane logPanel;

    // Test
    private int screenSelectionRows = SCREEN_ROWS;
    private int screenSelectionColumns = SCREEN_COLUMNS;

    public AdminPanel(MainScreen mainScreen){
        super(mainScreen);
        initPanel();    // Init the panel
    }

    @Override
    protected void initPanel() {
        this.setLayout(mainLayout);
        addMainElements();
    }

    @Override
    protected void addMainElements() {
        addButtonsPanel();
        addScreenPanel(screenSelectionRows, screenSelectionColumns);
        addLogPanel();
    }

    //<editor-fold desc="ADDERS">
    private void addButtonsPanel() {
        // Create a new nested panel
        buttonsPanel = new JPanel();
        int padding = 80;
        buttonsPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        buttonsPanel.setLayout(new GridLayout(3, 0));

        // Create the buttons
        buttonPlay = new JButton(PLAY_TEXT);
        buttonPlay.setEnabled(false);
        buttonOptions = new JButton(OPTIONS_TEXT);
        buttonPlayers = new JButton(PLAYERS_TEXT);
        // Add action listeners to the buttons

        buttonPlay.addActionListener(this);
        buttonOptions.addActionListener(this);
        buttonPlayers.addActionListener(this);
        // Add the buttons to the panel
        buttonsPanel.add(buttonPlay);
        buttonsPanel.add(buttonOptions);
        buttonsPanel.add(buttonPlayers);
        //buttonsPanel.setBackground(Color.black);
        this.add(buttonsPanel);
    }

    private void addScreenPanel(int screenRows, int screenColumns){
        // Main panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(2, 0));

        //
        screenOptionsPanel = new JPanel();
        screenOptionsPanel.setLayout(new BorderLayout());

        // Add right panel - screen control
        addScreenControlPanel();

        // Add left panel - screen selection
        addScreenSelectionPanel(screenRows, screenColumns);

        rightPanel.add(screenOptionsPanel);

        // Add main panel to parent
        this.add(rightPanel);
    }

    private void addScreenControlPanel(){
        screenControlPanel = new JPanel();
        screenControlPanel.setLayout(new GridLayout(1, 5));
        int padding = 20;
        screenControlPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        // Right panel buttons
        JButton screenControlAddRowButton = new JButton(ADD_ROW_TEXT);
        JButton screenControlRemoveRowButton = new JButton(RMV_ROW_TEXT);
        JButton screenControlAddColumnButton = new JButton(ADD_COL_TEXT);
        JButton screenControlRemoveColumnButton = new JButton(RMV_COL_TEXT);
        JButton screenButton = new JButton(SCREEN_TEXT);
        screenControlRemoveRowButton.addActionListener(this);
        screenControlAddColumnButton.addActionListener(this);
        screenControlAddRowButton.addActionListener(this);
        screenControlRemoveColumnButton.addActionListener(this);
        screenButton.addActionListener(this);
        screenControlPanel.add(screenControlAddRowButton);
        screenControlPanel.add(screenControlRemoveRowButton);
        screenControlPanel.add(screenControlAddColumnButton);
        screenControlPanel.add(screenControlRemoveColumnButton);
        screenControlPanel.add(screenButton);

        // Finally add both panels to the main one
        screenOptionsPanel.add(screenControlPanel, BorderLayout.PAGE_START);
    }

    private void addScreenSelectionPanel(int rows, int columns){
        // Left panel - screen selection
        screenSelectionPanel = new JPanel();
        screenSelectionPanel.setLayout(new GridLayout(rows, columns));
        screenSelectionPanel.setBorder(new LineBorder(Color.ORANGE, 5, false));
        int padding = 20;
        screenSelectionPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                ScreenComponent screenComponent = new ScreenComponent();
                screenSelectionPanel.add(screenComponent);
            }
        }
        screenOptionsPanel.add(screenSelectionPanel, BorderLayout.CENTER);
    }

    private void addLogPanel(){
        Font font = new Font("Dialog", Font.BOLD + Font.ITALIC, 14);    // Font to be used
        JTextArea textArea = new JTextArea (10,30);
        logPanel = new JScrollPane(textArea);
        int padding = 20;
        logPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        // Set the font, background and font color
        logPanel.getViewport().getView().setFont(font);
        logPanel.getViewport().getView().setBackground(Color.black);
        logPanel.getViewport().getView().setForeground(Color.white);

        rightPanel.add(logPanel);
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    private void setBackgroundColor(Color backgroundColor){
        setBackground(backgroundColor);
    }
    //</editor-fold>

    /**
     * Checks the screenComponents grid and returns a list containing the position of the selected ones.
     */
    private List<Integer> getScreenComponentPositions(){
        // Todo return array containing screens positions on click "SELECCIONAR PANTALLAS"
        // Get all components from the screenControlPanel
        Component[] components = screenSelectionPanel.getComponents();
        List<Integer> componentPositions = new ArrayList();
        int j = 0;
        for (int componentIndex = 0; componentIndex < components.length; componentIndex++) {
            if (( (ScreenComponent) components[componentIndex]).isSelected()){
                componentPositions.add(componentIndex);
                j++;
            }
        }
        return componentPositions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Sound.soundInteractueMenu();
        final String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case PLAY_TEXT: {
                // Todo on click "JUGAR"
                mainScreen.changeScreen("GAME");
                break;
            }
            case OPTIONS_TEXT: {
                // Todo on click "OPCIONES"
                mainScreen.changeScreen("OPTIONS_PANEL");
                break;
            }
            case PLAYERS_TEXT: {
                // Todo on click "JUGADORES"
                mainScreen.changeScreen("PLAYERS_PANEL");
                break;
            }
            case SCREEN_TEXT:
                // Todo on click "SELECCIONAR PANTALLAS"
                //List<Integer> screenPositions = getScreenComponentPositions();
//                getFixedComponentPositions();
//                for (int i = 0; i < screenPositions.size(); i++) {
//                    System.out.println(screenPositions.get(i));
//                }
                break;
            case ADD_ROW_TEXT:
                screenSelectionRows++;
                screenOptionsPanel.remove(screenSelectionPanel);
                addScreenSelectionPanel(screenSelectionRows, screenSelectionColumns);
                break;
            case RMV_ROW_TEXT:
                screenSelectionRows--;
                screenOptionsPanel.remove(screenSelectionPanel);
                addScreenSelectionPanel(screenSelectionRows, screenSelectionColumns);
                break;
            case ADD_COL_TEXT:
                screenSelectionColumns++;
                screenOptionsPanel.remove(screenSelectionPanel);
                addScreenSelectionPanel(screenSelectionRows, screenSelectionColumns);
                break;
            case RMV_COL_TEXT:
                screenSelectionColumns--;
                screenOptionsPanel.remove(screenSelectionPanel);
                addScreenSelectionPanel(screenSelectionRows, screenSelectionColumns);
                break;
        }
    }

    /*private void getFixedComponentPositions(){
        // Get all components from the screenControlPanel
        List<Component> components = new ArrayList<>(Arrays.asList(screenSelectionPanel.getComponents()));
        System.out.println(components.size());
        int screenSelectionRows = this.screenSelectionRows;
        int screenSelectionColumns = this.screenSelectionColumns;
        // First delete arrows
        for (int row = 0; row < screenSelectionRows; row++) {
            System.out.println("new row");
            boolean busyRow = false;
            for (int column = 0; column < screenSelectionColumns; column++) {
                System.out.println("Fila "+ row +"  Columna " + column + " " + (row * screenSelectionColumns + column));
                if(( (ScreenComponent) components.get(row * screenSelectionColumns + column)).isSelected()){
                    busyRow = true;
                }
            }
            if(!busyRow){
                // Delete the row
                System.out.println("buse deleted");
                components.subList(row * screenSelectionColumns,
                        row * screenSelectionColumns + screenSelectionColumns ).clear();
//                components.remove(0);
                row--;
//                rowOffset++;
            }
        }
        System.out.println(components.size());
        // Delete columns
        for (int i = 0; i < screenSelectionRows; i++) {
            int emptyRow = 0;
            for (int j = 0; j < screenSelectionColumns; j++) {
                int pos = screenSelectionColumns * i + j;
                if(( (ScreenComponent) components[pos]).isSelected()){
                    emptyRow++;
                }
            }
            if(emptyRow == screenSelectionColumns){
                // The row is empty
            }
        }
        return (Component[]) fixedComponents.toArray();*//*
    }*/

}
