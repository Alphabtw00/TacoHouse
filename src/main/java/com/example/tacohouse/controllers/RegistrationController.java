package com.example.tacohouse.controllers;

import com.example.tacohouse.repositories.UserRepository;
import com.example.tacohouse.uses.RegistrationForm;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @GetMapping
    public String registerForm(){
        return "registration";
    }
    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, Errors errors, Model model) {
        if(userRepository.findByUsername(form.getUsername())!=null){
            model.addAttribute("usernameTakenError",  "Please Pick a different Username or Log in");
            return "registration";
        }
        if(errors.hasErrors()){
            return "registration";
        }
        if (!form.getPassword().equals(form.getConfirm())) {
            model.addAttribute("passwordMismatchError", "Passwords do not match.");
            return "registration";
        }
        userRepository.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }



    @ModelAttribute
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

}
