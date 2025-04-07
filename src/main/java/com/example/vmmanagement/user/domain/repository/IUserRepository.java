package com.example.vmmanagement.user.domain.repository;

import com.example.vmmanagement.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndPassword(String email, String password);
    
    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

}