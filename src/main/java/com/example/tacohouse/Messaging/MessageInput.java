package com.example.tacohouse.Messaging;

import com.example.tacohouse.entities.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;


@Slf4j
@Configuration
public class MessageInput {

    private final ObjectMapper objectMapper;

    public MessageInput(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public Consumer<String> consumer() {
        return contact -> {

            try {
                // Deserialize the JSON string into a Contact object
                Contact receivedContact = objectMapper.readValue(contact, Contact.class);
                log.info("""
                        Message received for contact with Id : {}\s
                        Made by : {}\s
                        Subject: {}\s
                        Comment: {}""",
                        receivedContact.getId(), receivedContact.getName(), receivedContact.getSubject(), receivedContact.getMessage());
            } catch (Exception e) {
                log.error("Error deserializing JSON to Contact object: {}", e.getMessage());
            }
        };
    }
}
