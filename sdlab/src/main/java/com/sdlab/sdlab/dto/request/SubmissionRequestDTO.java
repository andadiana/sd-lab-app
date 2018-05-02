package com.sdlab.sdlab.dto.request;

import javax.validation.constraints.NotNull;

public class SubmissionRequestDTO {

    @NotNull
    private String description;

    @NotNull
    private int grade;

    @NotNull
    private int studentId;

    @NotNull
    private int assignmentId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    @Override
    public String toString() {
        return "Submission " + description + " grade: " + grade + " studentId: " + studentId + " assignmentId: " + assignmentId;
    }
}
