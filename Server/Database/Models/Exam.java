package Server.Database.Models;

import java.sql.Date;

public class Exam {
    public int id;
    public String name;
    public Date startTime;
    public Date endTime;
    public Exam(int id, String name, Date startTime, Date endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
