package Client;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.*;
import java.awt.event.*;

import Client.Backend.Connection;
import Client.Backend.Response;
import Client.Pages.*;


public class Application extends ApplicationInterface  {
    public LoginFrame lf;
    public OnlineExamUI oeui;
    private Connection conn;
    private Dimension screenBounds;
    private ResultPage rp;
    public int student_id;
    public Application(){

        conn = new Connection();
        lf = new LoginFrame(this);
        oeui = new OnlineExamUI(this);
        setTitle("Online Exam");

        
        getContentPane().setLayout(new CardLayout());

        add(lf);
        add(oeui);

        // setAlwaysOnTop(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
            dispose();
            setUndecorated(true);
            revalidate();
        
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) throws InterruptedException {
       Application a = new Application();

    //    while (true) {
    //     Thread.sleep(1000);
    //     CardLayout cl = (CardLayout)a.getContentPane().getLayout();
    //     cl.next(a.getContentPane());
    //    }

    }
    @Override
    public void login(int id, String password) {
        Response r = conn.verifyStudent(id, password);
        if (r.props.containsKey("T")) {
            student_id = id;
            CardLayout cl = (CardLayout) getContentPane().getLayout();
            cl.next(getContentPane());
            setVisible(true);
            oeui.startTimer();
        } else if (r.props.containsKey("D")) {
            System.out.println("Already Given Exam");
            JOptionPane.showMessageDialog(this, "You have already given this exam");
        } else {
            //  login failed
            System.out.println("Login failed");
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }

    @Override
    public int getStudentID() {
        return student_id;
    }


    @Override
    public void endExam(int totalQuestions, int attemptedQuestions, int totalCorrect) {
        rp = new ResultPage(totalQuestions, attemptedQuestions, totalCorrect);
        add(rp);
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.next(getContentPane());
    }
}
