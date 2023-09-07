package com.example.tacohouse.controllers;

import com.example.tacohouse.model.Ingredient;
import com.example.tacohouse.model.Taco;
import com.example.tacohouse.model.TacoOrder;
import com.example.tacohouse.repositories.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    private IngredientRepository ingredientRepository;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        Iterable<Ingredient> tempo = ingredientRepository.findAll();
        List<Ingredient> ingredients = new ArrayList<>();
        tempo.forEach(ingredients::add);
        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type: types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredients , type));
        }
    }
    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    @PostMapping
    public String processTaco(@Valid Taco taco,Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if(errors.hasErrors()){
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }
    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
