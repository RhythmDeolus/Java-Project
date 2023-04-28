package Client.Pages;

import javax.swing.*;

import Client.Backend.*;
import Client.Pages.Components.QuestionMenu;
import Client.Pages.Components.QuestionUI;
import Client.Pages.Components.Util;

import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;

class Answer {
    public int id;
    public String content;
    public int question_id;
    public int exam_id;
    public int type;
    public boolean isCorrect;

    public Answer(int id, String content, int question_id, int exam_id, int type, boolean isCorrect) {
        this.id = id;
        this.content = content;
        this.question_id = question_id;
        this.exam_id = exam_id;
        this.type = type;
        this.isCorrect = isCorrect;
    }
}

class Question {
    public int id;
    public String statement;
    public int exam_id;
    public int options[];

    public Question(int id, String statement, int exam_id, int options[]) {
        this.id = id;
        this.statement = statement;
        this.exam_id = exam_id;
        this.options = options;
    }
}

public class OnlineExamUI extends JPanel implements ActionListener {

    private Container c;
    private JLabel title;
    private JTextArea questionTextArea;
    private JRadioButton[] optionRadioButtons;
    private ButtonGroup optionButtonGroup;
    private JButton submitBtn;
    private JButton resetBtn;
    private JLabel resultLabel;
    public String examName;
    private HashMap<Integer, Answer> answers;
    private Question currentQuestion;
    private QuestionUI qui;

    private Question[] questions;
    private Answer[] markedAnswer;

    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private ApplicationInterface app;
    private Dimension screenBounds;
    private QuestionMenu qm;

    public OnlineExamUI(ApplicationInterface app) {
        screenBounds = Toolkit.getDefaultToolkit().getScreenSize();
        this.app = app;
        // setSize(screenBounds.width*10, screenBounds.height*10);
        // setTitle("Online Exam");
        // setBounds(300, 90, 900, 600);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setResizable(true);

        Connection conn = new Connection();

        int exam_id = 0;

        Response rs = conn.getExam(exam_id);

        examName = rs.props.get("name");

        rs = conn.getQuestions(exam_id);

        questions = new Question[rs.props.size()];
        markedAnswer = new Answer[rs.props.size()];
        for (int i = 0; i < rs.props.size(); i++) {
            markedAnswer[i] = null;
        }

        answers = new HashMap<Integer, Answer>();

        int j = 0;

        for (String question : rs.props.keySet()) {
            int question_id = Integer.parseInt(question);
            Response r1 = conn.getQuestion(question_id);
            // questions.put(question_id, r2.props.get("statement"));

            Response r2 = conn.getOptions(question_id);

            int options[] = new int[r2.props.size()];
            int i = 0;

            for (String answer : r2.props.keySet()) {
                int answer_id = Integer.parseInt(answer);
                options[i++] = answer_id;
                Response r3 = conn.getAnswer(answer_id);
                int type = Integer.parseInt(r3.props.get("type"));
                System.out.println(r3.props.get("isCorrect"));
                boolean isCorrect = r3.props.get("isCorrect").equals("true");
                Answer a = new Answer(answer_id, r3.props.get("content"), exam_id, question_id, type, isCorrect);
                answers.put(answer_id, a);
            }

            Question q = new Question(question_id, r1.props.get("statement"), exam_id, options);

            questions[j++] = q;
        }
        for (Question q: questions) {
            System.out.println(q.statement);
        }

        // c = getContentPane();
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 20;
        gbc.weighty = 20;
        setBorder(null);
        // setLayout(new FlowLayout());

        Question q = questions[0];
        String optionStrings[] = new String[q.options.length];
        for (int i = 0; i < q.options.length; i++) {
            optionStrings[i] = answers.get(q.options[i]).content;
        }
        qui = new QuestionUI(q.statement, optionStrings, screenBounds, 10, 20, 70, 80);
        System.out.println(qui.getSize());
        currentQuestion = questions[0];
        // qui.setSize(400, 400);
        // qui.setFont(new Font("Arial", Font.PLAIN, 30));
        // qui.setBounds(new Rectangle(300, 300));

        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridheight = 60;
        gbc.gridwidth = 80;
        add(qui, gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        title = new JLabel(examName, JLabel.CENTER);
        title.setFont(Util.examTitleFont);
        // title.setSize(300, 30);
        // Util.setLocation(title, screenBounds, 10, 30);
        // title.setLocation(350, 30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 20;
        gbc.gridwidth = 80;
        add(title, gbc);

        // questionTextArea = new JTextArea();
        // questionTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        // questionTextArea.setSize(600, 100);
        // questionTextArea.setLocation(150, 100);
        // questionTextArea.setLineWrap(true);
        // questionTextArea.setEditable(false);
        // c.add(questionTextArea);

        // optionRadioButtons = new JRadioButton[4];
        // optionButtonGroup = new ButtonGroup();
        // for (int i = 0; i < 4; i++) {
        // optionRadioButtons[i] = new JRadioButton();
        // optionRadioButtons[i].setFont(new Font("Arial", Font.PLAIN, 15));
        // optionRadioButtons[i].setSize(300, 20);
        // optionRadioButtons[i].setLocation(200, 250 + i*30);
        // optionRadioButtons[i].addActionListener(this);
        // optionButtonGroup.add(optionRadioButtons[i]);
        // c.add(optionRadioButtons[i]);
        // }

        gbc.fill = GridBagConstraints.NONE;

        submitBtn = new JButton("Submit");
        submitBtn.setFont(Util.uiNormalFont);
        submitBtn.setSize(100, 20);
        // Util.setLocation(submitBtn, screenBounds, 20, 85);
        submitBtn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 40;
        add(submitBtn, gbc);

        resetBtn = new JButton("Reset");
        resetBtn.setFont(Util.uiNormalFont);
        resetBtn.setSize(100, 20);
        // Util.setLocation(resetBtn, screenBounds, 50, 85);
        resetBtn.addActionListener(this);
        gbc.gridx = 40;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 40;
        add(resetBtn, gbc);

        resultLabel = new JLabel();
        resultLabel.setFont(Util.uiNormalFont);
        resultLabel.setSize(300, 30);
        resultLabel.setLocation(550, 500);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 15;
        gbc.gridwidth = 75;
        // add(resultLabel, gbc);

        qm = new QuestionMenu(questions.length, screenBounds, 80, 20, 95, 95, this);

        gbc.gridx = 80;
        gbc.gridy = 20;
        gbc.gridheight = 60;
        gbc.gridwidth = 20;
        gbc.fill = GridBagConstraints.BOTH;
        add(qm, gbc);

        setVisible(true);

        // showNextQuestion();
    }
    public void showQuestion(String newindex_s) {
        int newindex = Integer.parseInt(newindex_s);
        if (newindex < questions.length) {
            // currentQuestion = questions[currentQuestionIndex];
            remove(qui);
            Question q = questions[newindex];
            String optionStrings[] = new String[q.options.length];
            for (int i = 0; i < q.options.length; i++) {
                optionStrings[i] = answers.get(q.options[i]).content;
            }
            System.out.println(q.statement);
            qui = new QuestionUI(q.statement, optionStrings, screenBounds, 10, 20, 70, 80);
            currentQuestion = q;
            app.getContentPane().repaint();
            qui.setVisible(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 20;
            gbc.gridheight = 60;
            gbc.gridwidth = 80;
            // gbc.anchor = GridBagConstraints.NONE;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 20;
            gbc.weighty = 20;
            add(qui, gbc);
            app.revalidate();
            app.getContentPane().repaint();
            currentQuestionIndex = newindex;
        }
    }

    private void showNextQuestion() {
        System.out.println(currentQuestionIndex);
        if (currentQuestionIndex < questions.length -1) {
            currentQuestion = questions[currentQuestionIndex];
            remove(qui);
            Question q = questions[currentQuestionIndex + 1];
            String optionStrings[] = new String[q.options.length];
            for (int i = 0; i < q.options.length; i++) {
                optionStrings[i] = answers.get(q.options[i]).content;
            }
            System.out.println(q.statement);
            qui = new QuestionUI(q.statement, optionStrings, screenBounds, 10, 20, 70, 80);
            currentQuestion = q;
            app.getContentPane().repaint();
            qui.setVisible(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 20;
            gbc.gridheight = 60;
            gbc.gridwidth = 80;
            // gbc.anchor = GridBagConstraints.NONE;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 20;
            gbc.weighty = 20;
            add(qui, gbc);
            app.revalidate();
            app.getContentPane().repaint();
            currentQuestionIndex++;
        } else {
            showResult();
        }
    }

    private void showResult() {
        int count = 0;
        for (Answer a: markedAnswer) {
            System.out.println(a.isCorrect);
            if (a != null && a.isCorrect) count++;
        }
        JOptionPane.showMessageDialog(app, "Correct: " + count + "/" + markedAnswer.length);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            // String selectedOption = "";
            // for (int i = 0; i < 4; i++) {
            // if (optionRadioButtons[i].isSelected()) {
            // selectedOption = optionRadioButtons[i].getText();
            // break;
            // }
            // }
            // String correctOption = questions[currentQuestionIndex - 1][5];
            // if (selectedOption.equals(correctOption)) {
            // correctAnswers++;
            // }
            int selectedIndex = qui.getSelectedIndex();
            markedAnswer[currentQuestionIndex] = answers.get(currentQuestion.options[selectedIndex]);
            showNextQuestion();
        } else if (e.getSource() == resetBtn) {
            // currentQuestionIndex = 0;
            // correctAnswers = 0;
            // showNextQuestion();
            // submitBtn.setEnabled(true);
            // resetBtn.setEnabled(false);
            // resultLabel.setText("");
        } else {
            // Do nothing
        }
    }

    public static void main(String[] args) {
        // OnlineExamUI examUI = new OnlineExamUI();
    }
}