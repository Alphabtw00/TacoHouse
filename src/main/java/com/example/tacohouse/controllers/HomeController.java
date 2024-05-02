package com.example.tacohouse.controllers;

import com.example.tacohouse.components.SessionScopedTacoOrderManager;
import com.example.tacohouse.entities.PreMadeTaco;
import com.example.tacohouse.entities.Review;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.OrderRepository;
import com.example.tacohouse.repositories.PreMadeTacoRepository;
import com.example.tacohouse.repositories.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class HomeController {
    private final ReviewRepository reviewRepository;
    private final SessionScopedTacoOrderManager sessionScopedTacoOrderManager;
    private final PreMadeTacoRepository preMadeTacoRepository;
    private OrderRepository orderRepository;

    public HomeController(ReviewRepository reviewRepository,
                          SessionScopedTacoOrderManager sessionScopedTacoOrderManager,
                          PreMadeTacoRepository preMadeTacoRepository,
                          OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.sessionScopedTacoOrderManager = sessionScopedTacoOrderManager;
        this.preMadeTacoRepository = preMadeTacoRepository;
        this.orderRepository = orderRepository;
    }



    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal User user){
        if(user!=null){
            model.addAttribute("profileName","Profile");
        }
        else {
            model.addAttribute("signIn","Sign In");
        }


        TacoOrder currentOrder = sessionScopedTacoOrderManager.getTacoOrder();
        if(!(currentOrder.getTacos().isEmpty()) && !(orderRepository.existsById(currentOrder.getId()))){
            model.addAttribute("goBackToOrderPage", "Attention!" + "You have an unsubmitted taco order.");
        }

        if(orderRepository.existsById(currentOrder.getId())){
            sessionScopedTacoOrderManager.makeNewTacoOrder();
        }

        return "home";
    }



    @GetMapping("/")
    public String home(){
        return "redirect:/home";
    }



    @ModelAttribute
    public void addReviewsToModel(Model model){
        Iterable<Review> tempo = reviewRepository.findAll();
        List<Review> reviews = new ArrayList<>();
        tempo.forEach(reviews::add);

        List<Review> sortedReviews = reviews.stream()
                .sorted(Comparator.comparing(Review::getPlacedAt).reversed())
                .collect(Collectors.toList()); //get all reviews then sort via date and then reverse so latest reviews are at firsts

        List<Review> latestReviews = sortedReviews.stream().limit(3).collect(Collectors.toList()); //we select 3 latest reviews so only 3 reviews are in show at once
        model.addAttribute("reviews",latestReviews);
    }

    @ModelAttribute
    public void addTacoToModel(Model model){
        Iterable<PreMadeTaco> tempo= preMadeTacoRepository.findAll();
        List<PreMadeTaco> preMadeTacos = new ArrayList<>();
        tempo.forEach(preMadeTacos::add);
        model.addAttribute("preMadeTacos",preMadeTacos);
    }
}
