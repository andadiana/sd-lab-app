package com.sdlab.sdlab.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Column(name = "description", nullable = false)
    private String description;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="lab_id")
    private Laboratory laboratory;

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
}
