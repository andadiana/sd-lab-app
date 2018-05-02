package com.sdlab.sdlab.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class LaboratoryRequestDTO {

    @NotNull
    private int labNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull
    private String title;

    @NotNull
    private String curricula;

    @NotNull
    private String description;

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
}
