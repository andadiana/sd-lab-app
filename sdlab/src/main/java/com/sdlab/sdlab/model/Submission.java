package com.sdlab.sdlab.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
public class Submission {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false, updatable = false)
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "grade")
    private int grade;

    @ManyToOne
    @JoinColumn(name="student_id", updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name="assignment_id", updatable = false)
    private Assignment assignment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return date + " " + student + " " + assignment + " " + grade + " " + description;
    }
}
