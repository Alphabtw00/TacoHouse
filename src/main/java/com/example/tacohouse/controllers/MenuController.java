package com.example.tacohouse.controllers;

import com.example.tacohouse.components.SessionScopedTacoOrderManager;
import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.entities.Taco;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.repositories.TacoRepository;
import com.example.tacohouse.services.PreMadeTacoToTacoService;
import com.example.tacohouse.services.TacoRecommendationService;
import com.example.tacohouse.uses.PreMadeTacoList;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/taco/menu")
public class MenuController {
    private final SessionScopedTacoOrderManager sessionScopedTacoOrderManager;
    private final TacoRecommendationService tacoRecommendationService;
    private final PreMadeTacoToTacoService preMadeTacoToTacoService;
    private final TacoRepository tacoRepository;

    public MenuController(SessionScopedTacoOrderManager sessionScopedTacoOrderManager,
                          TacoRecommendationService tacoRecommendationService,
                          PreMadeTacoToTacoService preMadeTacoToTacoService,
                          TacoRepository tacoRepository) {
        this.sessionScopedTacoOrderManager = sessionScopedTacoOrderManager;
        this.tacoRecommendationService = tacoRecommendationService;
        this.preMadeTacoToTacoService = preMadeTacoToTacoService;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String getMenu(){
        return "menu";
    }

    @PostMapping
    public String orderMenu(@Valid PreMadeTacoList preMadeTacoList, Errors errors){

        if(errors.hasErrors()){
            return "menu";
        }
        TacoOrder tacoOrder = sessionScopedTacoOrderManager.getTacoOrder();
        List<Taco> tacos = preMadeTacoToTacoService.convert(preMadeTacoList);


        List<Taco> tacoList = new ArrayList<>();

        for (Taco taco : tacos) { //all tacos saved first, then added to tacoOrder
            taco.setCreatedAt(LocalDateTime.now());
            Taco savedTaco = tacoRepository.save(taco);
            tacoList.add(savedTaco);
            log.info("Processing taco: {}", savedTaco);

        }

        for (Taco taco : tacoList) {  //made 2 different loops cause it was giving error if we have more than once premade taco selected at once
            taco.setTacoOrder(tacoOrder);
            tacoOrder.addTaco(taco);
        }

        return "redirect:/orders/current";
    }



    @ModelAttribute
    public void addTacoToModel(Model model){
        Map<PreMadeTaco, Boolean> preMadeTacos = tacoRecommendationService.getPreMadeTacosWithRecommendations();
        model.addAttribute("preMadeTacos",preMadeTacos);
    }




    @ModelAttribute
    public PreMadeTacoList preMadeTacoList(){
        return new PreMadeTacoList();
    }
}
