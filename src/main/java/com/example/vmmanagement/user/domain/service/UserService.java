package com.example.vmmanagement.user.domain.service;

import com.example.vmmanagement.config.Security.JwtUtil;
import com.example.vmmanagement.user.application.dto.UserDtoReponse;
import com.example.vmmanagement.user.application.dto.UserDtoRequest;
import com.example.vmmanagement.user.domain.model.User;
import com.example.vmmanagement.user.domain.repository.IUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final IUserRepository IUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(IUserRepository IUserRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.IUserRepository = IUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public UserDtoReponse login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            
            User user = IUserRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            
            String token = jwtUtil.generateToken(email, user.getId());
            
            UserDtoReponse userDtoReponse = new UserDtoReponse();
            userDtoReponse.userId = user.getId();
            userDtoReponse.username = user.getNome();
            userDtoReponse.token = token;
            
            return userDtoReponse;
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }

    public void createUser(UserDtoRequest userDto) {
        boolean existsByEmail = IUserRepository.existsByEmail(userDto.getEmail());
        boolean existsByCpf = IUserRepository.existsByCpf(userDto.getCpf());

        if (existsByEmail || existsByCpf) {
            throw new RuntimeException("Usuário com este e-mail ou CPF já existe");
        }
        
        User user = new User(
                userDto.getNome(),
                userDto.getCpf(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword())
        );
        IUserRepository.save(user);
    }

    public boolean validateUser(UUID userId) {
        User user = IUserRepository.findById(userId).orElse(null);
        return (user != null);
    }
}
