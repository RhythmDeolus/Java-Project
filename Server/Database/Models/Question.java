package Server.Database.Models;

// import java.util.ArrayList;

public class Question {
    public int id;
    public String statement;
    public int exam_id;
    public int type;
    public Question(int id, String statement, int exam_id, int type) {
        this.id = id;
        this.statement = statement;
        this.exam_id = exam_id;
        this.type = type;
    }
}
