package com.example.vmmanagement.user.domain.model;

import com.example.vmmanagement.virtualmachine.domain.model.VmEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    public User(){}

    public User(String nome , String cpf, String email, String password ) {
        this.email = email;
        this.password = password;
        this.nome  = nome;
        this.cpf = cpf;
    }

    public UUID getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false,unique = true)
    private String cpf;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VmEntity> vms = new ArrayList<>();

    public List<VmEntity> getVms() {
        return vms;
    }

    public void setVms(List<VmEntity> vms) {
        this.vms = vms;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
