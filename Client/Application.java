import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Pages.*;


public class Application extends JFrame  {
    // public LoginFrame lf;
    // public QuizPage qp;

    public Application(){
        LoginFrame lf = new LoginFrame(this);
        QuizPage qp = new QuizPage();
        setTitle("Online Exam");
        
        getContentPane().setLayout(new CardLayout());

        add(lf);
        add(qp);

        pack();
        setVisible(true);
    }
    public static void main(String[] args) throws InterruptedException {
       Application a = new Application();

    //    while (true) {
    //     Thread.sleep(1000);
    //     CardLayout cl = (CardLayout)a.getContentPane().getLayout();
    //     cl.next(a.getContentPane());
    //    }

    }    
}
