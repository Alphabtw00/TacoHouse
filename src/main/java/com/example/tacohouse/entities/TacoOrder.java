package com.example.tacohouse.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Getter
@Setter
//@RestResource(rel="orders", path="orders")
public class TacoOrder{
    @Id //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private final String id = UUID.randomUUID().toString();
    private LocalDateTime placedAt;
    @NotBlank
    private String deliveryStreet;
    @NotBlank(message = "City is required")
    private String deliveryCity;
    @NotBlank(message = "State is required")
    private String deliveryState;
    @NotBlank(message = "Zip Code is required")
    private String deliveryZip;
    @CreditCardNumber(message = "Invalid Credit Card Number")
    private String ccNumber;
    @Pattern(regexp = "^(0[1-9]|1[0-2])/20(2[3-9]|[3-9][0-9])$",message = "Must be of format MM/YYYY")
    private String ccExpiration;
    @NotNull
    @Digits(integer=3, fraction=0, message="Invalid CVV")
    private String ccCVV;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "tacoOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Taco> tacos = new ArrayList<>();
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }

}
