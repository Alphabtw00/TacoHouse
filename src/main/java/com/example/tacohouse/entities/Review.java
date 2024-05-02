package com.example.tacohouse.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime placedAt;
    @NotBlank(message = "Review Required")
    private String comment;
    @Min(value = 1, message = "Must give a rating")
    private int rating;
    @ManyToOne
    private User user;
    @OneToOne
    private TacoOrder tacoOrder;

}
