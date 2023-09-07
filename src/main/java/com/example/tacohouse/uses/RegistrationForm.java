package com.example.tacohouse.uses;

import com.example.tacohouse.model.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phone);
        user.getRoles().add("ROLE_USER");
        return user;
    }

}
