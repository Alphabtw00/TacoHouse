package com.example.tacohouse.controllers;

import com.example.tacohouse.entities.Contact;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.ContactRepository;
import com.example.tacohouse.repositories.UserRepository;
import com.example.tacohouse.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/contact")
public class ContactController {
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final MessageService messageService;

    public ContactController(UserRepository userRepository, ContactRepository contactRepository, MessageService messageService) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.messageService = messageService;
    }

    @GetMapping
    public String getContactForm(){
        return "contact";
    }

    @PostMapping
    public String submitContactForm(Contact contact, Errors errors, @AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "contact";
        }

        contact.setId(UUID.randomUUID().toString());
        contact.setPlacedAt(LocalDateTime.now());
        contact.setUser(userRepository.findByUsername(user.getUsername()));

        Contact savedContact = contactRepository.save(contact);

        messageService.sendContactMessage("contact", savedContact);

        return "redirect:/home";
    }

    @ModelAttribute
    public Contact contact(){
        return new Contact();
    }
}
