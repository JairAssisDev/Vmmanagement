package com.example.vmmanagement.virtualmachine.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Solicitação de criação de máquina virtual")
public class VmEntityDtoCreate {

    @Schema(description = "Nome de exibição da VM", example = "Development Server", required = true)
    private String displayName;
    
    @Schema(description = "Número de núcleos de CPU", example = "2", required = true)
    private int cpu;
    
    @Schema(description = "Memória RAM em MB", example = "4096", required = true)
    private int ram;
    
    @Schema(
        description = "ID do usuário que possui esta VM (definido automaticamente a partir do token JWT, não necessário na solicitação)",
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
