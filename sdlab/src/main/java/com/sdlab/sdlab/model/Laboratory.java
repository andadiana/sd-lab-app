package com.sdlab.sdlab.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Entity
public class Laboratory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "lab_nr", nullable = false)
    private int labNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    private Date date;

    @NotNull
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @NotNull
    @Column(name = "curricula", nullable = false, length = 200)
    private String curricula;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy="laboratory", cascade = CascadeType.ALL)
    private Set<Assignment> assignments;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy="laboratory", cascade = CascadeType.ALL)
    private Set<Attendance> attendance;


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

    @JsonIgnore
    public Set<Assignment> getAssignments() { return assignments; }

    @JsonIgnore
    public void setAssignments(Set<Assignment> assignments) { this.assignments = assignments; }

    @JsonIgnore
    public Set<Attendance> getAttendance() {
        return attendance;
    }

    @JsonIgnore
    public void setAttendance(Set<Attendance> attendance) {
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return id + " lab number " + labNumber + " date " + date + " title " + title + " curricula " +
                curricula + " description " + description;
    }
}
