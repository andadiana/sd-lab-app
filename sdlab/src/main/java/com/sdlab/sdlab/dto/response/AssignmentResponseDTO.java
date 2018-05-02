package com.sdlab.sdlab.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class AssignmentResponseDTO {

    private int id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date deadline;

    private String description;

    private int laboratoryId;

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

    public int getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(int laboratoryId) {
        this.laboratoryId = laboratoryId;
    }
}
