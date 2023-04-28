package Server.Database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import java.util.Properties;

import Server.Database.Models.Answer;
import Server.Database.Models.Exam;
import Server.Database.Models.Question;
import Server.Database.Models.Student;

public class DB {
    static DB db;
    Connection conn;

    DB() {
        conn = null;
        try {
            Properties prop = new Properties();
            String fileName = "Server/Database/db.config";
            try (FileInputStream fis = new FileInputStream(fileName)) {
                prop.load(fis);
            } catch (FileNotFoundException ex) {
                // FileNotFoundException catch is optional and can be collapsed
            } catch (IOException ex) {
            }
            String server = prop.getProperty("db.server");
            String name = prop.getProperty("db.name");
            String password = prop.getProperty("db.password");
            conn = DriverManager.getConnection(server, name, password);
            System.out.println("connected");

            Statement s1 = conn.createStatement();

            String q2 = "CREATE TABLE IF NOT EXISTS EXAM(  " +
                    "EXAM_ID int  NOT NULL,  " +
                    "NAME varchar(20) NOT NULL,  " +
                    "START_TIME datetime NOT NULL,  " +
                    "END_TIME datetime NOT NULL,  " +
                    "PRIMARY KEY (EXAM_ID)" +
                    ");  ";
            String q3 = "CREATE TABLE IF NOT EXISTS STUDENT(  " +
                    "STUDENT_ID int  NOT NULL,  " +
                    "NAME varchar(20) NOT NULL,  " +
                    "PASSWORD varchar(30) NOT NULL,  " +
                    "PRIMARY KEY (STUDENT_ID)" +
                    ");  ";

            String q4 = "CREATE TABLE IF NOT EXISTS EXAM_ENROLLED(  " +
                    "EXAM_ID int  NOT NULL ,  " +
                    "STUDENT_ID int NOT NULL,  " +
                    "FOREIGN KEY (EXAM_ID) REFERENCES EXAM(EXAM_ID), " +
                    "FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(STUDENT_ID)" +
                    ");  ";

            String q5 = "CREATE TABLE IF NOT EXISTS QUESTION(  " +
                    "QUESTION_ID int  NOT NULL,  " +
                    "STATEMENT text NOT NULL,  " +
                    "EXAM_ID int NOT NULL, " +
                    "QUESTION_TYPE int NOT NULL DEFAULT 0, " +
                    "PRIMARY KEY (QUESTION_ID), " +
                    "FOREIGN KEY (EXAM_ID) REFERENCES EXAM(EXAM_ID) " +
                    ");  ";

            String q6 = "CREATE TABLE IF NOT EXISTS ANSWER(  " +
                    "EXAM_ID int  NOT NULL ,  " +
                    "QUESTION_ID int NOT NULL,  " +
                    "ANSWER_ID int NOT NULL, " +
                    "CONTENT text NOT NULL, " +
                    "ANSWER_TYPE int NOT NULL DEFAULT 0, " +
                    "IS_CORRECT bool NOT NULL DEFAULT False," +
                    "PRIMARY KEY (ANSWER_ID), " +
                    "FOREIGN KEY (EXAM_ID) REFERENCES EXAM(EXAM_ID), " +
                    "FOREIGN KEY (QUESTION_ID) REFERENCES QUESTION(QUESTION_ID)" +
                    ");  ";

            // String q7 = "ALTER TABLE QUESTION ADD FOREIGN KEY (ANSWER_ID) REFERENCES
            // ANSWER(ANSWER_ID)";

            s1.execute(q2);
            s1.execute(q3);
            s1.execute(q4);
            s1.execute(q5);
            s1.execute(q6);
            // s1.execute(q7);

            s1.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("had an error");
        }
    }

    public static DB getDataBase() {
        if (db == null) {
            // initialize DB
            db = new DB();
        }
        return db;
    }

    private boolean checkConnection() {
        try {
            if (conn != null && conn.isValid(0))
                return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("No Connection to Database was found");
        return false;
    }

    private boolean updateRow(String q) {
        if (!checkConnection())
            return false;
        try {
            Statement s = conn.createStatement();
            int rs = s.executeUpdate(q);
            if (rs > 0)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean hasRow(String q) {
        if (!checkConnection())
            return false;
        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(q);
            if (rs.next())
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Exam getExam(int id) {
        if (!checkConnection())
            return null;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM EXAM WHERE EXAM_ID='" + id + "';";
            ResultSet rs = s.executeQuery(q);
            if (rs.next()) {
                String name = rs.getString("NAME");
                Date startDate = rs.getDate("START_TIME");
                Date endDate = rs.getDate("END_TIME");
                return new Exam(id, name, startDate, endDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public boolean addExam(Exam exam) {
        String q = "INSERT INTO EXAM(EXAM_ID, NAME, START_TIME, END_TIME) VALUES('" + exam.id + "', '" + exam.name
                + "', '" + exam.startTime + "', '" + exam.endTime + "');";
        return updateRow(q);
    }

    public boolean hasExam(int id) {
        String q = "SELECT * FROM EXAM WHERE EXAM_ID='" + id + "';";
        return hasRow(q);
    }

    public boolean deleteExam(int id) {
        String q = "DELETE FROM EXAM WHERE EXAM_ID='" + id + "';";
        return updateRow(q);
    }

    public Object[] getQuestions(int exam_id) {
        if (!checkConnection()) 
            return null;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM QUESTION WHERE EXAM_ID=" + exam_id + ";";
            ResultSet rs = s.executeQuery(q);
            ArrayList<Integer> res = new ArrayList<Integer>();

            while (rs.next()) {
                int id = rs.getInt("QUESTION_ID");
                res.add(id);
            }

            return res.toArray();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student getStudent(int id) {
        if (!checkConnection())
            return null;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM STUDENT WHERE STUDENT_ID=" + id + ";";
            ResultSet rs = s.executeQuery(q);
            if (rs.next()) {
                String name = rs.getString("NAME");

                return new Student(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public boolean addStudent(Student student, String password) {
        String q = "INSERT INTO STUDENT(STUDENT_ID, NAME, PASSWORD) VALUES('" + student.id + "', '" + student.name
                + "', '" + password + "');";
        return updateRow(q);
    }

    public boolean hasStudent(int id) {
        String q = "SELECT * FROM STUDENT WHERE STUDENT_ID='" + id + "';";
        return hasRow(q);
    }

    public boolean deleteStudent(int id) {
        String q = "DELETE FROM STUDENT WHERE STUDENT_ID='" + id + "';";
        return updateRow(q);
    }

    public Question getQuestion(int id) {
        if (!checkConnection())
            return null;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM QUESTION WHERE QUESTION_ID='" + id + "';";
            ResultSet rs = s.executeQuery(q);
            if (rs.next()) {
                String statement = rs.getString("STATEMENT");
                int type = rs.getInt("QUESTION_TYPE");
                int exam_id = rs.getInt("EXAM_ID");
                return new Question(id, statement, exam_id, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public boolean addQuestion(Question question) {
        String q = "INSERT INTO QUESTION(QUESTION_ID, STATEMENT, EXAM_ID) VALUES('" + question.id + "', '"
                + question.statement + "', " + question.exam_id + ");";
        return updateRow(q);
    }

    public boolean hasQuestion(int id) {
        String q = "SELECT * FROM QUESTION WHERE QUESTION_ID='" + id + "';";
        return hasRow(q);
    }

    public boolean deleteQuestion(int id) {
        String q = "DELETE FROM QUESTION WHERE QUESTION_ID='" + id + "';";
        return updateRow(q);
    }

    public Answer getAnswer(int id) {
        if (!checkConnection())
            return null;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM ANSWER WHERE ANSWER_ID=" + id + ";";
            ResultSet rs = s.executeQuery(q);
            if (rs.next()) {
                String content = rs.getString("CONTENT");
                int question_id = rs.getInt("QUESTION_ID");
                int exam_id = rs.getInt("EXAM_ID");
                int type = rs.getInt("ANSWER_TYPE");
                boolean isCorrect = rs.getBoolean("IS_CORRECT");
                return new Answer(id, content, question_id, exam_id, type, isCorrect);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public boolean addAnswer(Answer answer) {
        String q = "INSERT INTO ANSWER(ANSWER_ID, CONTENT, EXAM_ID, QUESTION_ID, IS_CORRECT) VALUES('" + answer.id + "', '"
                + answer.content + "', '" + answer.exam_id + "', '" + answer.question_id + "', " + (answer.isCorrect? 1: 0)+");";
        return updateRow(q);
    }

    public boolean hasAnswer(int id) {
        String q = "SELECT * FROM ANSWER WHERE ANSWER_ID='" + id + "';";
        return hasRow(q);
    }

    public boolean deleteAnswer(int id) {
        String q = "DELETE FROM ANSWER WHERE ANSWER_ID='" + id + "';";
        return updateRow(q);
    }

    public ArrayList<Answer> getOptions(int id) {
        if (!checkConnection())
            return null;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM ANSWER WHERE QUESTION_ID='" + id + "';";
            ResultSet rs = s.executeQuery(q);
            ArrayList<Answer> result = new ArrayList<Answer>();
            while (rs.next()) {
                String content = rs.getString("CONTENT");
                int question_id = rs.getInt("QUESTION_ID");
                int exam_id = rs.getInt("EXAM_ID");
                int type = rs.getInt("ANSWER_TYPE");
                int answer_id = rs.getInt("ANSWER_ID");
                boolean isCorrect = rs.getBoolean("IS_CORRECT");
                result.add(new Answer(answer_id, content, question_id, exam_id, type, isCorrect));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifyPassword(int student_id, String password) {
        if (!checkConnection())
            return false;
        try {
            Statement s = conn.createStatement();
            String q = "SELECT * FROM STUDENT WHERE STUDENT_ID='" + student_id + "';";
            ResultSet rs = s.executeQuery(q);
            if (rs.next()) {
                String p = rs.getString("PASSWORD");
                if (p.equals(password))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean enrollStudent(int student_id, int exam_id) {
        String q = "INSERT INTO EXAM_ENROLLED(STUDENT_ID, EXAM_ID) VALUES ('" + student_id + "', '" + exam_id + "');";
        return updateRow(q);
    }

    public boolean isEnrolled(int student_id, int exam_id) {
        String q = "SELECT * FROM EXAM_ENROLLED WHERE STUDENT_ID='" + student_id + "' AND EXAM_ID='" + exam_id + "';";
        return hasRow(q);
    }

    public boolean unEnroll(int student_id, int exam_id) {
        String q = "DELETE FROM EXAM_ENROLLED WHERE STUDENT_ID='" + student_id + "' AND EXAM_ID='" + exam_id + "';";
        return updateRow(q);
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        DB database = DB.getDataBase();
        Student s = database.getStudent(0);
        System.out.println(s.name);
        database.closeConnection();
    }

}
