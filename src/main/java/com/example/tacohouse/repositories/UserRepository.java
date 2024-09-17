package com.example.tacohouse.repositories;

import com.example.tacohouse.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
    List<User> findAll();
}
