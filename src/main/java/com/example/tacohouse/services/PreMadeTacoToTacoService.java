package com.example.tacohouse.services;

import com.example.tacohouse.entities.Ingredient;
import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.entities.Taco;
import com.example.tacohouse.repositories.IngredientRepository;
import com.example.tacohouse.uses.PreMadeTacoList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreMadeTacoToTacoService {

    IngredientRepository ingredientRepository;

    public PreMadeTacoToTacoService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Taco> convert(PreMadeTacoList preMadeTacoList) {
        List<PreMadeTaco> preMadeTacos = preMadeTacoList.getPreMadeTacos();
        List<Taco> tacos = new ArrayList<>();  //making new ingredient clone objects so we dont get shared instances error every new order or more than one taco
        for (PreMadeTaco preMadeTaco : preMadeTacos) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (Ingredient ingredient : preMadeTaco.getIngredients()) {
                ingredients.add(ingredientRepository.findById(ingredient.getId()).orElse(null));  //adding new instances of ingredients in new tacos made via preMAadeTaco (was giving 2 shared instance error)
            }
            tacos.add(new Taco(preMadeTaco.getName(), ingredients));
        }
        return tacos;
    }
}
