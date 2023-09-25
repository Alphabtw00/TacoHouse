package com.example.tacohouse.controllers;

import com.example.tacohouse.components.SessionScopedTacoOrderManager;
import com.example.tacohouse.entities.Review;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.OrderRepository;
import com.example.tacohouse.repositories.ReviewRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final SessionScopedTacoOrderManager sessionScopedTacoOrderManager;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    public OrderController(OrderRepository orderRepository, ReviewRepository reviewRepository,SessionScopedTacoOrderManager sessionScopedTacoOrderManager){
        this.orderRepository=orderRepository;
        this.reviewRepository = reviewRepository;
        this.sessionScopedTacoOrderManager=sessionScopedTacoOrderManager;
    }


    @GetMapping
    public String returnOrder(){
        return "return";
    }
    @PostMapping
    public String saveReview(@Valid Review review,Errors errors,@AuthenticationPrincipal User user){
        if(errors.hasErrors()){
            return "return";
        }
        review.setUser(user);
        reviewRepository.save(review);
        log.info("Review saved:\n By: " + review.getUser().getFullName() + "\n Comment: " + review.getComment()
        + "\n Rating: " + review.getRating());
        return "return";
    }
    @GetMapping("/current")
    public String orderForm(){
        return "orderForm";
    }
    @Transactional
    @PostMapping("/current")
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if(errors.hasErrors()){
            return "orderForm";
        }
        order.setUser(user);
        TacoOrder tacoOrder = orderRepository.save(order);
       // user.getTacoOrders().size(); //to initialize the collection as it was loading lazily
        // and giving error(using transaction on this method (could use both)
        user.addTacoOrder(order);
        log.info("Order submitted: {}", tacoOrder);
        // redirectAttributes.addFlashAttribute("order",order);
        return "redirect:/orders";
    }


    @ModelAttribute("tacoOrder")
    public TacoOrder tacoOrder(){
        return sessionScopedTacoOrderManager.getTacoOrder();
    }
    @ModelAttribute
    public Review reviews(){
        return new Review();
    }
}
