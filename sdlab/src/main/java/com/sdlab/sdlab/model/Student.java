package com.sdlab.sdlab.model;

import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Student extends User {

    @Column(name = "group_nr", nullable = false)
    private String group;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "password_set")
    private boolean passwordSet;

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
}
