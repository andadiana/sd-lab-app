package client;

import model.Attendance;
import model.UserCredentials;

import java.util.List;

public interface AttendanceClient {

    public List<Attendance> getAttendance(UserCredentials userCredentials) throws Exception;
    public Attendance getAttendance(int id, UserCredentials userCredentials) throws Exception;
    public void createAttendance(Attendance attendance, UserCredentials userCredentials) throws Exception;
    public void updateAttendance(Attendance attendance, UserCredentials userCredentials) throws Exception;
    public void deleteAttendance(int id, UserCredentials userCredentials) throws Exception;
}
