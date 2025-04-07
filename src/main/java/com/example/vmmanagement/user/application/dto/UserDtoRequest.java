package com.example.vmmanagement.user.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "User registration request")
public class UserDtoRequest {
    @Schema(description = "User's full name", example = "John Doe", required = true)
    @NotBlank(message = "campo obrigatorio")
    private String nome;

    @Schema(description = "CPF", example = "123.456.789-00", required = true)
    @CPF(message = "CPF inválido")
    private String cpf;

    @Schema(description = "Email address", example = "john.doe@example.com", required = true)
    @Email(message = "Email inválido")
    private String email;

    @Schema(description = "Password", example = "StrongP@ssw0rd", required = true)
    @NotBlank(message = "campo obrigatorio")
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
