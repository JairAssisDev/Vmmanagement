package com.example.vmmanagement.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User login request")
public class UserDtoLogin {
    @Schema(description = "User email address", example = "user@example.com", required = true)
    private String email;
    
    @Schema(description = "User password", example = "password123", required = true)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDtoLogin(String password, String email) {
        this.password = password;
        this.email = email;
    }
    
    public UserDtoLogin() {
    }
}
