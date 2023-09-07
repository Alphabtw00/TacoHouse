package com.example.tacohouse.model;

import com.example.tacohouse.model.Taco;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.CreditCardNumber;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
//@RestResource(rel="orders", path="orders")

public class TacoOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date placedAt = new Date();
    @NotBlank(message = "Delivery name is required")
    private String deliveryName;
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
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "tacoOrder",cascade = CascadeType.ALL)
    private List<Taco> tacos = new ArrayList<>();
    public void addTaco(Taco taco) {
        taco.setTacoOrder(this);
        this.tacos.add(taco);
    }
}
