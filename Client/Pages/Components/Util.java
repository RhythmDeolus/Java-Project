package Client.Pages.Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;

public class Util {
    public static Font questionFont = new Font("Arial", Font.BOLD, 25);
    public static Font examTitleFont = new Font("Arial", Font.BOLD, 30);
    public static Font optionFont = new Font("Arial", Font.PLAIN, 20);
    public static Font uiSmallFont = new Font("Arial", Font.PLAIN, 16);
    public static Font uiNormalFont = new Font("Arial", Font.PLAIN, 20);
    public static Font uiBigFont = new Font("Arial", Font.PLAIN, 30);
    public static Color currentQuestionColor = new Color(122, 168, 116);
    public static Color markedQuestionColor = new Color(216, 100, 169);
    public static Color unmarkedQuestionColor = new Color(247, 219, 106);
    public static Color bottomToolBarBtnColor  = new Color(20, 108, 148);
    public static Color uiBackgroundColor = new Color(25, 167, 206);
    public static Color questionBackgroundColor = new Color(246, 241, 241);
    public static Color themeColor1 = new Color(246, 241, 241);
    public static Color themeColor2 = new Color(25, 167, 206);
    public static Color themeColor3 = new Color(20, 108, 148);
    public static Color themeColor4 = new Color(0, 0, 0);
    public static Color fontColor1 = new Color(246, 241, 241);
    public static Color timerColor1 = new Color(122, 168, 116);
    public static Color timerColor2 = new Color(255, 109, 96);
    public static void setLocation(JComponent c, Dimension bounds, int x1, int y1) {
        c.setLocation((int)(x1*bounds.getWidth()/100), (int)(y1*bounds.getHeight()/100));
    }
    public static void setSize(JComponent c, Dimension bounds, int x1, int y1, int x2, int y2) {
        c.setSize((int) ((x2-x1)*bounds.getWidth()/100), (int) ((y2-y1)*bounds.getHeight()/100));
    }
    public static void setPreferredSize(JComponent c, Dimension bounds, int x1, int y1, int x2, int y2) {
        c.setPreferredSize(new Dimension((int) ((x2-x1)*bounds.getWidth()/100), (int) ((y2-y1)*bounds.getHeight()/100)));
    }
}
