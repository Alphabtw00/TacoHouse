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

    @ManyToMany                                                                  //Taco is owning side as it manages the relation
    @JoinTable(                                                                 //to name the extra table made by list elements
            name = "ingredient_Taco_Ref",
            joinColumns = @JoinColumn(name = "taco_id"),                       //joinColumns expects the column to be of the owning side, @JoinColumn add that column, by default it adds the primary key column of the mapped entity(owning in this case)
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "taco_order_id") //@joinColumn is used to specify foreign key, name is to set the name of the extra column made (by default it looks for the primary key of the the collection it references(mappedBy), to change use referredColumnName= and it will map that column instead
    @ToString.Exclude // to remove circular dependency when we log it
    private TacoOrder tacoOrder;

    public Taco(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }
}
