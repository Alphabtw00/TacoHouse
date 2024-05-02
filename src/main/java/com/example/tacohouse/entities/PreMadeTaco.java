package com.example.tacohouse.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class PreMadeTaco {
    @Id
    private String id;
    private String name;


    @ManyToMany
    @JoinTable(
            name = "Ingredient_Pre_Made_Taco_Ref",
            joinColumns = @JoinColumn(name = "pre_made_taco_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;


    public PreMadeTaco(String id,String name,List<Ingredient> ingredients){
        this.id=id;
        this.name=name;
        this.ingredients=ingredients;
    }
}
