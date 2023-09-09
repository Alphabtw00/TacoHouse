package com.example.tacohouse.uses;

import com.example.tacohouse.model.User;
import com.example.tacohouse.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AddAdminUser implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public AddAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername("admin")==null){
            userRepository.save(new User("admin",passwordEncoder.encode("12345"),"ROLE_ADMIN"));
        }
    }
}
