package Client.Pages.Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionUI extends JPanel{
    private JLabel questionLabel;
    private JRadioButton questionOptions[];
    private ButtonGroup questionGroup;

    public QuestionUI(String text, String[] options,Dimension screenBounds, int x1, int y1, int x2, int y2) {
        // set window properties
        // setTitle("Quiz Page");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);
        JPanel p = new JPanel();
        Util.setPreferredSize(this, screenBounds, x1, y1, x2, y2);
        // Util.setLocation(this, screenBounds, x1, y1);
        // setSize((int)((x2-x1)*screenBounds.getWidth() / 100), (int) ((y2 - y1) * screenBounds.getHeight()/100));
        // setLocation((int)(x1*screenBounds.getWidth()/100), (int)(y2*screenBounds.getHeight()/100));
        // setBorder(BorderFactory.createLineBorder(Color.BLACK));

        p.setLayout(new BorderLayout());
        // Util.setSize(p, screenBounds, x1, y1, x2, y2);
        // setBackground(new Color(204, 240, 255));
        // p.setBackground(new Color(204, 240, 255));
        // p.setBorder(null);
        // p.setMinimumSize(this.getSize());
        // p.setPreferredSize(this.getSize());
        // setLayout(new BorderLayout());

        JScrollPane scrPane = new JScrollPane(p);
        scrPane.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
        // scrPane.setLayout(new ScrollPaneLayout() {
        //     @Override
        //     public void layoutContainer(Container parent) {
        //         JScrollPane scrollPane = (JScrollPane) parent;
        //         scrollPane.setComponentOrientation(
        //           ComponentOrientation.LEFT_TO_RIGHT);
        //         super.layoutContainer(parent);
        //         scrollPane.setComponentOrientation(
        //           ComponentOrientation.RIGHT_TO_LEFT);
        //     }
        // });
        add(scrPane);

        // create components
        // JPanel panel = new JPanel();
        questionLabel = new JLabel(text);
        questionLabel.setFont(Util.questionFont);
        int size = options.length;
        questionOptions = new JRadioButton[size];
        questionGroup = new ButtonGroup();
        for (int i = 0; i < size; i++) {
            questionOptions[i] = new JRadioButton(options[i]);
            questionOptions[i].setFont(Util.optionFont);
            questionOptions[i].setMargin(new Insets(30, 10, 0, 0));
            // questionOptions[i].setBackground(new Color(204, 240, 255));
            questionGroup.add(questionOptions[i]);
        }
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrPane.setBorder(null);
        // add components to panel
        JPanel optionUI = new JPanel();
        optionUI.setLayout(new GridLayout(questionOptions.length, 1));
        GridLayout gl = (GridLayout) optionUI.getLayout();
        p.add(questionLabel, BorderLayout.NORTH);
        for(int i =0; i < size; i++) {
            optionUI.add(questionOptions[i]);
        }
        // optionUI.setBackground(new Color(204, 240, 255));

        p.add(optionUI, BorderLayout.CENTER);
        // add panel to window
        // show window
        // add(panel);
        setVisible(true);
    }

    public int getSelectedIndex() {
        for (int i = 0; i < questionOptions.length; i++) {
            if (questionOptions[i].isSelected()) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        String [] o = {"yes", "no"};
        // new QuestionUI("hello", o);
    }
}
