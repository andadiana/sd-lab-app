package com.sdlab.sdlab.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {

    @NotNull
    @Column(name = "group_nr", nullable = false)
    private String group;

    @Column(name = "hobby")
    private String hobby;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    @JsonIgnore
    private Set<Attendance> attendance;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy="student")
    private Set<Submission> submissions;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    @JsonIgnore
    public Set<Attendance> getAttendance() {
        return attendance;
    }

    @JsonIgnore
    public void setAttendance(Set<Attendance> attendance) {
        this.attendance = attendance;
    }

    @JsonIgnore
    public Set<Submission> getSubmissions() {
        return submissions;
    }

    @JsonIgnore
    public void setSubmissions(Set<Submission> submissions) {
        this.submissions = submissions;
    }
}
