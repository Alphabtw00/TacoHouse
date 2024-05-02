package com.example.tacohouse.services;

import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.uses.EditForm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public UserService(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public User updateUserFromEditForm(User user, EditForm editForm){
        if(editForm.getNewUsername() != null && !editForm.getNewUsername().isEmpty()) {
            user.setUsername(editForm.getNewUsername());
        }
        if(editForm.getNewPassword() != null && !editForm.getNewPassword().isEmpty()){
            user.setPassword(passwordEncoder.encode(editForm.getNewPassword()));
        }
        if(editForm.getNewFullName() != null && !editForm.getNewFullName().isEmpty()){
            user.setFullName(editForm.getNewFullName());
        }
        if(editForm.getNewStreet() != null && !editForm.getNewStreet().isEmpty()){
            user.setStreet(editForm.getNewStreet());
        }
        if(editForm.getNewCity() != null && !editForm.getNewCity().isEmpty()){
            user.setCity(editForm.getNewCity());
        }
        if(editForm.getNewState() != null && !editForm.getNewState().isEmpty()){
            user.setState(editForm.getNewState());
        }
        if(editForm.getNewZip() != null && !editForm.getNewZip().isEmpty()){
            user.setZip(editForm.getNewZip());
        }
        if(editForm.getNewPhoneNumber() != null && !editForm.getNewPhoneNumber().isEmpty()){
            user.setPhoneNumber(editForm.getNewPhoneNumber());
        }

        return user;
    }


    public EditForm updateEditFormFromUser(EditForm editForm, User user){
        if(user.getUsername() != null){
            editForm.setNewUsername(user.getUsername());
        }
        if (user.getFullName() != null) {
            editForm.setNewFullName(user.getFullName());
        }
        if (user.getStreet() != null) {
            editForm.setNewStreet(user.getStreet());
        }
        if (user.getCity() != null) {
            editForm.setNewCity(user.getCity());
        }
        if (user.getState() != null) {
            editForm.setNewState(user.getState());
        }
        if (user.getZip() != null) {
            editForm.setNewZip(user.getZip());
        }
        //editForm.setCurrentPassword("");

        return editForm;

    }

    public User updateUserFromOrder(User user, TacoOrder tacoOrder) { //if user has empty fields when placing order, it sets when placing order
        if (user.getStreet() == null) {
            user.setStreet(tacoOrder.getDeliveryStreet());
        }
        if (user.getCity() == null) {
            user.setCity(tacoOrder.getDeliveryCity());
        }
        if (user.getState() == null) {
            user.setState(tacoOrder.getDeliveryState());
        }
        if (user.getZip() == null) {
            user.setZip(tacoOrder.getDeliveryZip());
        }
        return user;
    }


    public TacoOrder updateOrderFromUser(TacoOrder tacoOrder, User user){
        if (user.getStreet() != null) {
            tacoOrder.setDeliveryStreet(user.getStreet());
        }
        if (user.getCity() != null) {
            tacoOrder.setDeliveryCity(user.getCity());
        }
        if (user.getState() != null) {
            tacoOrder.setDeliveryState(user.getState());
        }
        if (user.getZip() != null) {
            tacoOrder.setDeliveryZip(user.getZip());
        }
        return tacoOrder;
    }

    public void updateCurrentAuthenticatedUser(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username); //gets the saved user entity via the username (could manually fetch from repo too)
        if (userDetails != null) {
            User user = (User) userDetails; //type cast as user implements userdetails and loadbyusername returns userDetails
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()); //this token const(object principal, object credentials, Collection<? extends GrantedAuthority>), could have put userdetails directly too with null password as only updating
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
