package com.sdlab.sdlab.dto.response;

public class AttendanceResponseDTO {

    private int id;

    private boolean attended;

    private int laboratoryId;

    private int studentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
