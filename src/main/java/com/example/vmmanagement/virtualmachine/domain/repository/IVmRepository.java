package com.example.vmmanagement.virtualmachine.domain.repository;

import com.example.vmmanagement.virtualmachine.domain.model.VmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IVmRepository extends JpaRepository<VmEntity, UUID> {
    List<VmEntity> findAllByUserId(UUID userId);
}

