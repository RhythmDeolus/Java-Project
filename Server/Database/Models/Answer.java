package Server.Database.Models;

public class Answer {
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
