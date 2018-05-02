package com.sdlab.sdlab.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.model.Submission;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="lab_id")
    private Laboratory laboratory;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy="assignment", cascade = CascadeType.ALL)
    private Set<Submission> submissions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Laboratory getLaboratory() {return laboratory;}

    public void setLaboratory(Laboratory laboratory) {this.laboratory = laboratory;}

    @JsonIgnore
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    @JsonIgnore
    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }

    @Override
    public String toString() {
        return id + " name: " + name + " deadline " + deadline + " description " + description + " lab " + laboratory;
    }
}
