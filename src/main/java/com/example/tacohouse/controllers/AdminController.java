package com.example.tacohouse.controllers;


import com.example.tacohouse.entities.Review;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.entities.User;
import com.example.tacohouse.repositories.OrderRepository;
import com.example.tacohouse.repositories.ReviewRepository;
import com.example.tacohouse.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public AdminController(UserRepository userRepository, OrderRepository orderRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("/dashboard")
    public String getAdminDashboard(Model model){
        model.addAttribute("totalUsersInNumbers", userRepository.count());
        model.addAttribute("totalOrdersInNumbers", orderRepository.count());
        model.addAttribute("totalReviewsInNumbers", reviewRepository.count());
        return "adminDashboard";
    }



    @GetMapping("/users")
    public String getAdminUsers(){
        return "adminUsers";
    }

    @GetMapping("/users/{userId}/view")
    public String viewUser(@PathVariable Long userId){
        return "dd";
    }


    @GetMapping("/users/{userId}/edit")
    public String editUser(@PathVariable Long userId){
        return "ss";
    }


    @PostMapping("/users/{userId}/delete")
    public String deleteUser(@PathVariable Long userId){
        return ";;";
    }


    @GetMapping("/orders")
    public String getAdminOrders(){
        return "adminOrders";
    }
    @GetMapping("/reviews")
    public String getAdminReviews(){
        return "adminReviews";
    }
    @GetMapping("contact")
    public String getAdminContactTickets(){
        return "adminContact";
    }



    @ModelAttribute("orderListWithLimit")
    public List<TacoOrder> orderListWithLimit(){
        List<TacoOrder> tacoOrderList = orderRepository.findAll();
        List<TacoOrder> tacoOrderListWithLimit = tacoOrderList.stream()
                .sorted(Comparator.comparing(TacoOrder::getPlacedAt).reversed())
                .limit(3)
                .collect(Collectors.toList()); //can  use .toList() but it sends a immutable list
        return tacoOrderListWithLimit;
    }

    @ModelAttribute("reviewListWithLimit")
    public List<Review> reviewListWithLimit() {
        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream()
                .sorted(Comparator.comparing(Review::getPlacedAt).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }


    @ModelAttribute("userList")
    public List<User> userList(){
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
