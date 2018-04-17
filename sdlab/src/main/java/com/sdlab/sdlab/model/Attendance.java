package com.sdlab.sdlab.model;


import javax.persistence.*;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "attended", nullable = false)
    private boolean attended;

    @ManyToOne
    @JoinColumn(name="lab_id")
    private Laboratory laboratory;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
