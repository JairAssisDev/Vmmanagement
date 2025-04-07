package com.example.vmmanagement;

import com.example.vmmanagement.user.application.dto.UserDtoRequest;
import com.example.vmmanagement.user.domain.model.User;
import com.example.vmmanagement.user.domain.repository.IUserRepository;
import com.example.vmmanagement.user.domain.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class VmmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(VmmanagementApplication.class, args);
    }
    @Bean
    @Profile("dev")
    public CommandLineRunner devProfile(UserService userService) {
        UserDtoRequest user = new UserDtoRequest("jair", "1231231233", "admin@admin.com", "123456");
        System.out.print(user.toString());
        return args -> {
            userService.createUser(user);
        };
    }
}
