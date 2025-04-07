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
@Tag(name = "Virtual Machine Management", description = "Endpoints for VM operations")
@SecurityRequirement(name = "bearerAuth")
public class VmController {

    private final VmService vmService;
    private final SecurityContext securityContext;

    public VmController(VmService vmService, SecurityContext securityContext) {
        this.vmService = vmService;
        this.securityContext = securityContext;
    }

    @Operation(
            summary = "Create a new virtual machine",
            description = "Creates a virtual machine for the authenticated user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "VM created successfully",
            content = @Content(schema = @Schema(implementation = VmEntityDtoResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PostMapping
    public ResponseEntity<VmEntityDtoResponse> createVm(@RequestBody VmEntityDtoCreate dtoCreate) {
        try {
            // Get current user ID from JWT token
            UUID currentUserId = securityContext.getCurrentUserId();
            if (currentUserId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            // Set the user ID from the token
            dtoCreate.setIdUser(currentUserId);
            
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
            summary = "List authenticated user's virtual machines",
            description = "Returns all virtual machines belonging to the authenticated user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "VMs retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = VmEntityDtoResponse.class)))
    )
    @ApiResponse(responseCode = "401", description = "User not authenticated")
    @ApiResponse(responseCode = "404", description = "User not found")
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
            summary = "Update VM status",
            description = "Changes the running status of a virtual machine owned by the authenticated user"
    )
    @ApiResponse(responseCode = "200", description = "VM status updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid status value")
    @ApiResponse(responseCode = "403", description = "Not authorized to update this VM")
    @ApiResponse(responseCode = "404", description = "VM not found")
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
            
            // First check if VM belongs to current user
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
            summary = "Delete a virtual machine",
            description = "Permanently removes a virtual machine owned by the authenticated user"
    )
    @ApiResponse(responseCode = "204", description = "VM deleted successfully")
    @ApiResponse(responseCode = "403", description = "Not authorized to delete this VM")
    @ApiResponse(responseCode = "404", description = "VM not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVm(@PathVariable UUID id) {
        UUID currentUserId = securityContext.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // First check if VM belongs to current user
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
