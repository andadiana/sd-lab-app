package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.AssignmentResponseDTO;
import dto.response.StudentResponseDTO;
import dto.response.SubmissionResponseDTO;
import model.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class SubmissionClientImpl implements SubmissionClient {

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;


    public SubmissionClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Submission> getSubmissions() {
        try {
            String jsonResponse = HTTPRequest.sendGet("/submissions");

            System.out.println("RESPONSE: " + jsonResponse);
            List<SubmissionResponseDTO> submissionsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<SubmissionResponseDTO>>(){});
            List<Submission> submissions = submissionsDTO.stream()
                    .map(s -> modelMapper.map(s, Submission.class)).collect(Collectors.toList());
            for (Submission s: submissions) {
                //get assignment for submission
                String path = "/assignments/" + s.getAssignment().getId();
                jsonResponse = HTTPRequest.sendGet(path);
                System.out.println("RESPONSE: " + jsonResponse);
                AssignmentResponseDTO assignmentDTO = jsonMapper.readValue(jsonResponse, AssignmentResponseDTO.class);
                Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
                s.setAssignment(assignment);

                //get student for submission
                path = "/students/" + s.getStudent().getId();
                jsonResponse = HTTPRequest.sendGet(path);
                System.out.println("RESPONSE: " + jsonResponse);
                StudentResponseDTO studentDTO = jsonMapper.readValue(jsonResponse, StudentResponseDTO.class);
                Student student = modelMapper.map(studentDTO, Student.class);
                s.setStudent(student);
            }
            return submissions;

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
