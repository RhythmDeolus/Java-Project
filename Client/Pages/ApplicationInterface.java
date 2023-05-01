package Client.Pages;

import java.awt.*;
import javax.swing.*;

public abstract class ApplicationInterface extends JFrame {
    abstract public void login(int id, String password);
    abstract public void endExam(int totalQuestions, int attemptedQuestions, int correctQuestions);
    abstract public int getStudentID();
}
