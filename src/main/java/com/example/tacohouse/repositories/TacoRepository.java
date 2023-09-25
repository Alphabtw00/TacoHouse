package com.example.tacohouse.repositories;

import com.example.tacohouse.entities.Taco;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends CrudRepository<Taco,Long> {
    Page<Taco> findAll(Pageable pageable);
}
