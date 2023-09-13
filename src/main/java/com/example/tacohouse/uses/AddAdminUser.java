package com.example.tacohouse.uses;

import com.example.tacohouse.model.User;
import com.example.tacohouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AddAdminUser implements CommandLineRunner {
    private UserRepository userRepository;
    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;
    private PasswordEncoder passwordEncoder;

    public AddAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername(username)==null){
            userRepository.save(new User(username,passwordEncoder.encode(password),"ROLE_ADMIN"));
        }
    }
}
