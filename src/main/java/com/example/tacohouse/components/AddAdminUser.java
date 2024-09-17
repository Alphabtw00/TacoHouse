package com.example.tacohouse.components;

import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class AddAdminUser implements CommandLineRunner { //add admin user with admin roles
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
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        userRepository.save(new User(username,passwordEncoder.encode(password),roles));
    }

}
