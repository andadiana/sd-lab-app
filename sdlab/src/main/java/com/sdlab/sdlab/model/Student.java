package com.sdlab.sdlab.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {

    @Column(name = "group_nr", nullable = false)
    private String group;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "password_set")
    private boolean passwordSet;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    @JsonIgnore
    private Set<Attendance> attendance;

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

    public boolean isPasswordSet() {
        return passwordSet;
    }

    public void setPasswordSet(boolean passwordSet) {
        this.passwordSet = passwordSet;
    }

    @JsonIgnore
    public Set<Attendance> getAttendance() {
        return attendance;
    }

    @JsonIgnore
    public void setAttendance(Set<Attendance> attendance) {
        this.attendance = attendance;
    }
}
