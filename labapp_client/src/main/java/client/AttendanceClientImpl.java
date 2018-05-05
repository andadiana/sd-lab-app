package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.AttendanceResponseDTO;
import dto.response.LaboratoryResponseDTO;
import dto.response.StudentResponseDTO;
import model.Attendance;
import model.Laboratory;
import model.Student;
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
    public List<Attendance> getAttendance() {
        try {
            String jsonResponse = HTTPRequest.sendGet("/attendance");

            System.out.println("RESPONSE: " + jsonResponse);
            List<AttendanceResponseDTO> attendanceDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<AttendanceResponseDTO>>(){});
            List<Attendance> attendance = attendanceDTO.stream()
                    .map(a -> modelMapper.map(a, Attendance.class)).collect(Collectors.toList());
            for (Attendance a: attendance) {
                //get laboratory for attendance
                String path = "/labs/" + a.getLaboratory().getId();
                jsonResponse = HTTPRequest.sendGet(path);
                System.out.println("RESPONSE: " + jsonResponse);
                LaboratoryResponseDTO labDTO = jsonMapper.readValue(jsonResponse, LaboratoryResponseDTO.class);
                Laboratory lab = modelMapper.map(labDTO, Laboratory.class);
                a.setLaboratory(lab);

                //get student for attendance
                path = "/students/" + a.getStudent().getId();
                jsonResponse = HTTPRequest.sendGet(path);
                System.out.println("RESPONSE: " + jsonResponse);
                StudentResponseDTO studentDTO = jsonMapper.readValue(jsonResponse, StudentResponseDTO.class);
                Student student = modelMapper.map(studentDTO, Student.class);
                a.setStudent(student);
            }
            return attendance;

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
