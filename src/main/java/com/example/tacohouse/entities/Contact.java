package com.example.tacohouse.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
//@JsonIgnoreProperties(value = {"user"}) //to ignore user object to be serialized and deserialized
public class Contact {
    @Id
    private String id;
    private LocalDateTime placedAt;
    @NotBlank(message = "Name Required!")
    private String name;
    @NotBlank(message = "Email Required!")
    private String email;

    @NotBlank(message = "Subject Required!")
    private String subject;

    @NotBlank(message = "Message Required!")
    private String message;

    @Min(value = 1, message = "Pls select Urgency Level")
    private int urgencyLevel;
    //@JsonIgnore
    @ManyToOne
    private User user;

}
