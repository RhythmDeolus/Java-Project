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
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(margin, margin, margin, margin);
        p.setPreferredSize(new Dimension((int) ((x2 - x1) * bounds.getWidth() / 100),
                (int) ((Math.ceil(q_length / 3)) * (margin + buttonSize) + margin)));
        JScrollPane scrPane = new JScrollPane(p);
        scrPane.setBorder(null);
        scrPane.setViewportView(p);
        scrPane.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        add(scrPane);
        questionButtons = new JButton[q_length];
        for (int i = 0; i < q_length; i++) {
            questionButtons[i] = new JButton("" + i);
            questionButtons[i].setFont(Util.uiNormalFont);
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            p.add(questionButtons[i], gbc);
            questionButtons[i].addActionListener(e -> ui.showQuestion(((JButton)e.getSource()).getText()));
        }
    }
}
