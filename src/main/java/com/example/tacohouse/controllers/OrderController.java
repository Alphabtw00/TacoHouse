package com.example.tacohouse.controllers;

import com.example.tacohouse.components.SessionScopedTacoOrderManager;
import com.example.tacohouse.entities.Review;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.OrderRepository;
import com.example.tacohouse.repositories.ReviewRepository;
import com.example.tacohouse.repositories.UserRepository;
import com.example.tacohouse.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final SessionScopedTacoOrderManager sessionScopedTacoOrderManager;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public OrderController(SessionScopedTacoOrderManager sessionScopedTacoOrderManager,
                           OrderRepository orderRepository,
                           ReviewRepository reviewRepository,
                           UserRepository userRepository,
                           UserService userService) {
        this.sessionScopedTacoOrderManager = sessionScopedTacoOrderManager;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping
    public String getReview(@AuthenticationPrincipal User user, Model model) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        TacoOrder tacoOrder = orderRepository.findById(sessionScopedTacoOrderManager.getTacoOrder().getId()).orElse(null);

        if (tacoOrder != null) {
            Review review = reviewRepository.findReviewByUserAndTacoOrder(userFromDb, tacoOrder);

            if (review != null) {
                model.addAttribute("reviewSubmitted", true);
            }
            return "review";
        }
        model.addAttribute("orderNotSubmitted", true);
        return "review";
    }

    @PostMapping
    public String saveReview(@Valid Review review, Errors errors, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "review";
        }
        User currentUser = userRepository.findByUsername(user.getUsername());
        review.setPlacedAt(LocalDateTime.now());
        review.setUser(currentUser);
        review.setTacoOrder(orderRepository.findById(sessionScopedTacoOrderManager.getTacoOrder().getId()).get());

        reviewRepository.save(review);

        log.info("Review saved:\n By: " + review.getUser().getFullName() + "\n Comment: " + review.getComment()
                + "\n Rating: " + review.getRating());
        return "redirect:/orders";
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @Transactional
    //to make the whole method in session (before it ended after saving in repo and was giving error as i was loading lazily in the next line)
    @PostMapping("/current")
    public String processOrder(@ModelAttribute("order") @Valid TacoOrder order, Errors errors,
                               @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        User currentUser = userRepository.findByUsername(user.getUsername());

        order.setUser(currentUser);
        order.setPlacedAt(LocalDateTime.now());

        TacoOrder tacoOrder = orderRepository.save(order);

        currentUser.addTacoOrder(tacoOrder); //lazily after saving to used @transactional or made eager

        userService.updateUserFromOrder(currentUser, tacoOrder);
        userRepository.save(currentUser);

        // user.getTacoOrders().size(); //to initialize the collection as it was loading lazily after ending the session ( saved in repo)
        // and giving error (using transaction on this method (could use both), or just eager the orderList.
        // sent it before saving so no need of transactional
        log.info("Order submitted: {}", tacoOrder);
        //redirectAttributes.addFlashAttribute("orderId", tacoOrder.getId());
        return "redirect:/orders";
    }


    @ModelAttribute("order")
    public TacoOrder tacoOrder(@AuthenticationPrincipal User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        TacoOrder tacoOrder = userService.updateOrderFromUser(sessionScopedTacoOrderManager.getTacoOrder(), currentUser);
        return tacoOrder;
    }

    @ModelAttribute
    public Review reviews() {
        return new Review();
    }

    @ModelAttribute("currentUser")
    public User user(@AuthenticationPrincipal User user) {
        User currentUser = userRepository.findByUsername(user.getUsername());
        return currentUser;
    }
}
