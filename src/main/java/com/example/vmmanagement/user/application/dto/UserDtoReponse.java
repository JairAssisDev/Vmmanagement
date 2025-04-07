package com.example.vmmanagement.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "User login response")
public class UserDtoReponse {

    @Schema(description = "User's display name", example = "John Doe")
    public String username;
    
    @Schema(description = "User's unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    public UUID userId;
    
    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    public String token;
}
