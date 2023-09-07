package com.example.tacohouse.repositories;

import com.example.tacohouse.model.TacoOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder,Long> {
    @Transactional
    TacoOrder save(TacoOrder order);
}
