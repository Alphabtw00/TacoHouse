package com.example.tacohouse.repositories;

import com.example.tacohouse.entities.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<TacoOrder,String> {


    @Transactional
    public TacoOrder save(TacoOrder tacoOrder);

}
