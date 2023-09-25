package com.example.tacohouse.controllers;

import com.example.tacohouse.components.SessionScopedTacoOrderManager;
import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.entities.Taco;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.repositories.PreMadeTacoRepository;
import com.example.tacohouse.services.PreMadeTacoToTacoService;
import com.example.tacohouse.uses.PreMadeTacoList;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {
    private SessionScopedTacoOrderManager sessionScopedTacoOrderManager;
    private final PreMadeTacoRepository preMadeTacoRepository;
    private final PreMadeTacoToTacoService preMadeTacoToTacoService;
    public MenuController(PreMadeTacoRepository preMadeTacoRepository, PreMadeTacoToTacoService preMadeTacoToTacoService,
                          SessionScopedTacoOrderManager sessionScopedTacoOrderManager){
        this.preMadeTacoRepository=preMadeTacoRepository;
        this.preMadeTacoToTacoService=preMadeTacoToTacoService;
        this.sessionScopedTacoOrderManager=sessionScopedTacoOrderManager;
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
        TacoOrder tacoOrder=sessionScopedTacoOrderManager.getTacoOrder();
        List<Taco> tacos = preMadeTacoToTacoService.convert(preMadeTacoList);
        tacos.forEach(tacoOrder::addTaco);
        if(tacos.size()==1){
            log.info("Processing taco: {}" , tacos);
        }
        else {
            log.info("Processing tacos: {}",tacos);
        }
        return "redirect:/orders/current";
    }



    @ModelAttribute
    public void addTacoToModel(Model model){
        Iterable<PreMadeTaco> tempo= preMadeTacoRepository.findAll();
        List<PreMadeTaco> preMadeTacos = new ArrayList<>();
        tempo.forEach(preMadeTacos::add);
        model.addAttribute("preMadeTacos",preMadeTacos);
    }
    @ModelAttribute
    public PreMadeTaco preMadeTaco(){
        return new PreMadeTaco();
    }
    @ModelAttribute
    public PreMadeTacoList preMadeTacoList(){
        return new PreMadeTacoList();
    }
}
