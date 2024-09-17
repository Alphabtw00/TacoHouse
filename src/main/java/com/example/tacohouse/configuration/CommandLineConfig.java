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
            IngredientRepository ingredientRepository, PreMadeTacoRepository preMadeTacoRepository) {
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
            Ingredient scrambledEggs = new Ingredient(
                    "SCEGG", "Scrambled Eggs", Ingredient.Type.PROTEIN);
            Ingredient bacon = new Ingredient(
                    "BACON", "Bacon", Ingredient.Type.PROTEIN);
            Ingredient chickenBreast = new Ingredient(
                    "CHBR", "Chicken Breast", Ingredient.Type.PROTEIN);
            Ingredient quinoa = new Ingredient(
                    "QUIN", "Quinoa", Ingredient.Type.PROTEIN);
            Ingredient grilledFish = new Ingredient(
                    "GRFI", "Grilled Fish", Ingredient.Type.PROTEIN);
            Ingredient herbs = new Ingredient(
                    "HERB", "Fresh Herbs", Ingredient.Type.VEGGIES);
            Ingredient portobelloMushrooms = new Ingredient(
                    "PORM", "Portobello Mushrooms", Ingredient.Type.VEGGIES);
            Ingredient zucchini = new Ingredient(
                    "ZUCC", "Zucchini", Ingredient.Type.VEGGIES);
            Ingredient spinach = new Ingredient(
                    "SPIN", "Spinach", Ingredient.Type.VEGGIES);
            Ingredient ribeye = new Ingredient(
                    "RBEY", "Ribeye Steak", Ingredient.Type.PROTEIN);
            Ingredient mushrooms = new Ingredient(
                    "MUSH", "Mushrooms", Ingredient.Type.VEGGIES);

            ingredientRepository.saveAll(Arrays.asList(
                    flourTortilla, cornTortilla, groundBeef, carnitas, tomatoes, lettuce,
                    cheddar, jack, salsa, sourCream, guacamole, jalapenos, blackBeans, rice,
                    avocado, scrambledEggs, bacon, chickenBreast, quinoa, grilledFish, herbs,
                    portobelloMushrooms, zucchini, spinach, ribeye, mushrooms
            ));

            preMadeTacoRepository.saveAll(Arrays.asList(
                    new PreMadeTaco("BFTS", "Breakfast Taco Supreme", Arrays.asList(flourTortilla, scrambledEggs, bacon, cheddar, salsa), PreMadeTaco.MealTime.BREAKFAST, Arrays.asList(PreMadeTaco.Season.ALL)),
                    new PreMadeTaco("AVEF", "Avocado Egg Fiesta", Arrays.asList(cornTortilla, scrambledEggs, avocado, tomatoes, salsa), PreMadeTaco.MealTime.BREAKFAST, Arrays.asList(PreMadeTaco.Season.SPRING, PreMadeTaco.Season.SUMMER)),
                    new PreMadeTaco("LBVT", "Lunch Beef & Veggie Taco", Arrays.asList(flourTortilla, groundBeef, lettuce, tomatoes, guacamole), PreMadeTaco.MealTime.LUNCH, Arrays.asList(PreMadeTaco.Season.ALL)),
                    new PreMadeTaco("VQLT", "Veggie Quinoa Lunch Taco", Arrays.asList(cornTortilla, quinoa, blackBeans, lettuce, salsa), PreMadeTaco.MealTime.LUNCH, Arrays.asList(PreMadeTaco.Season.SPRING, PreMadeTaco.Season.SUMMER)),
                    new PreMadeTaco("CHDT", "Chicken Dinner Taco", Arrays.asList(flourTortilla, chickenBreast, cheddar, lettuce, sourCream), PreMadeTaco.MealTime.DINNER, Arrays.asList(PreMadeTaco.Season.ALL)),
                    new PreMadeTaco("CADT", "Carnitas Avocado Dinner Taco", Arrays.asList(cornTortilla, carnitas, avocado, tomatoes, salsa), PreMadeTaco.MealTime.DINNER, Arrays.asList(PreMadeTaco.Season.FALL, PreMadeTaco.Season.WINTER)),
                    new PreMadeTaco("FHBT", "Fish & Herb Breakfast Taco", Arrays.asList(cornTortilla, grilledFish, herbs, tomatoes, salsa), PreMadeTaco.MealTime.BREAKFAST, Arrays.asList(PreMadeTaco.Season.SPRING, PreMadeTaco.Season.SUMMER)),
                    new PreMadeTaco("SWLT", "Southwest Lunch Taco", Arrays.asList(flourTortilla, groundBeef, blackBeans, cheddar, jalapenos), PreMadeTaco.MealTime.LUNCH, Arrays.asList(PreMadeTaco.Season.FALL, PreMadeTaco.Season.WINTER)),
                    new PreMadeTaco("VDDT", "Veggie Delight Dinner Taco", Arrays.asList(cornTortilla, portobelloMushrooms, zucchini, spinach, guacamole), PreMadeTaco.MealTime.DINNER, Arrays.asList(PreMadeTaco.Season.SPRING, PreMadeTaco.Season.SUMMER)),
                    new PreMadeTaco("STMT", "Steak & Mushroom Taco", Arrays.asList(flourTortilla, ribeye, mushrooms, jack, lettuce), PreMadeTaco.MealTime.DINNER, Arrays.asList(PreMadeTaco.Season.FALL, PreMadeTaco.Season.WINTER))
            ));

        };
    }
}