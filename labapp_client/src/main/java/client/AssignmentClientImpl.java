package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
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
                String path = "/labs/" + a.getLaboratory().getId();
                jsonResponse = HTTPRequest.sendGet(path);
                System.out.println("RESPONSE: " + jsonResponse);
                LaboratoryResponseDTO labDTO = jsonMapper.readValue(jsonResponse, LaboratoryResponseDTO.class);
                Laboratory lab = modelMapper.map(labDTO, Laboratory.class);
                a.setLaboratory(lab);
            }
            return assignments;

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}