package eu.cifpfbmoll.graphic.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HudPanel extends CustomPanel  {
    // CONS
    private int PADDING_TOP = 30, PADDING_LEFT = 40;

    // VARS
    private Hud hud;
    //    private JPanel hudPanel, parentPanel;
    private HudType hudType;
    private String clientId, clientIp;
    private int blueScore, redScore;
    private int meteorAmount, blackHoleAmount, powerupAmount;
    private GridBagConstraints gbCons;

    public HudPanel(MainScreen mainScreen, HudType hudType){
        super(mainScreen);
        this.hudType = hudType;
        this.clientId = Integer.toString(mainScreen.theaterMode.getMyID());
        this.clientIp = mainScreen.theaterMode.getIP();
        initPanel();
    }

    public HudPanel(MainScreen mainScreen, HudType hudType, String clientId, String clientIp){
        super(mainScreen);
        this.hudType = hudType;
        this.clientId = clientId;
        this.clientIp = clientIp;
        initPanel();
    }

    private void setDefaultLayout(){
        gbCons = new GridBagConstraints();
        gbCons.anchor = GridBagConstraints.NORTHWEST;
        gbCons.weightx = 1;
        gbCons.weighty = 1;
        gbCons.ipadx = hudType.getWidth();
        gbCons.ipady = hudType.getHeight();
        gbCons.insets = new Insets(PADDING_TOP, PADDING_LEFT, 0, 0);
    }

    // INHERIT
    @Override
    protected void initPanel() {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        setDefaultLayout();
        addMainElements();
    }

    @Override
    protected void addMainElements() {
        initComponents();
    }

    private void initComponents(){
        hud = new Hud();
        this.add(hud, gbCons);
//        hud.setSize(300, 300);
//        parentPanel = new JPanel(new GridLayout(1,1));
//        parentPanel.setOpaque(false);
//        parentPanel.add(hud);
//        Graphics g2 = hudPanel.getGraphics();
//        g2.setColor(Color.white);
//        g2.drawLine(0, 0, 200, 200);
    }

    //<editor-fold desc="GETTERS">
    public String getClientId() {
        return clientId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getRedScore() {
        return redScore;
    }

    public int getMeteorAmount() {
        return meteorAmount;
    }

    public int getBlackHoleAmount() {
        return blackHoleAmount;
    }

    public int getPowerupAmount() {
        return powerupAmount;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public void setBlueScore(int blueScore) {
        this.blueScore = blueScore;
    }

    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }

    public void setMeteorAmount(int meteorAmount) {
        this.meteorAmount = meteorAmount;
    }

    public void setBlackHoleAmount(int blackHoleAmount) {
        this.blackHoleAmount = blackHoleAmount;
    }

    public void setPowerupAmount(int powerupAmount) {
        this.powerupAmount = powerupAmount;
    }
    //</editor-fold>

    /**
     *
     */
    public enum HudType{
        INGAME(200, 300, 50),
        OUTGAME(50, 50, 100);

        private int height, width;
        private int opacity;

        HudType (int height, int width, int opacity){
            this.height = height;
            this.width = width;
            this.opacity = opacity;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getOpacity() {
            return opacity;
        }
    }

    /**
     *
     */
    public class Hud extends JPanel implements MouseListener {
        private boolean hovered = false;

        public Hud(){
            this.setOpaque(true);
            this.setBackground(GraphicStyle.SECONDARY_COLOR);
            setHudPanel();
            this.addMouseListener(this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            if(hovered){
                Graphics2D graphics2d = (Graphics2D) g;
                graphics2d.setComposite(AlphaComposite.SrcOver.derive(0.2f));
                graphics2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        public JPanel setHudPanel(){
            JPanel returnPanel = new JPanel();
            switch(hudType){
                case INGAME:
                    setInGamePanel();
                    break;
                case OUTGAME:
                    setOutGamePanel();
                    break;
            }
            return returnPanel;
        }

        private void setOutGamePanel(){
            this.setLayout(new GridLayout(2, 1));
            // Background and opacity
            this.setBackground(GraphicStyle.BLACK_COLOR);
            this.setBorder(new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 5, false));
            // Add the id and ip
            JLabel idLabel = new JLabel("ID: " + clientId, SwingConstants.CENTER);
            idLabel.setFont(GraphicStyle.INFO_HUD_FONT);
            idLabel.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
            idLabel.setOpaque(true);
            idLabel.setBackground(GraphicStyle.BLACK_COLOR);
            this.add(idLabel);
            JLabel ipLabel = new JLabel("IP: " + clientIp, SwingConstants.CENTER);
            ipLabel.setFont(GraphicStyle.INFO_HUD_FONT);
            ipLabel.setForeground(GraphicStyle.TEXT_YELLOW_COLOR);
            ipLabel.setOpaque(true);
            ipLabel.setBackground(GraphicStyle.BLACK_COLOR);
            this.add(ipLabel);
        }

        private void setInGamePanel(){
            this.setLayout(new GridLayout(3, 1));
//        hudPanel.setMaximumSize(new Dimension(100, 100));
            // Background and opacity
            this.setBackground(new Color(GraphicStyle.HUD_INGAME_BACKGROUND_COLOR.getRed(), GraphicStyle.HUD_INGAME_BACKGROUND_COLOR.getGreen(),
                    GraphicStyle.HUD_INGAME_BACKGROUND_COLOR.getBlue(), hudType.getOpacity()));
            // Add the name
            JPanel infoPanel = getInfoPanel();
            for (Component component: infoPanel.getComponents()){
                component.setFont(GraphicStyle.INFO_INGAME_HUD_FONT);
                component.setForeground(GraphicStyle.GLOW_BLUE_COLOR);
            }
            this.add(infoPanel);
            //
            this.add(getTeamsPanel());
            //
            this.add(new JLabel("PC 0"));
        }

        private JPanel getTeamsPanel(){
            JPanel teamsPanel = new JPanel(new GridLayout(1, 2));
            //teamsPanel.setBackground(Color.BLUE);
            //
            JLabel blueLabel = new JLabel(String.valueOf(blueScore), SwingConstants.CENTER);
            blueLabel.setFont(GraphicStyle.TEAM_HUD_FONT);
            blueLabel.setForeground(Color.WHITE);
            //blueLabel.setBorder(new EmptyBorder(0, 0, 0, 30));
            blueLabel.setBackground(GraphicStyle.TEAM_BLUE_PANEL_COLOR);
            blueLabel.setOpaque(true);
            teamsPanel.add(blueLabel);
            //
            JLabel redLabel = new JLabel(String.valueOf(redScore), SwingConstants.CENTER);
            redLabel.setFont(GraphicStyle.TEAM_HUD_FONT);
            redLabel.setForeground(Color.WHITE);
            //redLabel.setBorder(new EmptyBorder(0, 0, 0, 30));
            redLabel.setBackground(GraphicStyle.TEAM_RED_PANEL_COLOR);
            redLabel.setOpaque(true);
            teamsPanel.add(redLabel);
            return teamsPanel;
        }

        private JPanel getInfoPanel(){
            JPanel hudPanel = new JPanel();
            hudPanel.setPreferredSize(new Dimension(10, 10));
            hudPanel.setLayout(new BoxLayout(hudPanel, BoxLayout.Y_AXIS));
            hudPanel.setOpaque(false);
            hudPanel.add(Box.createRigidArea(new Dimension(0, 25)));
            // Add the id and ip
            JLabel idLabel = new JLabel("ID: " + clientId, SwingConstants.CENTER);

            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            hudPanel.add(idLabel);
            JLabel ipLabel = new JLabel("IP: " + clientIp, SwingConstants.CENTER);
            hudPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            ipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            hudPanel.add(ipLabel);

            //
            return hudPanel;
        }

        //<editor-fold desc="MOUSE METHODS">
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            System.out.println("entered");
            hovered = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            System.out.println("exit");
            hovered = false;
        }
        //</editor-fold>
    }
}


