package Client.Pages.Components;

import javax.swing.JPanel;

import Client.Pages.OnlineExamUI;
import Server.Database.Models.Question;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class QuestionMenu extends JPanel {
    private int margin = 10;
    private int buttonSize = 30;
    private JButton[] questionButtons;

    public QuestionMenu(int q_length, Dimension bounds, int x1, int y1, int x2, int y2, OnlineExamUI ui) {
        Util.setPreferredSize(this, bounds, x1, y1, x2, y2);
        // Util.setLocation(this, bounds, x1, y1);
        buttonSize = (int) ((getSize().width) / 4.33);
        margin = buttonSize / 3;
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension((int) ((x2 - x1) * bounds.getWidth() / 100),
                (int) ((Math.ceil(q_length / 3)) * (margin + buttonSize) + margin)));
        // p.setPreferredSize(new Dimension(200, 2000));

        // p.setMinimumSize(this.getSize());
        // p.setPreferredSize(this.getSize());
        // p.setBackground(new Color(204, 240, 255));
        JScrollPane scrPane = new JScrollPane(p);
        scrPane.setViewportView(p);
        // Util.setPreferredSize(scrPane, bounds, x1, y1, x2, y2);
        scrPane.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        // p.setLayout(null);
        int val = (int) Math.ceil(q_length / 3);
        if (val < 10)
            val = 10;
        p.setLayout(new GridLayout(val, 3));
        GridLayout gl = (GridLayout) p.getLayout();
        // gl.setHgap(5);
        // gl.setVgap(5);
        add(scrPane);
        questionButtons = new JButton[q_length];
        // setBorder(BorderFactory.createLineBorder(Color.black));
        for (int i = 0; i < q_length; i++) {
            questionButtons[i] = new JButton("" + i);
            questionButtons[i].setFont(Util.uiNormalFont);
            questionButtons[i].setLocation(margin + (i % 3) * (margin + buttonSize),
                    margin + (i / 3) * (margin + buttonSize));
            // questionButtons[i].setSize(buttonSize, buttonSize);
            // questionButtons[i].setMargin(new Insets(5, 5, 5, 5));
            questionButtons[i].addActionListener(e -> ui.showQuestion(((JButton)e.getSource()).getText()));
            p.add(questionButtons[i]);
        }
    }
}
