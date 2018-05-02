package com.sdlab.sdlab.dto.request;

import javax.validation.constraints.NotNull;

public class StudentRequestDTO {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String group;

    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}
