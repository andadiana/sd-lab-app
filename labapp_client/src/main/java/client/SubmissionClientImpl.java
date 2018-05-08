package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.SubmissionRequestDTO;
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
    public List<Submission> getSubmissions(UserCredentials userCredentials) throws Exception{
//        try {
            String jsonResponse = HTTPRequest.sendGet("/submissions", userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            List<SubmissionResponseDTO> submissionsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<SubmissionResponseDTO>>(){});
            List<Submission> submissions = submissionsDTO.stream()
                    .map(s -> modelMapper.map(s, Submission.class)).collect(Collectors.toList());
            for (Submission s: submissions) {
                //get assignment for submission
                Assignment assignment = getAssignment(s.getAssignment().getId(), userCredentials);
                s.setAssignment(assignment);

                //get student for submission
                Student student = getStudent(s.getStudent().getId(), userCredentials);
                s.setStudent(student);
            }
            return submissions;

//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
    }

    @Override
    public Submission getSubmission(int id, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/submissions/" + id;
            String jsonResponse = HTTPRequest.sendGet(path, userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            SubmissionResponseDTO submissionDTO = jsonMapper.readValue(jsonResponse, SubmissionResponseDTO.class);
            Submission submission = modelMapper.map(submissionDTO, Submission.class);
            Assignment assignment = getAssignment(submission.getAssignment().getId(), userCredentials);
            submission.setAssignment(assignment);

            Student student = getStudent(submission.getStudent().getId(), userCredentials);
            submission.setStudent(student);

            return submission;

//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public void createSubmission(Submission submission, UserCredentials userCredentials) throws Exception{
//        try {
            SubmissionRequestDTO submissionDTO = modelMapper.map(submission, SubmissionRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(submissionDTO);
            HTTPRequest.sendPost("/submissions", jsonString, userCredentials);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void updateSubmission(Submission submission, UserCredentials userCredentials) throws Exception{
//        try {
            SubmissionRequestDTO submissionDTO = modelMapper.map(submission, SubmissionRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(submissionDTO);
            String path = "/submissions/" + submission.getId();
            HTTPRequest.sendPut(path, jsonString, userCredentials);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void deleteSubmission(int id, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/submissions/" + id;
            HTTPRequest.sendDelete(path, userCredentials);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public List<Submission> getSubmissionsForAssignment(Assignment assignment, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/submissions/assignments/" + assignment.getId();
            String jsonResponse = HTTPRequest.sendGet(path, userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            List<SubmissionResponseDTO> submissionsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<SubmissionResponseDTO>>(){});
            List<Submission> submissions = submissionsDTO.stream()
                    .map(s -> modelMapper.map(s, Submission.class)).collect(Collectors.toList());

            for (Submission s: submissions) {
                //get assignment for submission
                s.setAssignment(assignment);

                //get student for submission
                Student student = getStudent(s.getStudent().getId(), userCredentials);
                s.setStudent(student);
            }
            return submissions;

//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
    }

    @Override
    public List<Submission> getSubmissionsForStudent(Student student, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/submissions/students/" + student.getId();
            String jsonResponse = HTTPRequest.sendGet(path, userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            List<SubmissionResponseDTO> submissionsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<SubmissionResponseDTO>>(){});
            List<Submission> submissions = submissionsDTO.stream()
                    .map(s -> modelMapper.map(s, Submission.class)).collect(Collectors.toList());

            for (Submission s: submissions) {
                //get assignment for submission
                Assignment assignment = getAssignment(s.getAssignment().getId(), userCredentials);
                s.setAssignment(assignment);

                //set student for submission
                s.setStudent(student);
            }
            return submissions;

//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
    }

    private Assignment getAssignment(int id, UserCredentials userCredentials) throws Exception{
        String path = "/assignments/" + id;
        String jsonResponse = HTTPRequest.sendGet(path, userCredentials);
        System.out.println("RESPONSE: " + jsonResponse);
        AssignmentResponseDTO assignmentDTO = jsonMapper.readValue(jsonResponse, AssignmentResponseDTO.class);
        return modelMapper.map(assignmentDTO, Assignment.class);
    }

    private Student getStudent(int id, UserCredentials userCredentials) throws Exception{
        String path = "/students/" + id;
        String jsonResponse = HTTPRequest.sendGet(path, userCredentials);
        System.out.println("RESPONSE: " + jsonResponse);
        StudentResponseDTO studentDTO = jsonMapper.readValue(jsonResponse, StudentResponseDTO.class);
        return modelMapper.map(studentDTO, Student.class);
    }
}
