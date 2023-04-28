package Client.Pages.Components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;

public class Util {
    public static Font questionFont = new Font("Arial", Font.PLAIN, 20);
    public static Font examTitleFont = new Font("Arial", Font.BOLD, 30);
    public static Font optionFont = new Font("Arial", Font.PLAIN, 15);
    public static Font uiSmallFont = new Font("Arial", Font.PLAIN, 16);
    public static Font uiNormalFont = new Font("Arial", Font.PLAIN, 20);
    public static Font uiBigFont = new Font("Arial", Font.PLAIN, 30);
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
