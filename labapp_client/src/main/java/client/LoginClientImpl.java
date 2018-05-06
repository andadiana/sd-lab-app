package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.request.LoginRequestDTO;
import dto.response.LoginResponseDTO;
import org.modelmapper.ModelMapper;

public class LoginClientImpl implements LoginClient {

    private ObjectMapper jsonMapper;

    private ModelMapper modelMapper;


    public LoginClientImpl(ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public LoginResponseDTO login(String email, String password) {
        //returns response DTO or null if not successful
        try {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
            loginRequestDTO.setEmail(email);
            loginRequestDTO.setPassword(password);
            String jsonString = jsonMapper.writeValueAsString(loginRequestDTO);

            String jsonResponse = HTTPRequest.sendPost("/login", jsonString);
            LoginResponseDTO loginResponseDTO = jsonMapper.readValue(jsonResponse, LoginResponseDTO.class);
            return loginResponseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
