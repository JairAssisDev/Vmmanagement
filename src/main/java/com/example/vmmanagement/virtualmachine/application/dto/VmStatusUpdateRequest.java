package com.example.vmmanagement.virtualmachine.application.dto;

import com.example.vmmanagement.virtualmachine.domain.model.StatusVm;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "VM status update request")
public class VmStatusUpdateRequest {
    @Schema(
        description = "New status for the VM", 
        example = "RUNNING", 
        allowableValues = {"RUNNING", "STOPPED", "PAUSED"},
        required = true
    )
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StatusVm getStatusEnum() {
        return StatusVm.valueOf(status);
    }
}
