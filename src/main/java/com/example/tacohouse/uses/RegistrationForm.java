package com.example.tacohouse.uses;

import com.example.tacohouse.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "confirm Password is required")
    private String confirm;
    @NotBlank(message = "Name is is required")
    private String fullName;
    @NotBlank(message = "Street is required")
    private String street;
    @NotBlank(message = "City is required")
    private String city;
    @NotBlank(message = "State is required")
    private String state;
    @Pattern(regexp = "\\d{6}",message = "ZIP must be 6 digits")
    private String zip;
    @Pattern(regexp = "\\d{10}", message = "Phone Number must be 10 digits")
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(username,passwordEncoder.encode(password),"ROLE_USER",fullName,street,city,state,zip,phone);
        return user;
    }

}
