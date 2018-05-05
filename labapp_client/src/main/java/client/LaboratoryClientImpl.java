package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import connection.HTTPRequest;
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
            System.out.println(e.getMessage());
        }
        return null;
    }
}
