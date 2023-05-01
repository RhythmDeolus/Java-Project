package Client.Pages;

import javax.swing.*;

import Client.Backend.*;
import Client.Pages.Components.InfoUI;
import Client.Pages.Components.QuestionMenu;
import Client.Pages.Components.QuestionUI;
import Client.Pages.Components.TimerUI;
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
    private JButton nextBtn;
    private JButton prevBtn;
    private JButton endExamBtn;
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
    private TimerUI timerui;
    private InfoUI infoUI;
    private boolean examEnded = false;
    private Connection conn;

    public OnlineExamUI(ApplicationInterface app) {
        screenBounds = Toolkit.getDefaultToolkit().getScreenSize();
        this.app = app;
        // setSize(screenBounds.width*10, screenBounds.height*10);
        // setTitle("Online Exam");
        // setBounds(300, 90, 900, 600);
        // setDefaultCloseOperation(EXIT_ON_CLOSE);
        // setResizable(true);

        conn = new Connection();

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
                Answer a = new Answer(answer_id, r3.props.get("content"), question_id, exam_id, type, isCorrect);
                answers.put(answer_id, a);
            }

            Question q = new Question(question_id, r1.props.get("statement"), exam_id, options);

            questions[j++] = q;
        }
        for (Question q: questions) {
            System.out.println(q.statement);
        }

        System.out.println("Number of questions: " + questions.length);

        // c = getContentPane();
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 20;
        // gbc.insets = new Insets(0, 0, 0, 0);
        // gbc.ipadx = 0 ;
        // gbc.ipady = 0 ;
        gbc.weighty = 20;
        setBorder(null);
        // setLayout(new FlowLayout());

        Question q = questions[0];
        String optionStrings[] = new String[q.options.length];
        for (int i = 0; i < q.options.length; i++) {
            optionStrings[i] = answers.get(q.options[i]).content;
        }
        qui = new QuestionUI(q.statement, optionStrings, screenBounds,0 , 20, 80, 80, -1);
        System.out.println(qui.getSize());
        currentQuestion = questions[0];
        // qui.setSize(400, 400);
        // qui.setFont(new Font("Arial", Font.PLAIN, 30));
        // qui.setBounds(new Rectangle(300, 300));

        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridheight = 50;
        gbc.gridwidth = 80;
        add(qui, gbc);
        gbc.anchor = GridBagConstraints.CENTER;

        title = new JLabel(examName, JLabel.CENTER);
        title.setFont(Util.examTitleFont);
        title.setForeground(Util.themeColor1);
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
        infoUI = new InfoUI();
        gbc.gridx = 0;
        gbc.gridy = 70;
        gbc.gridheight = 10;
        gbc.gridwidth = 80;
        add(infoUI, gbc);

        gbc.fill = GridBagConstraints.NONE;

        

        submitBtn = new JButton("Mark");
        // submitBtn.setBorder(new RoundedBorder(10));
        submitBtn.setBackground(Util.bottomToolBarBtnColor);
        submitBtn.setForeground(Util.fontColor1);

        submitBtn.setFont(Util.uiNormalFont);
        submitBtn.setSize(100, 20);
        // Util.setLocation(submitBtn, screenBounds, 20, 85);
        submitBtn.addActionListener(this);
        gbc.gridx = 10;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 10;
        add(submitBtn, gbc);

        resetBtn = new JButton("Clear");
        resetBtn.setBackground(Util.bottomToolBarBtnColor);
        resetBtn.setForeground(Util.fontColor1);
        resetBtn.setFont(Util.uiNormalFont);
        resetBtn.setSize(100, 20);
        // Util.setLocation(resetBtn, screenBounds, 50, 85);
        resetBtn.addActionListener(this);
        gbc.gridx = 20;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 10;
        add(resetBtn, gbc);

        nextBtn = new JButton("Next");
        nextBtn.setBackground(Util.bottomToolBarBtnColor);
        nextBtn.setForeground(Util.fontColor1);
        nextBtn.setFont(Util.uiNormalFont);
        nextBtn.setSize(100, 20);
        // Util.setLocation(resetBtn, screenBounds, 50, 85);
        nextBtn.addActionListener(this);
        gbc.gridx = 30;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 10;
        add(nextBtn, gbc);

        prevBtn = new JButton("Previous");
        prevBtn.setBackground(Util.bottomToolBarBtnColor);
        prevBtn.setForeground(Util.fontColor1);
        prevBtn.setFont(Util.uiNormalFont);
        prevBtn.setSize(100, 20);
        // Util.setLocation(resetBtn, screenBounds, 50, 85);
        prevBtn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 10;
        add(prevBtn, gbc);

        endExamBtn = new JButton("End Exam");
        endExamBtn.setBackground(Util.bottomToolBarBtnColor);
        endExamBtn.setForeground(Util.fontColor1);
        endExamBtn.setFont(Util.uiNormalFont);
        endExamBtn.setSize(100, 20);
        // Util.setLocation(resetBtn, screenBounds, 50, 85);
        endExamBtn.addActionListener(this);
        gbc.gridx = 70;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 10;
        add(endExamBtn, gbc);


        gbc.gridx = 40;
        gbc.gridy = 80;
        gbc.gridheight = 20;
        gbc.gridwidth = 30;

        add(new Label(""), gbc);
        

        // resultLabel = new JLabel();
        // resultLabel.setFont(Util.uiNormalFont);
        // resultLabel.setSize(300, 30);
        // resultLabel.setLocation(550, 500);
        // gbc.gridx = 0;
        // gbc.gridy = 0;
        // gbc.gridheight = 15;
        // gbc.gridwidth = 75;
        // add(resultLabel, gbc);

        qm = new QuestionMenu(questions.length, screenBounds, 80, 20, 100, 80, this);

        gbc.gridx = 80;
        gbc.gridy = 20;
        gbc.gridheight = 60;
        gbc.gridwidth = 20;
        gbc.fill = GridBagConstraints.BOTH;
        add(qm, gbc);

        timerui = new TimerUI(this, screenBounds, 80, 0, 95, 20, 5*60 + 20);

        gbc.gridx = 80;
        gbc.gridy = 0;
        gbc.gridheight = 20;
        gbc.gridwidth = 20;
        gbc.fill = GridBagConstraints.BOTH;
        add(timerui, gbc);

        setBackground(Util.uiBackgroundColor);

        setVisible(true);

        // showNextQuestion();
    }
    public void showQuestion(String newindex_s) {
        int newindex = Integer.parseInt(newindex_s);
        newindex -= 1;
        if (newindex < questions.length) {
            // currentQuestion = questions[currentQuestionIndex];
            remove(qui);
            Question q = questions[newindex];
            String optionStrings[] = new String[q.options.length];
            for (int i = 0; i < q.options.length; i++) {
                optionStrings[i] = answers.get(q.options[i]).content;
            }
            System.out.println(q.statement);
            Answer ans = markedAnswer[(newindex)%questions.length];
            int index = -1;
            if (ans != null) for (int i = 0; i < q.options.length; i++) {
                if (q.options[i] == ans.id) {
                    index = i;
                    break;
                }
            }
            qui = new QuestionUI(q.statement, optionStrings, screenBounds, 0, 20, 80, 80, index);
            currentQuestion = q;
            app.getContentPane().repaint();
            qui.setVisible(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 20;
            gbc.gridheight = 50;
            gbc.gridwidth = 80;
            // gbc.anchor = GridBagConstraints.NONE;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 20;
            gbc.weighty = 20;
            add(qui, gbc);
            app.revalidate();
            app.getContentPane().repaint();
            currentQuestionIndex = newindex;
            qm.setCurrent(currentQuestionIndex);
        }
    }

    private void showNextQuestion() {
        System.out.println(currentQuestionIndex);
        if (true) {
            currentQuestionIndex %= questions.length;
            currentQuestion = questions[currentQuestionIndex];
            remove(qui);
            Question q = questions[(currentQuestionIndex + 1 )%questions.length];
            String optionStrings[] = new String[q.options.length];
            for (int i = 0; i < q.options.length; i++) {
                optionStrings[i] = answers.get(q.options[i]).content;
            }
            System.out.println(q.statement);
            Answer ans = markedAnswer[(currentQuestionIndex + 1 )%questions.length];
            int index = -1;
            if (ans != null) for (int i = 0; i < q.options.length; i++) {
                if (q.options[i] == ans.id) {
                    index = i;
                    break;
                }
            }
            qui = new QuestionUI(q.statement, optionStrings, screenBounds, 0, 20, 80, 80, index);
            currentQuestion = q;
            app.getContentPane().repaint();
            qui.setVisible(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 20;
            gbc.gridheight = 50;
            gbc.gridwidth = 80;
            // gbc.anchor = GridBagConstraints.NONE;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 20;
            gbc.weighty = 20;
            add(qui, gbc);
            app.revalidate();
            app.getContentPane().repaint();
            currentQuestionIndex++;
            currentQuestionIndex %= questions.length;
            qm.setCurrent(currentQuestionIndex);
        }
    }

    // private void showResult() {
    //     int count = 0;
    //     for (Answer a: markedAnswer) {
    //         System.out.println(a.isCorrect);
    //         if (a != null && a.isCorrect) count++;
    //     }
    //     JOptionPane.showMessageDialog(app, "Correct: " + count + "/" + markedAnswer.length);
    // }

    public void endExamTimer() {
        if (!examEnded) {
            System.out.println("Exam Ended");
            examEnded = true;
            int attempted = 0;
            int total = questions.length;
            int correct = 0;
            for (Answer ans: markedAnswer) {
                if (ans != null) {
                    System.out.println(ans.id + "," + ans.exam_id + "," + ans.question_id + "," + app.getStudentID());
                    conn.setStudentResponse(ans.exam_id, ans.question_id, app.getStudentID(), ans.id);
                }
                if (ans != null && ans.isCorrect) correct++;
                if (ans != null) attempted++;
            }
            app.endExam(total, attempted, correct);
        }
    }

    public void startTimer() {
        timerui.start();
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
            if (selectedIndex == -1) return;
            markedAnswer[currentQuestionIndex] = answers.get(currentQuestion.options[selectedIndex]);
            qm.mark(currentQuestionIndex);
            showNextQuestion();
        } else if (e.getSource() == resetBtn) {
            // currentQuestionIndex = 0;
            // correctAnswers = 0;
            // showNextQuestion();
            // submitBtn.setEnabled(true);
            // resetBtn.setEnabled(false);
            // resultLabel.setText("");
            markedAnswer[currentQuestionIndex] = null;
            qm.unmark(currentQuestionIndex);
            qui.clear();
        } else if (e.getSource() == nextBtn) {
            showNextQuestion();
        } else if (e.getSource() == prevBtn) {
            if (currentQuestionIndex - 1 >= 0) showQuestion((currentQuestionIndex) + "");
        } else if (e.getSource() == endExamBtn) {
            endExamTimer();
        }
    }

    public static void main(String[] args) {
        // OnlineExamUI examUI = new OnlineExamUI();
    }
}