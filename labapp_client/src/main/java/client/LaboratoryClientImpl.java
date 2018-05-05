package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.LaboratoryRequestDTO;
import dto.response.LaboratoryResponseDTO;
import model.Laboratory;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class LaboratoryClientImpl implements  LaboratoryClient{

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;


    public LaboratoryClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    public List<Laboratory> getLaboratories() {
        try {
            String jsonResponse = HTTPRequest.sendGet("/labs");

            System.out.println("RESPONSE: " + jsonResponse);
            List<LaboratoryResponseDTO> labsDTO = jsonMapper.readValue(jsonResponse.toString(),
                    new TypeReference<List<LaboratoryResponseDTO>>(){});
            List<Laboratory> labs = labsDTO.stream().map(l -> modelMapper.map(l, Laboratory.class)).collect(Collectors.toList());

            return labs;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createLaboratory(Laboratory lab) {
        try {
            LaboratoryRequestDTO labDTO = modelMapper.map(lab, LaboratoryRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(labDTO);
            HTTPRequest.sendPost("/labs", jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLaboratory(Laboratory lab) {
        try {
            LaboratoryRequestDTO labDTO = modelMapper.map(lab, LaboratoryRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(labDTO);
            String path = "/labs/" + lab.getId();
            HTTPRequest.sendPut(path, jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLaboratory(int id) {
        try {
            String path = "/labs/" + id;
            HTTPRequest.sendDelete(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
