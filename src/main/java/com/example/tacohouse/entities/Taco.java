package com.example.tacohouse.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor //if we make a constructor @data wont make a noArgConstructor iteelf, so we define it
//@RestResource(rel="tacos", path="tacos")
public class Taco{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    @NotBlank
    @Size(min = 5, message = "Name must be at least 5 characters long")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "ingredient_Taco_Ref",
            joinColumns = @JoinColumn(name = "taco_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "taco_order_id")
    @ToString.Exclude
    private TacoOrder tacoOrder;

    public Taco(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
