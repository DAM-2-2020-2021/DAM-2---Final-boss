package eu.cifpfbmoll.graphic.component;

import eu.cifpfbmoll.graphic.panels.GraphicStyle;

import javax.swing.*;
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
        this.setBackground(customButtonType.getBackgroundColor());
        this.setFont(customButtonType.getFont());
        this.setForeground(customButtonType.fontColor);
        this.setText(text);
        // Add the mouse listeners
        addMouseListener(this);
        this.setEnabled(false);
        this.setBorder(new LineBorder(GraphicStyle.TERTIARY_COLOR, 4, false));
    }

    public CustomButton(CustomButtonType customButtonType, String text, boolean enabled){
        this.customButtonType = customButtonType;
        this.setBackground(customButtonType.getBackgroundColor());
        this.setFont(customButtonType.getFont());
        this.setForeground(customButtonType.fontColor);
        this.setText(text);
        // Add the mouse listeners
        addMouseListener(this);
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
        this.setBorder(new LineBorder(GraphicStyle.HELPER_COLOR, 4, false));
        this.setBackground(GraphicStyle.BUTTON_HOVER_COLOR);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBorder(null);
        this.setBackground(customButtonType.backgroundColor);
    }
    //</editor-fold>

    public void setCustomButtonType(CustomButtonType customButtonType) {
        this.customButtonType = customButtonType;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public enum CustomButtonType{
        // CustomButtonType has backgroundColor, font, fontColor and hoverColor
        PRIMARY(GraphicStyle.HELPER_COLOR, GraphicStyle.BUTTON_FONT_1, GraphicStyle.WHITE_COLOR),
        SECONDARY(GraphicStyle.PRIMARY_COLOR, GraphicStyle.BUTTON_FONT_2, GraphicStyle.WHITE_COLOR),
        TERTIARY(GraphicStyle.TERTIARY_COLOR, GraphicStyle.ANY_LOG_FONT, GraphicStyle.WHITE_COLOR),
        DANGER(GraphicStyle.PRIMARY_COLOR, GraphicStyle.ANY_LOG_FONT, GraphicStyle.WHITE_COLOR);

        private Color backgroundColor, fontColor;
        private Font font;

        CustomButtonType(Color backgroundColor, Font font, Color fontColor){
            this.backgroundColor = backgroundColor;
            this.font = font;
            this.fontColor = fontColor;
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
    }
}
