package com.example.vmmanagement.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User registration request")
public class UserDtoRequest {
    @Schema(description = "User's full name", example = "John Doe", required = true)
    private String nome;

    @Schema(description = "CPF (Brazilian ID)", example = "123.456.789-00", required = true)
    private String cpf;

    @Schema(description = "Email address", example = "john.doe@example.com", required = true)
    private String email;

    @Schema(description = "Password", example = "StrongP@ssw0rd", required = true)
    private String password;

    public UserDtoRequest(String nome, String cpf, String email, String password) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
    }
    
    public UserDtoRequest() {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
