package Client.Backend;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Connection {
    Socket s;
    private Socket getSocket() throws UnknownHostException, IOException {
        if (s == null || s.isClosed()) s = new Socket("127.0.0.1", 8000);

        return s;
    }
    private Response getResponse(String query) {
        try (Socket s = getSocket()) {
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(query);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = in.readLine();
            String[] parts = line.split(" ");
            if (line != null && parts.length > 1) {
                String type = parts[1];
                line = in.readLine();
                // System.out.println(line);
                parts = line.split(" ");
                String path = parts[0];
                int noLines = Integer.parseInt(parts[1]);


                HashMap<String, String> properties = new HashMap<String, String>();

                for(int i = 0; i < noLines; i++) {
                    String temp_s = in.readLine();
                    // System.out.println(temp_s);
                    String[] temp = temp_s.split("=");

                    if (temp.length <= 1) {
                        properties.put(temp[0], "T");
                        continue;
                    }
                    properties.put(temp[0], temp[1]);
                }
                s.close();
                out.close();
                in.close();
                return new Response(type, path, properties);

            }
            s.close();
            out.close();
            in.close();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public Response getStudent(int id) {
        String query = "MYPROTOCOL GET\n/student 1\nstudent_id=" + id + "\n";
        return getResponse(query);
    }

    public Response getQuestions(int id) {
        String query = "MYPROTOCOL GET\n/questions 1\nexam_id=" + id + "\n";
        return getResponse(query);
    }
    public Response getOptions(int id) {
        String query = "MYPROTOCOL GET\n/options 1\nquestion_id=" + id + "\n";
        return getResponse(query);
    }
    public Response verifyStudent(int id, String password) {
        String query = "MYPROTOCOL GET\n/verify_student 2\nstudent_id=" + id + "\n" + "password=" + password + "\n";
        return getResponse(query);
    }
    public Response getQuestion(int id) {
        String query = "MYPROTOCOL GET\n/question 1\nquestion_id=" + id + "\n";
        return getResponse(query);
    }
    public Response getAnswer(int id) {
        String query = "MYPROTOCOL GET\n/answer 1\nanswer_id=" + id + "\n";
        return getResponse(query);
    }
    public Response getExam(int id) {
        String query = "MYPROTOCOL GET\n/exam 1\nexam_id=" + id + "\n";
        return getResponse(query);
    }

    public static void main(String[] args) {
        Connection conn = new Connection();
        Response rs = conn.getOptions(0);
        for(String option : rs.props.keySet()) {
            int answer_id = Integer.parseInt(option);
            System.out.println(answer_id);
            Response r2 = conn.getAnswer(answer_id);
            System.out.println(r2.props.get("content"));
        };
    }
}
