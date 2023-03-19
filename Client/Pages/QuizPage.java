package Pages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizPage extends JPanel {
    private JLabel question1Label, question2Label;
    private JRadioButton question1Option1, question1Option2, question1Option3, question1Option4;
    private JRadioButton question2Option1, question2Option2, question2Option3, question2Option4;
    private ButtonGroup question1Group, question2Group;
    private JButton submitButton;

    public QuizPage() {
        // set window properties
        // setTitle("Quiz Page");
        setSize(400, 300);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLocationRelativeTo(null);

        // create components
        question1Label = new JLabel("Question 1: What is the capital of France?");
        question1Option1 = new JRadioButton("New York");
        question1Option2 = new JRadioButton("Tokyo");
        question1Option3 = new JRadioButton("Paris");
        question1Option4 = new JRadioButton("London");
        question1Group = new ButtonGroup();
        question1Group.add(question1Option1);
        question1Group.add(question1Option2);
        question1Group.add(question1Option3);
        question1Group.add(question1Option4);

        question2Label = new JLabel("Question 2: What is the largest continent?");
        question2Option1 = new JRadioButton("Africa");
        question2Option2 = new JRadioButton("Asia");
        question2Option3 = new JRadioButton("Europe");
        question2Option4 = new JRadioButton("North America");
        question2Group = new ButtonGroup();
        question2Group.add(question2Option1);
        question2Group.add(question2Option2);
        question2Group.add(question2Option3);
        question2Group.add(question2Option4);

        submitButton = new JButton("Submit Exam");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check answers
                String answer1 = "";
                if (question1Option3.isSelected()) {
                    answer1 = "Correct!";
                } else {
                    answer1 = "Incorrect.";
                }
                String answer2 = "";
                if (question2Option2.isSelected()) {
                    answer2 = "Correct!";
                } else {
                    answer2 = "Incorrect.";
                }
                // show results
                JOptionPane.showMessageDialog(null, "Question 1: " + answer1 + "\nQuestion 2: " + answer2);
            }
        });

        // add components to panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(question1Label);
        panel.add(question1Option1);
        panel.add(question1Option2);
        panel.add(question1Option3);
        panel.add(question1Option4);
        panel.add(question2Label);
        panel.add(question2Option1);
        panel.add(question2Option2);
        panel.add(question2Option3);
        panel.add(question2Option4);
        panel.add(submitButton);

        // add panel to window
        add(panel);

        // show window
        setVisible(true);
    }

    public static void main(String[] args) {
        new QuizPage();

    }
}
