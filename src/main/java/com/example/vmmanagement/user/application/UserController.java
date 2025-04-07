package com.example.vmmanagement.user.application;

import com.example.vmmanagement.user.application.dto.UserDtoLogin;
import com.example.vmmanagement.user.application.dto.UserDtoReponse;
import com.example.vmmanagement.user.application.dto.UserDtoRequest;
import com.example.vmmanagement.user.domain.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management", description = "Endpoints for user operations")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "User Authentication",
            description = "Authenticates a user with email and password, returns user info with JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Authentication successful",
            content = @Content(schema = @Schema(implementation = UserDtoReponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid credentials",
            content = @Content
    )
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<UserDtoReponse> login(@RequestBody UserDtoLogin userDtoLogin) {
        try {
            UserDtoReponse userDtoReponse = userService.login(userDtoLogin.getEmail(), userDtoLogin.getPassword());
            return ResponseEntity.ok(userDtoReponse);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "User Registration",
            description = "Creates a new user with the provided details"
    )
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Missing required fields")
    @ApiResponse(responseCode = "409", description = "Usuário com este e-mail ou CPF já existe")
    @SecurityRequirements
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid UserDtoRequest userDtoCreat) {
        if(userDtoCreat.getEmail() == null || userDtoCreat.getEmail().isEmpty() || 
           userDtoCreat.getPassword() == null || userDtoCreat.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("E-mail e senha são obrigatórios");
        }
        try {
            userService.createUser(userDtoCreat);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
