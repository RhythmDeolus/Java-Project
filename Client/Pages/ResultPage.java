package Client.Pages;

import javax.swing.*;

import Client.Pages.Components.Util;

import java.awt.*;
//  previous question,clear, mark, nextquestion  end exam
public class ResultPage extends JPanel {

    public ResultPage(int totalQuestions, int attemptedQuestions, int totalCorrect) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.weightx = 20;
        gbc.weighty = 20;

        JLabel label = new JLabel("Result Summary");
        label.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, Util.themeColor1));
        label.setFont(Util.examTitleFont);
        setBackground(Util.themeColor3);
        label.setForeground(Util.themeColor1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        add(label, gbc);

        JLabel totalQuestionsLabel = new JLabel("Total Questions:", Label.RIGHT);
        totalQuestionsLabel.setFont(Util.questionFont);
        totalQuestionsLabel.setForeground(Util.themeColor1);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(totalQuestionsLabel, gbc);
        JLabel totalQuestionsValue = new JLabel(String.valueOf(totalQuestions));
        totalQuestionsValue.setFont(Util.questionFont);
        totalQuestionsValue.setForeground(Util.themeColor1);
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(totalQuestionsValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new Label(""), gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(new Label(""), gbc);

        JLabel attemptedQuestionsLabel = new JLabel("Attempted Questions:");
        attemptedQuestionsLabel.setFont(Util.questionFont);
        attemptedQuestionsLabel.setForeground(Util.themeColor1);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(attemptedQuestionsLabel, gbc);

        JLabel attemptedQuestionsValue = new JLabel(String.valueOf(attemptedQuestions));
        attemptedQuestionsValue.setFont(Util.questionFont);
        attemptedQuestionsValue.setForeground(Util.themeColor1);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(attemptedQuestionsValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new Label(""), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(new Label(""), gbc);

        JLabel totalCorrectLabel = new JLabel("Total Correct:");
        totalCorrectLabel.setFont(Util.questionFont);
        totalCorrectLabel.setForeground(Util.themeColor1);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(totalCorrectLabel, gbc);

        JLabel totalCorrectValue = new JLabel(String.valueOf(totalCorrect));
        totalCorrectValue.setFont(Util.questionFont);
        totalCorrectValue.setForeground(Util.themeColor1);
        gbc.gridx = 2;
        gbc.gridy = 3;
        add(totalCorrectValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new Label(""), gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(new Label(""), gbc);

        JLabel unattemptedQuestionLabel = new JLabel("Unattempted Questions:");
        unattemptedQuestionLabel.setFont(Util.questionFont);
        unattemptedQuestionLabel.setForeground(Util.themeColor1);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(unattemptedQuestionLabel, gbc);

        JLabel unattemptedQuestionValue = new JLabel(String.valueOf(totalQuestions - attemptedQuestions));
        unattemptedQuestionValue.setFont(Util.questionFont);
        unattemptedQuestionValue.setForeground(Util.themeColor1);
        gbc.gridx = 2;
        gbc.gridy = 4;
        add(unattemptedQuestionValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new Label(""), gbc);
        gbc.gridx = 3;
        gbc.gridy = 4;
        add(new Label(""), gbc);

        JLabel incorrectQuetionLabel = new JLabel("Incorrect Questions:");
        incorrectQuetionLabel.setFont(Util.questionFont);
        incorrectQuetionLabel.setForeground(Util.themeColor1);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(incorrectQuetionLabel, gbc);

        JLabel incorrectQuestionValue = new JLabel(String.valueOf(attemptedQuestions - totalCorrect));
        incorrectQuestionValue.setFont(Util.questionFont);
        incorrectQuestionValue.setForeground(Util.themeColor1);
        gbc.gridx = 2;
        gbc.gridy = 5;
        add(incorrectQuestionValue, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new Label(""), gbc);
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(new Label(""), gbc);
    }
}