package com.example.tacohouse.services;


import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.repositories.PreMadeTacoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TacoRecommendationService {
    private final PreMadeTacoRepository preMadeTacoRepository;

    public TacoRecommendationService(PreMadeTacoRepository preMadeTacoRepository) {
        this.preMadeTacoRepository = preMadeTacoRepository;
    }

    public Map<PreMadeTaco, Boolean> getPreMadeTacosWithRecommendations() {
        Map<PreMadeTaco, Boolean> recommendationMap = new HashMap<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        PreMadeTaco.MealTime currentMealTimeType = getMealTimeFromHour(currentDateTime.getHour());
        PreMadeTaco.Season currentSeasonType = getSeasonFromMonth(currentDateTime.getMonthValue());

        Iterable<PreMadeTaco> tempo= preMadeTacoRepository.findAll();
        List<PreMadeTaco> preMadeTacos = new ArrayList<>();
        tempo.forEach(preMadeTacos::add);

        for (PreMadeTaco preMadeTaco : preMadeTacos) {
            boolean isRecommended = preMadeTaco.getMealTime() == currentMealTimeType &&
                    preMadeTaco.getSeasons().contains(currentSeasonType);

            recommendationMap.put(preMadeTaco, isRecommended);
        }

        return recommendationMap;
    }

    private PreMadeTaco.MealTime getMealTimeFromHour(int hour) { //returns current time
        if (hour >= 5 && hour < 11) {
            return PreMadeTaco.MealTime.BREAKFAST;
        } else if (hour >= 11 && hour < 16) {
            return PreMadeTaco.MealTime.LUNCH;
        } else {
            return PreMadeTaco.MealTime.DINNER;
        }
    }

    private PreMadeTaco.Season getSeasonFromMonth(int month) { //returns current season
        switch (month) {
            case 12:
            case 1:
            case 2:
                return PreMadeTaco.Season.WINTER;
            case 3:
            case 4:
            case 5:
                return PreMadeTaco.Season.SPRING;
            case 6:
            case 7:
            case 8:
                return PreMadeTaco.Season.SUMMER;
            default:
                return PreMadeTaco.Season.FALL;
        }
    }
}
