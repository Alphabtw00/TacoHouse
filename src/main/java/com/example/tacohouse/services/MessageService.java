package com.example.tacohouse.services;

import com.example.tacohouse.entities.Contact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MessageService {
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    public MessageService(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public void sendContactMessage(String bindingName, Contact contact){
        log.info("Sending kafka message for contact id: {}, made by {}", contact.getId(), contact.getName());
        // Serialize the Contact object to JSON before sending
        try {
            String jsonContact = objectMapper.writeValueAsString(contact);
            streamBridge.send(bindingName, jsonContact);
        } catch (JsonProcessingException e) {
            log.error("Error serializing Contact object to JSON: {}", e.getMessage());
        }
    }


}
