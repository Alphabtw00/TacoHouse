package com.example.tacohouse.controllers;

import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.UserRepository;
import com.example.tacohouse.services.UserService;
import com.example.tacohouse.uses.EditForm;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class MyProfileController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    public MyProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.userService=userService;
    }


    @GetMapping
    public String getProfile(){
        return "profile";
    }
    @GetMapping("/edit")
    public String getEditProfile(){
        return "editProfile";
    }
    @PostMapping("/edit")
    public String editProfile(@Valid EditForm editForm, Errors errors,@AuthenticationPrincipal User user, Model model,
                              RedirectAttributes redirectAttributes){

        if(!(editForm.getNewPassword().equals(editForm.getNewConfirmPassword()))){
            model.addAttribute("passwordMismatchError","New Password and Confirm Password doesnt match!");
            return "editProfile";
        }
        if(!(passwordEncoder.matches(editForm.getCurrentPassword(),user.getPassword()))){
            model.addAttribute("passwordMismatchError", "Current Password and New Password doesnt match!");
            return "editProfile";
        }
        if(errors.hasErrors()){
            return "editProfile";
        }
        userRepository.save(userService.updateUser(user,editForm));
        redirectAttributes.addFlashAttribute("message","Profile Saved Successfully");
        return "redirect:/profile";  //spring security handles session automatically
    }



    @ModelAttribute("user")
    public User user(@AuthenticationPrincipal User user){
        return user;
    }
    @ModelAttribute
    public EditForm editForm(){
        return new EditForm();
    }
}
