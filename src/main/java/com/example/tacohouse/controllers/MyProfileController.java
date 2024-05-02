package com.example.tacohouse.controllers;

import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.UserRepository;
import com.example.tacohouse.services.UserService;
import com.example.tacohouse.uses.EditForm;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
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
        User currentUser = userRepository.findByUsername(user.getUsername());

        if((userRepository.findByUsername(editForm.getNewUsername())!=null)){
            model.addAttribute("usernameTakenError",  "Username Already Taken!");
            return "editProfile";
        }

        if(!(passwordEncoder.matches(editForm.getCurrentPassword(), currentUser.getPassword()))){
            model.addAttribute("passwordMismatchError", "Current Password is wrong!");
            return "editProfile";
        }


        if(!(editForm.getNewPassword().equals(editForm.getNewConfirmPassword()))){
            model.addAttribute("passwordMismatchError","New Password and Confirm Password doesnt match!");
            return "editProfile";
        }

        if(errors.hasErrors()){
            return "editProfile";
        }

        User savedUser = userRepository.save(userService.updateUserFromEditForm(currentUser,editForm));
        userService.updateCurrentAuthenticatedUser(savedUser.getUsername()); //updates currently authenticated principal
        redirectAttributes.addFlashAttribute("message","Profile Saved Successfully");
        return "redirect:/profile";
    }


    @ModelAttribute
    public EditForm editForm(@AuthenticationPrincipal User user){
        EditForm editForm = userService.updateEditFormFromUser(new EditForm(), user);
        return editForm;
    }

    @ModelAttribute(name = "currentUser")
    public User user(@AuthenticationPrincipal User user){
        User currentUser = userRepository.findByUsername(user.getUsername());
        return currentUser;
    }
}
