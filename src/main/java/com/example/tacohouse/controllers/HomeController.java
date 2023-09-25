package com.example.tacohouse.controllers;

import com.example.tacohouse.entities.Review;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.ReviewRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ReviewRepository reviewRepository;
    public HomeController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @GetMapping
    public String home(Model model,@AuthenticationPrincipal User user){
        if(user!=null){
            model.addAttribute("profileName","Profile");
        }
        else {
            model.addAttribute("signIn","Sign In");
        }
        return "home";
    }



    @ModelAttribute
    public void addReviewsToModel(Model model){
        Iterable<Review> tempo = reviewRepository.findAll();
        List<Review> reviews = new ArrayList<>();
        tempo.forEach(reviews::add);
        model.addAttribute("reviews",reviews);
    }
}
