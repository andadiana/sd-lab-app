package client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.AssignmentRequestDTO;
import dto.response.AssignmentResponseDTO;
import dto.response.LaboratoryResponseDTO;
import model.Assignment;
import model.Laboratory;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AssignmentClientImpl implements AssignmentClient {

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;


    public AssignmentClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Assignment> getAssignments() {
        try {

            String jsonResponse = HTTPRequest.sendGet("/assignments");

            System.out.println("RESPONSE: " + jsonResponse);
            List<AssignmentResponseDTO> assignmentsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<AssignmentResponseDTO>>(){});
            List<Assignment> assignments = assignmentsDTO.stream()
                    .map(a -> modelMapper.map(a, Assignment.class)).collect(Collectors.toList());
            for (Assignment a: assignments) {
                Laboratory lab = getLaboratory(a.getLaboratory().getId());
                a.setLaboratory(lab);
            }
            return assignments;

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Assignment getAssignment(int id) {
        try {
            String path = "/assignments/" + id;
            String jsonResponse = HTTPRequest.sendGet(path);

            System.out.println("RESPONSE: " + jsonResponse);
            AssignmentResponseDTO assignmentDTO = jsonMapper.readValue(jsonResponse, AssignmentResponseDTO.class);
            Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
            Laboratory lab = getLaboratory(assignment.getLaboratory().getId());
            assignment.setLaboratory(lab);

            return assignment;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createAssignment(Assignment assignment) {
        try {
            AssignmentRequestDTO assignmentDTO = modelMapper.map(assignment, AssignmentRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(assignmentDTO);
            HTTPRequest.sendPost("/assignments", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAssignment(Assignment assignment) {
        try {
            AssignmentRequestDTO assignmentDTO = modelMapper.map(assignment, AssignmentRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(assignmentDTO);
            String path = "/assignments/" + assignment.getId();
            HTTPRequest.sendPut(path, jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAssignment(int id) {
        try {
            String path = "/assignments/" + id;
            HTTPRequest.sendDelete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Laboratory getLaboratory(int id) throws Exception{
        String path = "/labs/" + id;
        String jsonResponse = HTTPRequest.sendGet(path);
        System.out.println("RESPONSE: " + jsonResponse);
        LaboratoryResponseDTO labDTO = jsonMapper.readValue(jsonResponse, LaboratoryResponseDTO.class);
        return modelMapper.map(labDTO, Laboratory.class);
    }
}
