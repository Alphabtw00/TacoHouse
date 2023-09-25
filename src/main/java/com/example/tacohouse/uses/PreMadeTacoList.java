package com.example.tacohouse.uses;

import com.example.tacohouse.entities.PreMadeTaco;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
@Data
public class PreMadeTacoList {
    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<PreMadeTaco> preMadeTacos;
}
