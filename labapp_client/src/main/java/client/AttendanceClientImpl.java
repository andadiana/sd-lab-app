package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.AttendanceResponseDTO;
import model.Attendance;
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
            return attendanceDTO.stream().map(a -> modelMapper.map(a, Attendance.class)).collect(Collectors.toList());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
