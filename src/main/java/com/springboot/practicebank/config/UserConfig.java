package com.springboot.practicebank.config;

import com.springboot.practicebank.dto.UserDto;
import com.springboot.practicebank.entity.Role;
import com.springboot.practicebank.entity.Status;
import com.springboot.practicebank.service.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public CommandLineRunner commandLineRunner(UserServiceImpl userService) {
        return args -> {

            UserDto admin = UserDto.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("admin@gmail.com")
                    .password("admin")
                    .role(Role.valueOf("ADMIN"))
                    .status(Status.valueOf("ACTIVE"))
                    .build();
            System.out.println(userService.createUser(admin));

            UserDto banker = UserDto.builder()
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
