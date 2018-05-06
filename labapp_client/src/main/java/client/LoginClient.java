package client;

import dto.response.LoginResponseDTO;
import model.Role;

public interface LoginClient {

    public LoginResponseDTO login(String username, String password);
}
