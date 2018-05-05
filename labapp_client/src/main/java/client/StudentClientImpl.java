package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.StudentResponseDTO;
import model.Student;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class StudentClientImpl implements StudentClient {

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;

    public StudentClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Student> getStudents() {
        try {
            String jsonResponse = HTTPRequest.sendGet("/students");

            System.out.println("RESPONSE: " + jsonResponse);
            List<StudentResponseDTO> studentsDTO = jsonMapper.readValue(jsonResponse, new TypeReference<List<StudentResponseDTO>>(){});
            return studentsDTO.stream().map(s -> modelMapper.map(s, Student.class)).collect(Collectors.toList());

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
