package com.example.tacohouse.components;

import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.repositories.PreMadeTacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PreMadeTacoByIdConverter implements Converter<String, PreMadeTaco> {
    private PreMadeTacoRepository preMadeTacoRepository;
    @Autowired
    public PreMadeTacoByIdConverter(PreMadeTacoRepository preMadeTacoRepository) {
        this.preMadeTacoRepository = preMadeTacoRepository;
    }


    @Override
    public PreMadeTaco convert(String id) {  //whenever form is submitted, instead of returning string ids it returns preMadeTaco
        return preMadeTacoRepository.findById(id).orElse(null);
    }
}
