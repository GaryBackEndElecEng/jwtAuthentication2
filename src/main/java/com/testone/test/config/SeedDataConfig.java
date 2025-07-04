package com.testone.test.config;

import com.testone.test.model.Role;
import com.testone.test.model.User_;
import com.testone.test.repository.UserRepo;
import com.testone.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class SeedDataConfig implements CommandLineRunner {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;



    @Override
    public void run(String... args) throws Exception {
        if(userRepo.count()==0){

            User_ admin=User_
                    .builder()
                    .firstName("admin")
                    .lastname("admin")
                    .email("admin@email.com")
                    .password(passwordEncoder.encode("12345"))
                    .role(Role.ROLE_ADMIN)
                    .build();
            userService.save(admin);

        }

    }
}
