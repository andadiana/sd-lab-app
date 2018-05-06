package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.PasswordUpdateDTO;
import dto.request.StudentRequestDTO;
import dto.response.StudentCreationResponseDTO;
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

    @Override
    public Student getStudent(int id) {
        try {
            String path = "/students/" + id;
            String jsonResponse = HTTPRequest.sendGet(path);

            System.out.println("RESPONSE: " + jsonResponse);
            StudentResponseDTO studentDTO = jsonMapper.readValue(jsonResponse, StudentResponseDTO.class);
            Student student = modelMapper.map(studentDTO, Student.class);

            return student;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String createStudent(Student student) {
        //returns token or null if not successful
        try {
            StudentRequestDTO studentDTO = modelMapper.map(student, StudentRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(studentDTO);

            String jsonResponse = HTTPRequest.sendPost("/students", jsonString);
            StudentCreationResponseDTO creationResponseDTO = jsonMapper.readValue(jsonResponse, StudentCreationResponseDTO.class);
            return creationResponseDTO.getToken();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            StudentRequestDTO studentDTO = modelMapper.map(student, StudentRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(studentDTO);
            String path = "/students/" + student.getId();
            HTTPRequest.sendPut(path, jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(int id) {
        try {
            String path = "/students/" + id;
            HTTPRequest.sendDelete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updatePassword(Student student, PasswordUpdateDTO passwordUpdateDTO) {
        try {
            String path = "/students/" + student.getId() + "/password";
            String jsonString = jsonMapper.writeValueAsString(passwordUpdateDTO);
            HTTPRequest.sendPut(path, jsonString);

            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
