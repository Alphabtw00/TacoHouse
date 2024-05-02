package com.example.tacohouse.components;

import com.example.tacohouse.repositories.IngredientRepository;
import com.example.tacohouse.entities.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> { //
    private IngredientRepository ingredientRepository;
    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }


    @Override
    public Ingredient convert(String id) { //whenever form is submitted, instead of returning string ids it returns ingredients
        return ingredientRepository.findById(id).orElse(null);
    }
}
