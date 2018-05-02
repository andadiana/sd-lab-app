package com.sdlab.sdlab.dto.response;

public class LoginResponseDTO {

    private String role;

    private boolean passwordSet;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isPasswordSet() {
        return passwordSet;
    }

    public void setPasswordSet(boolean passwordSet) {
        this.passwordSet = passwordSet;
    }
}
