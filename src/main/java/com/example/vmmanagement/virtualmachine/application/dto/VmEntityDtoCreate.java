package com.example.vmmanagement.virtualmachine.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Virtual machine creation request")
public class VmEntityDtoCreate {

    @Schema(description = "Display name for the VM", example = "Development Server", required = true)
    private String displayName;
    
    @Schema(description = "Number of CPU cores", example = "2", required = true)
    private int cpu;
    
    @Schema(description = "RAM memory in MB", example = "4096", required = true)
    private int ram;
    
    @Schema(
        description = "User ID that owns this VM (automatically set from JWT token, not required in request)",
        example = "123e4567-e89b-12d3-a456-426614174000",
        required = false
    )
    private UUID idUser;

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

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }
}
