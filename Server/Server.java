package Server;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import Server.Backend.Request;
import Server.Backend.Response;
import Server.Database.DB;
import Server.Database.Models.Answer;
import Server.Database.Models.Exam;
import Server.Database.Models.Question;
import Server.Database.Models.Student;

public class Server {
    public static void main(String[] args) throws IOException {
        DB db = DB.getDataBase();
        int port = 8000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                Request req = Request.getRequest(clientSocket);

                if (req.path.equals("/student")) {
                    if (req.props.containsKey("student_id")) {
                        int student_id = Integer.parseInt((String)req.props.get("student_id"));
                        Student s = db.getStudent(student_id);
                        Response<Student> rs = new Response<Student>(s);
                        rs.sendResponse(clientSocket, "/student");
                    }
                } else if (req.path.equals("/questions")) {
                    if (req.props.containsKey("exam_id")) {
                        int exam_id = Integer.parseInt((String)req.props.get("exam_id"));
                        Object[] arr = db.getQuestions(exam_id);
                        int questions[] = new int[arr.length];
                        for (int i = 0; i < arr.length; i++) {
                            questions[i] = (int) arr[i];
                        }
                        String res_text = "";
                        for (int i = 0; i < questions.length; i++) {
                            res_text += questions[i] + "\n";
                        }
                        Response.sendTextResponse(clientSocket, req.path, questions.length, res_text);
                    }
                } else if (req.path.equals("/options")) {
                    if (req.props.containsKey("question_id")) {
                        int exam_id = Integer.parseInt((String)req.props.get("question_id"));
                        ArrayList<Answer> arr = db.getOptions(exam_id);
                        String res_text = "";
                        for (int i = 0; i < arr.size(); i++) {
                            res_text += arr.get(i).id + "\n";
                        }
                        Response.sendTextResponse(clientSocket, req.path, arr.size(), res_text);
                    }
                } else if (req.path.equals("/verify_student")) {
                    if (req.props.containsKey("student_id") && req.props.containsKey("password")) {
                        int student_id = Integer.parseInt((String)req.props.get("student_id"));
                        String password = req.props.get("password");
                        boolean res = db.verifyPassword(student_id, password);
                        Response.sendTextResponse(clientSocket, req.path, 1, res? "T\n": "F\n");
                    }
                } else if (req.path.equals("/question")) {
                    if (req.props.containsKey("question_id")) {
                        int question_id = Integer.parseInt((String)req.props.get("question_id"));
                        Question q = db.getQuestion(question_id);
                        Response<Question> rs = new Response<Question>(q);
                        rs.sendResponse(clientSocket, "/question");
                    }
                } else if (req.path.equals("/answer")) {
                    if (req.props.containsKey("answer_id")) {
                        int answer_id = Integer.parseInt((String)req.props.get("answer_id"));
                        // System.out.println(answer_id);
                        Answer q = db.getAnswer(answer_id);
                        Response<Answer> rs = new Response<Answer>(q);
                        rs.sendResponse(clientSocket, "/answer");
                    }
                } else if (req.path.equals("/exam")) {
                    if (req.props.containsKey("exam_id")) {
                        int exam_id = Integer.parseInt((String)req.props.get("exam_id"));
                        Exam e = db.getExam(exam_id);
                        Response<Exam> rs = new Response<Exam>(e);
                        rs.sendResponse(clientSocket, "/exam");
                    }
                } 

                clientSocket.close();
            }
        }
    }
}
