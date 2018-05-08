package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.AttendanceRequestDTO;
import dto.response.AttendanceResponseDTO;
import dto.response.LaboratoryResponseDTO;
import dto.response.StudentResponseDTO;
import model.Attendance;
import model.Laboratory;
import model.Student;
import model.UserCredentials;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AttendanceClientImpl implements AttendanceClient {

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;


    public AttendanceClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Attendance> getAttendance(UserCredentials userCredentials) throws Exception{
//        try {
            String jsonResponse = HTTPRequest.sendGet("/attendance", userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            List<AttendanceResponseDTO> attendanceDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<AttendanceResponseDTO>>(){});
            List<Attendance> attendance = attendanceDTO.stream()
                    .map(a -> modelMapper.map(a, Attendance.class)).collect(Collectors.toList());
            for (Attendance a: attendance) {
                //get laboratory for attendance
                Laboratory lab = getLaboratory(a.getLaboratory().getId(), userCredentials);
                a.setLaboratory(lab);

                //get student for attendance
                Student student = getStudent(a.getStudent().getId(), userCredentials);
                a.setStudent(student);
            }
            return attendance;

//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
    }

    @Override
    public Attendance getAttendance(int id, UserCredentials userCredentials) throws Exception {
//        try {
            String path = "/attendance/" + id;
            String jsonResponse = HTTPRequest.sendGet(path, userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            AttendanceResponseDTO attendanceDTO = jsonMapper.readValue(jsonResponse, AttendanceResponseDTO.class);
            Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);
            Laboratory lab = getLaboratory(attendance.getLaboratory().getId(), userCredentials);
            attendance.setLaboratory(lab);

            Student student = getStudent(attendance.getStudent().getId(), userCredentials);
            attendance.setStudent(student);

            return attendance;

//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public void createAttendance(Attendance attendance, UserCredentials userCredentials) throws Exception{
//        try {
            AttendanceRequestDTO attendanceDTO = modelMapper.map(attendance, AttendanceRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(attendanceDTO);
            HTTPRequest.sendPost("/attendance", jsonString, userCredentials);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void updateAttendance(Attendance attendance, UserCredentials userCredentials) throws Exception{
//        try {
            AttendanceRequestDTO attendanceDTO = modelMapper.map(attendance, AttendanceRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(attendanceDTO);
            String path = "/attendance/" + attendance.getId();
            HTTPRequest.sendPut(path, jsonString, userCredentials);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void deleteAttendance(int id, UserCredentials userCredentials) {
        try {
            String path = "/attendance/" + id;
            HTTPRequest.sendDelete(path, userCredentials);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Laboratory getLaboratory(int id, UserCredentials userCredentials) throws Exception{
        String path = "/labs/" + id;
        String jsonResponse = HTTPRequest.sendGet(path, userCredentials);
        System.out.println("RESPONSE: " + jsonResponse);
        LaboratoryResponseDTO labDTO = jsonMapper.readValue(jsonResponse, LaboratoryResponseDTO.class);
        return modelMapper.map(labDTO, Laboratory.class);
    }

    private Student getStudent(int id, UserCredentials userCredentials) throws Exception{
        String path = "/students/" + id;
        String jsonResponse = HTTPRequest.sendGet(path, userCredentials);
        System.out.println("RESPONSE: " + jsonResponse);
        StudentResponseDTO studentDTO = jsonMapper.readValue(jsonResponse, StudentResponseDTO.class);
        return modelMapper.map(studentDTO, Student.class);
    }
}
