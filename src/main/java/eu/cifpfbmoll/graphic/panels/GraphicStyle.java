package eu.cifpfbmoll.graphic.panels;

import java.awt.*;

public class GraphicStyle {
    // COLORS - Relevance order
    public static final Color PRIMARY_COLOR = new Color(12, 22, 79);
    public static final Color SECONDARY_COLOR = new Color(29, 17, 53);
    public static final Color DANGER_COLOR = new Color(186, 30, 104);
    public static final Color TERTIARY_COLOR = new Color(86, 67, 253);
    public static final Color HELPER_COLOR = new Color(118, 73, 254);
    public static final Color BLACK_COLOR = Color.BLACK;



    public static final Color TEXT_YELLOW_COLOR = new Color(255, 220, 96);
    public static final Color TEXT_YELLOW_BORDER_COLOR = new Color(211, 141, 36);
    public static final Color GLOW_BLUE_COLOR = new Color(133, 210, 232);
    public static final Color GLOW_SELECTED_COLOR = TEXT_YELLOW_COLOR;
//    public static final Color PRIMARY_COLOR = new Color(12, 22, 79);
//    public static final Color SECONDARY_COLOR = new Color(29, 17, 53);
//    public static final Color DANGER_COLOR = new Color(186, 30, 104);
//    public static final Color TERTIARY_COLOR = new Color(86, 67, 253);
//    public static final Color HELPER_COLOR = new Color(118, 73, 254);
//    public static final Color WHITE_COLOR = new Color(255, 255, 255);

    // Teams
    public static final Color TEAM_RED_PANEL_COLOR = new Color (207, 27, 27);
    public static final Color TEAM_BLUE_PANEL_COLOR = new Color (27, 92, 207);
    public static final Color TEAM_RED_TEXTPANEL_COLOR = Color.RED;
    public static final Color TEAM_BLUE_TEXTPANEL_COLOR = Color.BLUE;

    // Buttons
    public static final Color BUTTON_HOVER_COLOR = DANGER_COLOR;

    // Hud
    public static final Color HUD_OUTGAME_BACKGROUND_COLOR = TERTIARY_COLOR;
    public static final Color HUD_INGAME_BACKGROUND_COLOR = GLOW_BLUE_COLOR;

    // FONTS
    // Start
    public static final Font TITLE_GAME_FONT = new Font(Font.DIALOG_INPUT, Font.BOLD, 120);

    // Logs
    public static final Font TITLE_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 34);
    public static final Font ANY_LOG_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 13);
    public static final Font TEAM_LOG_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 13);

    // Buttons
    public static final Font BUTTON_FONT_1 = new Font(Font.DIALOG_INPUT, Font.BOLD, 35);
    public static final Font BUTTON_FONT_2 = new Font(Font.DIALOG_INPUT, Font.PLAIN, 23);

    // Hud
    public static final Font TEAM_HUD_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 30);
    public static final Font INFO_HUD_FONT = new Font(Font.DIALOG_INPUT, Font.PLAIN, 30);
    public static final Font INFO_INGAME_HUD_FONT = new Font(Font.DIALOG_INPUT, Font.BOLD, 20);
}