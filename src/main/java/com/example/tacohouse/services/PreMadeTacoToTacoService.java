package com.example.tacohouse.services;

import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.entities.Taco;
import com.example.tacohouse.uses.PreMadeTacoList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreMadeTacoToTacoService {
    public List<Taco> convert(PreMadeTacoList preMadeTacoList){
        List<PreMadeTaco> preMadeTacos = preMadeTacoList.getPreMadeTacos();
        List<Taco> tacos = new ArrayList<>();
        for(PreMadeTaco taco :  preMadeTacos){
            tacos.add(new Taco(taco.getName(),taco.getIngredients()));
        }
        return tacos;
    }
}
