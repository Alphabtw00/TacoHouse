package com.example.tacohouse.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Review Required")
    private String comment;
    @NotNull(message = "Must check one star")
    private int rating;
    @ManyToOne
    private User user;
}
