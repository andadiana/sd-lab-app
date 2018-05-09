package client;

import dto.response.LoginResponseDTO;

public interface LoginClient {

    public LoginResponseDTO login(String username, String password) throws Exception;
}
