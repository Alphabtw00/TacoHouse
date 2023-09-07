package com.example.tacohouse.model;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED,force = true)
@RequiredArgsConstructor
//@RestResource(rel="ingredients", path="ingredients")

public class Ingredient {
    @Id
    private final String id;
    private final String name;
    @Enumerated(EnumType.STRING)
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
