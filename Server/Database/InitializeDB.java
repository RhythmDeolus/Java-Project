package Server.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.Scanner;

import Server.Database.Models.*;

public class InitializeDB {

    public static void main(String[] args) throws FileNotFoundException {
        DB db = DB.getDataBase();

        Scanner sc = new Scanner(new File("Server\\Database\\test_data\\Student.csv"));

        String [] columns = sc.nextLine().split(",");
        for (int i = 0; i< columns.length; i++) {
            columns[i] = columns[i].trim();
        }

        while (sc.hasNextLine()) // returns a boolean value
        {
            String [] data = sc.nextLine().split(",");
            for (int i = 0; i< data.length; i++) {
                data[i] = data[i].trim();
            }
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String password = data[2];
            Student student = new Student(id, name);
            db.addStudent(student, password);
        }
        sc.close(); // closes the scanner


        sc = new Scanner(new File("Server\\Database\\test_data\\Exam.csv"));

        columns = sc.nextLine().split(",");
        for (int i = 0; i< columns.length; i++) {
            columns[i] = columns[i].trim();
        }

        while (sc.hasNextLine()) // returns a boolean value
        {
            String [] data = sc.nextLine().split(",");
            for (int i = 0; i< data.length; i++) {
                columns[i] = columns[i].trim();
            }
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            Date startTime = new Date(id);
            Date endTime = new Date(id);
            db.addExam(new Exam(id, name, startTime, endTime));
        }
        sc.close();

        sc = new Scanner(new File("Server\\Database\\test_data\\Question.csv"));

        columns = sc.nextLine().split(",");
        for (int i = 0; i< columns.length; i++) {
            columns[i] = columns[i].trim();
        }

        while (sc.hasNextLine()) // returns a boolean value
        {
            String [] data = sc.nextLine().split(",");
            for (int i = 0; i< data.length; i++) {
                data[i] = data[i].trim();
            }
            int id = Integer.parseInt(data[0]);
            String statement = data[1];
            int exam_id = Integer.parseInt(data[2]);
            // int answer_id = Integer.parseInt(data[3]);
            int type = Integer.parseInt(data[3]);
            db.addQuestion(new Question(id, statement, exam_id, type));
        }
        sc.close();

        sc = new Scanner(new File("Server\\Database\\test_data\\Answer.csv"));

        columns = sc.nextLine().split(",");
        for (int i = 0; i< columns.length; i++) {
            columns[i] = columns[i].trim();
        }

        while (sc.hasNextLine()) // returns a boolean value
        {
            String [] data = sc.nextLine().split(",");
            for (int i = 0; i< data.length; i++) {
                data[i] = data[i].trim();
            }
            int id = Integer.parseInt(data[0]);
            String content = data[1];
            int question_id = Integer.parseInt(data[2]);
            int exam_id = Integer.parseInt(data[3]);
            int type = Integer.parseInt(data[4]);
            boolean isCorrect = Integer.parseInt(data[5]) != 0;
            db.addAnswer(new Answer(id, content, question_id, exam_id, type, isCorrect));
        }
        sc.close();

        sc = new Scanner(new File("Server\\Database\\test_data\\Exam_Enrolled.csv"));

        columns = sc.nextLine().split(",");
        for (int i = 0; i< columns.length; i++) {
            columns[i] = columns[i].trim();
        }

        while (sc.hasNextLine()) // returns a boolean value
        {
            String [] data = sc.nextLine().split(",");
            for (int i = 0; i< data.length; i++) {
                data[i] = data[i].trim();
            }
            int student_id = Integer.parseInt(data[0]);
            int exam_id = Integer.parseInt(data[1]);
            db.enrollStudent(student_id, exam_id);
        }
        sc.close();
    }
}
