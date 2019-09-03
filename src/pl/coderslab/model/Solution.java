package pl.coderslab.model;


import java.sql.Date;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private int exerciseId;
    private int userId;
    private String comment;
    private int grade;

    //pusty konstruktor
    public Solution() {
    }

    //konstruktor ze wszystkimi parametrami bez id
    public Solution(Date created, Date updated, String description, int exerciseId, int usersId, String comment, int grade) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exerciseId = exerciseId;
        this.userId = usersId;
        this.comment = comment; //= null;
        this.grade = grade; // = 0;
    }

    //gettery i settery dla wszystkich pól
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
