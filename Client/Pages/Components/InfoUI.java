package Client.Pages.Components;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoUI extends Panel {
    private JPanel answeredLogo;
    private JLabel answeredText;
    private JPanel unansweredLogo;
    private JLabel unansweredText;
    private JPanel currentLogo;
    private JLabel currentText;
    public InfoUI() {
        setLayout(new FlowLayout());
        FlowLayout fl = (FlowLayout) getLayout();
        fl.setAlignment(FlowLayout.LEFT);

        Dimension d = new Dimension(30, 30);

        fl.setHgap(30);

        // add(new Insets(30, 30, 30 , 30));

        answeredLogo = new JPanel();
        answeredLogo.setPreferredSize(d);
        answeredLogo.setBackground(Util.markedQuestionColor);
        add(answeredLogo);
        answeredText = new JLabel("Answered Questions");
        answeredText.setFont(Util.uiNormalFont);
        add(answeredText);
        setBackground(Util.themeColor1);

        unansweredLogo = new JPanel();
        unansweredLogo.setPreferredSize(d);
        unansweredLogo.setBackground(Util.unmarkedQuestionColor);
        add(unansweredLogo);
        unansweredText = new JLabel("Unanswered Questions");
        unansweredText.setFont(Util.uiNormalFont);
        add(unansweredText);

        currentLogo = new JPanel();
        currentLogo.setPreferredSize(d);
        currentLogo.setBackground(Util.currentQuestionColor);
        add(currentLogo);
        currentText = new JLabel("Current Question");
        currentText.setFont(Util.uiNormalFont);
        add(currentText);
    }
}
