package com.example.vmmanagement.virtualmachine.application;

import com.example.vmmanagement.config.security.SecurityContext;
import com.example.vmmanagement.virtualmachine.application.dto.VmEntityDtoCreate;
import com.example.vmmanagement.virtualmachine.application.dto.VmEntityDtoResponse;
import com.example.vmmanagement.virtualmachine.application.dto.VmStatusUpdateRequest;
import com.example.vmmanagement.virtualmachine.domain.service.VmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vm")
@Tag(name = "Gerenciamento de máquinas virtuais", description = "Pontos de extremidade para operações de VM")
@SecurityRequirement(name = "bearerAuth")
public class VmController {

    private final VmService vmService;
    private final SecurityContext securityContext;

    public VmController(VmService vmService, SecurityContext securityContext) {
        this.vmService = vmService;
        this.securityContext = securityContext;
    }

    @Operation(
            summary = "Crie uma nova máquina virtual",
            description = "Cria uma máquina virtual para o usuário autenticado"
    )
    @ApiResponse(
            responseCode = "201",
            description = "VM criada com sucesso",
            content = @Content(schema = @Schema(implementation = VmEntityDtoResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Dados de solicitação inválidos")
    @ApiResponse(responseCode = "401", description = "Usuario não autenticado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "429 ",description = "Quantidade maxima de Vms Atingida")
    @PostMapping
    public ResponseEntity<VmEntityDtoResponse> createVm(@RequestBody VmEntityDtoCreate dtoCreate) {
        try {
            UUID currentUserId = securityContext.getCurrentUserId();
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            dtoCreate.setIdUser(currentUserId);
            if (vmService.maxSizeReached(currentUserId)){
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
            };
            VmEntityDtoResponse response = vmService.createVm(dtoCreate);
            if (response != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Listar máquinas virtuais do usuário autenticado",
            description = "Retorna todas as máquinas virtuais pertencentes ao usuário"
    )
    @ApiResponse(
            responseCode = "200",
            description = "VMs Listadas com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VmEntityDtoResponse.class)))
    )
    @ApiResponse(responseCode = "401", description = "Usuario não autenticado")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping
    public ResponseEntity<?> getCurrentUserVms() {
        try {
            UUID currentUserId = securityContext.getCurrentUserId();
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            List<VmEntityDtoResponse> vms = vmService.listVmsByUser(currentUserId);
            return ResponseEntity.ok(vms);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @Operation(
            summary = "Mudar status da VM",
            description = "Altera o status de execução de uma máquina virtual de propriedade do usuário"
    )
    @ApiResponse(responseCode = "200", description = "a alteração do status da VM foi alterado com sucesso")
    @ApiResponse(responseCode = "400", description = "valor do status invalido")
    @ApiResponse(responseCode = "401", description = "Usuario não autenticado")
    @ApiResponse(responseCode = "403", description = "sem alteração para alterado a VM")
    @ApiResponse(responseCode = "404", description = "VM não encontrada")
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateVmStatus(
            @PathVariable UUID id,
            @RequestBody VmStatusUpdateRequest request
    ) {
        try {
            UUID currentUserId = securityContext.getCurrentUserId();
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            if (!vmService.isVmOwnedByUser(id, currentUserId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            boolean updated = vmService.updateVmStatus(id, request.getStatus());
            if (updated) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(
            summary = "Excluir uma máquina virtual",
            description = "Remove permanentemente uma máquina virtual de propriedade do usuário autenticado"
    )
    @ApiResponse(responseCode = "204", description = "VM excluída com sucesso")
    @ApiResponse(responseCode = "401", description = "Usuario não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado a excluir esta VM")
    @ApiResponse(responseCode = "404", description = "VM não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVm(@PathVariable UUID id) {
        UUID currentUserId = securityContext.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!vmService.isVmOwnedByUser(id, currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        boolean deleted = vmService.deleteVm(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
