package com.example.tacohouse.configuration;

import com.example.tacohouse.entities.Ingredient;
import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.repositories.IngredientRepository;
import com.example.tacohouse.repositories.PreMadeTacoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class CommandLineConfig {
    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository repo, PreMadeTacoRepository tacoRepo) {
        return args -> {
            Ingredient flourTortilla = new Ingredient(
                    "FLTO", "Flour Tortilla", Ingredient.Type.WRAP);
            Ingredient cornTortilla = new Ingredient(
                    "COTO", "Corn Tortilla", Ingredient.Type.WRAP);
            Ingredient groundBeef = new Ingredient(
                    "GRBF", "Ground Beef", Ingredient.Type.PROTEIN);
            Ingredient carnitas = new Ingredient(
                    "CARN", "Carnitas", Ingredient.Type.PROTEIN);
            Ingredient tomatoes = new Ingredient(
                    "TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES);
            Ingredient lettuce = new Ingredient(
                    "LETC", "Lettuce", Ingredient.Type.VEGGIES);
            Ingredient cheddar = new Ingredient(
                    "CHED", "Cheddar", Ingredient.Type.CHEESE);
            Ingredient jack = new Ingredient(
                    "JACK", "Monterrey Jack", Ingredient.Type.CHEESE);
            Ingredient salsa = new Ingredient(
                    "SLSA", "Salsa", Ingredient.Type.SAUCE);
            Ingredient sourCream = new Ingredient(
                    "SRCR", "Sour Cream", Ingredient.Type.SAUCE);
            Ingredient guacamole = new Ingredient(
                    "GUAC", "Guacamole", Ingredient.Type.EXTRA);
            Ingredient jalapenos = new Ingredient(
                    "JALA", "Jalapeños", Ingredient.Type.VEGGIES);
            Ingredient blackBeans = new Ingredient(
                    "BLBN", "Black Beans", Ingredient.Type.PROTEIN);
            Ingredient rice = new Ingredient(
                    "RICE", "Rice", Ingredient.Type.EXTRA);
            Ingredient avocado = new Ingredient(
                    "AVOC", "Avocado", Ingredient.Type.EXTRA);
            repo.save(flourTortilla);
            repo.save(cornTortilla);
            repo.save(groundBeef);
            repo.save(carnitas);
            repo.save(tomatoes);
            repo.save(lettuce);
            repo.save(cheddar);
            repo.save(jack);
            repo.save(salsa);
            repo.save(sourCream);
            repo.save(guacamole);
            repo.save(jalapenos);
            repo.save(blackBeans);
            repo.save(rice);
            repo.save(avocado);

            tacoRepo.save(new PreMadeTaco("CLBT","Classic Beef Taco", Arrays.asList(flourTortilla, groundBeef, cheddar, lettuce, salsa)));
            tacoRepo.save(new PreMadeTaco("SPCD","Spicy Carnitas Delight", Arrays.asList(cornTortilla, carnitas, jalapenos, lettuce, salsa)));
            tacoRepo.save(new PreMadeTaco("VEGF","Veggie Fiesta", Arrays.asList(cornTortilla, blackBeans, lettuce, tomatoes, guacamole)));
            tacoRepo.save(new PreMadeTaco("CHCS","Cheesy Chicken Supreme", Arrays.asList(flourTortilla, groundBeef, cheddar, sourCream, lettuce)));
            tacoRepo.save(new PreMadeTaco("AVCD","Avocado Delight", Arrays.asList(cornTortilla, carnitas, avocado, tomatoes, salsa)));
            tacoRepo.save(new PreMadeTaco("ZEVC","Zesty Veggie Crunch", Arrays.asList(cornTortilla, lettuce, jalapenos, tomatoes, salsa)));
            tacoRepo.save(new PreMadeTaco("DOCP","Double Cheese Pleasure", Arrays.asList(flourTortilla, cheddar, jack, sourCream, groundBeef)));
            tacoRepo.save(new PreMadeTaco("BARE","Bean and Rice Explosion", Arrays.asList(cornTortilla, blackBeans, rice, cheddar, salsa)));
            tacoRepo.save(new PreMadeTaco("SOCD","Sour Cream Dream", Arrays.asList(flourTortilla, sourCream, groundBeef, lettuce, tomatoes)));
            tacoRepo.save(new PreMadeTaco("MOJF","Monterrey Jack Fiesta", Arrays.asList(cornTortilla, carnitas, jack, lettuce, salsa)));
        };
    }
}
