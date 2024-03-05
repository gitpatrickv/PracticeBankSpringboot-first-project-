package com.springboot.practicebank.config;

import com.springboot.practicebank.entity.constants.Role;
import com.springboot.practicebank.entity.constants.Status;
import com.springboot.practicebank.model.UserModel;
import com.springboot.practicebank.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserConfig {

    @Bean
    public CommandLineRunner commandLineRunner(UserServiceImpl userService) {
        return args -> {

            UserModel admin = UserModel.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("admin@gmail.com")
                    .password("admin")
                    .role(Role.valueOf("ADMIN"))
                    .status(Status.valueOf("ACTIVE"))
                    .build();
            System.out.println(userService.createUser(admin));

            UserModel banker = UserModel.builder()
                    .firstName("Banker")
                    .lastName("Banker")
                    .email("banker@gmail.com")
                    .password("banker")
                    .role(Role.valueOf("BANKER"))
                    .status(Status.valueOf("ACTIVE"))
                    .build();
            System.out.println(userService.createUser(banker));

        };
    }

}
