package com.sdlab.sdlab.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
public class Laboratory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "lab_nr", nullable = false)
    private int labNumber;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "curricula", nullable = false, length = 200)
    private String curricula;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy="laboratory")
    private Set<Assignment> assignments;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(int labNumber) {
        this.labNumber = labNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurricula() {
        return curricula;
    }

    public void setCurricula(String curricula) {
        this.curricula = curricula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Assignment> getAssignments() { return assignments; }

    public void setAssignments(Set<Assignment> assignments) { this.assignments = assignments; }
}
