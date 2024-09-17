package com.example.tacohouse.controllers;

import com.example.tacohouse.components.SessionScopedTacoOrderManager;
import com.example.tacohouse.entities.Ingredient;
import com.example.tacohouse.entities.Taco;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.repositories.IngredientRepository;
import com.example.tacohouse.repositories.TacoRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/taco/design")
public class DesignTacoController {
    private IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;
    private SessionScopedTacoOrderManager sessionScopedTacoOrderManager;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository,
                                TacoRepository tacoRepository,
                                SessionScopedTacoOrderManager sessionScopedTacoOrderManager) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
        this.sessionScopedTacoOrderManager = sessionScopedTacoOrderManager;
    }





    @GetMapping
    public String showDesignTacoForm() {
        return "designTaco";
    }


    @PostMapping
    public String processTaco(@Valid Taco taco,Errors errors) {
        if(errors.hasErrors()){
            return "designTaco";
        }

        TacoOrder tacoOrder = sessionScopedTacoOrderManager.getTacoOrder();

        taco.setCreatedAt(LocalDateTime.now());

        Taco savedTaco = tacoRepository.save(taco); //we save the taco as @Id is auto incremented. If not saved tacoOrder objects had null id for child entity and was giving error (null tacoOrder list) when saving TacoOrder

        taco.setTacoOrder(tacoOrder); //we do this after saving (if done before saving it gives error as tacoOrder field is not saved), if we save tacoOrder it cascades automatically after
        tacoOrder.addTaco(savedTaco);
        log.info("Processing taco: {} ", savedTaco);
        return "redirect:/orders/current";

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
    @ModelAttribute
    public Taco taco() {
        return new Taco();
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
