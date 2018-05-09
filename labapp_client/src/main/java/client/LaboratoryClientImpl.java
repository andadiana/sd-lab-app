package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.LaboratoryRequestDTO;
import dto.response.LaboratoryResponseDTO;
import model.Laboratory;
import model.UserCredentials;
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

    @Override
    public List<Laboratory> getLaboratories(UserCredentials userCredentials) throws Exception{
//        try {
            String jsonResponse = HTTPRequest.sendGet("/labs", userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            List<LaboratoryResponseDTO> labsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<LaboratoryResponseDTO>>(){});
            List<Laboratory> labs = labsDTO.stream().map(l -> modelMapper.map(l, Laboratory.class)).collect(Collectors.toList());

            return labs;

//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public List<Laboratory> getLaboratories(String keyword, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/labs?keyword=" + keyword;
            String jsonResponse = HTTPRequest.sendGet(path, userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            List<LaboratoryResponseDTO> labsDTO = jsonMapper.readValue(jsonResponse,
                    new TypeReference<List<LaboratoryResponseDTO>>(){});
            List<Laboratory> labs = labsDTO.stream().map(l -> modelMapper.map(l, Laboratory.class)).collect(Collectors.toList());

            return labs;

//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public Laboratory getLaboratory(int id, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/labs/" + id;
            String jsonResponse = HTTPRequest.sendGet(path, userCredentials);

            System.out.println("RESPONSE: " + jsonResponse);
            LaboratoryResponseDTO labDTO = jsonMapper.readValue(jsonResponse, LaboratoryResponseDTO.class);
            Laboratory lab = modelMapper.map(labDTO, Laboratory.class);

            return lab;

//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    public void createLaboratory(Laboratory lab, UserCredentials userCredentials) throws Exception{
//        try {
            LaboratoryRequestDTO labDTO = modelMapper.map(lab, LaboratoryRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(labDTO);
            HTTPRequest.sendPost("/labs", jsonString, userCredentials);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void updateLaboratory(Laboratory lab, UserCredentials userCredentials) throws Exception{
//        try {
            LaboratoryRequestDTO labDTO = modelMapper.map(lab, LaboratoryRequestDTO.class);
            String jsonString = jsonMapper.writeValueAsString(labDTO);
            String path = "/labs/" + lab.getId();
            HTTPRequest.sendPut(path, jsonString, userCredentials);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void deleteLaboratory(int id, UserCredentials userCredentials) throws Exception{
//        try {
            String path = "/labs/" + id;
            HTTPRequest.sendDelete(path, userCredentials);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
