package Server.Database.Models;

public class StudentResponse {
    public int exam_id;
    public int response_id;
    public int student_id;
    public int question_id;
    public int answer_id;
    public StudentResponse(int exam_id, int response_id, int student_id, int question_id, int answer_id) {
        this.exam_id = exam_id;
        this.response_id = response_id;
        this.student_id = student_id;
        this.question_id = question_id;
        this.answer_id = answer_id;
    }
}
