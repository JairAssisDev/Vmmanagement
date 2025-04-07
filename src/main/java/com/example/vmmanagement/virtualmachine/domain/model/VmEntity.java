package com.example.vmmanagement.virtualmachine.domain.model;

import com.example.vmmanagement.user.domain.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class VmEntity {

    //{"displayName": "vm1", "cpu": 2, "memory": 1024, "status": "RUNNING"},

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private int cpu;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;


    @Column(nullable = false)
    private int ram;

    @Column(name="StatusVm")
    @Enumerated(EnumType.STRING)
    private StatusVm status;

    public VmEntity(int cpu, String displayName, StatusVm status, int ram) {
        this.cpu = cpu;
        this.displayName = displayName;
        this.status = status;
        this.ram = ram;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public VmEntity() {

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public StatusVm getStatus() {
        return status;
    }

    public void setStatus(StatusVm status) {
        this.status = status;
    }
}
