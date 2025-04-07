package com.example.vmmanagement.virtualmachine.domain.service;

import com.example.vmmanagement.user.domain.model.User;
import com.example.vmmanagement.user.domain.repository.IUserRepository;
import com.example.vmmanagement.virtualmachine.application.dto.VmEntityDtoCreate;
import com.example.vmmanagement.virtualmachine.application.dto.VmEntityDtoResponse;
import com.example.vmmanagement.virtualmachine.domain.model.StatusVm;
import com.example.vmmanagement.virtualmachine.domain.model.VmEntity;
import com.example.vmmanagement.virtualmachine.domain.repository.IVmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VmService {

    @Autowired
    private final IUserRepository IUserRepository;
    private final IVmRepository IVmRepository;

    public VmService(IUserRepository IUserRepository, IVmRepository IVmRepository) {
        this.IUserRepository = IUserRepository;
        this.IVmRepository = IVmRepository;
    }

    public VmEntityDtoResponse createVm(VmEntityDtoCreate dto) {
        User user = IUserRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        VmEntity vmEntity = new VmEntity(dto.getCpu(), dto.getDisplayName(), StatusVm.RUNNING, dto.getRam());
        vmEntity.setUser(user);

        VmEntity savedVm = IVmRepository.save(vmEntity);
        return new VmEntityDtoResponse(
                savedVm.getId(),
                savedVm.getDisplayName(),
                savedVm.getCpu(),
                savedVm.getStatus().toString(),
                savedVm.getRam()
        );
    }

    public List<VmEntityDtoResponse> listVmsByUser(UUID userId) {
        User user = IUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<VmEntity> vms = IVmRepository.findAllByUserId(user.getId());

        return vms.stream()
                .map(vm -> new VmEntityDtoResponse(
                        vm.getId(),
                        vm.getDisplayName(),
                        vm.getCpu(),
                        vm.getStatus().toString(),
                        vm.getRam()
                )).toList();
    }

    public boolean updateVmStatus(UUID id, String statusStr) {
        try {
            StatusVm status = StatusVm.valueOf(statusStr);
            Optional<VmEntity> vmOptional = IVmRepository.findById(id);
            
            if (vmOptional.isPresent()) {
                VmEntity vm = vmOptional.get();
                vm.setStatus(status);
                IVmRepository.save(vm);
                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + statusStr);
        }
    }

    public boolean deleteVm(UUID id) {
        if (IVmRepository.existsById(id)) {
            IVmRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Check if a VM is owned by a specific user
     * @param vmId the VM ID to check
     * @param userId the user ID to check ownership against
     * @return true if the VM is owned by the user, false otherwise
     */
    public boolean isVmOwnedByUser(UUID vmId, UUID userId) {
        Optional<VmEntity> vmOptional = IVmRepository.findById(vmId);
        if (vmOptional.isPresent()) {
            VmEntity vm = vmOptional.get();
            return vm.getUser().getId().equals(userId);
        }
        return false;
    }
}
