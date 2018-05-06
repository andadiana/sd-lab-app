package client;

import model.Attendance;

import java.util.List;

public interface AttendanceClient {

    public List<Attendance> getAttendance();
    public Attendance getAttendance(int id);
    //TODO boolean return value for create operations? to know when operations are not successful
    public void createAttendance(Attendance attendance);
    public void updateAttendance(Attendance attendance);
    public void deleteAttendance(int id);
}
