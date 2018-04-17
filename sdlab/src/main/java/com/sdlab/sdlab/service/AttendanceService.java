package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Attendance;

import java.util.List;

public interface AttendanceService {

    public List<Attendance> getAllAttendance();
    public Attendance getAttendanceById(int id);
    public Attendance createAttendance(Attendance attendance);
    public Attendance updateAttendance(Attendance attendance);
    public void deleteAttendance(int id);
    public List<Attendance> getAttendanceByLabId(int labId);
}
