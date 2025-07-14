package com.web.banbut.configuration;

import com.web.banbut.entity.User;
import com.web.banbut.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationInitConfig {
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                User user = new User(
                        "admin",
                        passwordEncoder.encode("admin"),
                        "admin@admin.com"
                );
                user.setRole("ADMIN");
                userRepository.save(user);
            }
        };
    }
}
