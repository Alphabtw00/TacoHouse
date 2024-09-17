package com.example.tacohouse.entities;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity //needs a noArgConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED,force = true)
@RequiredArgsConstructor //needed if we wanna use ingredients() with fields anywhere else //if final fields, then @data makes a requiredArgCons itself if no constructor is defined
//@RestResource(rel="ingredients", path="ingredients")
public class Ingredient {
    @Id
    private final String id;
    private final String name;
    @Enumerated(EnumType.STRING) //saving enum as string to not get errors
    private final Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE, EXTRA
    }
}
