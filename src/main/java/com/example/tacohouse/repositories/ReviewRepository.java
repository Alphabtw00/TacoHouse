package com.example.tacohouse.repositories;

import com.example.tacohouse.entities.Review;
import com.example.tacohouse.entities.TacoOrder;
import com.example.tacohouse.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Long> {
    public Review findReviewByUserAndTacoOrder(User user, TacoOrder tacoOrder);

}
