package eu.cifpfbmoll.graphic.component;

import eu.cifpfbmoll.graphic.panels.GraphicStyle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButton extends JButton implements MouseListener{
    // CONS

    // VARS
    private CustomButtonType customButtonType;
    private boolean enabled;

    /**
     * Default constructor. By default the button is enabled.
     * @param customButtonType
     * @param text
     */
    public CustomButton(CustomButtonType customButtonType, String text){
        this.customButtonType = customButtonType;
        this.setText(text);
        // By default the button is enabled
        this.setEnabled(true);
    }

    public CustomButton(CustomButtonType customButtonType, String text, boolean enabled){
        this.customButtonType = customButtonType;
        this.setText(text);
        this.setEnabled(enabled);
    }

    //<editor-fold desc="MOUSE EVENTS">
    @Override
    public void mouseClicked(MouseEvent e) {
        this.setBackground(GraphicStyle.BUTTON_HOVER_COLOR);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBorder(customButtonType.getHoverBorder());
        this.setBackground(customButtonType.getHoverColor());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBorder(customButtonType.getBorder());
        this.setBackground(customButtonType.getBackgroundColor());
    }
    //</editor-fold>

    public void setCustomButtonType(CustomButtonType customButtonType) {
        this.customButtonType = customButtonType;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if(enabled == true){
            // Add the mouse listener
            addMouseListener(this);
            this.enabled = true;
            this.setBackground(customButtonType.getBackgroundColor());
            this.setFont(customButtonType.getFont());
            this.setForeground(customButtonType.getFontColor());
            this.setBorder(customButtonType.getBorder());
        }else{
            System.out.println("disabled");
            // Remove the mouse listener
            removeMouseListener(this);
            this.enabled = false;
            this.setBackground(Color.GRAY);
            this.setFont(customButtonType.getFont());
            this.setForeground(Color.BLACK);
            this.setBorder(null);
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public enum CustomButtonType{
        // CustomButtonType has backgroundColor, hoverColor, font, fontColor, border, hoverBorder
        PRIMARY(GraphicStyle.SECONDARY_COLOR, GraphicStyle.BUTTON_HOVER_COLOR, GraphicStyle.BUTTON_FONT_1,
                GraphicStyle.TEXT_YELLOW_COLOR, new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 4, false),
                new LineBorder(GraphicStyle.GLOW_SELECTED_COLOR, 4, false)),
        SECONDARY(GraphicStyle.PRIMARY_COLOR, GraphicStyle.BUTTON_HOVER_COLOR, GraphicStyle.BUTTON_FONT_2,
                GraphicStyle.TEXT_YELLOW_COLOR, new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 4, false),
                new LineBorder(GraphicStyle.GLOW_SELECTED_COLOR, 4, false)),
        TERTIARY(GraphicStyle.TERTIARY_COLOR, GraphicStyle.BUTTON_HOVER_COLOR, GraphicStyle.BUTTON_FONT_2,
                GraphicStyle.TEXT_YELLOW_COLOR, new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 4, false),
                new LineBorder(GraphicStyle.GLOW_SELECTED_COLOR, 4, false)),
        DANGER(GraphicStyle.PRIMARY_COLOR, GraphicStyle.BUTTON_HOVER_COLOR, GraphicStyle.ANY_LOG_FONT,
                GraphicStyle.TEXT_YELLOW_COLOR, new LineBorder(GraphicStyle.GLOW_BLUE_COLOR, 4, false),
                new LineBorder(GraphicStyle.GLOW_SELECTED_COLOR, 4, false));

        private Color backgroundColor, hoverColor, fontColor;
        private Font font;
        private Border border, hoverBorder;

        CustomButtonType(Color backgroundColor, Color hoverColor, Font font, Color fontColor, Border border, Border hoverBorder){
            this.backgroundColor = backgroundColor;
            this.hoverColor = hoverColor;
            this.font = font;
            this.fontColor = fontColor;
            this.border = border;
            this.hoverBorder = hoverBorder;
        }

        public Color getBackgroundColor() {
            return backgroundColor;
        }

        public Color getFontColor() {
            return fontColor;
        }

        public Font getFont() {
            return font;
        }

        public Color getHoverColor() {
            return hoverColor;
        }

        public Border getBorder() {
            return border;
        }

        public Border getHoverBorder() {
            return hoverBorder;
        }
    }
}
