package com.example.vmmanagement.virtualmachine.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Virtual machine response data")
public class VmEntityDtoResponse {
    @Schema(description = "VM unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;
    
    @Schema(description = "Display name of the VM", example = "Development Server")
    private String displayName;
    
    @Schema(description = "Número de núcleos de CPU", example = "2")
    private int cpu;
    
    @Schema(description = "Status atual da VM", example = "RUNNING", allowableValues = {"RUNNING", "STOPPED", "PAUSED"})
    private String status;
    
    @Schema(description = "Memória RAM em MB", example = "4096")
    private int ram;

    public VmEntityDtoResponse(UUID id, String displayName, int cpu, String status, int ram) {
        this.id = id;
        this.displayName = displayName;
        this.cpu = cpu;
        this.status = status;
        this.ram = ram;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }
}
