package com.sdlab.sdlab.dto.request;

import javax.validation.constraints.NotNull;

public class AttendanceRequestDTO {

    @NotNull
    private boolean attended;

    @NotNull
    private int laboratoryId;

    @NotNull
    private int studentId;

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
    }

    public int getLaboratoryId() {
        return laboratoryId;
    }

    public void setLaboratoryId(int laboratoryId) {
        this.laboratoryId = laboratoryId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
