package com.example.tacohouse.services;

import com.example.tacohouse.entities.User;
import com.example.tacohouse.uses.EditForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private PasswordEncoder passwordEncoder;
    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }
    public User updateUser(User user, EditForm editForm){
        if(!(editForm.getNewUsername().isEmpty())) {
            user.setUsername(editForm.getNewUsername());
        }
        if(!(editForm.getNewPassword().isEmpty())){
            user.setPassword(passwordEncoder.encode(editForm.getNewPassword()));
        }
        if(!(editForm.getNewFullName().isEmpty())){
            user.setFullName(editForm.getNewFullName());
        }
        if(!(editForm.getNewStreet().isEmpty())){
            user.setStreet(editForm.getNewStreet());
        }
        if(!(editForm.getNewCity().isEmpty())){
            user.setCity(editForm.getNewCity());
        }
        if(!(editForm.getNewState().isEmpty())){
            user.setState(editForm.getNewState());
        }
        if(!(editForm.getNewZip().isEmpty())){
            user.setZip(editForm.getNewZip());
        }
        if(!(editForm.getNewPhoneNumber().isEmpty())){
            user.setPhoneNumber(editForm.getNewPhoneNumber());
        }

        return user;
    }
}
